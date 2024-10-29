package com.example.retail_rocket.controller;

import com.example.retail_rocket.Utils.RandomValues;
import com.example.retail_rocket.model.Products;
import com.example.retail_rocket.repository.PaginationRepo;
import com.example.retail_rocket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
     ProductService productService;
    @Autowired
   PaginationRepo PaginationRepo;


    @GetMapping("/products")
    public Page<Products> getAllProducts(Pageable pageable) {
        return PaginationRepo.findAll(pageable);
    }

    @PostMapping("/products")
    public ResponseEntity<Products> addProduct(@RequestBody Products product) {
        product.setProductCode(RandomValues.generateRandomValues());
        return productService.addProduct(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody Products updatedProduct) {
        return productService.updateProduct(id, updatedProduct); // Delegate to the service layer
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id); // Delegate to the service layer
    }


}
