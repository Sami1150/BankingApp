package com.redmath.assignment.bankingapplication.transaction;

import com.redmath.assignment.bankingapplication.Balance.Balance;
import com.redmath.assignment.bankingapplication.Balance.BalanceRepository;
import com.redmath.assignment.bankingapplication.Balance.BalanceService;
import com.redmath.assignment.bankingapplication.account.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private  final Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Constructor
//    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
//        this.transactionRepository = transactionRepository;
//        this.accountRepository=accountRepository;
//    }
    public List<Transaction> findAllTransactinos() {
        logger.debug("Fetching all transactions");
        return transactionRepository.findAll();
    }

    // Post Mapping
    public Transaction create(Transaction transaction) {
        logger.info("Transaction with account ID {} is added. ", transaction.getTransaction_id());
        return transactionRepository.save(transaction);
    }


    // Put Mapping
    public Transaction update(Transaction newTransactionData) {
        logger.info("Transaction with account ID {} is updated. ", newTransactionData.getTransaction_id());

        Optional<Transaction> existingTransaction = transactionRepository.findById(newTransactionData.getTransaction_id());

        if (existingTransaction.isPresent()) {
            Transaction transactionToUpdate = existingTransaction.get();
            transactionToUpdate.setDate(newTransactionData.getDate());
            transactionToUpdate.setAmount(newTransactionData.getAmount());
            transactionToUpdate.setTransaction_id(newTransactionData.getTransaction_id());
            transactionToUpdate.setTransactionType(newTransactionData.getTransactionType());
            transactionToUpdate.setDescription(newTransactionData.getDescription());

            return transactionRepository.save(transactionToUpdate);
        }

        return null;
    }

    // Delete Mapping
    public boolean delete(long accountId) {
        Optional<Transaction> TransactionToDelete = transactionRepository.findById(accountId);

        if (TransactionToDelete.isPresent()) {
            transactionRepository.delete(TransactionToDelete.get());
            return true;
        }

        return false; // Transaction with given account ID not found
    }

    public Transaction addFunds(long accountId, double amount, String description) {
        // Fetch the latest balance record for the account
        Balance latestBalance = balanceService.findTopByAccountIdOrderByDateDesc(accountId);

        double updatedBalance = latestBalance.getAmount() + amount;

        String currentDate = String.valueOf(LocalDate.now());
        // Compare currentDate and latestBalance date
        if (currentDate.equals(latestBalance.getDate())) {
            latestBalance.setbalanceType("CR");
            latestBalance.setAmount(updatedBalance);
            balanceService.update(latestBalance);
        } else {
            // Create a new balance record
            Balance newBalance = new Balance();
            newBalance.setAccount(accountRepository.findById(accountId).orElse(null));
            newBalance.setDate(String.valueOf(LocalDate.now()));
            newBalance.setAmount(updatedBalance);
            newBalance.setbalanceType("CR");
            balanceService.create(newBalance);
        }

        // Create a new transaction
        Transaction newTransaction = new Transaction();
        newTransaction.setAccount(accountRepository.findById(accountId).orElse(null));
        newTransaction.setDate(String.valueOf(currentDate));
        newTransaction.setAmount(amount);
        newTransaction.setTransactionType("CR"); // Assuming it's a credit
        newTransaction.setDescription(description);

        return transactionRepository.save(newTransaction);
    }
}
