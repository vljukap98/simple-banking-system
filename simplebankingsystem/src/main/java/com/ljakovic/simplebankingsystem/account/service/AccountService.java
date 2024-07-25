package com.ljakovic.simplebankingsystem.account.service;

import com.ljakovic.simplebankingsystem.account.dto.AccountDto;
import com.ljakovic.simplebankingsystem.account.mapper.AccountMapper;
import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;
import com.ljakovic.simplebankingsystem.account.repo.AccountRepository;
import com.ljakovic.simplebankingsystem.customer.model.Customer;
import com.ljakovic.simplebankingsystem.customer.repo.CustomerRepository;
import com.ljakovic.simplebankingsystem.customer.service.CustomerService;
import com.ljakovic.simplebankingsystem.service.dataimport.dto.ImportDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final CustomerService customerService;

    private final Object lock = new Object();

    public AccountService(AccountRepository accountRepo, CustomerRepository customerRepo, CustomerService customerService) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
        this.customerService = customerService;
    }

    public AccountDto createAccount(AccountDto accountDto) {
        final Customer customer = customerRepo.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        final Account account = new Account();
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountType(accountDto.getAccountType());
        account.setAccountCurrency(ECurrency.EUR);

        if (accountDto.getBalance() != null) {
            account.setBalance(accountDto.getBalance());
        } else {
            account.setBalance(BigDecimal.ZERO);
        }

        account.setCustomer(customer);
        accountRepo.save(account);

        customer.getAccounts().add(account);
        customerRepo.save(customer);

        return AccountMapper.mapTo(account, true);
    }

    public Account findOrCreateAccount(Long accountId, Long customerId) {
        Optional<Account> existingAccount = accountRepo.findById(accountId);
        if (existingAccount.isPresent()) {
            return existingAccount.get();
        } else {
            Customer customer = customerRepo.findById(customerId)
                    .orElseGet(() -> createCustomer(customerId));
            Account account = new Account();
            account.setCustomer(customer);
            account.setBalance(BigDecimal.ZERO);
            account.setId(accountId);
            return accountRepo.save(account);
        }
    }

    public Customer createCustomer(Long customerId) {
        Customer customer = new Customer();
        customer.setId(customerId);
        return customerRepo.save(customer);
    }
}
