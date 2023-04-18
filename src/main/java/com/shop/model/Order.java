package com.shop.model;


import com.shop.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "orderID")
    private String id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "order_status")
    private OrderStatus status;
    @Column(name = "order_detailing")
    private String detailing;

    @Column(name = "order_date")
    private String date;
    @Column(name = "user_id")
    private String userID;
    @Transient
    private OrderedProductsMap orderedProductsMap;
    @OneToMany(mappedBy = "order")
    private Set<OrderedProduct> orderedProductSet;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (Objects.isNull(object) || getClass() != object.getClass()) {
            return false;
        }
        Order order = (Order) object;
        return Objects.equals(id, order.id) && status == order.status && Objects.equals(detailing, order.detailing)
                && Objects.equals(date, order.date) && Objects.equals(userID, order.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, detailing, date, userID);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", detailing='" + detailing + '\'' +
                ", date=" + date +
                ", userID='" + userID + '\'' +
                '}';
    }
}
