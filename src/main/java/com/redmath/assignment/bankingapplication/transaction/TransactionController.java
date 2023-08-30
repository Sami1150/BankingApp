package com.redmath.assignment.bankingapplication.transaction;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, List<Transaction>>> findAll(
            @RequestParam(name = "search", required = false) String search) {

        List<Transaction> transactions;

        if (search != null && !search.isEmpty()) {
//            balances = balanceService.findAllByAccountEmail(search);
            transactions = transactionService.findAllTransactinos();

        } else {
            transactions = transactionService.findAllTransactinos();
        }

        if (transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of("content", transactions));
    }

    @PostMapping("/add")
    public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
        Transaction newTransaction = transactionService.create(transaction);
        if (newTransaction != null) {
            return ResponseEntity.ok(newTransaction);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Transaction> update(@RequestBody Transaction transaction) {
        Transaction updated = transactionService.update(transaction);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> delete(@PathVariable long accountId) {
        boolean deleted = transactionService.delete(accountId);
        if (deleted) {
            return ResponseEntity.ok("Resource deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found or could not be deleted");
    }


//    http://localhost:8080/api/v1/transaction/addfunds?accountId=3&amount=1000.0&description=CR
    @PostMapping("/addfunds")
    public ResponseEntity<Transaction> addFunds(@RequestParam long accountId,
                                                @RequestParam double amount,
                                                @RequestParam String description) {

//        long l=Long.parseLong(String.valueOf(accountId));
//        double a=Double.parseDouble(String.valueOf(amount));
        Transaction newTransaction = transactionService.addFunds(accountId, amount, description);
        return ResponseEntity.ok(newTransaction);
    }
}
