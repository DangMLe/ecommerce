package com.example.ecommerce.restcontroller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ecommerce.DTO.ProductDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;

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
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private ProductDTO convertToDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDecs(product.getDecs());
        productDTO.setCategory(product.getCategory().getName());
        productDTO.setUpdateDate(product.getUpdateDate());
        productDTO.setImage(product.getImage());
        return productDTO;
    }
    private Product convertToEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDecs(productDTO.getDecs());
        product.setCategory(categoryRepository.findByName(productDTO.getCategory()));
        product.setUpdateDate(productDTO.getUpdateDate());
        product.setImage(productDTO.getImage());
        return product;
    }

    @GetMapping
    List<ProductDTO> getAll(){
        List<Product> products = productService.getAllProduct();
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/product/{id}")
    ProductDTO getProduct(@PathVariable Long id){
        return convertToDTO(productService.getProductById(id));
    }

    @GetMapping("/product/category/{id}")
    List<ProductDTO> getProductByCategory(@PathVariable Long id){
        List<Product> products = productService.getProductByCategory(categoryService.getCategory(id));
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    ProductDTO newProduct(@RequestBody ProductDTO productDTO){
        return convertToDTO(productService.addProduct(convertToEntity(productDTO)));
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long id){
        productService.updateProduct(convertToEntity(productDTO), id);
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }
}
