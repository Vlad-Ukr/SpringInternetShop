package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.MAX_PRICE_PARAMETER_KEY;
import static com.shop.query.chain.constants.ChainConstants.MIN_PRICE_PARAMETER_KEY;
import static com.shop.query.chain.constants.ChainConstants.NUMBER_REGEX;
import static com.shop.query.chain.constants.ChainConstants.PRICE_COLUMN_NAME;


public class PriceChain extends QueryChain {
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder
            , QueryBuilder countQueryBuilder) {
        double minPrice = Double.MIN_VALUE;
        double maxPrice = Double.MAX_VALUE;
        if (Objects.nonNull(request.getParameter(MIN_PRICE_PARAMETER_KEY))
                && request.getParameter(MIN_PRICE_PARAMETER_KEY).matches(NUMBER_REGEX)) {
            minPrice = Double.parseDouble(request.getParameter(MIN_PRICE_PARAMETER_KEY));
        }
        if (Objects.nonNull(request.getParameter(MAX_PRICE_PARAMETER_KEY))
                && request.getParameter(MAX_PRICE_PARAMETER_KEY).matches(NUMBER_REGEX)) {
            maxPrice = Double.parseDouble(request.getParameter(MAX_PRICE_PARAMETER_KEY));
        }
        filterDTO.setMaxPrice(maxPrice);
        filterDTO.setMinPrice(minPrice);
        readQueryBuilder.betweenValue(PRICE_COLUMN_NAME, minPrice, maxPrice);
        countQueryBuilder.betweenValue(PRICE_COLUMN_NAME, minPrice, maxPrice);
         buildNext(filterDTO,request, readQueryBuilder, countQueryBuilder);
    }
}
