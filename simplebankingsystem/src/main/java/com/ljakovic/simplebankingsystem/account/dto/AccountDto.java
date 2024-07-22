package com.ljakovic.simplebankingsystem.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ljakovic.simplebankingsystem.account.model.EAccountType;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto{
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal pastMonthTurnover;
    private EAccountType accountType;
    private ECurrency accountCurrency;

    private Long customerId;
    public AccountDto() {
    }

    public AccountDto(Long id, String accountNumber, BigDecimal balance, BigDecimal pastMonthTurnover, EAccountType accountType, Long customerId, ECurrency accountCurrency) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pastMonthTurnover = pastMonthTurnover;
        this.accountType = accountType;
        this.customerId = customerId;
        this.accountCurrency = accountCurrency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPastMonthTurnover() {
        return pastMonthTurnover;
    }

    public void setPastMonthTurnover(BigDecimal pastMonthTurnover) {
        this.pastMonthTurnover = pastMonthTurnover;
    }

    public EAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(EAccountType accountType) {
        this.accountType = accountType;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public ECurrency getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(ECurrency accountCurrency) {
        this.accountCurrency = accountCurrency;
    }
}

