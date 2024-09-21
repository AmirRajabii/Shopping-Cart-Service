package com.ecommerce.system.shopping_cart_service.model.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItem {

    private Long id;

    @NotNull(message = "Product cannot be null")
    private Product product;

    @NotNull(message = "Shopping Cart cannot be null")
    private ShoppingCart shoppingCart;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity in stock must be greater than or equal to 1")
    private Integer quantity;
}
