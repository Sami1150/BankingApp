package com.redmath.assignment.bankingapplication.Balance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private  final Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Constructor
    public BalanceService(BalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public List<Balance> findAllBalances() {
        logger.debug("Fetching all balances");
        return balanceRepository.findAll();
    }



//    public Optional<Balance> findByAccountId(long accountId) {
//        logger.info("Balance details with account ID {} are: ", accountId);
//        return balanceRepository.findById()  //.findById(accountId);
//    }

    // Post Mapping
    public Balance create(Balance balance) {
        logger.info("Balance with account ID {} is added. ", balance.getBalance_id());
        Balance newBalance = new Balance();
        newBalance.setDate(String.valueOf(LocalDate.now())); // Set current date
        newBalance.setAmount(0.0); // Set balance to 0
        newBalance.setbalanceType("CR"); // Set balanceType to CR

        return balanceRepository.save(newBalance);
    }


    // Put Mapping
    public Balance update(Balance newBalanceData) {
        logger.info("Balance with account ID {} is updated. ", newBalanceData.getBalance_id());

        Optional<Balance> existingBalance = balanceRepository.findById(newBalanceData.getBalance_id());

        if (existingBalance.isPresent()) {
            Balance balanceToUpdate = existingBalance.get();
            balanceToUpdate.setDate(newBalanceData.getDate());
            balanceToUpdate.setAmount(newBalanceData.getAmount());
            balanceToUpdate.setbalanceType(newBalanceData.getBalanceType());

            return balanceRepository.save(balanceToUpdate);
        }

        return null;
    }

    // Delete Mapping
    public boolean delete(long accountId) {
        Optional<Balance> balanceToDelete = balanceRepository.findById(accountId);

        if (balanceToDelete.isPresent()) {
            balanceRepository.delete(balanceToDelete.get());
            return true;
        }

        return false; // Balance with given account ID not found
    }
}
