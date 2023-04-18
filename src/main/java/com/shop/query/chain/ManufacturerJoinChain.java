package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.JOIN_MANUFACTURER_TABLE_NAME;
import static com.shop.query.chain.constants.ChainConstants.MANUFACTURER_ID;
import static com.shop.query.chain.constants.ChainConstants.MANUFACTURER_PARAMETER_KEY;
import static com.shop.query.chain.constants.ChainConstants.PRODUCT_MANUFACTURER_ID;

public class ManufacturerJoinChain extends QueryChain {
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder, QueryBuilder countQueryBuilder) {
        List<String> manufacturers = getRequestParameterValuesList(request, MANUFACTURER_PARAMETER_KEY);
        if (Objects.nonNull(manufacturers) && !manufacturers.isEmpty()) {
            readQueryBuilder.joinTableOn(JOIN_MANUFACTURER_TABLE_NAME, MANUFACTURER_ID, PRODUCT_MANUFACTURER_ID);
            countQueryBuilder.joinTableOn(JOIN_MANUFACTURER_TABLE_NAME, MANUFACTURER_ID, PRODUCT_MANUFACTURER_ID);
        }
        buildNext(filterDTO, request, readQueryBuilder, countQueryBuilder);
    }
}
