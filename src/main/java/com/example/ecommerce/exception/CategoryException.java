package com.example.ecommerce.exception;

public class CategoryException extends RuntimeException{
    public CategoryException(Long id){
        super("No category with the following id: "+id);
    }    
    public CategoryException(String name){
        super("No category with the following name: "+name);
    }
}
