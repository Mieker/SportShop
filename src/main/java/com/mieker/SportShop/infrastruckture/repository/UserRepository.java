package com.mieker.SportShop.infrastruckture.repository;

import com.mieker.SportShop.domain.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
