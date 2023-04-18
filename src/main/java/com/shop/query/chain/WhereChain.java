package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;

public class WhereChain extends QueryChain{
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder, QueryBuilder countQueryBuilder) {
        readQueryBuilder.where();
        countQueryBuilder.where();
         buildNext(filterDTO,request,readQueryBuilder,countQueryBuilder);
    }
}
