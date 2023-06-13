package com.mieker.SportShop.domain.model.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {

    private String productId;
    private int quantity;
}
