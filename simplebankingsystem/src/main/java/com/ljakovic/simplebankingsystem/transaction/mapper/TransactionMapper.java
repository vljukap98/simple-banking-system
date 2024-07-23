package com.ljakovic.simplebankingsystem.transaction.mapper;

import com.ljakovic.simplebankingsystem.transaction.dto.ETransactionType;
import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.model.Transaction;

public class TransactionMapper {

    private TransactionMapper() {}

    public static TransactionDto mapTo(Transaction transaction, boolean mapWholeObject, ETransactionType transactionType) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setTransactionType(transactionType);

        if (mapWholeObject) {
            mapWholeObject(transactionDto, transaction);
        }

        return transactionDto;
    }

    private static void mapWholeObject(TransactionDto transactionDto, Transaction transaction) {
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setCurrency(transaction.getCurrency());
        transactionDto.setCreatedAt(transaction.getCreatedAt());

        if (transaction.getMessage() != null) {
            transactionDto.setMessage(transaction.getMessage());
        }
    }
}
