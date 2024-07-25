package com.ljakovic.simplebankingsystem.transaction.service;

import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;
import com.ljakovic.simplebankingsystem.account.repo.AccountRepository;
import com.ljakovic.simplebankingsystem.cache.CurrencyCache;
import com.ljakovic.simplebankingsystem.hnb.dto.HnbRateDto;
import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.model.Transaction;
import com.ljakovic.simplebankingsystem.transaction.repo.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class TransactionProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionProcessor.class);

    private final TransactionRepository transactionRepo;
    private final AccountRepository accountRepo;

    public TransactionProcessor(TransactionRepository transactionRepo, AccountRepository accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    @Transactional
    public Transaction processTransaction(TransactionDto transactionDto) {
        final Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setCurrency(transactionDto.getCurrency());
        transaction.setCreatedAt(new Date());

        if (transactionDto.getMessage() != null) {
            transaction.setMessage(transactionDto.getMessage());
        } else {
            transaction.setMessage("");
        }

        final BigDecimal targetCurrencyAmount = transactionDto.getAmount();
        final HnbRateDto hnbRate = CurrencyCache.getInstance().getCurrencyMap().get(transactionDto.getCurrency().toString());

        final String buyingRate;
        final String sellingRate;

        if (hnbRate == null || transactionDto.getCurrency().equals(ECurrency.EUR)) {
            buyingRate = "1";
            sellingRate = "1";
        } else {
            buyingRate = hnbRate.getBuyingRate();
            sellingRate = hnbRate.getSellingRate();
        }
        final BigDecimal senderCurrencyAmount = calcualte(targetCurrencyAmount, buyingRate);
        final BigDecimal receiverCurrencyAmount = calcualte(targetCurrencyAmount, sellingRate);

        final Account sender = accountRepo.findById(transactionDto.getSenderId())
                .orElseThrow(() -> new EntityNotFoundException("Sender account not found"));
        final Account receiver = accountRepo.findById(transactionDto.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver account not found"));

        final BigDecimal senderBalance = sender.getBalance();
        final BigDecimal receiverBalance = receiver.getBalance();

        sender.setBalance(senderBalance.subtract(senderCurrencyAmount));
        receiver.setBalance(receiverBalance.add(receiverCurrencyAmount));

        transaction.setSender(sender);
        transaction.setReceiver(receiver);

        try {
            transactionRepo.save(transaction);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

        sender.getTransactionsOutgoing().add(transaction);
        receiver.getTransactionsIncoming().add(transaction);

        accountRepo.save(sender);
        accountRepo.save(receiver);


        return transaction;
    }

    private BigDecimal calcualte(final BigDecimal amount, final String rate) {
        final BigDecimal currencyRate = new BigDecimal(rate.replace(",", "."));
        return amount.multiply(currencyRate);
    }
}
