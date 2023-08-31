package com.redmath.assignment.bankingapplication.account;

import com.redmath.assignment.bankingapplication.user.User;
import com.redmath.assignment.bankingapplication.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<Map<String, Optional<Account>>> findAll(Authentication authentication) {

        Optional<Account> accounts=accountService.findById(authentication);

        return ResponseEntity.ok(Map.of("content", accounts));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Map<String, List<Account>>> all() {

        List<Account> accounts=accountService.findAll();

        return ResponseEntity.ok(Map.of("content", accounts));
    }
    //Post Mapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account created = accountService.create(account);
        if (created ==null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(created);
    }

    //PUT Mapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<Account> update(@RequestBody Account account) {
        Account updated = accountService.update(account);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    //Delete Mapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        boolean deleted = accountService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Resource deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found or could not be deleted");
    }
}
