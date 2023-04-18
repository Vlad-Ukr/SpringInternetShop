package com.shop.query.builder;

public class QueryBuilder {
    private final StringBuilder stringBuilder;
    private static final String SELECT = "SELECT";
    private static final String ALL = "*";
    private static final String SPACE = " ";
    private static final String COUNT = "COUNT(*)";
    private static final String FROM = "FROM";
    private static final String WHERE = "WHERE";
    private static final String JOIN = "JOIN";
    private static final String ON = "ON";
    private static final String EQUALS = "=";
    private static final String AND = "AND";
    private static final String BETWEEN = "BETWEEN";
    private static final String OR = "OR";
    private static final String QUOTE = "'";
    private static final String ORDER_BY = "ORDER BY";
    private static final String LIMIT = "LIMIT";
    private static final String COMMA = ",";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String DESC="DESC";
    private static final String LIKE = "LIKE";
    private static final String PERCENT = "%";


    public QueryBuilder() {
        stringBuilder = new StringBuilder();
    }

    public QueryBuilder select() {
        stringBuilder.append(SELECT).append(SPACE);
        return this;
    }

    public QueryBuilder all() {
        stringBuilder.append(ALL).append(SPACE);
        return this;
    }

    public QueryBuilder count() {
        stringBuilder.append(COUNT).append(SPACE);
        return this;
    }

    public QueryBuilder fromTable(String tableName) {
        stringBuilder.append(FROM).append(SPACE);
        stringBuilder.append(tableName).append(SPACE);
        return this;
    }

    public QueryBuilder where() {
        stringBuilder.append(WHERE).append(SPACE);
        return this;
    }

    public QueryBuilder joinTableOn(String tableName, String firstIdName, String secondIdName) {
        stringBuilder.append(JOIN).append(SPACE);
        stringBuilder.append(tableName).append(SPACE);
        stringBuilder.append(ON).append(SPACE);
        stringBuilder.append(firstIdName).append(EQUALS);
        stringBuilder.append(secondIdName).append(SPACE);
        return this;
    }

    public QueryBuilder and() {
        stringBuilder.append(AND).append(SPACE);
        return this;
    }

    public QueryBuilder betweenValue(String columnName, Object minValue, Object maxValue) {
        stringBuilder.append(columnName).append(SPACE);
        stringBuilder.append(BETWEEN).append(SPACE);
        stringBuilder.append(QUOTE).append(minValue).append(QUOTE).append(SPACE);
        stringBuilder.append(AND).append(SPACE).append(QUOTE).append(maxValue).append(QUOTE)
                .append(SPACE);
        return this;
    }

    public QueryBuilder or() {
        stringBuilder.append(OR).append(SPACE);
        return this;
    }

    public QueryBuilder limit(int pageNum, int rowNum) {
        stringBuilder.append(LIMIT).append(SPACE);
        stringBuilder.append(pageNum * rowNum).append(COMMA).
                append(SPACE).append(rowNum).append(SPACE);
        return this;
    }

    public QueryBuilder orderBy(String columnName) {
        stringBuilder.append(ORDER_BY).append(SPACE).append(columnName).append(SPACE);
        return this;
    }

    public QueryBuilder columnName(String columnName) {
        stringBuilder.append(columnName).append(SPACE);
        return this;
    }

    public QueryBuilder equals() {
        stringBuilder.append(EQUALS);
        return this;
    }

    public QueryBuilder value(Object value) {
        stringBuilder.append(QUOTE).append(value).append(QUOTE).append(SPACE);
        return this;
    }

    public QueryBuilder leftBracket() {
        stringBuilder.append(LEFT_BRACKET);
        return this;
    }

    public QueryBuilder rightBracket() {
        stringBuilder.append(RIGHT_BRACKET).append(SPACE);
        return this;
    }


    public QueryBuilder Like(String columnName, String value) {
        stringBuilder.append(columnName).append(SPACE)
                .append(LIKE).append(SPACE).append(QUOTE)
                .append(value).append(PERCENT).append(QUOTE).append(SPACE);
        return this;
    }

    public String buildQuery() {
        return stringBuilder.toString();
    }

}
