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
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID idRoom;
    @Column(unique = true, nullable = false)
    private String number;
    @Column(nullable = false, length = 10)
    private String type;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Boolean available;
    @Column(nullable = false)
    private Integer beds;
    @Column(nullable = true, length = 300)
    private String description;
}
