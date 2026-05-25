package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.Unit;
import com.aliya.rentalmgmt.repository.UnitRepository;
import org.springframework.stereotype.Service;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public Unit create(Unit unit) {
        return unitRepository.save(unit);
    }
}
