package com.ecommerce.system.shopping_cart_service.service;

import com.ecommerce.system.shopping_cart_service.aop.CheckCartActive;
import com.ecommerce.system.shopping_cart_service.exception.CartItemNotFoundException;
import com.ecommerce.system.shopping_cart_service.exception.InvalidQuantityException;
import com.ecommerce.system.shopping_cart_service.exception.ProductNotFoundException;
import com.ecommerce.system.shopping_cart_service.mapper.CartItemMapper;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemResponseDto;
import com.ecommerce.system.shopping_cart_service.model.entity.CartItem;
import com.ecommerce.system.shopping_cart_service.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @CheckCartActive
    public CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto) {
        return CartItemMapper.toDto(cartItemRepository.save(CartItemMapper.toEntity(cartItemRequestDto)));
    }

    @CheckCartActive
    public void changeQuantity(Long id, Integer quantity) {
        CartItem existingCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException(id));
        if (existingCartItem.getQuantity() < quantity)
            throw new InvalidQuantityException(existingCartItem.getProduct().getId(), existingCartItem.getQuantity());
        cartItemRepository.changeQuantity(id, quantity);
    }

    public List<CartItemResponseDto> getCartItems(Long shoppingCartId) {
        return CartItemMapper.toDtoList(cartItemRepository.findByShoppingCartId(shoppingCartId));
    }

    @CheckCartActive
    public void deleteCartItemById(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        cartItemRepository.deleteById(id);
    }

}
