package com.redmath.assignment.bankingapplication.balance;

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
public class BalanceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BalanceService balanceService;

    @Test
    @WithMockUser(username = "user1", authorities = "USER")
    public void testFindAllBalances() throws Exception {
        String username = "user1";

        List<Balance> mockBalances = new ArrayList<>();
        mockBalances.add(new Balance(1L, String.valueOf(LocalDate.parse("2023-07-01")), 1000.0, "CR", new Account()));
        mockBalances.add(new Balance(2L, String.valueOf(LocalDate.parse("2023-07-02")), 500.0, "CR", new Account()));

        Mockito.when(balanceService.findAllBalances(Mockito.any(Authentication.class)))
                .thenReturn(mockBalances);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/balance")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].balance_id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].date", Matchers.is("2023-07-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].amount", Matchers.is(1000.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].balanceType", Matchers.is("CR")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].balance_id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].date", Matchers.is("2023-07-02")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].amount", Matchers.is(500.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].balanceType", Matchers.is("CR")));
    }

    @Test
    @WithMockUser(username = "user5", roles = "USER")
    public void testFindAllBalancesEmpty() throws Exception {
        Mockito.when(balanceService.findAllBalances(Mockito.any(Authentication.class))).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/balances"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user1", roles = "USER")
    public void testCreateBalance() throws Exception {
        Balance mockBalance = new Balance(1L, String.valueOf(LocalDate.parse("2023-07-01")), 1000.0, "CR", new Account());

        Mockito.when(balanceService.create(Mockito.any(Balance.class)))
                .thenReturn(mockBalance);

        String requestBody = "{\"balance_id\": 1, \"date\": \"2023-07-01\", \"amount\": 1000.0, \"balanceType\": \"CR\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/balance/add")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .content(requestBody)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance_id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("2023-07-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount", Matchers.is(1000.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balanceType", Matchers.is("CR")));
    }
}
