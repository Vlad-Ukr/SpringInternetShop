package com.shop.repository;

import com.shop.mapper.impl.ProductRowMapperImpl;
import com.shop.model.Product;
import com.shop.query.builder.QueryBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    default List<Product> readProductListByQuery(JdbcTemplate jdbcTemplate, QueryBuilder queryBuilder) {
        return jdbcTemplate.query(queryBuilder.buildQuery(), new ProductRowMapperImpl());
    }

    default int readProductsAmountByQuery(JdbcTemplate jdbcTemplate, QueryBuilder queryBuilder) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(queryBuilder.buildQuery(), Integer.class))
                .orElseThrow(IllegalStateException::new);
    }
}
