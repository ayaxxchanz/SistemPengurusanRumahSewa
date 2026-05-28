package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.Property;
import com.aliya.rentalmgmt.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl  implements PropertyService {
    private final PropertyRepository propertyRepository;

    @Override
    public Property create(Property property) {
        return propertyRepository.save(property);
    }

    @Override
    public List<Property> getAll() {
        return propertyRepository.findAll();
    }
}
