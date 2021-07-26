package com.example.ecommerce.exception;



public class AccountException extends RuntimeException{
    public AccountException(Long id){
        super("Could not find the account "+ id);
    }
    public AccountException(String username) {
        super("Could not find the account "+ username);
    }
}
