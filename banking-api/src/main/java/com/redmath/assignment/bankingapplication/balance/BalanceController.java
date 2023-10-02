package com.redmath.assignment.bankingapplication.balance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Balance>>> findAll(Authentication authentication) {

        List<Balance> balances;
            balances = balanceService.findAllBalances(authentication);

        if (balances.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of("content", balances));
    }
    @GetMapping("/getbalance")
    public double getBalance(Authentication authentication){
        double balance=balanceService.getBalance(authentication);
        return balance;
    }

    @PostMapping("/add")
    public ResponseEntity<Balance> create(@RequestBody Balance balance) {
        Balance newBalance = balanceService.create(balance);
        if (newBalance != null) {
            return ResponseEntity.ok(newBalance);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}