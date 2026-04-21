package com.alexhiz.reservations.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {
    private UUID id;
    private String fullName;
    private String rfc;
    private String email;
    private String phoneNumber;
}
