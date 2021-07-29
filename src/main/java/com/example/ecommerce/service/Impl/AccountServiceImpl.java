package com.example.ecommerce.service.Impl;


import java.util.List;


import com.example.ecommerce.entity.Account;
import com.example.ecommerce.exception.AccountException;
import com.example.ecommerce.repository.AccountRepository;
import com.example.ecommerce.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{
    
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public List<Account> retrieveAccounts(){
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(()-> new AccountException(accountId));
        
    }

    @Override
    public Account getAccountByName(String username) {
        // TODO Auto-generated method stub
        return accountRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with -> username or email : " + username));
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }

    @Override
    public Account updateAccount(Account newAccount, Long accountId) {
        return accountRepository.findById(accountId).map(account->{
            account.setName(newAccount.getName());
            account.setPassword(newAccount.getPassword());
            account.setFirstname(newAccount.getFirstname());
            account.setLastname(newAccount.getLastname());
            account.setAge(newAccount.getAge());
            account.setRole(newAccount.getRole());
            account.setEmail(newAccount.getEmail());
            account.setPhonenum(newAccount.getPhonenum());
            account.setAvatar(newAccount.getAvatar());
            return accountRepository.save(account);
        })
        .orElseThrow(()-> new AccountException(accountId));
    }
}
