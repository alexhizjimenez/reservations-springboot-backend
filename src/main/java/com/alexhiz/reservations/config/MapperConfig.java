package com.alexhiz.reservations.config;

import java.time.LocalDate;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alexhiz.reservations.dto.CustomerDTO;
import com.alexhiz.reservations.dto.ReservationDTO;
import com.alexhiz.reservations.model.Customer;
import com.alexhiz.reservations.model.Reservation;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapper customerMapper() {
        ModelMapper mapper = new ModelMapper();

        // Escritura POST PUT
        mapper.createTypeMap(CustomerDTO.class, Customer.class)
                .addMapping(CustomerDTO::getFullName, Customer::setFullname)
                .addMapping(CustomerDTO::getEmail, Customer::setEmail)
                .addMapping(CustomerDTO::getPhoneNumber, Customer::setPhone);

        // LECTURA GET
        mapper.createTypeMap(Customer.class, CustomerDTO.class)
                .addMapping(Customer::getFullname, CustomerDTO::setFullName)
                .addMapping(Customer::getEmail, CustomerDTO::setEmail)
                .addMapping(Customer::getPhone, CustomerDTO::setPhoneNumber);
        return mapper;
    }

    @Bean
    public ModelMapper reservationMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(Reservation.class, ReservationDTO.class)
                .addMapping(e -> e.getCustomer().getFullname(), (dest, v) -> dest.getCustomer().setFullName((String) v))
                .addMapping(e -> e.getCustomer().getEmail(), (dest, v) -> dest.getCustomer().setEmail((String) v))
                .addMapping(e -> e.getCustomer().getPhone(), (dest, v) -> dest.getCustomer().setPhoneNumber((String) v))
                .addMapping(e -> e.getRoom().getNumber(), (dest, v) -> dest.getRoom().setNumber((String) v))
                .addMapping(e -> e.getRoom().getType(), (dest, v) -> dest.getRoom().setType((String) v))
                .addMapping(e -> e.getRoom().getPrice(), (dest, v) -> dest.getRoom().setPrice((Double) v))
                .addMapping(e -> e.getRoom().getAvailable(), (dest, v) -> dest.getRoom().setAvailable((Boolean) v))
                .addMapping(e -> e.getRoom().getBeds(), (dest, v) -> dest.getRoom().setBeds((Integer) v))
                .addMapping(e -> e.getRoom().getDescription(), (dest, v) -> dest.getRoom().setDescription((String) v))
                .addMapping(e -> e.getCheckInDate(), (dest, v) -> dest.setCheckInDate((LocalDate) v))
                .addMapping(e -> e.getCheckOutDate(), (dest, v) -> dest.setCheckOutDate((LocalDate) v));
        return mapper;
    }

}
