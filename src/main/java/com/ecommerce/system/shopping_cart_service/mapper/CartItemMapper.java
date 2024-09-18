package com.ecommerce.system.shopping_cart_service.mapper;

import com.ecommerce.system.shopping_cart_service.model.dto.CartItemRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemResponseDto;
import com.ecommerce.system.shopping_cart_service.model.entity.CartItem;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;

import java.util.List;
import java.util.stream.Collectors;

public class CartItemMapper {

    public static CartItem toEntity(CartItemRequestDto cartItemRequestDto) {
        if (cartItemRequestDto == null) {
            return null;
        }
        return CartItem.builder()
                .shoppingCart(ShoppingCart.builder().id(cartItemRequestDto.getShoppigCartId()).build())
                .product(Product.builder().id(cartItemRequestDto.getProductId()).build())
                .quantity(cartItemRequestDto.getQuantity())
                .build();
    }

    public static CartItem toEntity(CartItemResponseDto cartItemResponseDto) {
        if (cartItemResponseDto == null) {
            return null;
        }
        return CartItem.builder()
                .shoppingCart(ShoppingCart.builder().id(cartItemResponseDto.getShoppigCartId()).build())
                .product(Product.builder().id(cartItemResponseDto.getProductId()).build())
                .quantity(cartItemResponseDto.getQuantity())
                .build();
    }

    public static CartItemResponseDto toDto(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }
        return CartItemResponseDto.builder()
                .id(cartItem.getId())
                .shoppigCartId(cartItem.getShoppingCart().getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static List<CartItemResponseDto> toDtoList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartItemMapper::toDto)
                .collect(Collectors.toList());
    }
}
