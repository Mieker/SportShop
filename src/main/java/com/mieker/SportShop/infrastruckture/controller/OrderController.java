package com.mieker.SportShop.infrastruckture.controller;

import com.mieker.SportShop.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

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
