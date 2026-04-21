package com.alexhiz.reservations.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alexhiz.reservations.model.Customer;

public interface ICustomerService extends ICRUD<Customer, UUID> {
    Page<Customer> listPage(Pageable pageable);
}
