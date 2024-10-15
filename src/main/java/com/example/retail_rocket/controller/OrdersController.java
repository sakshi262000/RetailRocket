package com.example.retail_rocket.controller;

import com.example.retail_rocket.model.Orders;
import com.example.retail_rocket.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrdersController {
    @Autowired
    OrdersService ordersService;
    @PostMapping("/orders")
    public Orders placeOrder(@RequestBody Orders orders){
        ordersService.placeOrder(orders);
        return new Orders();
    }
}