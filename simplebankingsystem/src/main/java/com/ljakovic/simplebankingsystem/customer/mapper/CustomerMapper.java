package com.ljakovic.simplebankingsystem.customer.mapper;

import com.ljakovic.simplebankingsystem.customer.dto.CustomerDto;
import com.ljakovic.simplebankingsystem.customer.model.Customer;

public class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerDto mapTo(Customer customer) {
        final CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setFullName(customer.getFirstName() + " " + customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        return customerDto;
    }
}
