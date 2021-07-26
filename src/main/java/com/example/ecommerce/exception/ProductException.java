package com.example.ecommerce.exception;

public class ProductException extends RuntimeException {
    public ProductException(Long id){
        super("No Product with the following id:"+id);
    }
}
