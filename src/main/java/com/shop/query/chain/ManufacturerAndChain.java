package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.shop.query.chain.constants.ChainConstants.MANUFACTURER_NAME;
import static com.shop.query.chain.constants.ChainConstants.MANUFACTURER_PARAMETER_KEY;

public class ManufacturerAndChain extends QueryChain {
    @Override
    public void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder,
                           QueryBuilder countQueryBuilder) {
        List<String> manufacturers = getRequestParameterValuesList(request, MANUFACTURER_PARAMETER_KEY);
        filterDTO.setManufacturers(manufacturers);
        if (Objects.nonNull(manufacturers) && !manufacturers.isEmpty()) {
            addListToQuery(manufacturers, MANUFACTURER_NAME, readQueryBuilder, countQueryBuilder);
            readQueryBuilder.and();
            countQueryBuilder.and();
        }
        buildNext(filterDTO, request, readQueryBuilder, countQueryBuilder);
    }


}
