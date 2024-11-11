package com.musalasoft.drone.drone.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musalasoft.drone.drone.model.Drone;
import com.musalasoft.drone.drone.model.PayloadBase;
import com.musalasoft.drone.drone.model.dto.BatteryLevelResponse;
import com.musalasoft.drone.drone.service.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/drones")
@AllArgsConstructor
public class DroneController {
  private final DroneService droneService;


  @Operation(summary = "Register a new drone",
      description = "Registers a new drone with its details.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully registered the drone"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")})
  @PostMapping("/register")
  public Drone registerDrone(@RequestBody Drone drone) {
    return droneService.registerDrone(drone);
  }

  @PostMapping("/{droneId}/load")
  public Drone loadDroneWithMedication(@PathVariable Long droneId,
      @RequestBody List<PayloadBase> medications) {
    return droneService.loadDroneWithMedication(droneId, medications);
  }

  @GetMapping("/{droneId}/medications")
  public List<PayloadBase> getLoadedMedications(@PathVariable Long droneId) {
    return droneService.getLoadedMedications(droneId);
  }

  @GetMapping("/available")
  public List<Drone> getAvailableDronesForLoading() {
    return droneService.getAvailableDronesForLoading();
  }

  @GetMapping("/{droneId}/battery")
  public BatteryLevelResponse getDroneBatteryLevel(@PathVariable Long droneId) {
    return droneService.getDroneBatteryLevel(droneId);
  }
}
