package com.mieker.SportShop.domain.model.order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
@Getter
@Setter
public class Order {

    @Id
    private String id;
    private String customerId;
    private List<OrderItem> orderItems;
}
