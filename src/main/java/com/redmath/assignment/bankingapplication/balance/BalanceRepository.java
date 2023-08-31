package com.redmath.assignment.bankingapplication.balance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
//    Optional<Balance> findById(long accountId);

    Balance findTopByAccountIdOrderByDateDesc(long accountId);

    List<Balance> findByAccountId(long accountId);
}
