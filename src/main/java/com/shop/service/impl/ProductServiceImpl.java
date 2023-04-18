package com.shop.service.impl;

import com.shop.model.Product;
import com.shop.query.builder.QueryBuilder;
import com.shop.repository.ProductRepository;
import com.shop.service.ProductService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final JdbcTemplate jdbcTemplate;

    public ProductServiceImpl(ProductRepository productRepository, JdbcTemplate jdbcTemplate) {
        this.productRepository = productRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product findProduct(String productID) {
        return productRepository.findById(productID).get();
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findProductListByQuery(QueryBuilder queryBuilder) {
        return productRepository.readProductListByQuery(jdbcTemplate,queryBuilder);
    }

    @Override
    public int countProductsAmountByQuery(QueryBuilder queryBuilder) {
        return productRepository.readProductsAmountByQuery(jdbcTemplate,queryBuilder);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
