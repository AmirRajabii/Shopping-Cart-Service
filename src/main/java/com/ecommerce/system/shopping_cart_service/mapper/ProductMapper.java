package com.ecommerce.system.shopping_cart_service.mapper;

import com.ecommerce.system.shopping_cart_service.model.dto.ProductDto;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static Product toEntity(ProductDto productDto) {
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

    public static ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setQuantityInStock(product.getQuantityInStock());
        return productDto;
    }

    public static List<ProductDto> toDtoList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public static List<Product> toEntityList(List<ProductDto> productDtos) {
        return productDtos.stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList());
    }
}
