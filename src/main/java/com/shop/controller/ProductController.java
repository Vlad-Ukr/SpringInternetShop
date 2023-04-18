package com.shop.controller;

import com.shop.model.Category;
import com.shop.model.Manufacturer;
import com.shop.model.Product;
import com.shop.service.impl.CategoryServiceImpl;
import com.shop.service.impl.ManufacturerServiceImpl;
import com.shop.service.impl.ProductServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.shop.controller.constants.ControllerConstants.CATEGORY_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.MANUFACTURER_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.PRODUCT_ID_REQUEST_PARAMETER;
import static com.shop.controller.constants.ControllerConstants.PRODUCT_MODEL_ATTRIBUTE;
import static com.shop.util.ViewPages.PRODUCT_PAGE;

@Controller
public class ProductController {

    private final ProductServiceImpl productService;
    private final ManufacturerServiceImpl manufacturerService;
    private final CategoryServiceImpl categoryService;

    public ProductController(ProductServiceImpl productService, ManufacturerServiceImpl manufacturerService, CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @GetMapping("/product")
    public String product(@RequestParam(name = PRODUCT_ID_REQUEST_PARAMETER) String id, Model model) {
        Product product = productService.findProduct(id);
        Category category = categoryService.find(product.getCategoryId());
        Manufacturer manufacturer = manufacturerService.find(product.getManufacturerId());
        model.addAttribute(PRODUCT_MODEL_ATTRIBUTE, product);
        model.addAttribute(CATEGORY_MODEL_ATTRIBUTE, category);
        model.addAttribute(MANUFACTURER_MODEL_ATTRIBUTE, manufacturer);
        return PRODUCT_PAGE;
    }
}
