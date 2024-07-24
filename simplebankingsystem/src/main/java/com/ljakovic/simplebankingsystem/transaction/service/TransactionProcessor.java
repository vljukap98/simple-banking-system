package com.ljakovic.simplebankingsystem.transaction.service;

import com.ljakovic.simplebankingsystem.account.model.Account;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;
import com.ljakovic.simplebankingsystem.cache.CurrencyCache;
import com.ljakovic.simplebankingsystem.hnb.dto.HnbRateDto;
import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.model.Transaction;
import com.ljakovic.simplebankingsystem.transaction.repo.TransactionRepository;
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

    public TransactionProcessor(TransactionRepository transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Transactional
    public Transaction processTransaction(TransactionDto transactionDto, Account sender, Account receiver) {
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

        return transactionRepo.save(transaction);
    }

    private BigDecimal calcualte(final BigDecimal amount, final String rate) {
        final BigDecimal currencyRate = new BigDecimal(rate.replace(",", "."));
        return amount.multiply(currencyRate);
    }
}
