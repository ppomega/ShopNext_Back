package com.shopnext.business_logic;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shopnext.repos.OrderRepo;

@Service
public class OrderService {
    @Value("${rzpay.id}")
    private String keyId;
    @Value("${rzpay.secret}")
    private String keySecret;

    @Autowired
    OrderRepo orderRepo;

    public Map<String, Object> createOrder(int amount) throws RazorpayException {
        try {
            RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount * 100);
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "receipt#1");
            Order order = razorpay.orders.create(orderRequest);
            return order.toJson().toMap();

        }

        catch (RazorpayException e) {
            System.out.println("Razorpay Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public com.shopnext.models.Order submitOrder(com.shopnext.models.Order order) {
        System.out.println(order.orderId);
        System.out.println(order.user);
        System.out.println(order.items);
        System.out.println(order.total);
        System.out.println(order.payement_id);
        System.out.println(order.shippingAddress);
        System.out.println(order.createdAt);
        return orderRepo.save(order);
    }
}
