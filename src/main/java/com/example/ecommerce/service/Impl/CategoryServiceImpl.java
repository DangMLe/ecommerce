package com.example.ecommerce.service.Impl;

import java.util.List;

import com.example.ecommerce.entity.Category;
import com.example.ecommerce.exception.CategoryException;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Override
    public List<Category> getCategories() {
        // TODO Auto-generated method stub
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        // TODO Auto-generated method stub
        return categoryRepository.findById(id).orElseThrow(()-> new CategoryException(id));
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        // TODO Auto-generated method stub
        return categoryRepository.findByName(categoryName);
    }

    @Override
    public Category addCategory(Category category) {
        // TODO Auto-generated method stub
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category newCategory, Long id) {
        // TODO Auto-generated method stub
        return categoryRepository.findById(id).map(category->{
            category.setName(newCategory.getName());
            return category;
        }).orElseThrow(()-> new CategoryException(id));
        
    }

    @Override
    public void deleteCategory(Long id) {
        // TODO Auto-generated method stub
        categoryRepository.deleteById(id);
    }
    
}
