package com.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "product_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(name = "product_categoryID")
    private int id;
    @Column(name = "categoryName")
    private String name;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (Objects.isNull(object) || getClass() != object.getClass()) {
            return false;
        }
        Category category = (Category) object;
        return id == category.id && Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
