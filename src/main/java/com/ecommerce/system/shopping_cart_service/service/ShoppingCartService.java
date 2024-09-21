package com.ecommerce.system.shopping_cart_service.service;

import com.ecommerce.system.shopping_cart_service.aop.CheckCartActive;
import com.ecommerce.system.shopping_cart_service.exception.InvalidQuantityException;
import com.ecommerce.system.shopping_cart_service.exception.ProductNotFoundException;
import com.ecommerce.system.shopping_cart_service.exception.ShoppingCartNotFoundException;
import com.ecommerce.system.shopping_cart_service.model.dto.ShoppingCartItemDto;
import com.ecommerce.system.shopping_cart_service.model.dto.ShoppingCartResponseDto;
import com.ecommerce.system.shopping_cart_service.model.entity.CartItem;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;
import com.ecommerce.system.shopping_cart_service.model.enums.ShoppingCartStatus;
import com.ecommerce.system.shopping_cart_service.repository.CartItemRepository;
import com.ecommerce.system.shopping_cart_service.repository.ProductRepository;
import com.ecommerce.system.shopping_cart_service.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public boolean isCartActive(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ShoppingCartNotFoundException(id)).getShoppingCartStatus().equals(ShoppingCartStatus.ACTIVE);
    }

    @CheckCartActive
    public void purchaseShoppingCart(Long id) {
        ShoppingCart shoppingCart = getShoppingCartById(id);
        cartItemRepository.findByShoppingCartId(shoppingCart.getId()).forEach(cartItem -> {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(cartItem.getProduct().getId()));
            validateStockAvailability(product, cartItem.getQuantity());
            productRepository.changeQuantityInStock(cartItem.getProduct().getId(), (product.getQuantityInStock() - cartItem.getQuantity()));
        });
        updateShoppingCartStatus(shoppingCart, ShoppingCartStatus.PURCHASED);
    }

    @CheckCartActive
    public void cancelShoppingCart(Long id) {
        ShoppingCart shoppingCart = getShoppingCartById(id);
        updateShoppingCartStatus(shoppingCart, ShoppingCartStatus.CANCELED);
    }

    private ShoppingCart getShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ShoppingCartNotFoundException(id));
    }

    private void updateShoppingCartStatus(ShoppingCart shoppingCart, ShoppingCartStatus newStatus) {
        shoppingCart.setShoppingCartStatus(newStatus);
        shoppingCartRepository.save(shoppingCart);
    }

    private void validateStockAvailability(Product product, Integer requestedQuantity) {
        if (product.getQuantityInStock() < requestedQuantity) {
            throw new InvalidQuantityException(product.getId(), product.getQuantityInStock());
        }
    }

    public ShoppingCartResponseDto getShoppingCart(Long id) {
        List<CartItem> cartItems = cartItemRepository.findByShoppingCartId(id);
        List<ShoppingCartItemDto> shoppingCartItemDtos = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(cartItem.getProduct().getId()));
            shoppingCartItemDtos.add(
                    new ShoppingCartItemDto(
                            product.getName(),
                            cartItem.getQuantity(),
                            product.getPrice()
                    )
            );
        });
        return new ShoppingCartResponseDto(shoppingCartItemDtos);
    }
}