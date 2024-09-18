package com.ecommerce.system.shopping_cart_service.repository;

import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;
import com.ecommerce.system.shopping_cart_service.model.enums.ShoppingCartStatus;

import java.util.Optional;

public interface ShoppingCartRepository {

    ShoppingCart save(ShoppingCart shoppingCart);

    Optional<ShoppingCart> findById(Long id);

    boolean existsById(Long id);

    void changeShoppingCartStatus(Long id,ShoppingCartStatus shoppingCartStatus);

    boolean isActive(Long id);
}
