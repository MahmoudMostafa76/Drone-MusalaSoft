package com.musalasoft.drone.drone.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.musalasoft.drone.drone.service.DroneService;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BatteryCheckScheduler {
  private final DroneService droneService;

  @Scheduled(fixedRate = 60000) // Every minute
  public void checkBatteryLevels() {
    droneService.logBatteryLevels();
  }
}
