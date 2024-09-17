package com.ecommerce.system.shopping_cart_service.mapper;

import com.ecommerce.system.shopping_cart_service.model.dto.ProductRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.ProductResponseDto;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static Product toEntity(ProductResponseDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantityInStock(productDto.getQuantityInStock());
        return product;
    }

    public static Product toEntity(ProductRequestDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantityInStock(productDto.getQuantityInStock());
        return product;
    }

    public static ProductResponseDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductResponseDto productDto = new ProductResponseDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantityInStock(product.getQuantityInStock());
        return productDto;
    }

    public static List<ProductResponseDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Product> toEntityList(List<ProductResponseDto> productDtos) {
        return productDtos.stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList());
    }
}
