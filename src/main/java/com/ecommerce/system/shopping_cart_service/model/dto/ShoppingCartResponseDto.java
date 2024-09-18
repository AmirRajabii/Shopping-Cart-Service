package com.ecommerce.system.shopping_cart_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShoppingCartResponseDto {

    record ShoppingItems(String productName, Integer productCount, Double productPrice) {
    }

    List<ShoppingItems> shoppingItems;

    private Double totalAmount;

    public Double getTotalAmount() {
        return this.totalAmount = shoppingItems.stream()
                .mapToDouble(item -> item.productPrice() * item.productCount())
                .sum();
    }

}
