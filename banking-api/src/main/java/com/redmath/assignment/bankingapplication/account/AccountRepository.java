package com.redmath.assignment.bankingapplication.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
//    Optional<Account> findAllByEmail(String email);

//    @Query(value = "SELECT a FROM account WHERE account.email = ?1")
    Account findAllByEmail(String email);


}
