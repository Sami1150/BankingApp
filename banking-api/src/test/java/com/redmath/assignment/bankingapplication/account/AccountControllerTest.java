//package com.redmath.assignment.bankingapplication.account;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.request.RequestPostProcessor;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.junit.jupiter.api.Test;
//
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class AccountControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private AccountService accountService;
//
//    private RequestPostProcessor testUser(String userName, String authority) {
//        return SecurityMockMvcRequestPostProcessors.user(userName).authorities(new SimpleGrantedAuthority(authority));
//    }
//
//    @Test
//    @WithMockUser(username = "user1", authorities = "USER")
//    public void testUser1FindAll() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account")
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(MockMvcResultMatchers.status().isConflict())
//                .andReturn();
//    }

//    @Test
//    @WithMockUser(username = "admin", authorities = "ADMIN")
//    public void testAdminFindAll() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/account")
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
//
//                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
//                .andReturn();
//    }
//
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    public void testCreateAccount() throws Exception {
//        Account inputAccount = new Account();
//        inputAccount.setName("John Doe");
//        inputAccount.setEmail("john@example.com");
//        inputAccount.setAddress("123 Main St");
//
//        Account createdAccount = new Account();
//        createdAccount.setId(1L);
//        createdAccount.setName("John Doe");
//        createdAccount.setEmail("john@example.com");
//        createdAccount.setAddress("123 Main St");
//
//        Mockito.when(accountService.create(Mockito.any(Account.class))).thenReturn(createdAccount);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/account/add")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType("application/json")
//                        .content(asJsonString(inputAccount)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john@example.com"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("123 Main St"));
//    }
//
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    public void testUpdateAccount() throws Exception {
//        Account inputAccount = new Account();
//        inputAccount.setId(1L);
//        inputAccount.setName("Updated John Doe");
//        inputAccount.setEmail("updated-john@example.com");
//        inputAccount.setAddress("456 Updated St");
//
//        Account updatedAccount = new Account();
//        updatedAccount.setId(1L);
//        updatedAccount.setName("Updated John Doe");
//        updatedAccount.setEmail("updated-john@example.com");
//        updatedAccount.setAddress("456 Updated St");
//
//        Mockito.when(accountService.update(Mockito.any(Account.class))).thenReturn(updatedAccount);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/account/edit")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType("application/json")
//                        .content(asJsonString(inputAccount)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated John Doe"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("updated-john@example.com"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("456 Updated St"));
//    }
//
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    public void testUpdateNonExistingAccount() throws Exception {
//        Account inputAccount = new Account();
//        inputAccount.setId(999L); // Non-existing ID
//        inputAccount.setName("Updated John Doe");
//        inputAccount.setEmail("updated-john@example.com");
//        inputAccount.setAddress("456 Updated St");
//
//        Mockito.when(accountService.update(Mockito.any(Account.class))).thenReturn(null);
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/account/edit")
//                        .with(SecurityMockMvcRequestPostProcessors.csrf())
//                        .contentType("application/json")
//                        .content(asJsonString(inputAccount)))
//                .andExpect(MockMvcResultMatchers.status().isNotFound());
//    }
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    public void testDeleteAccount() throws Exception {
//        long accountIdToDelete = 1L;
//
//        Mockito.when(accountService.delete(Mockito.anyLong())).thenReturn(true);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/account/{id}", accountIdToDelete)
//                .with(testUser("admin", "ADMIN"))
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Resource deleted successfully"));
//    }
//
//    @Test
//    @WithMockUser(authorities = "ADMIN")
//    public void testDeleteNonExistingAccount() throws Exception {
//        long nonExistingAccountId = 999L;
//
//        Mockito.when(accountService.delete(Mockito.anyLong())).thenReturn(false);
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/account/{id}", nonExistingAccountId)
//                .with(testUser("admin", "ADMIN"))
//                .with(SecurityMockMvcRequestPostProcessors.csrf()))
//                .andExpect(MockMvcResultMatchers.status().isNotFound())
//                .andExpect(MockMvcResultMatchers.content().string("Resource not found or could not be deleted"));
//    }
//
//    // Helper method to convert object to JSON string
//    private static String asJsonString(Object obj) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.writeValueAsString(obj);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
