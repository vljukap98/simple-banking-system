package com.ljakovic.simplebankingsystem.service.dataimport.dto;

import com.ljakovic.simplebankingsystem.account.model.ECurrency;
import com.ljakovic.simplebankingsystem.transaction.dto.ETransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class ImportDto {

    private Long id;
    private BigDecimal amount;
    private String message;
    private ECurrency currency;
    private Date createdAt;
    private Long senderAccountId;
    private Long receiverAccountId;
    private Long senderCustomerId;
    private Long receiverCustomerId;
    private ETransactionType transactionType;

    public ImportDto() {
    }

    public ImportDto(Long id, BigDecimal amount, String message, ECurrency currency, Date createdAt, Long senderAccountId, Long receiverAccountId, Long senderCustomerId, Long receiverCustomerId, ETransactionType transactionType) {
        this.id = id;
        this.amount = amount;
        this.message = message;
        this.currency = currency;
        this.createdAt = createdAt;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.senderCustomerId = senderCustomerId;
        this.receiverCustomerId = receiverCustomerId;
        this.transactionType = transactionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ECurrency getCurrency() {
        return currency;
    }

    public void setCurrency(ECurrency currency) {
        this.currency = currency;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public Long getSenderCustomerId() {
        return senderCustomerId;
    }

    public void setSenderCustomerId(Long senderCustomerId) {
        this.senderCustomerId = senderCustomerId;
    }

    public Long getReceiverCustomerId() {
        return receiverCustomerId;
    }

    public void setReceiverCustomerId(Long receiverCustomerId) {
        this.receiverCustomerId = receiverCustomerId;
    }

    public ETransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ETransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
