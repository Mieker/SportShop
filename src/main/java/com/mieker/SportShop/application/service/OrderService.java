package com.mieker.SportShop.application.service;

import com.mieker.SportShop.application.dto.order.RequestOrderDto;
import com.mieker.SportShop.application.dto.order.OrderDto;
import com.mieker.SportShop.application.dto.user.UserDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(RequestOrderDto order, String customerId);

    List<OrderDto> getOrders(UserDto user, String userId);

    void deleteOrder(String orderId);
}
