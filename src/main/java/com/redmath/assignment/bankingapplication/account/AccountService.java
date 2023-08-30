package com.redmath.assignment.bankingapplication.account;

import com.redmath.assignment.bankingapplication.Balance.Balance;
import com.redmath.assignment.bankingapplication.Balance.BalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //Constructor
    public AccountService(AccountRepository accountRepository, BalanceRepository balanceRepository) {
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
    }

    //Logger
    private  final Logger logger= LoggerFactory.getLogger(getClass());


    public List<Account> findAll() {
        logger.debug("Fetching all accounts");
        return accountRepository.findAll();
    }

    //Find by name or email
    public List<Account> findAllByNameOrEmail(String search) {
        logger.debug("Searching account with name or email containing: {}", search.replaceAll("[\r\n]",""));

        return jdbcTemplate.query("SELECT * FROM account WHERE name LIKE '%' || ? || '%' OR email LIKE '%' || ? || '%'",
                new Object[]{search, search},
                (rs, rowNum) -> {
                    Account account = new Account();
                    account.setId(rs.getLong("id"));
                    account.setPassword(rs.getString("password"));
                    account.setName(rs.getString("name"));
                    account.setEmail(rs.getString("email"));
                    account.setAddress(rs.getString("address"));

                    return account;
                });
    }
    public Optional<Account> findAllByEmail(String email) {
        logger.info("Account details with email {} is: ", email);
        return accountRepository.findAllByEmail(email);
    }

    //Post Mapping
    public Account create(Account account) {

        logger.info("Account with email {} is added. ", account.getEmail());

        // Save the account in the account table
        Account savedAccount = accountRepository.save(account);

        // Create a new Balance entry with default values and associate it with the account
        Balance defaultBalance = new Balance();
        defaultBalance.setBalance_id(savedAccount.getId()+10);
        defaultBalance.setDate(String.valueOf(LocalDate.now())); // Set current date
        defaultBalance.setAmount(0.0); // Default amount
        defaultBalance.setbalanceType("CR"); // Default balance type
        defaultBalance.setAccount(account);
        // Save the default balance entry in the balance table
        balanceRepository.save(defaultBalance);

        return savedAccount;
    }

    //Put Mapping
    public Account update(Account newAccountData) {
        logger.info("Account with email {} is updated. ", newAccountData.getEmail());

        Optional<Account> existingAccount = accountRepository.findAllByEmail(newAccountData.getEmail());

        if (existingAccount.isPresent()) {
            Account accountToUpdate = existingAccount.get();

            accountToUpdate.setId(newAccountData.getId());
            accountToUpdate.setName(newAccountData.getName());
            accountToUpdate.setAddress(newAccountData.getPassword());
            accountToUpdate.setPassword(newAccountData.getPassword());

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
