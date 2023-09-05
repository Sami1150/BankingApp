package com.redmath.assignment.bankingapplication.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/account")
@CrossOrigin(origins = "http://localhost:3000",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE,RequestMethod.PUT})  // <- use your url of frontend
public class AccountController {

    @Autowired
    private AccountService accountService;
    private  final Logger logger= LoggerFactory.getLogger(getClass());

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
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Account> create(@RequestBody Account account, @RequestParam String password) {
        logger.debug("Add new account");
        Account created = accountService.create(account,password);
        if (created ==null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(created);
    }

    //PUT Mapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<Account> update(@RequestBody Account account) {
        Account updated = accountService.update(account);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    //Delete Mapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        logger.debug("Delete Mapping");
        boolean deleted = accountService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Resource deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found or could not be deleted");
    }
}
