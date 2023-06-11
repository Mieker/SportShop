package com.mieker.SportShop.infrastruckture.repository;

import com.mieker.SportShop.domain.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
