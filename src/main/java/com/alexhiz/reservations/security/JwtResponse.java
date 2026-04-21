package com.alexhiz.reservations.security;

import com.fasterxml.jackson.annotation.JsonProperty;

//Clase 3
public record JwtResponse(@JsonProperty(value = "access_token") String accessToken) {
}
