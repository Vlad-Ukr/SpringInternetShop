package com.shop.repository.impl;


import com.shop.model.Product;
import com.shop.repository.CartRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class CartRepositoryImpl implements CartRepository {
    private Map<Product, Integer> cartMap;

    @Override
    public void setCart(Map<Product, Integer> cartMap) {
        this.cartMap = cartMap;
    }

    @Override
    public void addProductToCart(Product product, int productAmount) {
        if (cartMap.containsKey(product)) {
            int oldAmount = cartMap.get(product);
            cartMap.put(product, productAmount + oldAmount);
        } else {
            cartMap.put(product, productAmount);
        }
    }

    @Override
    public void deleteProductFromCart(String productID) {
        cartMap.remove(cartMap.keySet().stream()
                .filter(product -> product.getId().equals(productID))
                .findFirst().orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<Product> readProductList() {
        return new ArrayList<>(cartMap.keySet());
    }

    @Override
    public int calculateAmountOfProducts() {
        return cartMap.values().stream().reduce(Integer::sum).orElse(0);
    }

    @Override
    public double calculateTotalPrice() {
        return cartMap.entrySet().stream()
                .mapToDouble(value -> value.getKey().getPrice() * value.getValue()).sum();
    }

    @Override
    public Map<Product, Integer> readAllCart() {
        return cartMap;
    }

    @Override
    public void updateProductAmount(String productID, int newAmount) {
        cartMap.entrySet().stream()
                .filter(productIntegerEntry -> productIntegerEntry.getKey().getId().equals(productID))
                .forEach(productIntegerEntry -> productIntegerEntry.setValue(newAmount));
    }

    @Override
    public void clearCart() {
        cartMap.clear();
    }


}
