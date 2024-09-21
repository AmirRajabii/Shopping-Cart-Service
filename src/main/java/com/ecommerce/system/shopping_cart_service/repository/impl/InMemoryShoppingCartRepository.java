package com.ecommerce.system.shopping_cart_service.repository.impl;

import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;
import com.ecommerce.system.shopping_cart_service.repository.ShoppingCartRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryShoppingCartRepository implements ShoppingCartRepository {

    private final Map<Long, ShoppingCart> shoppingCartStore = new HashMap<>();
    private final AtomicLong shoppingCartIdSequence = new AtomicLong(0);

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        if (shoppingCart.getId() == null) {
            shoppingCart.setId(shoppingCartIdSequence.getAndIncrement());
        }
        shoppingCartStore.put(shoppingCart.getId(), shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        return Optional.ofNullable(shoppingCartStore.get(id));
    }

}