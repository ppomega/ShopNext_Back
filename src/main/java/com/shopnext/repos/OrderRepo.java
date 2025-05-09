package com.shopnext.repos;

import org.springframework.data.repository.CrudRepository;

import com.shopnext.models.Order;

public interface OrderRepo extends CrudRepository<Order, String> {

}
