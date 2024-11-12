package com.musalasoft.drone.drone.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.musalasoft.drone.drone.model.Drone;
import com.musalasoft.drone.drone.service.DroneService;

class DroneControllerTest {

  @Test
  @DisplayName("Given a drone, when registerDrone, then return the drone")
  void registerDrone() {
    // Given
    Drone drone = new Drone();
    DroneService droneService = Mockito.mock(DroneService.class);
    DroneController droneController = new DroneController(droneService);

    drone.setSerialNumber("123456");
    when(droneService.registerDrone(drone)).thenReturn(drone);

    // When
    Drone result = droneController.registerDrone(drone);

    // Then
    assertEquals(drone.getSerialNumber(), result.getSerialNumber());
  }

}
