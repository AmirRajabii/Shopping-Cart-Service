package com.ecommerce.system.shopping_cart_service.service;

import com.ecommerce.system.shopping_cart_service.exception.InvalidQuantityException;
import com.ecommerce.system.shopping_cart_service.exception.ProductNotFoundException;
import com.ecommerce.system.shopping_cart_service.exception.ShoppingCartNotFoundException;
import com.ecommerce.system.shopping_cart_service.model.entity.CartItem;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;
import com.ecommerce.system.shopping_cart_service.model.enums.ShoppingCartStatus;
import com.ecommerce.system.shopping_cart_service.repository.CartItemRepository;
import com.ecommerce.system.shopping_cart_service.repository.ProductRepository;
import com.ecommerce.system.shopping_cart_service.repository.ShoppingCartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isCartActive_shouldReturnTrue_whenCartIsActive() {
        ShoppingCart activeCart = ShoppingCart.builder()
                .id(1L)
                .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                .build();

        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(activeCart));

        boolean isActive = shoppingCartService.isCartActive(1L);

        assertTrue(isActive);
        verify(shoppingCartRepository, times(1)).findById(1L);
    }

    @Test
    void isCartActive_shouldThrowShoppingCartNotFoundException_whenCartNotFound() {
        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> shoppingCartService.isCartActive(1L));
    }

    @Test
    void purchaseShoppingCart_shouldPurchase_whenValidCartAndStockAvailable() {
        ShoppingCart cart = ShoppingCart.builder()
                .id(1L)
                .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                .build();

        Product product = Product.builder()
                .id(1L)
                .quantityInStock(10)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(5)
                .build();

        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByShoppingCartId(1L)).thenReturn(List.of(cartItem));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        shoppingCartService.purchaseShoppingCart(1L);

        verify(productRepository, times(1)).changeQuantityInStock(product.getId(), product.getQuantityInStock() - cartItem.getQuantity());
        verify(shoppingCartRepository, times(1)).save(cart);
    }

    @Test
    void purchaseShoppingCart_shouldThrowProductNotFoundException_whenProductNotFound() {
        ShoppingCart cart = ShoppingCart.builder()
                .id(1L)
                .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(Product.builder().id(1L).build())
                .quantity(5)
                .build();

        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByShoppingCartId(1L)).thenReturn(List.of(cartItem));
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> shoppingCartService.purchaseShoppingCart(1L));
    }

    @Test
    void purchaseShoppingCart_shouldThrowInvalidQuantityException_whenStockIsInsufficient() {
        ShoppingCart cart = ShoppingCart.builder()
                .id(1L)
                .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                .build();

        Product product = Product.builder()
                .id(1L)
                .quantityInStock(2)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(5)
                .build();

        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findByShoppingCartId(1L)).thenReturn(List.of(cartItem));
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        assertThrows(InvalidQuantityException.class, () -> shoppingCartService.purchaseShoppingCart(1L));
    }

    @Test
    void cancelShoppingCart_shouldCancel_whenValidCart() {
        ShoppingCart cart = ShoppingCart.builder()
                .id(1L)
                .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                .build();

        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(cart));

        shoppingCartService.cancelShoppingCart(1L);

        verify(shoppingCartRepository, times(1)).save(cart);
        assertEquals(ShoppingCartStatus.CANCELED, cart.getShoppingCartStatus());
    }

    @Test
    void cancelShoppingCart_shouldThrowShoppingCartNotFoundException_whenCartNotFound() {
        when(shoppingCartRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> shoppingCartService.cancelShoppingCart(1L));
    }
}
