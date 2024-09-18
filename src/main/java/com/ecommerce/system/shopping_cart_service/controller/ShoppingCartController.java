package com.ecommerce.system.shopping_cart_service.controller;

import com.ecommerce.system.shopping_cart_service.model.dto.CartItemRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.CartItemResponseDto;
import com.ecommerce.system.shopping_cart_service.service.CartItemService;
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

    @Autowired
    public ShoppingCartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }


    @PostMapping
    public ResponseEntity<CartItemResponseDto> addItemToShoppingCart(@RequestBody @Valid CartItemRequestDto cartItemRequestDto) {
        CartItemResponseDto cartItemResponseDto = cartItemService.addCartItem(cartItemRequestDto);
        return new ResponseEntity<>(cartItemResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CartItemResponseDto>> getShppingCartItems(@PathVariable Long id) {
        List<CartItemResponseDto> cartItemResponseDtos = cartItemService.getCartItems(id);
        return new ResponseEntity<>(cartItemResponseDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}/{quantity}")
    public ResponseEntity<String> changeQuantityOfItem(@PathVariable Long id, @PathVariable Integer quantity) {
        cartItemService.changeQuantity(id, quantity);
        return new ResponseEntity<>("Successfully Changed", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItemById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
