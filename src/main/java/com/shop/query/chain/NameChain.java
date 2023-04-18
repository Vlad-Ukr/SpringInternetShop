package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;
import org.apache.logging.log4j.util.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.NAME_COLUMN_NAME;
import static com.shop.query.chain.constants.ChainConstants.SEARCH;

public class NameChain extends QueryChain {
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder, QueryBuilder countQueryBuilder) {
        String name = Strings.EMPTY;
        if (Objects.nonNull(request.getParameter(SEARCH))) {
            name = request.getParameter(SEARCH);
        }
        filterDTO.setNameLike(name);
        if(!name.isEmpty()){
            readQueryBuilder.and();
            countQueryBuilder.and();
            readQueryBuilder.Like(NAME_COLUMN_NAME, name);
            countQueryBuilder.Like(NAME_COLUMN_NAME, name);
        }
         buildNext( filterDTO,request,readQueryBuilder,countQueryBuilder);
    }
}
