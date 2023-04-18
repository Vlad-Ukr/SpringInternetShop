package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.CATEGORY_ID;
import static com.shop.query.chain.constants.ChainConstants.CATEGORY_PARAMETER_KEY;
import static com.shop.query.chain.constants.ChainConstants.JOIN_CATEGORY_TABLE_NAME;
import static com.shop.query.chain.constants.ChainConstants.PRODUCT_CATEGORY_ID;

public class CategoryJoinChain extends QueryChain {
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder, QueryBuilder countQueryBuilder) {
        List<String> categories = getRequestParameterValuesList(request, CATEGORY_PARAMETER_KEY);
        if (Objects.nonNull(categories) && !categories.isEmpty()) {
            readQueryBuilder.joinTableOn(JOIN_CATEGORY_TABLE_NAME, CATEGORY_ID, PRODUCT_CATEGORY_ID);
            countQueryBuilder.joinTableOn(JOIN_CATEGORY_TABLE_NAME, CATEGORY_ID, PRODUCT_CATEGORY_ID);
        }
        buildNext(filterDTO, request, readQueryBuilder, countQueryBuilder);
    }
}
