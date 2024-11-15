package com.example.retail_rocket.service;

import com.example.retail_rocket.ExceptionHandler.ExceptionOccured;
import com.example.retail_rocket.model.Orders;
import com.example.retail_rocket.model.Products;
import com.example.retail_rocket.repository.OrdersRepository;
import com.example.retail_rocket.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrdersService {
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;

    public Orders placeOrder(Orders orders) {
        Products product = productService.isProductIsAvailable(orders.getProductCode());

        if(product!=null)
        {
            if(orders.getNumberOfItems()<=product.getStock()) {
                Orders savedOrder =  ordersRepository.save(orders);
                product.setStock(product.getStock() - orders.getNumberOfItems());
                var productDetail =  productService.updateProduct(product.getId(), product);
                if(productDetail.getStatusCode() == HttpStatus.OK)
                    return savedOrder;
                else
                    throw new ExceptionOccured("Order not placed");
            }else {
                throw new ExceptionOccured("Stock Not available");
            }
        }else {
            throw new ExceptionOccured("Order Not available");
        }

    }

    public List<Orders> getAllOrders() {
        return StreamSupport.stream(ordersRepository.findAll().spliterator(),false)
                .collect(Collectors.toUnmodifiableList());
    }

    public Orders updateOrder(Long orderId, Orders orders) {
        return ordersRepository.findById(orderId).map(orders1 -> {

            Optional.ofNullable(orders.getProductCode()).ifPresent(orders1::setProductCode);
            Optional.ofNullable(orders.getNumberOfItems()).filter(integer -> integer.compareTo(0)!=0)
                    .ifPresent(orders1::setNumberOfItems);
            Optional.ofNullable(orders.getCustomerId()).filter(integer -> integer.compareTo(0L)!=0)
                    .ifPresent(orders1::setCustomerId);
            return ordersRepository.save(orders1);

        }).orElseThrow( () -> new ExceptionOccured("Order not found"));

    }
}
