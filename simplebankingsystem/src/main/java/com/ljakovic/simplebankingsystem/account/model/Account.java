package com.ljakovic.simplebankingsystem.account.model;

import com.ljakovic.simplebankingsystem.customer.model.Customer;
import com.ljakovic.simplebankingsystem.transaction.model.Transaction;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "balance")
    private BigDecimal balance;
    @Column(name = "past_month_turnover")
    private BigDecimal pastMonthTurnover;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private EAccountType accountType;
    @ManyToOne
    private Customer customer;
    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL)
    private List<Transaction> transactionsOutgoing;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "receiver", cascade = CascadeType.ALL)

    private List<Transaction> transactionsIncoming;*/

    public Account() {
    }

    public Account(Long id, String accountNumber, BigDecimal balance, BigDecimal pastMonthTurnover, EAccountType accountType, Customer customer, List<Transaction> transactionsOutgoing, List<Transaction> transactionsIncoming) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.pastMonthTurnover = pastMonthTurnover;
        this.accountType = accountType;
        this.customer = customer;
        //this.transactionsOutgoing = transactionsOutgoing;
        //this.transactionsIncoming = transactionsIncoming;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /*public List<Transaction> getTransactionsOutgoing() {
        return transactionsOutgoing;
    }

    public void setTransactionsOutgoing(List<Transaction> transactionsOutgoing) {
        this.transactionsOutgoing = transactionsOutgoing;
    }

    public List<Transaction> getTransactionsIncoming() {
        return transactionsIncoming;
    }

    public void setTransactionsIncoming(List<Transaction> transactionsIncoming) {
        this.transactionsIncoming = transactionsIncoming;
    }*/
}
