package com.example.retail_rocket.controller;

import com.example.retail_rocket.Utils.RandomValues;
import com.example.retail_rocket.model.Cart;
import com.example.retail_rocket.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class CartController {

CartService CartService;

    @PostMapping("/addCart")
         ResponseEntity<Cart> addItem(@RequestBody Cart cart)
        {
            cart.setCustomerid(RandomValues.generateRandomHex());
            return CartService.additems(cart);
 
        }

        @GetMapping("/getCart")
     ResponseEntity<ArrayList<Cart>>getAllItems()
        {
            return CartService.getAll();
        }

    	@DeleteMapping("/cart/{productId}")
     ResponseEntity<Void> deleteItem(@PathVariable Long id)
    {
        return CartService.deletebyid(id);
    }

    @PutMapping("/cart/{productId}")
    ResponseEntity<Cart> updateItem(Long id,Cart cart)
    {
        return CartService.updateById(id,cart);
    }
}
