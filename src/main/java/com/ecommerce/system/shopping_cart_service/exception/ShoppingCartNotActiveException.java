package com.ecommerce.system.shopping_cart_service.exception;

public class ShoppingCartNotActiveException extends RuntimeException {
    public ShoppingCartNotActiveException(Long id) {
        super("Shopping Cart with ID " + id + " not Active");
    }
}
