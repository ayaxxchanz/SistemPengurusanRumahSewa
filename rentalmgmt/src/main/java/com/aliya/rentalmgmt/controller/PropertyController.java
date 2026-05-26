package com.aliya.rentalmgmt.controller;

import com.aliya.rentalmgmt.entity.Property;
import com.aliya.rentalmgmt.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @PostMapping("/create")
    public Property create(@RequestBody Property property) {
        return propertyService.create(property);
    }

    @GetMapping("")
    public List<Property> getAll() {
        return propertyService.getAll();
    }
}
