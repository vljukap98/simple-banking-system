package com.ljakovic.simplebankingsystem.customer.service;

import com.ljakovic.simplebankingsystem.customer.dto.CustomerDto;
import com.ljakovic.simplebankingsystem.customer.mapper.CustomerMapper;
import com.ljakovic.simplebankingsystem.customer.model.Customer;
import com.ljakovic.simplebankingsystem.customer.repo.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepo;

    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer getCustomer(Long customerId) {
        return customerRepo.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public CustomerDto getCustomerDetails(Long customerId) {
        final Customer customer = getCustomer(customerId);
        return CustomerMapper.mapTo(customer);
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        final Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        customerRepo.save(customer);
        return CustomerMapper.mapTo(customer);
    }
}
