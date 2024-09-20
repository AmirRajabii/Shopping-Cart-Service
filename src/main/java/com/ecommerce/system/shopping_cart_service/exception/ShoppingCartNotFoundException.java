package com.ecommerce.system.shopping_cart_service.exception;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(Long id) {
        super("Shopping Cart with ID " + id + " not found");
    }
}
