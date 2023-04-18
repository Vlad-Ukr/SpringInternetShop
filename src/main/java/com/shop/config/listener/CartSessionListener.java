package com.shop.config.listener;

import com.shop.model.Product;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

import static com.shop.command.constanst.CartCommandConstants.CART_PARAMETER_KEY;

@WebListener
public class CartSessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        Map<Product, Integer> cart = new HashMap<>();
        event.getSession().setAttribute(CART_PARAMETER_KEY, cart);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().invalidate();
    }
}
