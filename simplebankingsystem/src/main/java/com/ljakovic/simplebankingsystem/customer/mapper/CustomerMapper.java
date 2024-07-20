package com.ljakovic.simplebankingsystem.customer.mapper;

import com.ljakovic.simplebankingsystem.account.dto.AccountDto;
import com.ljakovic.simplebankingsystem.account.mapper.AccountMapper;
import com.ljakovic.simplebankingsystem.customer.dto.CustomerDto;
import com.ljakovic.simplebankingsystem.customer.model.Customer;

import java.util.List;

public class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerDto mapTo(Customer customer, boolean mapAccounts) {
        final CustomerDto customerDto = mapTo(customer);

        if (mapAccounts &&
                customer.getAccounts() != null &&
                !customer.getAccounts().isEmpty()) {
            List<AccountDto> accountDtoList = customer.getAccounts().stream()
                    .map(AccountMapper::mapTo)
                    .toList();
            customerDto.setAccounts(accountDtoList);
        }

        return customerDto;
    }

    public static CustomerDto mapTo(Customer customer) {
        final CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setFullName(customer.getFirstName() + " " + customer.getLastName());

        if (customer.getEmail() != null) {
            customerDto.setEmail(customer.getEmail());
        }
        if (customer.getPhoneNumber() != null) {
            customerDto.setPhoneNumber(customer.getPhoneNumber());
        }
        return customerDto;
    }
}
