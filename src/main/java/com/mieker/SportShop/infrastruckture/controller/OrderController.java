package com.mieker.SportShop.infrastruckture.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    public ResponseEntity<?> createOrder() {
        return null;
    }

    public ResponseEntity<?> getOrders() {
        return null;
    }

    public ResponseEntity<?> deleteOrder() {
        return null;
    }
}
