package com.aliya.rentalmgmt.repository;

import com.aliya.rentalmgmt.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}