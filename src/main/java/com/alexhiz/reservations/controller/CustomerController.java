package com.alexhiz.reservations.controller;

import com.alexhiz.reservations.dto.CustomerDTO;
import com.alexhiz.reservations.model.Customer;
import com.alexhiz.reservations.service.ICustomerService;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final ICustomerService customerService;

    @Qualifier("customerMapper")
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws Exception {
        List<CustomerDTO> customers = customerService.findAll().stream().map(this::convertToDto).toList();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<Customer>> listPage(Pageable pageable) throws Exception {
        Page<Customer> page = customerService.listPage(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID id) throws Exception {
        CustomerDTO customer = convertToDto(customerService.findById(id));
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO dto) throws Exception {
        Customer savedCustomer = customerService.save(convertToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomer.getIdCustomer())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID id, @RequestBody CustomerDTO dto)
            throws Exception {
        Customer updateCustomer = customerService.update(id, convertToEntity(dto));
        return ResponseEntity.ok(convertToDto(updateCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) throws Exception {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Customer convertToEntity(CustomerDTO dto) {
        return modelMapper.map(dto, Customer.class);
    }

    private CustomerDTO convertToDto(Customer obj) {
        return modelMapper.map(obj, CustomerDTO.class);
    }
}
