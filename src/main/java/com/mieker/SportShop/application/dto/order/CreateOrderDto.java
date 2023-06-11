package com.mieker.SportShop.application.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderDto {

    private String customerId;
    private List<OrderItemDto> orderItems;
}
