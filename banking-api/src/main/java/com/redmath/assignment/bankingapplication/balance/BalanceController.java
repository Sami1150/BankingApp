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

//    @GetMapping("/{accountId}")
//    public ResponseEntity<Balance> findByAccountId(@PathVariable("accountId") long accountId) {
//        Balance balance = balanceService.findTopByAccountIdOrderByDateDesc(accountId);
//        if (balance == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(balance);
//    }
//    @GetMapping("/viewbalances")
//    public ResponseEntity<List<Balance>> viewTransactions(@RequestParam long accountId) {
//        List<Balance> balances = balanceService.getBalancesByAccountId(accountId);
//        return ResponseEntity.ok(balances);
//    }

//    @GetMapping("/{accountId}")
//    public ResponseEntity<Optional<Balance>> findByAccountId(@PathVariable("accountId") long accountId) {
//        Optional<Balance> balance = balanceService.findByAccountId(accountId);
//        if (balance == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(balance);
//    }

    @PostMapping("/add")
    public ResponseEntity<Balance> create(@RequestBody Balance balance) {
        Balance newBalance = balanceService.create(balance);
        if (newBalance != null) {
            return ResponseEntity.ok(newBalance);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

//    @PutMapping("/edit")
//    public ResponseEntity<Balance> update(@RequestBody Balance balance) {
//        Balance updated = balanceService.update(balance);
//        if (updated != null) {
//            return ResponseEntity.ok(updated);
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @DeleteMapping("/{accountId}")
//    public ResponseEntity<String> delete(@PathVariable long accountId) {
//        boolean deleted = balanceService.delete(accountId);
//        if (deleted) {
//            return ResponseEntity.ok("Resource deleted successfully");
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found or could not be deleted");
//    }
}