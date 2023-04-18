package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.CATEGORY_NAME;
import static com.shop.query.chain.constants.ChainConstants.CATEGORY_PARAMETER_KEY;

public class CategoryAndChain extends QueryChain {
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder
            , QueryBuilder countQueryBuilder) {
        List<String> categories = getRequestParameterValuesList(request, CATEGORY_PARAMETER_KEY);
        filterDTO.setCategories(categories);
        if (Objects.nonNull(categories) && !categories.isEmpty()) {
            addListToQuery(categories, CATEGORY_NAME, readQueryBuilder, countQueryBuilder);
            readQueryBuilder.and();
            countQueryBuilder.and();
        }
        buildNext(filterDTO, request, readQueryBuilder, countQueryBuilder);
    }

}
