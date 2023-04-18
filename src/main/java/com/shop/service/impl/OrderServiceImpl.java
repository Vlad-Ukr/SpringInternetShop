package com.shop.service.impl;

import com.shop.model.Order;
import com.shop.model.OrderedProduct;
import com.shop.model.Product;
import com.shop.repository.OrderRepository;
import com.shop.repository.OrderedProductsRepository;
import com.shop.service.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    public final OrderedProductsRepository orderedProductsRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderedProductsRepository orderedProductsRepository) {
        this.orderRepository = orderRepository;
        this.orderedProductsRepository = orderedProductsRepository;
    }

    @Transactional
    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
        Map<Product, Integer> orderedProducts = order.getOrderedProductsMap().getOrderedProductsMap();
        List<OrderedProduct> orderedProductList = orderedProducts.entrySet().stream().map(productIntegerEntry ->
                        new OrderedProduct(UUID.randomUUID().toString(), productIntegerEntry.getKey().getName(),
                                productIntegerEntry.getKey(), order, productIntegerEntry.getValue(),
                                productIntegerEntry.getKey().getPrice()))
                .collect(Collectors.toList());
        orderedProductsRepository.saveAll(orderedProductList);
    }
}
