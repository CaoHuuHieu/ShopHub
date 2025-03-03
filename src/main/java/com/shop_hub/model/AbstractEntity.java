package com.shop_hub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    void setCreatedAt(){
        this.createdAt = Instant.now();
    }

    @PreUpdate
    void setUpdatedAt(){
        this.updatedAt = Instant.now();
    }

}
