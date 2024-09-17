package com.ecommerce.system.shopping_cart_service.service;

import com.ecommerce.system.shopping_cart_service.exception.ProductNotFoundException;
import com.ecommerce.system.shopping_cart_service.mapper.ProductMapper;
import com.ecommerce.system.shopping_cart_service.model.dto.ProductDto;
import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto addProduct(ProductDto product) {
        return ProductMapper.toDto(productRepository.save(ProductMapper.toEntity(product)));
    }

    public ProductDto updateProduct(Long id, ProductDto updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setQuantityInStock(updatedProduct.getQuantityInStock());

        return ProductMapper.toDto( productRepository.save(existingProduct));
    }

    public ProductDto getProductById(Long id) {
        return ProductMapper.toDto(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.toDtoList(products);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

}
