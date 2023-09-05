package com.redmath.assignment.bankingapplication.transaction;

import com.redmath.assignment.bankingapplication.account.Account;
import com.redmath.assignment.bankingapplication.balance.Balance;
import com.redmath.assignment.bankingapplication.balance.BalanceService;
import com.redmath.assignment.bankingapplication.account.AccountRepository;
import com.redmath.assignment.bankingapplication.user.User;
import com.redmath.assignment.bankingapplication.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private  AccountRepository accountRepository;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private UserService userService;
    private  final Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Transaction> findAllTransactions(Authentication authentication)
    {
        logger.debug("Fetching all transactions");
        String username = authentication.getName();

        // Retrieve the user object using the username
        User user = userService.findByUsername(username);

        // Get the account ID from the user object
        Long accountId = user.getAccount().getId();
        return transactionRepository.findByAccountId(accountId);
    }


    // Post Mapping
    public Transaction create(Transaction transaction) {
        logger.info("Transaction with account ID {} is added. ", transaction.getTransaction_id());
        return transactionRepository.save(transaction);
    }


    public Transaction addFunds(Authentication authentication, double amount) {
        String username = authentication.getName();

        // Retrieve the user object using the username
        User user = userService.findByUsername(username);

        // Get the account ID from the user object
        Long accountId = user.getAccount().getId();

        return performTransaction(accountId, amount, "Credit Transaction", "CR");
    }

    public Transaction withdrawFunds(Authentication authentication, double amount) {
        String username = authentication.getName();

        // Retrieve the user object using the username
        User user = userService.findByUsername(username);

        // Get the account ID from the user object
        Long accountId = user.getAccount().getId();

        return performTransaction(accountId, amount, "Debit Transaction", "DB");
    }

    public Transaction performTransaction(long accountId, double amount, String description, String transactionType) {

            Balance latestBalance = balanceService.findTopByAccountIdOrderByDateDesc(accountId);
            double updatedBalance = transactionType.equals("CR") ? latestBalance.getAmount() + amount : latestBalance.getAmount() - amount;

            String currentDate = String.valueOf(LocalDate.now());
            String balanceType = updatedBalance >= 0 ? "CR" : "DB";

            if (!currentDate.equals(latestBalance.getDate())) {
                balanceService.createNewBalance(accountId, updatedBalance, balanceType);
            } else {
                latestBalance.setAmount(updatedBalance);
                latestBalance.setbalanceType(balanceType);
                balanceService.update(latestBalance);
            }

            Transaction newTransaction = createTransaction(accountId, currentDate, amount, transactionType, description);
            return transactionRepository.save(newTransaction);
        }



    private Transaction createTransaction(long accountId, String currentDate, double amount, String transactionType, String description) {
        Transaction newTransaction = new Transaction();
        newTransaction.setAccount(accountRepository.findById(accountId).orElse(null));
        newTransaction.setDate(currentDate);
        newTransaction.setAmount(amount);
        newTransaction.setTransactionType(transactionType);
        newTransaction.setDescription(description);
        return newTransaction;
    }


    public Boolean transferFunds(Authentication authentication, double amount, String receiverEmail) {
        String username = authentication.getName();

        // Retrieve the user object using the username
        User user = userService.findByUsername(username);

        // Get the account ID from the user object
        Long senderAccountId = user.getAccount().getId();

        // Retrieve the sender and receiver account objects using their emails
        Optional<Account> AccountOfReceiver = accountRepository.findAllByEmail(receiverEmail);

        double balance=balanceService.getBalance(authentication)-amount;
        logger.debug("Balance in service is: {}",balance);
        if(balance<0){
            return false;
        }
        try {
            if (AccountOfReceiver.isPresent()) {
                // Get the account IDs of the sender and receiver
                Long receiverAccountId = AccountOfReceiver.get().getId();
                logger.debug("Account Id of sender is {} and receiver is {} :", senderAccountId, receiverAccountId);

                // Deduct funds from the sender's account
                Transaction senderTransaction = performTransaction(senderAccountId, amount, "Debit Transaction", "DB");

                // Add funds to the receiver's account
                Transaction receiverTransaction = performTransaction(receiverAccountId, amount, "Credit Transaction", "CR");

                // Save both transactions to the database
                create(senderTransaction);
                create(receiverTransaction);

                return true; // You can return any of the transactions as needed
            } else {
                throw new Exception("Receiver account does not exist.");
            }

        } catch (Exception ex) {
            logger.error("Receiver account not found!");
        }
        return false;
    }
}
