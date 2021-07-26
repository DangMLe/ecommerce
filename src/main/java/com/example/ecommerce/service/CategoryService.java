package com.example.ecommerce.service;

import java.util.List;

import com.example.ecommerce.entity.Category;

import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    List<Category> getCategories();
    Category getCategory(Long id);
    Category getCategoryByName(String categoryName);
    Category addCategory(Category category);
    Category updateCategory(Category category, Long id);
    void deleteCategory(Long id);
}
