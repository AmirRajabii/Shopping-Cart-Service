package com.ecommerce.system.shopping_cart_service.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {

    private Long id;
    private Product product;
    private ShoppingCart shoppingCart;
    private Integer quantity;
}
