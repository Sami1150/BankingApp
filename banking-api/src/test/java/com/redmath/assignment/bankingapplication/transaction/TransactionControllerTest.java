//package com.redmath.assignment.bankingapplication.transaction;
//
//import com.redmath.assignment.bankingapplication.account.Account;
//import org.hamcrest.Matchers;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.security.Principal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class TransactionControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TransactionService transactionService;
//
//    @Test
//    @WithMockUser(username = "user1", authorities = "USER")
//    public void testFindAllTransactions() throws Exception {
//        String username = "user1";
//
//        List<Transaction> mockTransactions = new ArrayList<>();
//        mockTransactions.add(new Transaction(1L, String.valueOf(LocalDate.parse("2023-07-01")), 100.0, "CR", "Credit transaction", new Account()));
//        mockTransactions.add(new Transaction(2L, String.valueOf(LocalDate.parse("2023-07-02")), 50.0, "CR", "Credit transaction", new Account()));
//
//        when(transactionService.findAllTransactions(any(Authentication.class)))//findAllTransactions(Mockito.any(Authentication.class)))
//                .thenReturn(mockTransactions);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transaction")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content", Matchers.hasSize(2)))
//                .andExpect(jsonPath("$.content[0].transaction_id", Matchers.is(1)))
//                .andExpect(jsonPath("$.content[0].date", Matchers.is("2023-07-01")))
//                .andExpect(jsonPath("$.content[0].amount", Matchers.is(100.0)))
//                .andExpect(jsonPath("$.content[0].transactionType", Matchers.is("CR")))
//                .andExpect(jsonPath("$.content[0].description", Matchers.is("Credit transaction")))
//                .andExpect(jsonPath("$.content[1].transaction_id", Matchers.is(2)))
//                .andExpect(jsonPath("$.content[1].date", Matchers.is("2023-07-02")))
//                .andExpect(jsonPath("$.content[1].amount", Matchers.is(50.0)))
//                .andExpect(jsonPath("$.content[1].transactionType", Matchers.is("CR")));
//    }
//
//    @Test
//    @WithMockUser(username = "user1", authorities = "USER")
//    public void testCreateTransaction() throws Exception {
//        Transaction mockTransaction = new Transaction(1L, String.valueOf(LocalDate.parse("2023-07-01")), 100.0, "CR", "Credit transaction", new Account());
//
//        when(transactionService.create(any(Transaction.class)))
//                .thenReturn(mockTransaction);
//
//        String requestBody = "{\n" +
//                "    \"transaction_id\": 1,\n" +
//                "    \"date\": \"2023-07-01\",\n" +
//                "    \"amount\": 100.0,\n" +
//                "    \"transactionType\": \"CR\",\n" +
//                "    \"description\": \"Credit transaction\",\n" +
//                "    \"account\": {\n" +
//                "        \"id\": 1,\n" +
//                "        \"name\": \"John Doe\",\n" +
//                "        \"email\": \"john@example.com\",\n" +
//                "        \"address\": \"123 Main St\"\n" +
//                "    }\n" +
//                "}";
//
//        mockMvc.perform(post("/api/v1/transaction/add")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .content(requestBody)
//                        .contentType("application/json")
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.transaction_id", Matchers.is(1)))
//                .andExpect(jsonPath("$.date", Matchers.is("2023-07-01")))
//                .andExpect(jsonPath("$.amount", Matchers.is(100.0)))
//                .andExpect(jsonPath("$.transactionType", Matchers.is("CR")))
//                .andExpect(jsonPath("$.description", Matchers.is("Credit transaction")));
//    }
//    @Test
//    @WithMockUser(username = "user1", authorities = "USER")
//    public void testAddFunds() throws Exception {
//        Transaction mockTransaction = new Transaction();
//        mockTransaction.setTransaction_id(1L);
//        mockTransaction.setDate("2023-07-10");
//        mockTransaction.setAmount(1000.0);
//        mockTransaction.setTransactionType("CR");
//
//        when(transactionService.addFunds(any(Authentication.class), Mockito.anyDouble()))
//                .thenReturn(mockTransaction);
//
//        mockMvc.perform(post("/api/v1/transaction/addfunds")
//                        .param("amount", "1000.0")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.transaction_id", Matchers.is(1)))
//                .andExpect(jsonPath("$.date", Matchers.is("2023-07-10")))
//                .andExpect(jsonPath("$.amount", Matchers.is(1000.0)))
//                .andExpect(jsonPath("$.transactionType", Matchers.is("CR")));
//    }
//    @Test
//    @WithMockUser(username = "user1", authorities = "USER")
//    public void testWithdrawFunds() throws Exception {
//        double amount = 150.0;
//        String currentDate = LocalDate.now().toString();
//
//        Transaction newTransaction = new Transaction();
//        newTransaction.setAccount(new Account());
//        newTransaction.setDate(currentDate);
//        newTransaction.setAmount(amount);
//        newTransaction.setTransactionType("DB");
//        newTransaction.setDescription("Debit Transaction");
//
//        when(transactionService.withdrawFunds(any(Authentication.class), Mockito.anyDouble()))
//                .thenReturn(newTransaction);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transaction/withdrawfunds")
//                        .param("amount", String.valueOf(amount))
//                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(currentDate)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(amount)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.transactionType", Matchers.is("DB")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Debit Transaction")));
//    }
//    @Test
//    @WithMockUser(username = "user1", authorities = "USER")
//    public void testTransferFunds() throws Exception {
//        // Define a test email for the receiver
//        String receiverEmail = "receiver@example.com";
//
//        // Create a mock sender transaction (you can customize this as needed)
//        Transaction mockSenderTransaction = new Transaction();
//        mockSenderTransaction.setTransaction_id(1L);
//        mockSenderTransaction.setDate("2023-07-10");
//        mockSenderTransaction.setAmount(500.0);
//        mockSenderTransaction.setTransactionType("DB");
//
//        // Create a mock receiver transaction (you can customize this as needed)
//        Transaction mockReceiverTransaction = new Transaction();
//        mockReceiverTransaction.setTransaction_id(2L);
//        mockReceiverTransaction.setDate("2023-07-11");
//        mockReceiverTransaction.setAmount(500.0);
//        mockReceiverTransaction.setTransactionType("CR");
//
//        // Mock the transactionService to return the sender transaction
//        when(transactionService.transferFunds(any(Authentication.class), any(Double.class), any(String.class)))
//                .thenReturn(mockSenderTransaction);
//
//        // Perform the POST request to the /api/v1/transaction/transferfunds endpoint
//        mockMvc.perform(post("/api/v1/transaction/transferfunds")
//                        .param("amount", "500.0")
//                        .param("email", receiverEmail)
//                .with(SecurityMockMvcRequestPostProcessors.csrf())
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.transaction_id").value(1L)) // Adjust the expected values accordingly
//                .andExpect(jsonPath("$.date").value("2023-07-10"))
//                .andExpect(jsonPath("$.amount").value(500.0))
//                .andExpect(jsonPath("$.transactionType").value("DB"));
//
//    }
//
//}
