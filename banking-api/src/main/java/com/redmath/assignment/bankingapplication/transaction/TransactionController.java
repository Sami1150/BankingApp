package com.redmath.assignment.bankingapplication.transaction;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE,RequestMethod.PUT})
@RequestMapping("api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private  final Logger logger= LoggerFactory.getLogger(getClass());

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //GET Mapping
    @GetMapping
    public ResponseEntity<Map<String, List<Transaction>>> findAll(Authentication authentication)
    {

        List<Transaction> transactions=transactionService.findAllTransactions(authentication);

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


//    http://localhost:8080/api/v1/transaction/addfunds?amount=1000.0
    @PostMapping("/addfunds")
    public ResponseEntity<Transaction> addFunds(Authentication authentication,
                                                @RequestParam double amount) {
        logger.debug("This is my addFunds function in controller");

        Transaction newTransaction = transactionService.addFunds(authentication, amount);
        return ResponseEntity.ok(newTransaction);
    }

//http://localhost:8080/api/v1/transaction/withdrawfunds?amount=150.0
    @PostMapping("/withdrawfunds")
    public ResponseEntity<Transaction> withdrawFunds(Authentication authentication,
                                                     @RequestParam double amount) {
        logger.debug("This is my withdraw function in controller");

        Transaction newTransaction = transactionService.withdrawFunds(authentication, amount);
        return ResponseEntity.ok(newTransaction);
    }
    @PostMapping("/transferfunds")
    public ResponseEntity<Transaction> transferFunds(Authentication authentication,
                                                     @RequestParam double amount,
                                                     @RequestParam String email)
    {
        logger.debug("Transfer funds in Controller");
        Transaction newTransaction = transactionService.transferFunds(authentication, amount,email);
        return ResponseEntity.ok(newTransaction);
    }

}
