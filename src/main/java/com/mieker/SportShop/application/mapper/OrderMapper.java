package com.mieker.SportShop.application.mapper;

import com.mieker.SportShop.application.dto.order.OrderDto;
import com.mieker.SportShop.application.dto.order.OrderItemDto;
import com.mieker.SportShop.application.dto.order.ProductDto;
import com.mieker.SportShop.application.dto.order.request.RequestOrderDto;
import com.mieker.SportShop.application.dto.order.request.RequestOrderItemDto;
import com.mieker.SportShop.application.exception.OrderNotFoundException;
import com.mieker.SportShop.application.exception.ProductNotFoundException;
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
        order.setOrderItems(map(requestOrderDto.getOrderItems()));
        return order;
    }

    private List<OrderItem> map(List<RequestOrderItemDto> orderItemDtoList) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (RequestOrderItemDto orderItemDto : orderItemDtoList) {
            orderItems.add(map(orderItemDto));
        }
        return orderItems;
    }

    private OrderItem map(RequestOrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(orderItemDto.getQuantity());
        Optional<Product> productFromRequest = productRepository.findById(orderItemDto.getProductId());
        if (productFromRequest.isPresent()) {
            orderItem.setProductId(orderItemDto.getProductId());
            return orderItem;
        } else {
            throw new ProductNotFoundException("Product with id: " + orderItem.getProductId() + " doesn't exists.");
        }
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
            throw new OrderNotFoundException("Order corrupted because product with id " + orderItem.getProductId() + " doesn't exists.");
        }
    }

    private ProductDto map(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<OrderDto> mapOrders(List<Order> orders) {
        return orders.stream().map(this::map).toList();
    }

}
