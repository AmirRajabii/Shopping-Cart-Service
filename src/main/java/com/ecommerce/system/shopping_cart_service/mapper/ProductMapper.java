package com.ecommerce.system.shopping_cart_service.mapper;

import com.ecommerce.system.shopping_cart_service.model.dto.ProductRequestDto;
import com.ecommerce.system.shopping_cart_service.model.dto.ProductResponseDto;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static Product toEntity(ProductRequestDto productDto) {
        return productDto == null ? null : Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantityInStock(productDto.getQuantityInStock())
                .build();
    }

    public static ProductResponseDto toDto(Product product) {
        return product == null ? null : ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantityInStock(product.getQuantityInStock())
                .build();
    }

    public static List<ProductResponseDto> toDtoList(List<Product> products) {
        return products.parallelStream()
                .unordered()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

}
