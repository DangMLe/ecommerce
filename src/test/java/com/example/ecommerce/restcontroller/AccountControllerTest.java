package com.example.ecommerce.restcontroller;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.ecommerce.entity.Account;

import com.example.ecommerce.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AccountController accountController;

    @Mock
    AccountService accountService;

    private List<Account> accountList = new ArrayList<Account>();

    @BeforeEach
    void setUp(){
        Account account1 = new Account("Dang", "Admin", "Dang", "Le", "ROLE_ADMIN", 23, "Leminhdang0701@gmail.com", "address", "123456789",null);
        Account account2 = new Account("Test", "Test", "Testfirtname", "TestLastname", "ROLE_USER", 22, "Test@Testmail.com", "address", "987654321", null);
        this.accountList.add(account1);
        this.accountList.add(account2);
    }

    @Test
    @WithMockUser(username="Dang", password = "Admin", roles="ADMIN")
    public void getAccountListTest() throws Exception{
        when(accountService.retrieveAccounts()).thenReturn(accountList);
        this.mockMvc.perform(get("/api/accounts")).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()").isNotEmpty());
    }

    @Test
    @WithMockUser(username="Dang", password = "Admin", roles="ADMIN")
    public void addAccountTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/accounts")
        .content(asJsonString(new Account("newAccount", "newAccount", "Testfirtname", "TestLastname", "ROLE_USER", 25, "Test@Testmail.com", "address", "987654321", null)))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful());
    }
    
    public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
