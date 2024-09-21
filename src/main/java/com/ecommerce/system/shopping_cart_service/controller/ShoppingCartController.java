package com.ecommerce.system.shopping_cart_service.controller;

import com.ecommerce.system.shopping_cart_service.model.dto.CartItemRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemResponseDto;
import com.ecommerce.system.shopping_cart_service.model.dto.ShoppingCartResponseDto;
import com.ecommerce.system.shopping_cart_service.service.CartItemService;
import com.ecommerce.system.shopping_cart_service.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/shopping-cart")
@Tag(name = "Shopping Cart", description = "Operations related to Shopping Cart")
public class ShoppingCartController {

    private final CartItemService cartItemService;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(CartItemService cartItemService, ShoppingCartService shoppingCartService) {
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("/cart-item")
    public ResponseEntity<CartItemResponseDto> addItemToShoppingCart(@RequestBody @Valid CartItemRequestDto cartItemRequestDto) {
        CartItemResponseDto cartItemResponseDto = cartItemService.addCartItem(cartItemRequestDto);
        return new ResponseEntity<>(cartItemResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("cart-items/{id}")
    public ResponseEntity<List<CartItemResponseDto>> getShoppingCartItems(@PathVariable Long id) {
        List<CartItemResponseDto> cartItemResponseDtos = cartItemService.getCartItems(id);
        return new ResponseEntity<>(cartItemResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCartResponseDto> getShoppingCart(@PathVariable Long id) {
        ShoppingCartResponseDto shoppingCartResponseDtos = shoppingCartService.getShoppingCart(id);
        return new ResponseEntity<>(shoppingCartResponseDtos, HttpStatus.OK);
    }

    @PutMapping("cart-item/{id}/{quantity}")
    public ResponseEntity<String> changeQuantityOfItem(@PathVariable Long id, @PathVariable Integer quantity) {
        cartItemService.changeQuantity(id, quantity);
        return new ResponseEntity<>("Successfully Changed", HttpStatus.OK);
    }

    @DeleteMapping("cart-item/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("purchase/{id}")
    public ResponseEntity<String> purchaseShoppingCart(@PathVariable Long id) {
        shoppingCartService.purchaseShoppingCart(id);
        return new ResponseEntity<>("Successfully Purchased", HttpStatus.OK);
    }

    @PostMapping("cancel/{id}")
    public ResponseEntity<String> cancelShoppingCart(@PathVariable Long id) {
        shoppingCartService.cancelShoppingCart(id);
        return new ResponseEntity<>("Successfully Canceled", HttpStatus.OK);
    }
}
