package com.ecommerce.system.shopping_cart_service.repository;

import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> productStore = new HashMap<>();
    private long productIdSequence = 0;

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            productIdSequence++;
            product.setId(productIdSequence);
            product.setCreatedAt(LocalDateTime.now());
        }
        product.setUpdatedAt(LocalDateTime.now());
        productStore.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productStore.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productStore.values());
    }

    @Override
    public void deleteById(Long id) {
        productStore.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return productStore.containsKey(id);
    }
}