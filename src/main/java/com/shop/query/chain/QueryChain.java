package com.shop.query.chain;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shop.query.chain.constants.ChainConstants.WORD_REGEX;

public abstract class QueryChain {
    private QueryChain next;

    public static QueryChain link(QueryChain first, QueryChain... queryChains) {
        QueryChain head = first;
        for (QueryChain nextInChain : queryChains) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract void buildQuery(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder
            , QueryBuilder countQueryBuilder);

    protected void buildNext(FilterDTO filterDTO, HttpServletRequest request, QueryBuilder readQueryBuilder
            , QueryBuilder countQueryBuilder) {
        if (next == null) {
            return ;
        }
         next.buildQuery(filterDTO, request, readQueryBuilder, countQueryBuilder);
    }
    protected List<String> getRequestParameterValuesList(HttpServletRequest request, String parameterKey){
        List<String> parameters = new ArrayList<>();
        for (String str : Arrays
                .stream(Optional.ofNullable(request.getParameterValues(parameterKey)).orElse(new String[]{}))
                .collect(Collectors.toList())) {
            if (str.matches(WORD_REGEX)) {
                parameters.add(str);
            }
        }
        return parameters;
    }
    protected void addListToQuery(List<String> list, String columnName, QueryBuilder readQueryBuilder,
                                              QueryBuilder countQueryBuilder) {
        readQueryBuilder.leftBracket();
        countQueryBuilder.leftBracket();
        for (String value : list) {
            readQueryBuilder.columnName(columnName).equals().value(value);
            countQueryBuilder.columnName(columnName).equals().value(value);
            if (list.lastIndexOf(value) != list.size() - 1) {
                readQueryBuilder.or();
                countQueryBuilder.or();
            }
        }
        readQueryBuilder.rightBracket();
        countQueryBuilder.rightBracket();
    }
}
