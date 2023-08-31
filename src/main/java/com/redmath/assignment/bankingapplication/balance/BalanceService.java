package com.redmath.assignment.bankingapplication.balance;

import com.redmath.assignment.bankingapplication.account.Account;
import com.redmath.assignment.bankingapplication.account.AccountRepository;
import com.redmath.assignment.bankingapplication.user.User;
import com.redmath.assignment.bankingapplication.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final AccountRepository accountRepository;
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Constructor
    public BalanceService(BalanceRepository balanceRepository, AccountRepository accountRepository) {
        this.balanceRepository = balanceRepository;
        this.accountRepository = accountRepository;
    }

    public List<Balance> findAllBalances(Authentication authentication) {
        logger.debug("Fetching all balances");
        String username = authentication.getName();

        // Retrieve the user object using the username
        User user = userService.findByUsername(username);

        // Get the account ID from the user object
        Long accountId = user.getAccount().getId();
        return balanceRepository.findByAccountId(accountId);
    }

    public Balance findTopByAccountIdOrderByDateDesc(long accountId) {
        logger.debug("Fetching TopByAccountIdOrderByDateDesc balances");
        return balanceRepository.findTopByAccountIdOrderByDateDesc(accountId);
    }

//    public List<Balance> getBalancesByAccountId(long accountId) {
//        // Fetch all transactions for the given account ID
//        return balanceRepository.findByAccountId(accountId);
//    }
//    public Optional<Balance> findByAccountId(long accountId) {
//        logger.info("Balance details with account ID {} are: ", accountId);
//        return balanceRepository.findById()  //.findById(accountId);
//    }

    // Post Mapping
    public Balance create(Balance balance) {
        logger.info("Balance with account ID {} is added. ", balance.getBalance_id());
        balance.setDate(String.valueOf(LocalDate.now())); // Set current date

        return balanceRepository.save(balance);
    }


    // Put Mapping
    public Balance update(Balance newBalanceData) {
        logger.info("Balance with account ID {} is updated. ", newBalanceData.getBalance_id());

        Optional<Balance> existingBalance = balanceRepository.findById(newBalanceData.getBalance_id());

        if (existingBalance.isPresent()) {
            Balance balanceToUpdate = existingBalance.get();
            balanceToUpdate.setDate(newBalanceData.getDate());
            balanceToUpdate.setAmount(newBalanceData.getAmount());
            balanceToUpdate.setbalanceType(newBalanceData.getBalanceType());

            return balanceRepository.save(balanceToUpdate);
        }

        return null;
    }
//
//    // Delete Mapping
//    public boolean delete(long accountId) {
//        Optional<Balance> balanceToDelete = balanceRepository.findById(accountId);
//
//        if (balanceToDelete.isPresent()) {
//            balanceRepository.delete(balanceToDelete.get());
//            return true;
//        }
//
//        return false; // Balance with given account ID not found
//    }

    public boolean defaultBalance(Account account) {
        try {
            Balance defaultBalance = new Balance();
            defaultBalance.setDate(String.valueOf(LocalDate.now())); // Set current date
            defaultBalance.setAmount(0.0); // Default amount
            defaultBalance.setbalanceType("CR"); // Default balance type
            defaultBalance.setAccount(account);
            balanceRepository.save(defaultBalance);
            return true;
        } catch (Exception e) {
            // Handle exceptions here
            throw e;
        }
    }
}
