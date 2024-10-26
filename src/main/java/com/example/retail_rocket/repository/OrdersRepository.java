package com.example.retail_rocket.repository;

import com.example.retail_rocket.model.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Orders,Long> {
}
