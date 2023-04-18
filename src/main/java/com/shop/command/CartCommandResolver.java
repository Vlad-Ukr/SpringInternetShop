package com.shop.command;


import com.shop.command.impl.AddCartCommand;
import com.shop.command.impl.ClearCartCommand;
import com.shop.command.impl.DeleteCartCommand;
import com.shop.command.impl.UpdateCartCommand;
import com.shop.command.constanst.CartCommandConstants;
import com.shop.service.CartService;
import com.shop.service.ProductService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CartCommandResolver {
    private Map<String, CartCommand> cartCommandMap;

    public CartCommandResolver(ProductService productService, CartService cartService) {
        this.cartCommandMap = new HashMap<>();
        cartCommandMap.put(CartCommandConstants.ADD_COMMAND_PARAMETER, new AddCartCommand(productService, cartService));
        cartCommandMap.put(CartCommandConstants.UPDATE_COMMAND_PARAMETER, new UpdateCartCommand(cartService));
        cartCommandMap.put(CartCommandConstants.DELETE_COMMAND_PARAMETER, new DeleteCartCommand(cartService));
        cartCommandMap.put(CartCommandConstants.CLEAR_COMMAND_PARAMETER, new ClearCartCommand(cartService));
    }

    public void invoke(String command, HttpServletRequest request) throws IOException {
        cartCommandMap.get(command).execute(request);
    }

}
