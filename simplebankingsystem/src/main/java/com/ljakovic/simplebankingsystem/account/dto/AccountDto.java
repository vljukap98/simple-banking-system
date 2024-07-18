package com.ljakovic.simplebankingsystem.account.dto;

import java.math.BigDecimal;

public record AccountDto(
        Long id,
        String accountNumber,
        BigDecimal balance,
        BigDecimal pastMonthTurnover,
        String accountType) {}
