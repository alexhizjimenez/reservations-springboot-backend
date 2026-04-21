package com.alexhiz.reservations.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alexhiz.reservations.model.Customer;
import com.alexhiz.reservations.repo.ICustomerRepo;
import com.alexhiz.reservations.repo.IGenericRepo;
import com.alexhiz.reservations.service.ICustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends CRUDImpl<Customer, UUID> implements ICustomerService{

    private final ICustomerRepo repo;

    @Override
    protected IGenericRepo<Customer, UUID> getRepo() {
        return repo;
    }

    @Override
    public Page<Customer> listPage(Pageable pageable) {
        return repo.findAll(pageable);
    }

}
