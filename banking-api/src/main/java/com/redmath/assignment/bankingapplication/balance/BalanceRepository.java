package com.redmath.assignment.bankingapplication.balance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Balance findTopByAccountIdOrderByDateDesc(long accountId);

    List<Balance> findByAccountId(long accountId);

    List<Balance> findByAccountIdOrderByDateDesc(Long accountId);
}
