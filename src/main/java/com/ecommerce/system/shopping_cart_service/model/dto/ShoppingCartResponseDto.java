package com.ecommerce.system.shopping_cart_service.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ShoppingCartResponseDto {

    List<ShoppingCartItemDto> shoppingItems;

    private Double totalAmount;

    private ShoppingCartResponseDto() {
    }

    public ShoppingCartResponseDto(List<ShoppingCartItemDto> shoppingItems) {
        this.shoppingItems = shoppingItems;
        setTotalAmount();
    }

    private void setTotalAmount() {
        this.totalAmount = shoppingItems.stream()
                .mapToDouble(item -> item.productPrice() * item.productCount())
                .sum();
    }
}
