package com.redmath.assignment.bankingapplication.account;


import com.redmath.assignment.bankingapplication.balance.BalanceService;
import com.redmath.assignment.bankingapplication.user.User;
import com.redmath.assignment.bankingapplication.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Logger
    private  final Logger logger= LoggerFactory.getLogger(getClass());


    public Optional findById(Authentication authentication) {
        logger.debug("Fetching all accounts");
        String username = authentication.getName();

        Optional account;
        logger.debug("Fetching account with username {}", username );
        if(username.equals("admin"))
        {
            List<Account> accounts = accountRepository.findAll();
            return Optional.ofNullable(accounts.isEmpty() ? null : accounts);

        }
        else if (username.contains("user")){

            // Retrieve the user object using the username
            User user = userService.findByUsername(username);

            // Get the account ID from the user object
            Long accountId = user.getAccount().getId();

            // Retrieve the account details using the account ID
            account = accountRepository.findById(accountId);
            return account;
        }
        return null;
    }
    public List<Account> findAll() {
        logger.info("All account details are: ");
        return accountRepository.findAll();
    }

    public Optional<Account> findByEmail(String email){
        logger.debug("Fetching Account By Email");
        return accountRepository.findAllByEmail(email);
    }
    //Post Mapping
    public Account create(Account account, String username, String password) {

        logger.info("Account with email {} is added. ", account.getId());

        // Save the account in the account table
        Account savedAccount = accountRepository.save(account);


        // Save the default balance and userService entry in the balance table
        balanceService.defaultBalance(account);
        userService.createUser(account, username, password);
        return savedAccount;
    }

    //Put Mapping
    public Account update(Account newAccountData) {
        logger.info("Account with email {} is updated. ", newAccountData.getEmail());

        Optional<Account> existingAccount = accountRepository.findById(newAccountData.getId());

        if (existingAccount.isPresent()) {
            Account accountToUpdate = existingAccount.get();

            accountToUpdate.setEmail(newAccountData.getEmail());
            accountToUpdate.setName(newAccountData.getName());
            accountToUpdate.setAddress(newAccountData.getAddress());

            return accountRepository.save(accountToUpdate);
        }

        return null;
    }

    //Delete Mapping

    public boolean delete(long id) {
        Optional<Account> accountToDelete = accountRepository.findById(id);

        if (accountToDelete.isPresent()) {
            Account account = accountToDelete.get();
            accountRepository.delete(account);

            return true;
        }

        return false;
    }
}
