package com.shop.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "products_orders")
@Getter
@Setter
public class OrderedProduct {
    @Id()
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "ordered_product_amount")
    private int productAmount;
    @Column(name = "price_by_order_date")
    private double priceByOrderDate;

    public OrderedProduct() {
    }

    public OrderedProduct(String id, String name, Product product, Order order, int productAmount,
                          double priceByOrderDate) {
        this.id = id;
        this.name = name;
        this.product = product;
        this.order = order;
        this.productAmount = productAmount;
        this.priceByOrderDate = priceByOrderDate;
    }
}
