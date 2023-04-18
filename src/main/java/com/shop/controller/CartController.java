package com.shop.controller;

import com.shop.command.CartCommandResolver;
import com.shop.model.Product;
import com.shop.service.impl.CartServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static com.shop.command.constanst.CartCommandConstants.CART_PARAMETER_KEY;
import static com.shop.controller.constants.ControllerConstants.ACTION_REQUEST_PARAMETER;
import static com.shop.controller.constants.ControllerConstants.INVALID_AMOUNT_ERROR;
import static com.shop.controller.constants.ControllerConstants.INVALID_AMOUNT_ERROR_MESSAGE;
import static com.shop.controller.constants.ControllerConstants.PAGE_ATTRIBUTE_KEY;
import static com.shop.controller.constants.ControllerConstants.PRODUCT_SERVLET_PATH_REQUEST_PARAMETER;
import static com.shop.controller.constants.ControllerConstants.TOTAL_PRICE_MODEL_ATTRIBUTE_KEY;
import static com.shop.util.ViewPages.CART_PAGE;
import static com.shop.util.ViewPages.REDIRECT_PREFIX;

@Controller
public class CartController {

    private final CartServiceImpl cartService;
    private final CartCommandResolver cartCommandInvoker;

    public CartController(CartServiceImpl cartService, CartCommandResolver cartCommandInvoker) {
        this.cartService = cartService;
        this.cartCommandInvoker = cartCommandInvoker;
    }

    @GetMapping("/cart")
    public String getCart(HttpSession session, Model model) {
        setCartIfNull(session);
        double totalPrice = cartService.getTotalPrice();
        model.addAttribute(TOTAL_PRICE_MODEL_ATTRIBUTE_KEY, totalPrice);
        if (Objects.nonNull(session.getAttribute(INVALID_AMOUNT_ERROR))) {
            model.addAttribute(INVALID_AMOUNT_ERROR, INVALID_AMOUNT_ERROR_MESSAGE);
            session.removeAttribute(INVALID_AMOUNT_ERROR);
        }
        String page = (String) session.getAttribute(PAGE_ATTRIBUTE_KEY);
        session.removeAttribute(PAGE_ATTRIBUTE_KEY);
        return page;
    }

    @PostMapping("/cart")
    public String postCart(HttpSession session, HttpServletRequest request,
                           @RequestParam(name = ACTION_REQUEST_PARAMETER) String action,
                           @RequestParam(name = PRODUCT_SERVLET_PATH_REQUEST_PARAMETER, defaultValue = CART_PARAMETER_KEY) String page)
            throws IOException {
        setCartIfNull(session);
        try {
            cartCommandInvoker.invoke(action, request);
        } catch (IllegalArgumentException exception) {
            session.setAttribute(INVALID_AMOUNT_ERROR, INVALID_AMOUNT_ERROR_MESSAGE);
        }
        session.setAttribute(PAGE_ATTRIBUTE_KEY, page);
        return REDIRECT_PREFIX + CART_PAGE;
    }

    private void setCartIfNull(HttpSession session) {
        if (Objects.isNull(cartService.getCart())) {
            cartService.setCart((HashMap<Product, Integer>) session.getAttribute(CART_PARAMETER_KEY));
        }
    }
}
