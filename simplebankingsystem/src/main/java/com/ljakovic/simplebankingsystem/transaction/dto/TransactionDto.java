package com.ljakovic.simplebankingsystem.transaction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ljakovic.simplebankingsystem.account.model.ECurrency;

import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {

    private Long id;
    private BigDecimal amount;
    private String message;
    private ECurrency currency;
    private Date createdAt;
    private Long senderId;
    private Long receiverId;
    private ETransactionType transactionType;

    public TransactionDto() {
    }

    public TransactionDto(Long id, BigDecimal amount, String message, ECurrency currency, Date createdAt, Long senderId, Long receiverId, ETransactionType transactionType) {
        this.id = id;
        this.amount = amount;
        this.message = message;
        this.currency = currency;
        this.createdAt = createdAt;
        this.senderId = senderId;
        this.receiverId = receiverId;
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

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public ETransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ETransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", amount=" + amount +
                ", message='" + message + '\'' +
                ", currency=" + currency +
                ", createdAt=" + createdAt +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", transactionType=" + transactionType +
                '}';
    }
}
