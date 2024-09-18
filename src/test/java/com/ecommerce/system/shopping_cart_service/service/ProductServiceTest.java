package com.ecommerce.system.shopping_cart_service.service;

import com.ecommerce.system.shopping_cart_service.exception.ProductNotFoundException;
import com.ecommerce.system.shopping_cart_service.mapper.ProductMapper;
import com.ecommerce.system.shopping_cart_service.model.dto.ProductRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.ProductResponseDto;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.repository.ProductRepository;
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

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductRequestDto productRequestDto;
    private Product product;
    private ProductResponseDto productResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productRequestDto = new ProductRequestDto();
        productRequestDto.setName("Test Product");
        productRequestDto.setDescription("Description of Test Product");
        productRequestDto.setPrice(99.99);
        productRequestDto.setQuantityInStock(10);

        product = new Product();
        product.setId(1L);
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setQuantityInStock(productRequestDto.getQuantityInStock());

        productResponseDto = ProductMapper.toDto(product);
    }

    @Test
    void addProduct_shouldReturnProductResponseDto() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDto result = productService.addProduct(productRequestDto);

        assertNotNull(result);
        assertEquals(productResponseDto.getName(), result.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldReturnUpdatedProductResponseDto() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductRequestDto updatedProduct = new ProductRequestDto();
        updatedProduct.setName("Updated Product");
        updatedProduct.setDescription("Updated Description");
        updatedProduct.setPrice(89.99);
        updatedProduct.setQuantityInStock(5);

        ProductResponseDto result = productService.updateProduct(1L, updatedProduct);

        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getProductById_shouldReturnProductResponseDto() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(productResponseDto.getName(), result.getName());
        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_shouldThrowProductNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository).findById(1L);
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(productResponseDto.getName(), result.get(0).getName());
        verify(productRepository).findAll();
    }

    @Test
    void deleteProductById_shouldDeleteProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProductById(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProductById_shouldThrowProductNotFoundException() {
        when(productRepository.existsById(1L)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(1L));
        verify(productRepository, never()).deleteById(1L);
    }
}
