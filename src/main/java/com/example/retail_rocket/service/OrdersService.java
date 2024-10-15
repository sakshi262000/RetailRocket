package com.example.retail_rocket.service;

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
            if(product.getStock()>=1 &&orders.getNumberOfItems()<=product.getStock()) {
                ordersRepository.save(orders);
                if (product.getStock() == 1)
                    productService.deleteProduct(product.getId());
                else {
                    product.setStock(product.getStock() - 1);
                    productService.updateProduct(product.getId(), product);
                }
            }
        }
        return null;
    }
}
