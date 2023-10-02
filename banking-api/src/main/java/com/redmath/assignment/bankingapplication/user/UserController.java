package com.redmath.assignment.bankingapplication.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, List<User>>> findAll() {

        List<User> users=userService.findAll();


        return ResponseEntity.ok(Map.of("content", users));
    }
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
//        String username = credentials.get("username");
//        String password = credentials.get("password");
//        logger.debug("Username is {} and password is {}:",username, password);
//        String loginResult = userService.login(username, password);
//        logger.debug("loginResult is {} : ",loginResult);
//        if (loginResult.equals("Login Successful")) {
//            return ResponseEntity.ok(loginResult);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginResult);
//        }
//    }
    @GetMapping("/role")
    public String getRole(Authentication authentication){
        String username= authentication.getName();
        String role=userService.getRole(username);
        return role;
    }

}
