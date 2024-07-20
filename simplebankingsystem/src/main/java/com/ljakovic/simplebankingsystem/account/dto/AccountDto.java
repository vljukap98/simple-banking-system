package com.ljakovic.simplebankingsystem.account.dto;

import com.ljakovic.simplebankingsystem.account.model.EAccountType;

import java.math.BigDecimal;

public class AccountDto{
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal pastMonthTurnover;
    private EAccountType accountType;

    private Long customerId;
    public AccountDto() {
    }

    public AccountDto(Long id, String accountNumber, BigDecimal balance, BigDecimal pastMonthTurnover, EAccountType accountType, Long customerId) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pastMonthTurnover = pastMonthTurnover;
        this.accountType = accountType;
        this.customerId = customerId;
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
}

