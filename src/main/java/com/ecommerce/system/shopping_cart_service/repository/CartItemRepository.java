package com.ecommerce.system.shopping_cart_service.repository;

import com.ecommerce.system.shopping_cart_service.model.entity.CartItem;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {

    CartItem save(CartItem cartItem);

   List<CartItem> findByShoppingCartId(Long id);

    Optional<CartItem>  findById(Long id);

    void deleteById(Long id);

    void changeQuantity(long id , int quantity);

    boolean existsById(Long id);

}
