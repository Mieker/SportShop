package com.mieker.SportShop.application.service;

import com.mieker.SportShop.application.dto.order.CreateOrderDto;
import com.mieker.SportShop.application.dto.order.OrderDto;
import com.mieker.SportShop.application.dto.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(CreateOrderDto order, String customerId);

    List<OrderDto> getOrders(UserDto user, String userId);

    void deleteOrder(String orderId);
}
