package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.model.enums.Sorting;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.DEFAULT_SORTING;
import static com.shop.query.chain.constants.ChainConstants.SORTING_PARAMETER_KEY;
import static com.shop.query.chain.constants.ChainConstants.SPACE;
import static com.shop.query.chain.constants.ChainConstants.UNDERSCORE;

public class OrderChain extends QueryChain{
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder, QueryBuilder countQueryBuilder) {
        if (Objects.nonNull(request.getParameter(SORTING_PARAMETER_KEY))) {
            int sortingKey;
            try {
                sortingKey = Integer.parseInt(request.getParameter(SORTING_PARAMETER_KEY));
            } catch (NumberFormatException ex) {
                sortingKey = 0;
            }
            for (Sorting sorting : Sorting.values()) {
                if (sorting.ordinal() == sortingKey) {
                    String sort = sorting.toString().replaceAll(UNDERSCORE, SPACE).toLowerCase(Locale.ROOT);
                    filterDTO.setOrderBy(sort);
                    break;
                }
            }
            if (Objects.isNull(filterDTO.getOrderBy())) {
                filterDTO.setOrderBy(DEFAULT_SORTING);
            }
        } else {
            filterDTO.setOrderBy(DEFAULT_SORTING);
        }
        if (Objects.nonNull(filterDTO.getOrderBy())) {
            readQueryBuilder.orderBy(filterDTO.getOrderBy());
        }
        buildNext(filterDTO, request, readQueryBuilder, countQueryBuilder);
    }
}
