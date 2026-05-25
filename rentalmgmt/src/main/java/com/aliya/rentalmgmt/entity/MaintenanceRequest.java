package com.aliya.rentalmgmt.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "maintenance_requests")
public class MaintenanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id", nullable = false)
    private Lease lease;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_file", length = 255)
    private String imageFile;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status = MaintenanceStatus.OPEN;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}