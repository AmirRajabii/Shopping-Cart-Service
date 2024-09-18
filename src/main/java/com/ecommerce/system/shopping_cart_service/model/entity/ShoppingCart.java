package com.ecommerce.system.shopping_cart_service.model.entity;


import com.ecommerce.system.shopping_cart_service.model.enums.ShoppingCartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCart {

    private Long id;

    private ShoppingCartStatus shoppingCartStatus;
}
