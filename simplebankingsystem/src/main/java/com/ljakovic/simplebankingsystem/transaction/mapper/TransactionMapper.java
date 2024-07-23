package com.ljakovic.simplebankingsystem.transaction.mapper;

import com.ljakovic.simplebankingsystem.transaction.dto.TransactionDto;
import com.ljakovic.simplebankingsystem.transaction.model.Transaction;

public class TransactionMapper {

    public static TransactionDto mapTo(Transaction transaction, boolean mapWholeObject) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());

        if (mapWholeObject) {
            //TODO: map the rest of the properties
        }

        return transactionDto;
    }
}
