package com.aliya.rentalmgmt.service;

import com.aliya.rentalmgmt.entity.RentPayment;
import com.aliya.rentalmgmt.repository.RentPaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final RentPaymentRepository rentPaymentRepository;

    public PaymentService(RentPaymentRepository rentPaymentRepository) {
        this.rentPaymentRepository = rentPaymentRepository;
    }

    public RentPayment create(RentPayment rentPayment) {
        return rentPaymentRepository.save(rentPayment);
    }
}
