package com.shop.service;


import com.shop.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartService {

    void saveProductInCart(Product product, int productAmount);

    void removeProductFromCart(String productID);

    List<Product> getProductList();

    int getAmountOfProducts();

    double getTotalPrice();

    public Map<Product, Integer> getCart();

    void changeProductAmount(String productID, int newAmount);

    void clearCart();

    void setCart(HashMap<Product, Integer> cart);

}
