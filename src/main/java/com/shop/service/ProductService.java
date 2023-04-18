package com.shop.service;

import com.shop.model.Product;
import com.shop.query.builder.QueryBuilder;

import java.util.List;

public interface ProductService {
    Product findProduct(String productID);

    void updateProduct(Product product);

    List<Product> findProductListByQuery(QueryBuilder queryBuilder);

    int countProductsAmountByQuery(QueryBuilder queryBuilder);

    List<Product> findAll();
}
