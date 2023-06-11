package com.mieker.SportShop.application.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class ProductDto {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
