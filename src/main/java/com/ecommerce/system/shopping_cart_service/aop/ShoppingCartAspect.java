package com.ecommerce.system.shopping_cart_service.aop;

import com.ecommerce.system.shopping_cart_service.exception.ShoppingCartNotActiveException;
import com.ecommerce.system.shopping_cart_service.service.ShoppingCartService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ShoppingCartAspect {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartAspect(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Before("@annotation(CheckCartActive) && args(id,..)")
    public void checkCartIsActive(Long id) {
        if (!shoppingCartService.isCartActive(id)) {
            throw new ShoppingCartNotActiveException(id);
        }
    }
}
