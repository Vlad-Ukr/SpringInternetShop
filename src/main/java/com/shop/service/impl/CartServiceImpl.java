package com.shop.service.impl;


import com.shop.model.Product;
import com.shop.repository.impl.CartRepositoryImpl;
import com.shop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepositoryImpl cartRepository;

    public CartServiceImpl(CartRepositoryImpl cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public void saveProductInCart(Product product, int productAmount) {
        if (productAmount <= 0) {
            throw new IllegalArgumentException();
        }
        cartRepository.addProductToCart(product, productAmount);
    }

    @Override
    public void removeProductFromCart(String productID) {
        cartRepository.deleteProductFromCart(productID);
    }

    @Override
    public List<Product> getProductList() {
        return cartRepository.readProductList();
    }

    @Override
    public int getAmountOfProducts() {
        return cartRepository.calculateAmountOfProducts();
    }

    @Override
    public double getTotalPrice() {
        return cartRepository.calculateTotalPrice();
    }

    @Override
    public Map<Product, Integer> getCart() {
        return cartRepository.readAllCart();
    }

    @Override
    public void changeProductAmount(String productID, int newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException();
        }
        cartRepository.updateProductAmount(productID, newAmount);
    }

    @Override
    public void clearCart() {
        cartRepository.clearCart();
    }

    @Override
    public void setCart(HashMap<Product, Integer> cart) {
        cartRepository.setCart(cart);
    }

}
