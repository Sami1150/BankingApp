package com.redmath.assignment.bankingapplication.balance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redmath.assignment.bankingapplication.account.Account;
import jakarta.persistence.*;

@Entity
@Table(name="balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id") // Rename the column
    private long balance_id;

    private String date;
    private double amount;
    private String balanceType;

    @JsonIgnore
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    // Constructors, getters, setters, etc.


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Balance() {
    }

    public Balance(String date, double amount, String balanceType) {
        this.date = date;
        this.amount = amount;
        this.balanceType = balanceType;
    }
    public Balance(long balance_id, String date, double amount, String balanceType, Account account) {
        this.balance_id=balance_id;
        this.date = date;
        this.amount = amount;
        this.balanceType = balanceType;
        this.account=account;
    }

    public long getBalance_id() {
        return balance_id;
    }

    public void setBalance_id(long balance_id) {
        this.balance_id = balance_id;
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

    public String getBalanceType() {
        return balanceType;
    }

    public void setbalanceType(String balanceType) {
        this.balanceType = balanceType;
    }
}