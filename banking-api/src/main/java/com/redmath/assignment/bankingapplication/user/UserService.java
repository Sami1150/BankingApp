package com.redmath.assignment.bankingapplication.user;

import com.redmath.assignment.bankingapplication.account.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService//, ApplicationListener<AbstractAuthenticationEvent>
{
    public static final String STATUS_ACTIVE = "ACTIVE";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserRepository repository;

    @Value("${login.attempts.max:3}")
    private int loginAttemptsMax = 3;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = repository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + userName);
        }
        boolean enabled = STATUS_ACTIVE.equals(user.getStatus());
        boolean locked = user.getLoginAttempts() == null || user.getLoginAttempts() < loginAttemptsMax;
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), enabled,
                true, true, locked, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
    }

//    @Override
//    public void onApplicationEvent(AbstractAuthenticationEvent event) {
//        if (event instanceof AuthenticationSuccessEvent success) {
//            logger.info("::security:: authentication successful for user: {}", success.getAuthentication().getName());
//        } else if (event instanceof InteractiveAuthenticationSuccessEvent success) {
//            logger.info("::security:: login successful for user: {}", success.getAuthentication().getName());
//        } else if (event instanceof AbstractAuthenticationFailureEvent failure) {
//            logger.warn("::security:: authentication failed for user: {}, reason: {}",
//                    failure.getAuthentication().getName(), String.valueOf(failure.getException()));
//        } else {
//            logger.info("::security:: authentication event for user: {}, {}", event.getAuthentication().getName(),
//                    event);
//        }
//    }
    @Cacheable("users")
    public UserDetails loadUserByUsername(String jti, String userName) throws UsernameNotFoundException {
        return loadUserByUsername(userName);
    }
    public List<User> findAll() {
        logger.debug("Fetching all accounts");
        return repository.findAll();
    }
    public User findByUsername(String username){
        logger.debug("Fetching USER by username");
        return repository.findByUserName(username);

    }
    public boolean createUser(Account account, String username, String password) {
        try {
            User newUser = new User();
            newUser.setUserName(username); // Use email as username

            newUser.setPassword("{noop}"+password); // Set default password temporarily
            newUser.setRoles("ROLE_USER"); // Default role
            newUser.setStatus("ACTIVE"); // Default status
            newUser.setLoginAttempts(0); // Default login attempts
            newUser.setLoginAt(null); // Set login time to null initially
            newUser.setAccount(account); // Associate with the account
            repository.save(newUser); // Save the new user
            return true;
        } catch (Exception e) {
            // Handle exceptions here
           throw e;
        }
    }
    public String login(String username, String password)
    {
        password="{noop}"+password;
        User user=repository.findByUserName(username);
        logger.debug(username,password);
        if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
            // Password matches, so login is successful.
            return "Login Successful";
        } else {
            // Password does not match.
            return "Login Failed: Incorrect Password";
        }
    }

    public String getRole(String username) {
        User user=repository.findByUserName(username);
        logger.debug("Role is: {}",user.getRoles());
        return user.getRoles();
    }
}