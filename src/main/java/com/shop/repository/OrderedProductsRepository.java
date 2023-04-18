package com.shop.repository;

import com.shop.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductsRepository extends JpaRepository<OrderedProduct, String> {
}
