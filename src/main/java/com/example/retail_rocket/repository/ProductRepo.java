package com.example.retail_rocket.repository;


import com.example.retail_rocket.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ProductRepo  extends JpaRepository<Products,Long> {
    Products findByProductCode(String productCode);
}
