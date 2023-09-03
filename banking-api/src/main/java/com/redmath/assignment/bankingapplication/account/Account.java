package com.redmath.assignment.bankingapplication.account;

import com.redmath.assignment.bankingapplication.balance.Balance;
import com.redmath.assignment.bankingapplication.transaction.Transaction;
import com.redmath.assignment.bankingapplication.user.User;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    private String name;
    private String email;
    private String address;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Balance> balance;

    @OneToMany(mappedBy = "account",cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToOne(mappedBy = "account",cascade = CascadeType.ALL)
    private User user;
    public Account() {
    }

    public Account(long id, String name, String email, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
