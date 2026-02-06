package com.autoflex.inventory.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product extends PanacheEntity {

    @Column(name = "code", unique = true, nullable = false, length = 50)
    @NotBlank(message = "Product code is required")
    @Size(min = 3, max = 50, message = "Product code must be between 3 and 50 characters")
    public String code;

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    public String name;

    @Column(name = "value", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Product value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Product value must be greater than 0")
    public BigDecimal value;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    public Product() {}

    public Product(String code, String name, BigDecimal value) {
        this.code = code;
        this.name = name;
        this.value = value;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Product findByCode(String code) {
        return find("code", code).firstResult();
    }

    public static boolean existsByCode(String code) {
        return count("code", code) > 0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}