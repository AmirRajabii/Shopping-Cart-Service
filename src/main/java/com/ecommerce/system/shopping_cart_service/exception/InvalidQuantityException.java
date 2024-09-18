package com.ecommerce.system.shopping_cart_service.exception;

public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException(Long productId, int quantity) {
        super("There are only " + quantity + "of product with ID " + productId);
    }
}
