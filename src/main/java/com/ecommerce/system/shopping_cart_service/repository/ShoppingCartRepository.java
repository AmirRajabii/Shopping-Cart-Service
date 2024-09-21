package com.ecommerce.system.shopping_cart_service.repository;

import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;

import java.util.Optional;

public interface ShoppingCartRepository {

    ShoppingCart save(ShoppingCart shoppingCart);

    Optional<ShoppingCart> findById(Long id);

}
