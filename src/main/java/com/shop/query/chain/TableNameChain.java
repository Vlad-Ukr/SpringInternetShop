package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;

import static com.shop.query.chain.constants.ChainConstants.TABLE_NAME;

public class TableNameChain extends QueryChain{
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder, QueryBuilder countQueryBuilder) {
        readQueryBuilder.select().all().fromTable(TABLE_NAME);
        countQueryBuilder.select().count().fromTable(TABLE_NAME);
         buildNext(filterDTO,request,readQueryBuilder,countQueryBuilder);
    }
}
