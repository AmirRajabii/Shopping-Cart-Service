package com.ecommerce.system.shopping_cart_service.exception;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Long id) {
        super("Cart Item with ID " + id + " not found");
    }
}
