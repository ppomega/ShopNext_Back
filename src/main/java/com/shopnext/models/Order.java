package com.shopnext.models;

import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "Orders")
public class Order {

    public String _id;
    public String orderId;
    public org.bson.types.ObjectId user;
    public org.bson.types.ObjectId[] items;
    public int total;
    public String payement_id;
    public String shippingAddress;
    public LocalDateTime createdAt;

}
