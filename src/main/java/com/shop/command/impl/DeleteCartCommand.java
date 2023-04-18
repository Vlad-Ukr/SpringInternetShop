package com.shop.command.impl;


import com.shop.command.CartCommand;
import com.shop.command.constanst.CartCommandConstants;
import com.shop.service.CartService;

import javax.servlet.http.HttpServletRequest;

public class DeleteCartCommand implements CartCommand {

    private CartService cartService;

    public DeleteCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        String productID = request.getParameter(CartCommandConstants.PRODUCT_ID_PARAMETER_KEY);
        cartService.removeProductFromCart(productID);
    }

}
