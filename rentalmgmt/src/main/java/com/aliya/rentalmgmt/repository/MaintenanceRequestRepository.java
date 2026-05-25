package com.aliya.rentalmgmt.repository;

import com.aliya.rentalmgmt.entity.MaintenanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
}
