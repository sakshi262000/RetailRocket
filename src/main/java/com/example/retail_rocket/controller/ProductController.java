package com.example.retail_rocket.controller;

import com.example.retail_rocket.Utils.RandomValues;
import com.example.retail_rocket.model.Products;
import com.example.retail_rocket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
     ProductService productService;

    //http://localhost:8086/api/products?pageNumber=1&pageSize=5
    @GetMapping("/products")
    public List<Products> getAllProducts(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber,pageSize);
        return productService.findAll(p).getContent();
    }

    @PostMapping("/products")
    public ResponseEntity<Products> addProduct(@RequestBody Products product) {
        product.setProductCode(RandomValues.generateRandomValues());
        return productService.addProduct(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable Long id, @RequestBody Products updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }


}
