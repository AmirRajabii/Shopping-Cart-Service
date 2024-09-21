package com.ecommerce.system.shopping_cart_service.service;

import com.ecommerce.system.shopping_cart_service.aop.CheckCartActive;
import com.ecommerce.system.shopping_cart_service.exception.CartItemNotFoundException;
import com.ecommerce.system.shopping_cart_service.exception.InvalidQuantityException;
import com.ecommerce.system.shopping_cart_service.exception.ProductNotFoundException;
import com.ecommerce.system.shopping_cart_service.exception.ShoppingCartNotFoundException;
import com.ecommerce.system.shopping_cart_service.mapper.CartItemMapper;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemResponseDto;
import com.ecommerce.system.shopping_cart_service.model.dto.ShoppingCartItemDto;
import com.ecommerce.system.shopping_cart_service.model.dto.ShoppingCartResponseDto;
import com.ecommerce.system.shopping_cart_service.model.entity.CartItem;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.repository.CartItemRepository;
import com.ecommerce.system.shopping_cart_service.repository.ProductRepository;
import com.ecommerce.system.shopping_cart_service.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public CartItemResponseDto addCartItem(CartItemRequestDto cartItemRequestDto) {
        shoppingCartRepository.findById(cartItemRequestDto.getShoppigCartId())
                .orElseThrow(() -> new ShoppingCartNotFoundException(cartItemRequestDto.getShoppigCartId()));
        Product product = productRepository.findById(cartItemRequestDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(cartItemRequestDto.getProductId()));
        validateStockAvailability(
                product,
                cartItemRequestDto.getQuantity()
        );
        return CartItemMapper.toDto(cartItemRepository.save(CartItemMapper.toEntity(cartItemRequestDto)));
    }

    public void changeQuantity(Long id, Integer quantity) {
        CartItem cartItem = getCartItemById(id);
        Product product = productRepository.findById(cartItem.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException(cartItem.getProduct().getId()));
        validateStockAvailability(product, quantity);
        cartItemRepository.changeQuantity(id, quantity);
    }

    public List<CartItemResponseDto> getCartItems(Long shoppingCartId) {
        return CartItemMapper.toDtoList(cartItemRepository.findByShoppingCartId(shoppingCartId));
    }

    public CartItem getCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException(cartItemId));
    }

    public void deleteCartItemById(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        cartItemRepository.deleteById(id);
    }

    private void validateStockAvailability(Product product, Integer requestedQuantity) {
        if (product.getQuantityInStock() < requestedQuantity) {
            throw new InvalidQuantityException(product.getId(), product.getQuantityInStock());
        }
    }


}
