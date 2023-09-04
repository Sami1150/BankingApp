package com.redmath.assignment.bankingapplication.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findAllByEmail(String email);


}
