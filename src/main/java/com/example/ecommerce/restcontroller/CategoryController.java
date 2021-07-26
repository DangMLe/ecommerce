package com.example.ecommerce.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ecommerce.DTO.CategoryDTO;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.service.CategoryService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    private CategoryDTO convertToDTO(Category category){
        CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
        return categoryDTO;
    }

    private Category convertToEntity(CategoryDTO categoryDTO){
        Category category = modelMapper.map(categoryDTO, Category.class);
        return category;
    }

    @GetMapping("/categories")
    List<CategoryDTO> getCategories(){
        List<Category> categories = categoryService.getCategories();
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO){
        return convertToDTO(categoryService.addCategory(convertToEntity(categoryDTO)));
    }

    @GetMapping("/category/{id}")
    CategoryDTO getCategory(@PathVariable Long id){
        return convertToDTO(categoryService.getCategory(id));
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
        categoryService.updateCategory(convertToEntity(categoryDTO), id);
    }

    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}