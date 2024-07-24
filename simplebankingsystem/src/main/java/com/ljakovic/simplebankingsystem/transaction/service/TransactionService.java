package com.ljakovic.simplebankingsystem.transaction.service;

import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.account.repo.AccountRepository;
import com.ljakovic.simplebankingsystem.account.service.AccountService;
import com.ljakovic.simplebankingsystem.cache.CurrencyCache;
import com.ljakovic.simplebankingsystem.customer.repo.CustomerRepository;
import com.ljakovic.simplebankingsystem.service.dataimport.dto.ImportDto;
import com.ljakovic.simplebankingsystem.service.scheduler.CurrencyScheduler;
import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.mapper.TransactionMapper;
import com.ljakovic.simplebankingsystem.transaction.model.Transaction;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);

    private final AccountRepository accountRepo;
    private final AccountService accountService;
    private final CustomerRepository customerRepo;
    private final CurrencyScheduler currencyScheduler;
    private final TransactionProcessor transactionProcessor;

    public TransactionService(AccountRepository accountRepo, AccountService accountService, CustomerRepository customerRepo, CurrencyScheduler currencyScheduler, TransactionProcessor transactionProcessor) {
        this.accountRepo = accountRepo;
        this.accountService = accountService;
        this.customerRepo = customerRepo;
        this.currencyScheduler = currencyScheduler;
        this.transactionProcessor = transactionProcessor;
    }

    @Transactional
    public TransactionDto processTransaction(TransactionDto transactionDto) {
        final Account sender = accountRepo.findById(transactionDto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));
        final Account receiver = accountRepo.findById(transactionDto.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        if (CurrencyCache.getInstance().getCurrencyMap().isEmpty()) {
            currencyScheduler.getCurrencies();
        }

        final Transaction transaction = transactionProcessor.processTransaction(transactionDto, sender, receiver);

        return TransactionMapper.mapTo(transaction, false, null);
    }

    @Transactional
    public void importTransaction(ImportDto importDto, Account sender, Account receiver) {
        LOG.info("Sender customer | {}", sender.getId());
        LOG.info("Receiver customer | {}", receiver.getId());

        final TransactionDto transactionDto = new TransactionDto();
        transactionDto.setSenderId(sender.getId());
        transactionDto.setReceiverId(receiver.getId());
        transactionDto.setAmount(importDto.getAmount());
        transactionDto.setCurrency(importDto.getCurrency());
        transactionDto.setCreatedAt(importDto.getCreatedAt());
        transactionDto.setMessage(importDto.getMessage());

        final Transaction transaction = transactionProcessor.processTransaction(transactionDto, sender, receiver);
        LOG.info("{}", transaction);
    }
}
