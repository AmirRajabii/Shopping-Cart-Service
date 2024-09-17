package com.ecommerce.system.shopping_cart_service.controller;

import com.ecommerce.system.shopping_cart_service.model.dto.ProductDto;
import com.ecommerce.system.shopping_cart_service.service.ProductService;
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
@RequestMapping("/api/v1/product")
@Tag(name = "Product", description = "Operations related to Product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService ProductService) {
        this.productService = ProductService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto ProductDto) {
        ProductDto createdProductDto = productService.addProduct(ProductDto);
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> ProductDtos = productService.getAllProducts();
        return new ResponseEntity<>(ProductDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto ProductDto = productService.getProductById(id);
        return new ResponseEntity<>(ProductDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductDto(@PathVariable Long id, @RequestBody @Valid ProductDto ProductDto) {
        ProductDto updatedProductDto = productService.updateProduct(id, ProductDto);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductDto(@PathVariable Long id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}