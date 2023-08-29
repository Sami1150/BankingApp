package com.redmath.assignment.bankingapplication.account;

import com.redmath.assignment.bankingapplication.Balance.Balance;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String password;
    private String name;
    private String email;
    private String address;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_account_Id", referencedColumnName = "id")
    private List<Balance> balance;

    public Account() {
    }

    public Account(long id, String password, String name, String email, String address) {
        this.id = id;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
