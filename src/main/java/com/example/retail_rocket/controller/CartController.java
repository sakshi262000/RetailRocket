package com.example.retail_rocket.controller;

import com.example.retail_rocket.model.Cart;
import com.example.retail_rocket.service.CartService;
import com.example.retail_rocket.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = LogManager.getLogger(CartController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestBody Cart cart) {
        List<Cart> existingCartItems = cartService.getCartItemsByCustomerId(cart.getCustomerId());


        Cart existingCartItem = existingCartItems.stream()
                .filter(item -> item.getProductCode().equals(cart.getProductCode()))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            int currentQuantity = Integer.parseInt(existingCartItem.getQuantity());
            currentQuantity += 1;
            existingCartItem.setQuantity(String.valueOf(currentQuantity));
            existingCartItem.setTotalAmount(cartService.calculateTotalAmount(existingCartItem).toString());
            Cart updatedCart = cartService.updateCartItem(existingCartItem);
            logger.info("Updated existing cart item: {}", updatedCart);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } else {
            // If the product does not exist, create a new cart item
            cart.calculateTotalAmount();
            Cart savedCart = cartService.addProductToCart(cart);
            logger.info("Added new cart item: {}", savedCart);
            return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
        }
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<List<Cart>> viewCart(@PathVariable String customerId) {
        List<Cart> cartItems = cartService.getCartItemsByCustomerId(customerId);
        logger.info("Getting Cart items");
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}/{productCode}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable String customerId, @PathVariable String productCode) {
        cartService.removeProductFromCart(customerId, productCode);
        logger.info("Deleting Cart item"+productCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{customerId}/product/{productCode}/update")
    public ResponseEntity<Cart> updateProductQuantity(@PathVariable String customerId,
                                                      @PathVariable String productCode) {
        String quantity = "1"; // Default quantity is set to 1
        Cart updatedCartItem = cartService.updateProductQuantity(customerId, productCode);
        logger.info("Updated Cart item for "+customerId);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.OK);
    }


    private String calculateTotalAmount(Cart cart) {
        // Assuming you have a method to get product price by product code
        BigDecimal price = productService.getProductPrice(cart.getProductCode());
        BigDecimal quantity = new BigDecimal(cart.getQuantity());
        return price.multiply(quantity).toString();
    }
}