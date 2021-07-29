package com.example.ecommerce.security.services;

import com.example.ecommerce.entity.Account;
import com.example.ecommerce.repository.AccountRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    public UserDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Account user = accountRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
);
                

        return UserDetailsImpl.build(user);
    }
    
    
}
