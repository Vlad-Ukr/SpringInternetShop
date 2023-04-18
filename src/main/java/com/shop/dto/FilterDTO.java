package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterDTO {
    private double minPrice;
    private double maxPrice;
    private List<String> manufacturers;
    private List<String> categories;
    private int page;
    private int limit;
    private String orderBy;
    private String nameLike;


    @Override
    public String toString() {
        return "FilterDTO{" +
                "minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", manufacturers=" + manufacturers +
                ", categories=" + categories +
                ", page=" + page +
                ", limit=" + limit +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }

}
