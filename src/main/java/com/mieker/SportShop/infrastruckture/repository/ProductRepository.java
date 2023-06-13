package com.mieker.SportShop.infrastruckture.repository;

import com.mieker.SportShop.domain.model.order.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
