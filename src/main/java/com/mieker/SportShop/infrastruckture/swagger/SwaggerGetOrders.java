package com.mieker.SportShop.infrastruckture.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Get all orders / get orders by user ID.",
        description = "As Admin you can get all orders or orders owned by specified user. As User you can get all your orders.",
        tags = {"Orders"})
@ApiResponse(responseCode = "200", description = "OK - After properly constructed request it returns orders list.")
@ApiResponse(responseCode = "400", description = "Bad Request - After discovering that any obligatory field "
        + "is missing either in request body or in the database.",
        content = {@Content(examples = {@ExampleObject("Access denied.")})
        }
)
public @interface SwaggerGetOrders {
}
