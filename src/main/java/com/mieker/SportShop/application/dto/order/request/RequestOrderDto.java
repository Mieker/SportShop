package com.mieker.SportShop.application.dto.order.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestOrderDto {

    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Invalid customerId.")
    private String customerId;

    @Valid
    @NotEmpty(message = "Cannot create an empty order.")
    private List<RequestOrderItemDto> orderItems;
}
