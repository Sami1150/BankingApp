package com.redmath.assignment.bankingapplication.transaction;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //GET Mapping
    @GetMapping
    public ResponseEntity<Map<String, List<Transaction>>> findAll(Authentication authentication) {

        List<Transaction> transactions=transactionService.findAllTransactions(authentication);

        return ResponseEntity.ok(Map.of("content", transactions));
    }

//    http://localhost:8080/api/v1/transaction/viewtransactions?accountId=2
//    @GetMapping("/viewtransactions")
//    public ResponseEntity<List<Transaction>> viewTransactions(@RequestParam long accountId) {
//        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
//        return ResponseEntity.ok(transactions);
//    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        Transaction newTransaction = transactionService.create(transaction);
        if (newTransaction != null) {
            return ResponseEntity.ok(newTransaction);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

//    @PutMapping("/edit")
//    public ResponseEntity<Transaction> update(@RequestBody Transaction transaction) {
//        Transaction updated = transactionService.update(transaction);
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @DeleteMapping("/{accountId}")
//    public ResponseEntity<String> delete(@PathVariable long accountId) {
//        boolean deleted = transactionService.delete(accountId);
//        if (deleted) {
//            return ResponseEntity.ok("Resource deleted successfully");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found or could not be deleted");
//    }


//    http://localhost:8080/api/v1/transaction/addfunds?accountId=3&amount=1000.0
    @PostMapping("/addfunds")
    public ResponseEntity<Transaction> addFunds(@RequestParam long accountId,
                                                @RequestParam double amount) {

        Transaction newTransaction = transactionService.addFunds(accountId, amount);
        return ResponseEntity.ok(newTransaction);
    }

//http://localhost:8080/api/v1/transaction/withdrawfunds?accountId=3&amount=150.0
    @PostMapping("/withdrawfunds")
    public ResponseEntity<Transaction> withdrawFunds(@RequestParam long accountId,
                                                     @RequestParam double amount) {
        Transaction newTransaction = transactionService.withdrawFunds(accountId, amount);
        return ResponseEntity.ok(newTransaction);
    }

}