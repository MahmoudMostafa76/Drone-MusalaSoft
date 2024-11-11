package com.musalasoft.drone.drone.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.musalasoft.drone.drone.enumerations.DroneState;
import com.musalasoft.drone.drone.model.Drone;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
  List<Drone> findByState(DroneState droneState);
}

