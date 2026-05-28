package com.aliya.rentalmgmt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landlord_id", nullable = false)
    private User landlord;

    @Column(name = "property_name", nullable = false, length = 150)
    private String propertyName;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "property_type", length = 50)
    private String propertyType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}