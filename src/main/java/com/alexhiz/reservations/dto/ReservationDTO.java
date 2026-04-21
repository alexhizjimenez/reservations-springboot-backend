package com.alexhiz.reservations.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private UUID idReservation;
    private CustomerDTO customer;
    private RoomDTO room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
