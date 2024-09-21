package com.ecommerce.system.shopping_cart_service.config;

import com.ecommerce.system.shopping_cart_service.model.entity.Product;
import com.ecommerce.system.shopping_cart_service.model.entity.ShoppingCart;
import com.ecommerce.system.shopping_cart_service.model.enums.ShoppingCartStatus;
import com.ecommerce.system.shopping_cart_service.repository.ProductRepository;
import com.ecommerce.system.shopping_cart_service.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public DataLoader(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {

        //Create a defualt shopping cart for test
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .shoppingCartStatus(ShoppingCartStatus.ACTIVE)
                .build();
        shoppingCartRepository.save(shoppingCart);

        //Create a defualt product for test
        Product product = Product.builder()
                .name("Product 1")
                .description("Test Product 1")
                .price(1000.0)
                .quantityInStock(10)
                .build();
        productRepository.save(product);
    }
}
