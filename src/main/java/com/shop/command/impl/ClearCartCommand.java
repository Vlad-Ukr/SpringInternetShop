package com.shop.command.impl;


import com.shop.command.CartCommand;
import com.shop.service.CartService;

import javax.servlet.http.HttpServletRequest;

public class ClearCartCommand implements CartCommand {

    private CartService cartService;

    public ClearCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        cartService.clearCart();
    }

}
