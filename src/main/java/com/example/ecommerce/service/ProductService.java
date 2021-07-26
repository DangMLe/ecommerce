package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;

import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    List<Product> getAllProduct();
    List<Product> getProductByCategory(Category Category);
    Product getProductById(Long id);
    Product getProductByName(String name);
    Product addProduct(Product product);
    Product updateProduct(Product product, Long id);
    void deleteProduct(Long id);
    
}
