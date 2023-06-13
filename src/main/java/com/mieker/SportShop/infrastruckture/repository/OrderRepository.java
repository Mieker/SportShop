package com.mieker.SportShop.infrastruckture.repository;

import com.mieker.SportShop.domain.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findAllByCustomerId(String customerId);
}
