package com.example.retail_rocket.repository;

import com.example.retail_rocket.model.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends CrudRepository<Cart, Long> {
    List<Cart> findByCustomerId(String customerId);
    List<Cart> findByCustomerIdAndProductCode(String customerId, String productCode);
}
