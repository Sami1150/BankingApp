package com.redmath.assignment.bankingapplication.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }



    //Get All accounts or with name or emial
    @GetMapping
    public ResponseEntity<Map<String, List<Account>>> findAll(
            @RequestParam(name = "search", required = false) String search) {

        List<Account> accounts;

        if (search != null && !search.isEmpty()) {
            accounts = accountService.findAllByNameOrEmail(search);
        } else {
            accounts = accountService.findAll();
        }

        if (accounts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Map.of("content", accounts));
    }

    //Get by email:
    @GetMapping("/{email}")
    public ResponseEntity<Optional<Account>> findAllByEmail(@PathVariable("email") String email) {
        Optional<Account> account = accountService.findAllByEmail(email);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }


    //Post Mapping
    @PostMapping("/add")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account created = accountService.create(account);
        if (created ==null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(created);
    }

    //PUT Mapping
    @PutMapping("/edit")
    public ResponseEntity<Account> update(@RequestBody Account account) {
        Account updated = accountService.update(account);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    //Delete Mapping
    @DeleteMapping("/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        boolean deleted = accountService.delete(email);
        if (deleted) {
            return ResponseEntity.ok("Resource deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found or could not be deleted");
    }





}
