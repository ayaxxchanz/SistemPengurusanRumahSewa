package com.aliya.rentalmgmt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "rent_payments")
public class RentPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;

    @Column(name = "payment_month", nullable = false)
    private LocalDate paymentMonth;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "receipt_file", length = 255)
    private String receiptFile;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String remarks;
}