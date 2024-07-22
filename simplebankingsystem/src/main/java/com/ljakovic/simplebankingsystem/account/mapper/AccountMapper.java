package com.ljakovic.simplebankingsystem.account.mapper;

import com.ljakovic.simplebankingsystem.account.dto.AccountDto;
import com.ljakovic.simplebankingsystem.account.model.Account;

public class AccountMapper {

    private AccountMapper() {}

    public static AccountDto mapTo(Account account, boolean mapTransactions) {
        final AccountDto accountDto = mapTo(account);

        if (mapTransactions &&
                account.getTransactions() != null &&
                !account.getTransactions().isEmpty()
        ) {
            //TODO: map transactions
        }
        return accountDto;
    }

    public static AccountDto mapTo(Account account) {
        final AccountDto accountDto = new AccountDto();
        accountDto.setId(account.getId());
        accountDto.setAccountNumber(account.getAccountNumber());
        accountDto.setAccountType(account.getAccountType());
        accountDto.setBalance(account.getBalance());
        accountDto.setCustomerId(account.getCustomer().getId());
        accountDto.setAccountCurrency(account.getAccountCurrency());

        if (account.getPastMonthTurnover() != null) {
            accountDto.setPastMonthTurnover(account.getPastMonthTurnover());
        }
        return accountDto;
    }
}
