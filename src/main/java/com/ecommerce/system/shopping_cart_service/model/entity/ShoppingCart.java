package com.ecommerce.system.shopping_cart_service.model.entity;


import com.ecommerce.system.shopping_cart_service.model.enums.ShoppingCartStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ShoppingCart {

    private Long id;

    @NotNull(message = "Shopping cart status cannot be null")
    private ShoppingCartStatus shoppingCartStatus;
}
