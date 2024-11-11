package com.musalasoft.drone.drone.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DroneService {



  private final DroneRepository droneRepository;
  private final BatteryAuditRepository batteryAuditRepository;

  public void logBatteryLevels() {
    List<Drone> drones = droneRepository.findAll();
    for (Drone drone : drones) {
      BatteryAudit audit = new BatteryAudit();
      audit.setDroneId(drone.getId());
      audit.setBatteryLevel(drone.getBatteryCapacity());
      audit.setTimestamp(LocalDateTime.now());
      batteryAuditRepository.save(audit);
    }
  }

  @Transactional
  public Drone registerDrone(Drone drone) {
    drone.setState(DroneState.IDLE);
    List<PayloadBase> payload = drone.getPayloads();
    drone.setPayloads(new ArrayList<>());
    drone = droneRepository.save(drone);

    if (payload != null && !payload.isEmpty()) {
      return loadDrone(payload, drone);
    }
    return drone;

  }

  @Transactional
  public Drone loadDroneWithMedication(Long droneId, List<PayloadBase> medications) {
    Drone drone = droneRepository.findById(droneId)
        .orElseThrow(() -> new DroneNotFoundException("Drone not found"));

    return loadDrone(medications, drone);
  }

  private Drone loadDrone(List<PayloadBase> payloadList, Drone drone) {
    validateDroneForLoading(drone, payloadList);


    // Add new payloads to the existing collection
    payloadList.forEach(payload -> {
      payload.setDrone(drone);
      drone.getPayloads().add(payload);
    });

    drone.setState(DroneState.LOADED);
    return droneRepository.save(drone);
  }

  private void validateDroneForLoading(Drone drone, List<PayloadBase> medications) {
    if (drone.getBatteryCapacity() < 25) {
      throw new InsufficientBatteryException("Battery level is too low");
    }

    int totalWeight = medications.stream().mapToInt(PayloadBase::getWeight).sum();
    if (totalWeight > drone.getWeightLimit()) {
      throw new DroneOverweightException("Drone is overweight");
    }
    if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
      throw new IllegalStateException("Drone is not in a state to be loaded");
    }

    drone.setState(DroneState.LOADING); // Update state to LOADING before completing load
  }

  public List<PayloadBase> getLoadedMedications(Long droneId) {
    Drone drone = droneRepository.findById(droneId)
        .orElseThrow(() -> new DroneNotFoundException("Drone not found"));



    return drone.getPayloads();
  }

  public List<Drone> getAvailableDronesForLoading() {
    return droneRepository.findByState(DroneState.IDLE);
  }

  public BatteryLevelResponse getDroneBatteryLevel(Long droneId) {
    Drone drone = droneRepository.findById(droneId)
        .orElseThrow(() -> new DroneNotFoundException("Drone not found"));

    return new BatteryLevelResponse(drone.getBatteryCapacity());
  }
}
