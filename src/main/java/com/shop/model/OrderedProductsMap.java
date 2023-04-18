package com.shop.model;

import java.util.HashMap;
import java.util.Map;

public final class OrderedProductsMap {

    private final Map<Product, Integer> orderedProductsMap;

    public OrderedProductsMap(Map<Product, Integer> orderedProductsMap) {
        this.orderedProductsMap = new HashMap<>(orderedProductsMap);
    }

    public Map<Product, Integer> getOrderedProductsMap() {
        return new HashMap<>(orderedProductsMap);
    }

    @Override
    public String toString() {
        return "OrderedProducts{" +
                "orderedProductsMap=" + orderedProductsMap +
                '}';
    }
}
