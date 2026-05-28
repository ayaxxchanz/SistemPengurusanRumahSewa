package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.Property;

import java.util.List;

public interface PropertyService {

    Property create(Property property);
    List<Property> getAll();

}
