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
            throw e;
        }
    }
    public void createNewBalance(long accountId, double updatedBalance, String balanceType) {
        Balance newBalance = new Balance();
        newBalance.setAccount(accountRepository.findById(accountId).orElse(null));
        newBalance.setDate(String.valueOf(LocalDate.now()));
        newBalance.setAmount(updatedBalance);
        newBalance.setbalanceType(balanceType);
        balanceRepository.save(newBalance);
    }

    public double getBalance(Authentication authentication) {
        String username = authentication.getName();

        // Retrieve the user object using the username
        User user = userService.findByUsername(username);

        // Get the account ID from the user object
        Long accountId = user.getAccount().getId();
        balanceRepository.findByAccountId(accountId);
        // Retrieve all balances for the user's account, sorted by date in descending order
        List<Balance> balances = balanceRepository.findByAccountIdOrderByDateDesc(accountId);

        // Get the latest balance (the first balance in the sorted list)
        Optional<Balance> latestBalance = balances.stream().findFirst();

        double latestBal=latestBalance.get().getAmount();
        // If a latest balance is found, return its value; otherwise, return 0.0 (or another default value)
        return latestBal;//.orElse(0.0);
    }
}
