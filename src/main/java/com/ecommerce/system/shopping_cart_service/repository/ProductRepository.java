package com.ecommerce.system.shopping_cart_service.repository;

import com.ecommerce.system.shopping_cart_service.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);

    void changeQuantityInStock(long id, int quantity);

}
