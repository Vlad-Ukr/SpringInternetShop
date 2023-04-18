package com.shop.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Column(name = "productID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "description")
    private String description;
    @Column(name = "categoryID")
    private int categoryId;
    @Column(name = "manufacturerID")
    private int manufacturerId;
    @OneToMany(mappedBy = "product")
    private Set<OrderedProduct> orderedProductSet;



    public Product(String name, double price, int quantity, String description,
                   int categoryId, int manufacturerId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }

    public Product(UUID id, String name, double price, int quantity,
                   String description, int categoryId, int manufacturerId) {
        this.id = id.toString();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
        this.manufacturerId = manufacturerId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", manufacturerId=" + manufacturerId +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (Objects.isNull(object) || getClass() != object.getClass()) {
            return false;
        }
        Product product = (Product) object;
        return Double.compare(product.price, price) == 0 && quantity == product.quantity
                && categoryId == product.categoryId && manufacturerId == product.manufacturerId
                && Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity, description, categoryId, manufacturerId);
    }
}
