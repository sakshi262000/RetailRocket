package com.example.retail_rocket.service;

import com.example.retail_rocket.ExceptionHandler.OrderNotFound;
import com.example.retail_rocket.model.Orders;
import com.example.retail_rocket.model.Products;
import com.example.retail_rocket.repository.OrdersRepository;
import com.example.retail_rocket.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;

    public Object placeOrder(Orders orders) {
        Products product = productService.isProductIsAvailable(orders.getProductCode());

        if(product!=null)
        {
            if(orders.getNumberOfItems()<=product.getStock()) {
                ordersRepository.save(orders);
                product.setStock(product.getStock() - orders.getNumberOfItems());
                productService.updateProduct(product.getId(), product);

            }else {
                throw new OrderNotFound("Order NOt avalable");
            }
        }else {
throw new OrderNotFound("Order NOt avalable");
        }
        return null;
    }
}
