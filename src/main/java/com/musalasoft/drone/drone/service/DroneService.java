package com.musalasoft.drone.drone.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.musalasoft.drone.drone.enumerations.DroneState;
import com.musalasoft.drone.drone.exception.DroneNotFoundException;
import com.musalasoft.drone.drone.exception.DroneOverweightException;
import com.musalasoft.drone.drone.exception.InsufficientBatteryException;
import com.musalasoft.drone.drone.model.BatteryAudit;
import com.musalasoft.drone.drone.model.Drone;
import com.musalasoft.drone.drone.model.PayloadBase;
import com.musalasoft.drone.drone.model.dto.BatteryLevelResponse;
import com.musalasoft.drone.drone.repository.BatteryAuditRepository;
import com.musalasoft.drone.drone.repository.DroneRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class DroneService {

  private static final String DRONE_NOT_FOUND_MSG = "Drone with ID: %d not found"; // Message
                                                                                   // template

  private final DroneRepository droneRepository;
  private final BatteryAuditRepository batteryAuditRepository;

  /**
   * Logs battery levels for all drones by creating a BatteryAudit entry for each drone in the
   * repository.
   */
  public void logBatteryLevels() {
    log.info("Starting battery level logging for all drones");
    List<Drone> drones = droneRepository.findAll();

    drones.forEach(drone -> {
      BatteryAudit audit = new BatteryAudit();
      audit.setDroneId(drone.getId());
      audit.setBatteryLevel(drone.getBatteryCapacity());
      audit.setTimestamp(LocalDateTime.now());
      batteryAuditRepository.save(audit);
      log.debug("Logged battery level for drone [ID: {}]: {}", drone.getId(),
          drone.getBatteryCapacity());
    });

    log.info("Completed battery level logging for all drones");
  }

  /**
   * Registers a new drone by setting its initial state and saving it to the repository. If payloads
   * are provided, they are loaded onto the drone.
   */
  @Transactional
  public Drone registerDrone(Drone drone) {
    log.info("Registering new drone with serial number: {}", drone.getSerialNumber());
    drone.setState(DroneState.IDLE);
    List<PayloadBase> payload = drone.getPayloads();
    drone.setPayloads(new ArrayList<>());

    drone = droneRepository.save(drone);

    if (payload != null && !payload.isEmpty()) {
      return loadDrone(payload, drone);
    }
    return drone;
  }

  /**
   * Loads a specified drone with the given list of medications.
   */
  @Transactional
  public Drone loadDroneWithMedication(Long droneId, List<PayloadBase> medications) {
    log.info("Loading drone with ID: {} with medications", droneId);
    Drone drone = droneRepository.findById(droneId)
        .orElseThrow(() -> new DroneNotFoundException(String.format(DRONE_NOT_FOUND_MSG, droneId)));
    return loadDrone(medications, drone);
  }

  /**
   * Loads the drone with a given payload list after validating.
   */
  private Drone loadDrone(List<PayloadBase> payloadList, Drone drone) {
    log.info("Loading drone with ID: {} with payload", drone.getId());
    validateDroneForLoading(drone, payloadList);

    payloadList.forEach(payload -> {
      payload.setDrone(drone);
      drone.getPayloads().add(payload);
    });

    drone.setState(DroneState.LOADED);
    return droneRepository.save(drone);
  }

  /**
   * Validates that a drone is ready for loading by checking battery level, weight, and current
   * state.
   */
  private void validateDroneForLoading(Drone drone, List<PayloadBase> payloads) {
    log.info("Validating drone with ID: {} for loading", drone.getId());
    checkBatteryLevel(drone);
    checkPayloadWeight(drone, payloads);
    checkDroneStateForLoading(drone);

    drone.setState(DroneState.LOADING); // Set to LOADING state after validation
  }

  private void checkBatteryLevel(Drone drone) {
    if (drone.getBatteryCapacity() < 25) {
      log.error("Drone with ID: {} has insufficient battery", drone.getId());
      throw new InsufficientBatteryException("Battery level is too low");
    }
  }

  private void checkPayloadWeight(Drone drone, List<PayloadBase> payloads) {
    int totalWeight = payloads.stream().mapToInt(PayloadBase::getWeight).sum();
    if (totalWeight > drone.getWeightLimit()) {
      log.error("Drone with ID: {} is overweight", drone.getId());
      throw new DroneOverweightException("Drone is overweight");
    }
  }

  private void checkDroneStateForLoading(Drone drone) {
    if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
      log.error("Drone with ID: {} is not in a state to be loaded", drone.getId());
      throw new IllegalStateException("Drone is not in a state to be loaded");
    }
  }

  /**
   * Retrieves the loaded medications for a specific drone.
   */
  @Transactional(readOnly = true)
  public List<PayloadBase> getLoadedMedications(Long droneId) {
    log.info("Getting loaded medications for drone with ID: {}", droneId);
    Drone drone = droneRepository.findById(droneId)
        .orElseThrow(() -> new DroneNotFoundException(String.format(DRONE_NOT_FOUND_MSG, droneId)));
    return drone.getPayloads();
  }

  /**
   * Finds all drones that are in an IDLE state and available for loading.
   */
  @Transactional(readOnly = true)
  public List<Drone> getAvailableDronesForLoading() {
    log.info("Getting available drones for loading");
    return droneRepository.findByState(DroneState.IDLE);
  }

  /**
   * Retrieves the current battery level for a specific drone.
   */
  @Transactional(readOnly = true)
  public BatteryLevelResponse getDroneBatteryLevel(Long droneId) {
    log.info("Getting battery level for drone with ID: {}", droneId);
    Drone drone = droneRepository.findById(droneId)
        .orElseThrow(() -> new DroneNotFoundException(String.format(DRONE_NOT_FOUND_MSG, droneId)));
    return new BatteryLevelResponse(drone.getBatteryCapacity());
  }
}
