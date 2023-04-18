package com.shop.mapper.impl;

import com.shop.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapperImpl implements RowMapper<Product> {
    private static final String PRODUCT_ID = "productID";
    private static final String CATEGORY_ID = "categoryID";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String QUANTITY = "quantity";
    private static final String DESCRIPTION = "description";
    private static final String MANUFACTURER = "manufacturerID";

    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getString(PRODUCT_ID));
        product.setCategoryId(resultSet.getInt(CATEGORY_ID));
        product.setName(resultSet.getString(NAME));
        product.setPrice(resultSet.getDouble(PRICE));
        product.setQuantity(resultSet.getInt(QUANTITY));
        product.setDescription(resultSet.getString(DESCRIPTION));
        product.setManufacturerId(resultSet.getInt(MANUFACTURER));
        return product;

    }
}
