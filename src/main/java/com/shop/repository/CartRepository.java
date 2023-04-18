package com.shop.repository;


import com.shop.model.Product;

import java.util.List;
import java.util.Map;

public interface CartRepository {

    void setCart(Map<Product, Integer> cartMap);

    void addProductToCart(Product product, int productAmount);

    void deleteProductFromCart(String productID);

    List<Product> readProductList();

    int calculateAmountOfProducts();

    double calculateTotalPrice();

    Map<Product, Integer> readAllCart();

    void updateProductAmount(String productID, int newAmount);

    void clearCart();

}
