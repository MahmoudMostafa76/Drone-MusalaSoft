package com.musalasoft.drone.drone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.musalasoft.drone.drone.model.BatteryAudit;

public interface BatteryAuditRepository extends JpaRepository<BatteryAudit, Long> {
}
