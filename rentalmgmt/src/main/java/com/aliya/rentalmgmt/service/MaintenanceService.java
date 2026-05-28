package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.MaintenanceRequest;
import com.aliya.rentalmgmt.repository.MaintenanceRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceService {

    private final MaintenanceRequestRepository maintenanceRequestRepository;

    public MaintenanceService(MaintenanceRequestRepository maintenanceRequestRepository) {
        this.maintenanceRequestRepository = maintenanceRequestRepository;
    }

    public MaintenanceRequest create(MaintenanceRequest request) {
        return maintenanceRequestRepository.save(request);
    }
}
