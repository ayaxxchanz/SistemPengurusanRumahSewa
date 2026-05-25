package com.aliya.rentalmgmt.repository;

import com.aliya.rentalmgmt.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
}
