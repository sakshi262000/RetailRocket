package com.example.retail_rocket.service;

import com.example.retail_rocket.ExceptionHandler.ResourceNotFoundException;
import com.example.retail_rocket.model.Cart;
import com.example.retail_rocket.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {

@Autowired
    public CartRepo cartRepo;

    @Autowired
    private ProductService productService;
    public Cart addProductToCart(Cart cart) {
        return cartRepo.save(cart);
    }

    public List<Cart> getCartItemsByCustomerId(String customerId) {
        return (List<Cart>) cartRepo.findByCustomerId(customerId);
    }

    public void removeProductFromCart(String customerId, String productCode) {
        List<Cart> cartItems = cartRepo.findByCustomerIdAndProductCode(customerId, productCode);

        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Cart item not found for customer ID: " + customerId + " and product code: " + productCode);
        }

        Cart cartItem = cartItems.get(0);
        cartRepo.delete(cartItem);
    }

    public Cart updateProductQuantity(String customerId, String productCode) {
        List<Cart> cartItems = cartRepo.findByCustomerIdAndProductCode(customerId, productCode);

        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Cart item not found for customer ID: " + customerId + " and product code: " + productCode);
        }

        Cart cartItem = cartItems.get(0);
        String currentQuantityStr = cartItem.getQuantity();
        int currentQuantity = (currentQuantityStr == null || currentQuantityStr.isEmpty()) ? 0 : Integer.parseInt(currentQuantityStr);
        currentQuantity += 1;

        cartItem.setQuantity(String.valueOf(currentQuantity));
        return cartRepo.save(cartItem);
    }


    public Cart updateCartItem(Cart cart) {
    return cartRepo.save(cart);
}
    public BigDecimal calculateTotalAmount(Cart cartItem) {
        BigDecimal price = productService.getProductPrice(cartItem.getProductCode());
        BigDecimal quantity = new BigDecimal(cartItem.getQuantity());
        return price.multiply(quantity);
    }
}