package com.example.retail_rocket.controller;

import com.example.retail_rocket.model.Orders;
import com.example.retail_rocket.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/orders")
    public List<Orders> getAllOrdersList(){
       return ordersService.getAllOrders();
    }
    @PutMapping("/orders/{orderId}/status")
    public Orders updateOrder(@PathVariable("orderId") Long orderId,@RequestBody Orders orders){
        return ordersService.updateOrder(orderId,orders);
    }
}
