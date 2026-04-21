package com.alexhiz.reservations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultProdDTO {
    private Integer quantity;
    private String reservationdate;
}
