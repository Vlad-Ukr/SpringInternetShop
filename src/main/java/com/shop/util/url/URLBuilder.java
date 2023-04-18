package com.shop.util.url;



import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class URLBuilder {
    private static final String CONTROLLER = "/category?";
    private static final String MINIMAL_PRICE = "minPrice";
    private static final String MAXIMUM_PRICE = "maxPrice";
    private static final String EQUALS = "=";
    private static final String AND = "&";
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String CATEGORY = "category";
    private static final String STRING_REGEX = "[A-Za-z]+";
    private static final String MANUFACTURER = "manufacturer";
    private static final String SORTING = "sorting";
    private static final String LIMIT = "limit";
    private static final int DEFAULT_LIMIT = 3;
    private static final String DEFAULT_SORTING="name";
    private static final String SEARCH = "nameLike";

    public static String buildFilterURL(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTROLLER);
        if (Objects.nonNull(request.getParameter(MINIMAL_PRICE))
                && request.getParameter(MINIMAL_PRICE).matches(NUMBER_REGEX)) {
            stringBuilder.append(MINIMAL_PRICE).append(EQUALS).append(request.getParameter(MINIMAL_PRICE)).append(AND);
        }
        if (Objects.nonNull(request.getParameter(MAXIMUM_PRICE))
                && request.getParameter(MAXIMUM_PRICE).matches(NUMBER_REGEX)) {
            stringBuilder.append(MAXIMUM_PRICE).append(EQUALS).append(request.getParameter(MAXIMUM_PRICE)).append(AND);
        }
        for (String str : Arrays
                .stream(Optional.ofNullable(request.getParameterValues(CATEGORY))
                        .orElse(new String[]{})).collect(Collectors.toList())) {
            if (str.matches(STRING_REGEX)) {
                stringBuilder.append(CATEGORY).append(EQUALS).append(str).append(AND);
            }
        }
        for (String str : Arrays
                .stream(Optional.ofNullable(request.getParameterValues(MANUFACTURER))
                        .orElse(new String[]{})).collect(Collectors.toList())) {
            if (str.matches(STRING_REGEX)) {
                stringBuilder.append(MANUFACTURER).append(EQUALS).append(str).append(AND);
            }
        }
        if (Objects.nonNull(request.getParameter(LIMIT)) && request.getParameter(LIMIT).matches(NUMBER_REGEX)) {
            stringBuilder.append(LIMIT).append(EQUALS).append(request.getParameter(LIMIT)).append(AND);
        } else {
            stringBuilder.append(LIMIT).append(EQUALS).append(DEFAULT_LIMIT).append(AND);
        }
        if (Objects.nonNull(request.getParameter(SORTING))) {
            stringBuilder.append(SORTING).append(EQUALS).append(request.getParameter(SORTING)).append(AND);
        } else {
            stringBuilder.append(SORTING).append(EQUALS).append(DEFAULT_SORTING).append(AND);
        }
        if (Objects.nonNull(request.getParameter(SEARCH))) {
            stringBuilder.append(SEARCH).append(EQUALS).append(request.getParameter(SEARCH)).append(AND);
        }
        return stringBuilder.toString();
    }
}
