package com.example.retail_rocket.repository;

import com.example.retail_rocket.model.Cart;
import org.springframework.data.repository.CrudRepository;

public interface CartRepo extends CrudRepository<Cart,Long>
{


}
