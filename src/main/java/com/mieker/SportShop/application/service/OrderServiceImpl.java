package com.mieker.SportShop.application.service;

import com.mieker.SportShop.application.dto.order.CreateOrderDto;
import com.mieker.SportShop.application.dto.order.OrderDto;
import com.mieker.SportShop.application.dto.user.UserDto;
import com.mieker.SportShop.application.mapper.OrderMapper;
import com.mieker.SportShop.infrastruckture.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto createOrder(CreateOrderDto order, String customerId) {
        return null;
    }

    @Override
    public List<OrderDto> getOrders(UserDto user, String userId) {
        return null;
    }

    @Override
    public void deleteOrder(String orderId) {

    }
}
