package com.musalasoft.drone.drone;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.musalasoft.drone.drone.controller.DroneController;
import com.musalasoft.drone.drone.service.DroneService;


@SpringBootTest
class DroneApplicationTests {

  @Autowired
  private DroneController droneController;

  @Autowired
  private DroneService droneService;


  @Test
  void contextLoads() {
    assertThat(droneController).isNotNull();
    assertThat(droneService).isNotNull();
  }
}
