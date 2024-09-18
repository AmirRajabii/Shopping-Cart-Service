package com.ecommerce.system.shopping_cart_service.repository.impl;

import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;
import com.ecommerce.system.shopping_cart_service.model.enums.ShoppingCartStatus;
import com.ecommerce.system.shopping_cart_service.repository.ShoppingCartRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryShoppingCartRepository implements ShoppingCartRepository {

    private final Map<Long, ShoppingCart> shoppingCartStore = new HashMap<>();
    private long shoppingCartIdSequence = 0;

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) {
        if (shoppingCart.getId() == null) {
            shoppingCartIdSequence++;
            shoppingCart.setId(shoppingCartIdSequence);
        }
        shoppingCartStore.put(shoppingCart.getId(), shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        return Optional.ofNullable(shoppingCartStore.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return shoppingCartStore.containsKey(id);
    }

    @Override
    public void changeShoppingCartStatus(Long id, ShoppingCartStatus shoppingCartStatus) {
        Optional.ofNullable(shoppingCartStore.get(id))
                .ifPresent(shoppingCart -> {
                    shoppingCart.setShoppingCartStatus(shoppingCartStatus);
                    shoppingCartStore.put(id, shoppingCart);
                });
    }

    @Override
    public boolean isActive(Long id) {
        return Optional.ofNullable(shoppingCartStore.get(id))
                .map(ShoppingCart::getShoppingCartStatus)
                .map(status -> status.equals(ShoppingCartStatus.ACTIVE))
                .orElse(false);
    }
}