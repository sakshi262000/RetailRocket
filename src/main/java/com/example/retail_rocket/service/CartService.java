package com.example.retail_rocket.service;

import com.example.retail_rocket.model.Cart;
import com.example.retail_rocket.repository.CartRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CartService {
    CartRepo CartRepo;
    public ResponseEntity<Cart> additems(Cart Cart)
    {
        return new ResponseEntity<>(CartRepo.save(Cart),HttpStatus.CREATED);
    }

    public ResponseEntity<ArrayList<Cart>> getAll()
    {
        ArrayList<Cart> cart=new ArrayList<>();
        for (Cart CartItem : CartRepo.findAll()) {
            cart.add(CartItem);
        }
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
   public ResponseEntity<Void> deletebyid(Long id)
    {
        if(CartRepo.existsById(id))
        {
            CartRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Cart>updateById(Long Id,Cart cart)
    {
        return CartRepo.findById(Id)
                .map(cartobj->
                {
                    Optional.ofNullable(cart.getProductcode()).ifPresent(cartobj::setProductcode);
                    Optional.ofNullable(cart.getCustomerid()).ifPresent(cartobj::setCustomerid);
                    Optional.ofNullable(cart.getProductname()).ifPresent(cartobj::setProductname);
                    Optional.ofNullable(cart.getAmount()).ifPresent(cartobj::setAmount);
                    Optional.ofNullable(cart.getQuantity()).ifPresent(cartobj::setQuantity);
                    Optional.ofNullable(cart.getQuantity()).ifPresent(cartobj::setQuantity);
                    Optional.ofNullable(cart.getUpdatedAt()).ifPresent(cartobj::setUpdatedAt);
                    return new ResponseEntity<>(CartRepo.save(cartobj),HttpStatus.OK);
                })
                .orElse( new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
