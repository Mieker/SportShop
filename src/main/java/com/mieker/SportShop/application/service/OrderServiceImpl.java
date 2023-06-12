package com.mieker.SportShop.application.service;

import com.mieker.SportShop.application.dto.order.OrderDto;
import com.mieker.SportShop.application.dto.order.request.RequestOrderDto;
import com.mieker.SportShop.application.dto.user.UserDto;
import com.mieker.SportShop.application.exception.NotSupportedRoleException;
import com.mieker.SportShop.application.mapper.OrderMapper;
import com.mieker.SportShop.domain.model.order.Order;
import com.mieker.SportShop.domain.model.user.Role;
import com.mieker.SportShop.infrastruckture.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto createOrder(RequestOrderDto order, String customerId) {
        order.setCustomerId(customerId);
        Order orderToSave = orderMapper.map(order);
        return orderMapper.map(orderRepository.save(orderToSave));
    }

    @Override
    public void deleteOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDto> getOrders(UserDto user, String customerId) {
        String userId= user.getId();
        Set<Role> roles = user.getAuthorities();

        if (roles.contains(Role.ADMIN)) {
            if (Strings.isBlank(customerId)) {
                return getAllOrders();
            } else {
                return getUserOrders(customerId);
            }
        } else if (roles.contains(Role.CUSTOMER)) {
            return getUserOrders(userId);
        }
        throw new NotSupportedRoleException("Given role is not supported");
    }

    private List<OrderDto> getUserOrders(String customerId) {
        return orderMapper.mapOrders(orderRepository.findAllByCustomerId(customerId));
    }

    private List<OrderDto> getAllOrders() {
        return orderMapper.mapOrders(orderRepository.findAll());
    }
}
