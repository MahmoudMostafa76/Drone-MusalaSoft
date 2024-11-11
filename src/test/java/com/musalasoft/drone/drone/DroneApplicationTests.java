package com.musalasoft.drone.drone;


import static org.assertj.core.api.Assertions.assertThat;

import com.musalasoft.drone.drone.controller.DroneController;
import com.musalasoft.drone.drone.service.DroneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
	}}
