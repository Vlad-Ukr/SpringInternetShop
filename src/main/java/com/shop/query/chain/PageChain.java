package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.DEFAULT_LIMIT;
import static com.shop.query.chain.constants.ChainConstants.LIMIT_PARAMETER_KEY;
import static com.shop.query.chain.constants.ChainConstants.NUMBER_REGEX;
import static com.shop.query.chain.constants.ChainConstants.PAGE_PARAMETER_KEY;

public class PageChain extends QueryChain {
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder, QueryBuilder countQueryBuilder) {
        int page = 0;
        if (Objects.nonNull(request.getParameter(PAGE_PARAMETER_KEY))
                && request.getParameter(PAGE_PARAMETER_KEY).matches(NUMBER_REGEX)) {
            page = Integer.parseInt(request.getParameter(PAGE_PARAMETER_KEY));
        }
        int limit = DEFAULT_LIMIT;
        if (Objects.nonNull(request.getParameter(LIMIT_PARAMETER_KEY))
                && request.getParameter(LIMIT_PARAMETER_KEY).matches(NUMBER_REGEX)) {
            limit = Integer.parseInt(request.getParameter(LIMIT_PARAMETER_KEY));
        }

        readQueryBuilder.limit(page, limit);
        filterDTO.setLimit(limit);
        filterDTO.setPage(page);
         buildNext(filterDTO, request, readQueryBuilder, countQueryBuilder);
    }
}
