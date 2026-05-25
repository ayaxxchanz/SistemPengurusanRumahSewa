package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.Lease;
import com.aliya.rentalmgmt.repository.LeaseRepository;
import org.springframework.stereotype.Service;

@Service
public class LeaseService {

    private final LeaseRepository leaseRepository;

    public LeaseService(LeaseRepository leaseRepository) {
        this.leaseRepository = leaseRepository;
    }

    public Lease create(Lease lease) {
        return leaseRepository.save(lease);
    }
}
