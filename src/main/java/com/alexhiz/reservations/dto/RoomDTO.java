package com.alexhiz.reservations.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private UUID id;
    private String number;
    private String type;
    private Double price;
    private Boolean available;
    private Integer beds;
    private String description;
}
