package com.redmath.assignment.bankingapplication.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redmath.assignment.bankingapplication.account.Account;
import jakarta.persistence.*;

@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id") // Rename the column
    private long transaction_id;

    private String date;
    private double amount;
    private String transactionType;
    private String description;

    @JsonIgnore
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;


    public Transaction() {
    }

    public Transaction(long transaction_id, String date, double amount, String transactionType, String description, Account account) {
        this.transaction_id = transaction_id;
        this.date = date;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.account = account;
    }

    public long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
