package com.mieker.SportShop.application.mapper;

import com.mieker.SportShop.application.dto.order.OrderDto;
import com.mieker.SportShop.application.dto.order.OrderItemDto;
import com.mieker.SportShop.application.dto.order.ProductDto;
import com.mieker.SportShop.application.dto.order.RequestOrderDto;
import com.mieker.SportShop.application.exception.OrderNotFoundException;
import com.mieker.SportShop.domain.model.order.Order;
import com.mieker.SportShop.domain.model.order.OrderItem;
import com.mieker.SportShop.domain.model.order.Product;
import com.mieker.SportShop.infrastruckture.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ProductRepository productRepository;

    public Order map(RequestOrderDto requestOrderDto) {
        Order order = new Order();
        order.setCustomerId(requestOrderDto.getCustomerId());
        return order;
    }

    public OrderDto map(Order order) {
        return new OrderDto(order.getId(), order.getCustomerId(), mapOrderItems(order.getOrderItems()));
    }

    private List<OrderItemDto> mapOrderItems(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::map).toList();
    }

    private OrderItemDto map(OrderItem orderItem) {
        int quantity = orderItem.getQuantity();
        Optional<Product> productOptional = productRepository.findById(orderItem.getProductId());
        if (productOptional.isPresent()) {
            return new OrderItemDto(map(productOptional.get()), quantity);
        } else {
            throw new OrderNotFoundException("Cannot get order with id: " + orderItem.getProductId());
        }
    }

    private ProductDto map(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<OrderDto> mapOrders(List<Order> orders) {
        //TODO check is it works properly
        return orders.stream().map(this::map).toList();
    }

}
