package com.redmath.assignment.bankingapplication.transaction;

import com.redmath.assignment.bankingapplication.account.Account;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testFindAllTransactions() throws Exception {
        String username = "user1";

        List<Transaction> mockTransactions = new ArrayList<>();
        mockTransactions.add(new Transaction(1L, String.valueOf(LocalDate.parse("2023-07-01")), 100.0, "CR", "Credit transaction", new Account()));
        mockTransactions.add(new Transaction(2L, String.valueOf(LocalDate.parse("2023-07-02")), 50.0, "CR", "Credit transaction", new Account()));

        Mockito.when(transactionService.findAllTransactions(Mockito.any(Authentication.class)))//findAllTransactions(Mockito.any(Authentication.class)))
                .thenReturn(mockTransactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transaction")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].transaction_id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].date", Matchers.is("2023-07-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].amount", Matchers.is(100.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].transactionType", Matchers.is("CR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description", Matchers.is("Credit transaction")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].transaction_id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].date", Matchers.is("2023-07-02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].amount", Matchers.is(50.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].transactionType", Matchers.is("CR")));
    }

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testCreateTransaction() throws Exception {
        Transaction mockTransaction = new Transaction(1L, String.valueOf(LocalDate.parse("2023-07-01")), 100.0, "CR", "Credit transaction", new Account());

        Mockito.when(transactionService.create(Mockito.any(Transaction.class)))
                .thenReturn(mockTransaction);

        String requestBody = "{\n" +
                "    \"transaction_id\": 1,\n" +
                "    \"date\": \"2023-07-01\",\n" +
                "    \"amount\": 100.0,\n" +
                "    \"transactionType\": \"CR\",\n" +
                "    \"description\": \"Credit transaction\",\n" +
                "    \"account\": {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"John Doe\",\n" +
                "        \"email\": \"john@example.com\",\n" +
                "        \"address\": \"123 Main St\"\n" +
                "    }\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transaction/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(requestBody)
                        .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transaction_id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("2023-07-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(100.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType", Matchers.is("CR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Credit transaction")));
    }
}
