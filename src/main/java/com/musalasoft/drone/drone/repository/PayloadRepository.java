package com.musalasoft.drone.drone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.musalasoft.drone.drone.model.PayloadBase;

@Repository
public interface PayloadRepository extends JpaRepository<PayloadBase, Long> {

}

