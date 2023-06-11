package com.mieker.SportShop.application.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderItemDto {

    private ProductDto product;
    private int quantity;
}
