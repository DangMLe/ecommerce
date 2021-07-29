package com.example.ecommerce.service.Impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ProductException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    

    @Override
    public List<Product> getAllProduct() {
        // TODO Auto-generated method stub
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(Category category) {
        // TODO Auto-generated method stub

        return productRepository.findByCategory(category);
    }

    @Override
    public Product getProductById(Long id) {
        // TODO Auto-generated method stub
        return productRepository.findById(id).orElseThrow(()-> new ProductException(id));
    }

    @Override
    public Product getProductByName(String name) {
        // TODO Auto-generated method stub
        return productRepository.findByName(name);
    }

    @Override
    public Product addProduct(Product product) {
        // TODO Auto-generated method stub
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product newProduct, Long id) {
        // TODO Auto-generated method stub
        return productRepository.findById(id).map(product->{
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setDecs(newProduct.getDecs());
            product.setCategory(newProduct.getCategory());
            product.setUpdateDate(newProduct.getUpdateDate());
            product.setImage(newProduct.getImage());
            return productRepository.save(product);
        }).orElseThrow(()-> new ProductException(id));
    }

    @Override
    public void deleteProduct(Long id) {
        // TODO Auto-generated method stub
        productRepository.deleteById(id);;
    }
    
}
