package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.entity.Account;

import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    public List<Account> retrieveAccounts();

    public Account getAccount(Long accountId);

    public Account getAccountByName(String name);

    public Account saveAccount(Account account);

    public void deleteAccount(Long accountId);

    public Account updateAccount(Account account, Long accountid);
}