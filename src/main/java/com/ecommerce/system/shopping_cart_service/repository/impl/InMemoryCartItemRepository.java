package com.ecommerce.system.shopping_cart_service.repository.impl;

import com.ecommerce.system.shopping_cart_service.model.entity.CartItem;
import com.ecommerce.system.shopping_cart_service.repository.CartItemRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryCartItemRepository implements CartItemRepository {

    private final Map<Long, CartItem> cartItemStore = new HashMap<>();
    private final AtomicLong cartItemIdSequence = new AtomicLong(0);


    @Override
    public CartItem save(CartItem cartItem) {
        if (cartItem.getId() == null) {
            cartItem.setId(cartItemIdSequence.getAndIncrement());
        }
        cartItemStore.put(cartItem.getId(), cartItem);
        return cartItem;
    }

    @Override
    public List<CartItem> findByShoppingCartId(Long id) {
        return cartItemStore.values().parallelStream()
                .filter(cartItem -> Objects.equals(cartItem.getShoppingCart().getId(), id))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return Optional.ofNullable(cartItemStore.get(id));
    }

    @Override
    public void deleteById(Long id) {
        cartItemStore.remove(id);
    }

    @Override
    public void changeQuantity(long id, int quantity) {
        Optional.ofNullable(cartItemStore.get(id))
                .ifPresent(cartItem -> {
                    cartItem.setQuantity(quantity);
                    cartItemStore.put(id, cartItem);
                });
    }

    public boolean existsById(Long id) {
        return cartItemStore.containsKey(id);
    }
}