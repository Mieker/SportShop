package com.mieker.SportShop.application.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class OrderDto {

    private String id;
    private String customerId;
    private List<OrderItemDto> orderItems;
}
