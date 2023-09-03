package com.redmath.assignment.bankingapplication.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redmath.assignment.bankingapplication.account.Account;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String roles;
    private String status;
    private Integer loginAttempts;
    private LocalDateTime loginAt;

    @JsonIgnore
    @OneToOne //(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
    public User() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public User(Long id, String userName, String password, String roles, String status, Integer loginAttempts, LocalDateTime loginAt) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.status = status;
        this.loginAttempts = loginAttempts;
        this.loginAt = loginAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(Integer loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public LocalDateTime getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(LocalDateTime loginAt) {
        this.loginAt = loginAt;
    }
}