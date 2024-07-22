package com.ljakovic.simplebankingsystem.account.service;

import com.ljakovic.simplebankingsystem.account.dto.AccountDto;
import com.ljakovic.simplebankingsystem.account.mapper.AccountMapper;
import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;
import com.ljakovic.simplebankingsystem.account.repo.AccountRepository;
import com.ljakovic.simplebankingsystem.customer.model.Customer;
import com.ljakovic.simplebankingsystem.customer.repo.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;


    public AccountService(AccountRepository accountRepo, CustomerRepository customerRepo) {
        this.accountRepo = accountRepo;
        this.customerRepo = customerRepo;
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
}
