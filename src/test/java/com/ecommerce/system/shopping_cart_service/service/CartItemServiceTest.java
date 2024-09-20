package com.ecommerce.system.shopping_cart_service.service;


import com.ecommerce.system.shopping_cart_service.exception.CartItemNotFoundException;
import com.ecommerce.system.shopping_cart_service.exception.ProductNotFoundException;
import com.ecommerce.system.shopping_cart_service.exception.ShoppingCartNotFoundException;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemResponseDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCartItem_shouldAddCartItem_whenValidRequest() {
        CartItemRequestDto requestDto = CartItemRequestDto.builder()
                .shoppigCartId(1L)
                .productId(1L)
                .quantity(5)
                .build();

        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .quantityInStock(10)
                .build();

        CartItem cartItem = CartItem.builder()
                .id(1L)
                .product(product)
                .quantity(5)
                .shoppingCart(ShoppingCart.builder()
                        .id(1L)
                        .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                        .build())
                .build();

        when(shoppingCartRepository.findById(requestDto.getShoppigCartId())).thenReturn(Optional.of(mock(ShoppingCart.class)));
        when(productRepository.findById(requestDto.getProductId())).thenReturn(Optional.of(product));
        when(cartItemRepository.save(any(CartItem.class))).thenReturn(cartItem);

        CartItemResponseDto responseDto = cartItemService.addCartItem(requestDto);

        assertEquals(5, responseDto.getQuantity());
        verify(cartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    void addCartItem_shouldThrowShoppingCartNotFoundException_whenCartNotFound() {
        CartItemRequestDto requestDto = CartItemRequestDto.builder()
                .shoppigCartId(1L)
                .productId(1L)
                .quantity(5)
                .build();

        when(shoppingCartRepository.findById(requestDto.getShoppigCartId())).thenReturn(Optional.empty());

        assertThrows(ShoppingCartNotFoundException.class, () -> cartItemService.addCartItem(requestDto));
    }

    @Test
    void addCartItem_shouldThrowProductNotFoundException_whenProductNotFound() {
        CartItemRequestDto requestDto = CartItemRequestDto.builder()
                .shoppigCartId(1L)
                .productId(1L)
                .quantity(5)
                .build();

        when(shoppingCartRepository.findById(requestDto.getShoppigCartId())).thenReturn(Optional.of(mock(ShoppingCart.class)));
        when(productRepository.findById(requestDto.getProductId())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> cartItemService.addCartItem(requestDto));
    }

    @Test
    void changeQuantity_shouldChangeQuantity_whenValidRequest() {
        CartItem cartItem = CartItem.builder()
                .id(1L)
                .product(Product.builder()
                        .id(1L)
                        .name("Product")
                        .quantityInStock(10)
                        .build())
                .quantity(2)
                .build();

        when(cartItemRepository.findById(1L)).thenReturn(Optional.of(cartItem));
        when(productRepository.findById(cartItem.getProduct().getId())).thenReturn(Optional.of(cartItem.getProduct()));

        cartItemService.changeQuantity(1L, 5);

        verify(cartItemRepository, times(1)).changeQuantity(1L, 5);
    }

    @Test
    void changeQuantity_shouldThrowCartItemNotFoundException_whenCartItemNotFound() {
        when(cartItemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CartItemNotFoundException.class, () -> cartItemService.changeQuantity(1L, 5));
    }

    @Test
    void getCartItems_shouldReturnCartItems_whenValidShoppingCartId() {
        List<CartItem> cartItems = List.of(CartItem.builder()
                .id(1L)
                .product(Product.builder()
                        .id(1L)
                        .name("Product")
                        .quantityInStock(10)
                        .build())
                .quantity(2)
                .shoppingCart(ShoppingCart.builder()
                        .id(1L)
                        .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                        .build())
                .build());

        when(cartItemRepository.findByShoppingCartId(1L)).thenReturn(cartItems);

        List<CartItemResponseDto> response = cartItemService.getCartItems(1L);

        assertEquals(1, response.size());
        verify(cartItemRepository, times(1)).findByShoppingCartId(1L);
    }

    @Test
    void deleteCartItemById_shouldDeleteCartItem_whenExists() {
        when(cartItemRepository.existsById(1L)).thenReturn(true);

        cartItemService.deleteCartItemById(1L);

        verify(cartItemRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCartItemById_shouldThrowProductNotFoundException_whenCartItemNotFound() {
        when(cartItemRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> cartItemService.deleteCartItemById(1L));
    }
}
