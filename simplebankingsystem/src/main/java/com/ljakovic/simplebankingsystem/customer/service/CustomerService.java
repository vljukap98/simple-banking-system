package com.ljakovic.simplebankingsystem.customer.service;

import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.customer.dto.CustomerDto;
import com.ljakovic.simplebankingsystem.customer.mapper.CustomerMapper;
import com.ljakovic.simplebankingsystem.customer.model.Customer;
import com.ljakovic.simplebankingsystem.customer.repo.CustomerRepository;
import com.ljakovic.simplebankingsystem.transaction.dto.ETransactionType;
import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.mapper.TransactionMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
        return CustomerMapper.mapTo(customer, true);
    }

    public Customer saveCustomer(CustomerDto customerDto) {
        final Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        customerRepo.save(customer);
        return customer;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        final Customer customer = saveCustomer(customerDto);
        return CustomerMapper.mapTo(customer);
    }

    public List<TransactionDto> getCustomerTransactionHistory(Long customerId) {
        final Customer customer = getCustomer(customerId);
        return customer.getAccounts().stream()
                .flatMap(a -> Stream.concat(
                    a.getTransactionsIncoming().stream()
                            .map(t -> TransactionMapper.mapTo(t, true, ETransactionType.INCOMING)),
                    a.getTransactionsOutgoing().stream()
                            .map(t -> TransactionMapper.mapTo(t, true, ETransactionType.OUTGOING))
                ))
                .toList();
    }
}
