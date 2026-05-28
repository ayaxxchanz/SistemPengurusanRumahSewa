package com.aliya.rentalmgmt.repository;

import com.aliya.rentalmgmt.entity.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentPaymentRepository extends JpaRepository<RentPayment, Long> {
}
