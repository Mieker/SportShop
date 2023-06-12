package com.mieker.SportShop.application.dto.order.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOrderItemDto {

    @NotBlank(message = "Please provide valid product Id.")
    //TODO: create validation for non existing product (id)
    private String productId;

    @NotNull(message = "Please set number of product quantity.")
    @Min(value = 1, message = "Product quantity cannot be less than 1.")
    @Max(value = 1000000, message = "Maximum value of product quantity is 1000000.")
    private Integer quantity;
}
