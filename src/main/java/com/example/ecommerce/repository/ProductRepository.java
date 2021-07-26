package com.example.ecommerce.repository;

import java.util.List;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    Product findByName(String name);
    
}