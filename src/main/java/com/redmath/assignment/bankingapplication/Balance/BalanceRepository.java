package com.redmath.assignment.bankingapplication.Balance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
//    Optional<Balance> findById(long accountId);

    Balance findTopByAccountIdOrderByDateDesc(long accountId);
}
