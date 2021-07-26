package com.example.ecommerce.exception;

public class OrderException extends RuntimeException{

    public OrderException(Long id) {
        super("No order with the following id: "+ id);
    }
    
}
