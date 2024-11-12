package com.musalasoft.drone.drone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.musalasoft.drone.drone.enumerations.DroneState;
import com.musalasoft.drone.drone.exception.DroneNotFoundException;
import com.musalasoft.drone.drone.exception.DroneOverweightException;
import com.musalasoft.drone.drone.exception.InsufficientBatteryException;
import com.musalasoft.drone.drone.model.Drone;
import com.musalasoft.drone.drone.model.Medication;
import com.musalasoft.drone.drone.model.PayloadBase;
import com.musalasoft.drone.drone.model.dto.BatteryLevelResponse;
import com.musalasoft.drone.drone.repository.BatteryAuditRepository;
import com.musalasoft.drone.drone.repository.DroneRepository;

class DroneServiceTest {

  @Mock
  private DroneRepository droneRepository;

  @Mock
  private BatteryAuditRepository batteryAuditRepository;

  @InjectMocks
  private DroneService droneService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @DisplayName("Given a drone, when registerDrone, then return the registered drone")
  void registerDrone_Success() {
    Drone drone = new Drone();
    drone.setSerialNumber("12345");
    drone.setWeightLimit(500);
    drone.setBatteryCapacity(100);
    drone.setState(DroneState.IDLE);

    when(droneRepository.save(any(Drone.class))).thenReturn(drone);

    Drone result = droneService.registerDrone(drone);

    assertNotNull(result);
    assertEquals("12345", result.getSerialNumber());
    verify(droneRepository, times(1)).save(drone);
  }

  @Test
  @DisplayName("Given a drone with low battery, when loadDroneWithMedication, then throw InsufficientBatteryException")
  void loadDroneWithMedication_LowBattery_Failure() {
    Drone drone = new Drone();
    drone.setId(1L);
    drone.setBatteryCapacity(20);
    drone.setState(DroneState.IDLE);

    List<PayloadBase> medications = new ArrayList<>();

    when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

    assertThrows(InsufficientBatteryException.class, () -> {
      droneService.loadDroneWithMedication(1L, medications);
    });

    verify(droneRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Given a drone with overweight payload, when loadDroneWithMedication, then throw DroneOverweightException")
  void loadDroneWithMedication_Overweight_Failure() {
    Drone drone = new Drone();
    drone.setId(1L);
    drone.setWeightLimit(100);
    drone.setBatteryCapacity(100);
    drone.setState(DroneState.IDLE);

    List<PayloadBase> medications = new ArrayList<>();
    Medication payload = new Medication("Paracetamol", "Painkiller", "img");
    payload.setWeight(200);
    medications.add(payload);

    when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

    assertThrows(DroneOverweightException.class, () -> {
      droneService.loadDroneWithMedication(1L, medications);
    });

    verify(droneRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Given a drone, when getDroneBatteryLevel, then return the battery level")
  void getDroneBatteryLevel_Success() {
    Drone drone = new Drone();
    drone.setId(1L);
    drone.setBatteryCapacity(80);

    when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

    BatteryLevelResponse response = droneService.getDroneBatteryLevel(1L);

    assertNotNull(response);
    assertEquals(80, response.getBatteryLevel());
    verify(droneRepository, times(1)).findById(1L);
  }

  @Test
  @DisplayName("Given a non-existent drone, when getDroneBatteryLevel, then throw DroneNotFoundException")
  void getDroneBatteryLevel_DroneNotFound_Failure() {
    when(droneRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(DroneNotFoundException.class, () -> {
      droneService.getDroneBatteryLevel(1L);
    });

    verify(droneRepository, times(1)).findById(1L);
  }
}
