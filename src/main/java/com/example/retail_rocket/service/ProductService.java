package com.example.retail_rocket.service;

import com.example.retail_rocket.model.Products;
import com.example.retail_rocket.repository.ProductRepo;
import com.example.retail_rocket.repository.PaginationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public List<Products> findAll() {
        List<Products> productList = new ArrayList<>();
        Iterable<Products> productsIterable = productRepo.findAll();

        for (Products product : productsIterable) {
            productList.add(product);
        }

        return productList;
    }

    // Method to add a new product
    public ResponseEntity<Products> addProduct(Products product) {
        Products savedProduct = productRepo.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Method to update product details
    public ResponseEntity<Products> updateProduct(Long id, Products updatedProduct) {
        return productRepo.findById(id)
                .map(product -> {

                    Optional.ofNullable(updatedProduct.getName()).ifPresent(product::setName);
                    Optional.ofNullable(updatedProduct.getDescription()).ifPresent(product::setDescription);
                    Optional.ofNullable(updatedProduct.getCategory()).ifPresent(product::setCategory);
                    Optional.ofNullable(updatedProduct.getStock())
                            .filter(stock->stock.compareTo(0L)!=0)
                            .ifPresent(product::setStock);
                    Optional.ofNullable(updatedProduct.getPrice())
                            .filter(price->price.compareTo(BigDecimal.ZERO)!=0)
                            .ifPresent(product::setPrice);

                    return new ResponseEntity<>(productRepo.save(product), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if product not found
        }
    }
    public Products isProductIsAvailable(String productCode){
        return productRepo.findByProductCode(productCode);
    }

}
