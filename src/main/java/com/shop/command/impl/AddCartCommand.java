package com.shop.command.impl;


import com.shop.command.CartCommand;
import com.shop.command.constanst.CartCommandConstants;
import com.shop.model.Product;
import com.shop.service.CartService;
import com.shop.service.ProductService;

import javax.servlet.http.HttpServletRequest;

public class AddCartCommand implements CartCommand {
    private ProductService productService;
    private CartService cartService;

    public AddCartCommand(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @Override
    public void execute(HttpServletRequest request) {
        String productID = request.getParameter(CartCommandConstants.PRODUCT_ID_PARAMETER_KEY);
        Product product = productService.findProduct(productID);
        int productAmount = Integer.parseInt(request.getParameter(CartCommandConstants.PRODUCT_QUANTITY_PARAMETER_KEY));
        cartService.saveProductInCart(product, productAmount);
    }

}
