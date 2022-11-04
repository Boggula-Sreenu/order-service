package com.order.orderservice.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.order.orderservice.entity.OrderItem;
/**
 * This is an Order Repository Interface which is used to communicate with the database
 * @author 0031BS744
 * @version 1.0
 *
 */

public interface OrderRepository extends ReactiveCrudRepository<OrderItem, Integer>{


}
