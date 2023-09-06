package com.redmath.assignment.bankingapplication.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM Users u WHERE u.userName = ?1")
    User findByUserName(String userName);


    boolean existsByUserName(String username);
}
