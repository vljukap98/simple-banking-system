package com.ljakovic.simplebankingsystem.transaction.service;

import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;
import com.ljakovic.simplebankingsystem.account.repo.AccountRepository;
import com.ljakovic.simplebankingsystem.cache.CurrencyCache;
import com.ljakovic.simplebankingsystem.hnb.dto.HnbRateDto;
import com.ljakovic.simplebankingsystem.service.scheduler.CurrencyScheduler;
import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.mapper.TransactionMapper;
import com.ljakovic.simplebankingsystem.transaction.model.Transaction;
import com.ljakovic.simplebankingsystem.transaction.repo.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final AccountRepository accountRepo;
    private final CurrencyScheduler currencyScheduler;

    public TransactionService(TransactionRepository transactionRepo, AccountRepository accountRepo, CurrencyScheduler currencyScheduler) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
        this.currencyScheduler = currencyScheduler;
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

        final BigDecimal senderBalance = sender.getBalance();
        final BigDecimal receiverBalance = receiver.getBalance();

        sender.setBalance(senderBalance.subtract(senderCurrencyAmount));
        receiver.setBalance(receiverBalance.add(receiverCurrencyAmount));

        transaction.setSender(sender);
        transaction.setReceiver(receiver);

        transactionRepo.save(transaction);

        return TransactionMapper.mapTo(transaction, false, null);
    }

    private BigDecimal calcualte(final BigDecimal amount, final String rate) {
        final BigDecimal currencyRate = new BigDecimal(rate.replace(",", "."));
        return amount.multiply(currencyRate);
    }
}
