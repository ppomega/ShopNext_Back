package com.shopnext.controller;

import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;
import com.shopnext.business_logic.OrderService;
import com.shopnext.models.Order;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @CrossOrigin(origins = "*")
    @PostMapping("/create_order/{amount}")
    public Map<String, Object> createPayOrder(@PathVariable("amount") int amount) throws RazorpayException {

        return orderService.createOrder(amount);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/submit_order")
    public Order submitPayOrder(@RequestBody Order order) {

        return orderService.submitOrder(order);
    }

    @CrossOrigin(origins = "*")

    @PostMapping("/")
    public String getMethodName(@RequestBody Order order) {
        System.out.println(order.orderId);
        System.out.println(order.user);
        System.out.println(order.items);
        System.out.println(order.total);
        System.out.println(order.payement_id);
        System.out.println(order.shippingAddress);
        System.out.println(order.createdAt);
        // System.out.println(order.createdAt);
        return "Hello World!";
    }

}
