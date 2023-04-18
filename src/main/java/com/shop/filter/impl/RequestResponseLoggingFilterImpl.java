package com.shop.filter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Order(1)
public class RequestResponseLoggingFilterImpl implements Filter {

    Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilterImpl.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        logger.info(
                "Logging Request  {} : {}", req.getMethod(),
                req.getRequestURI());
        filterChain.doFilter(req, res);
        logger.info(
                "Logging Response :{}",
                res.getContentType());
    }

    @Override
    public void destroy() {

    }


}
