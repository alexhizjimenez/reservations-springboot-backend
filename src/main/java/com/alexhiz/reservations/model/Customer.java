package com.alexhiz.reservations.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID idCustomer;
    @Column(nullable = false, length = 100)
    private String fullname;
    @Column(length = 13, unique = true)
    private String rfc;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(nullable = false, length = 10, unique = true)
    private String phone;
}
