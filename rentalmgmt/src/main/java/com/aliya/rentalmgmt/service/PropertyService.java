package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.Property;
import com.aliya.rentalmgmt.repository.PropertyRepository;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public Property create(Property property) {
        return propertyRepository.save(property);
    }
}
