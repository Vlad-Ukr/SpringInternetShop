package com.shop.command.impl;


import com.shop.command.CartCommand;
import com.shop.command.constanst.CartCommandConstants;
import com.shop.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public class UpdateCartCommand implements CartCommand {
    private CartService cartService;

    public UpdateCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        String[] productQuantities = Optional.ofNullable(request.getParameterValues(CartCommandConstants.PRODUCT_QUANTITY_PARAMETER_KEY)).orElse(new String[]{});
        int counter = 0;
        for (String productId : Optional.ofNullable(request.getParameterValues(CartCommandConstants.PRODUCT_ID_PARAMETER_KEY))
                .orElse(new String[]{})) {
            cartService.changeProductAmount(productId, Integer.parseInt(productQuantities[counter++]));
        }
    }

}
