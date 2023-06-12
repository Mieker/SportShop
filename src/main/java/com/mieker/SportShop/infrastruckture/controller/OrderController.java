package com.mieker.SportShop.infrastruckture.controller;

import com.mieker.SportShop.application.dto.order.OrderDto;
import com.mieker.SportShop.application.dto.order.request.RequestOrderDto;
import com.mieker.SportShop.application.dto.user.UserDto;
import com.mieker.SportShop.application.service.AuthorizationService;
import com.mieker.SportShop.application.service.OrderService;
import com.mieker.SportShop.domain.model.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final AuthorizationService authorizationService;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(
            @RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody RequestOrderDto order) {
        UserDto userDto = authorizationService.authorizeUser(authorizationHeader, List.of(Role.ADMIN, Role.CUSTOMER));
        return ResponseEntity.ok(orderService.createOrder(order, userDto.getId()));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestHeader("Authorization") String authorizationHeader, @RequestParam String customerId) {
        UserDto userDto = authorizationService.authorizeUser(authorizationHeader, List.of(Role.ADMIN, Role.CUSTOMER));
        return ResponseEntity.ok(orderService.getOrders(userDto, customerId));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(
            //TODO: validate for orderId - blank, null, etc.
            //TODO: check proper id or throw exception - message that non existing order were deleted

            @RequestHeader("Authorization") String authorizationHeader, @PathVariable("orderId") String orderId) {
        authorizationService.authorizeUser(authorizationHeader, List.of(Role.ADMIN));
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().body("Order " + orderId + " deleted.");
    }
}
