package com.shop.controller;

import com.shop.dto.FilterDTO;
import com.shop.query.builder.QueryBuilder;
import com.shop.query.chain.CategoryAndChain;
import com.shop.query.chain.CategoryJoinChain;
import com.shop.query.chain.ManufacturerAndChain;
import com.shop.query.chain.ManufacturerJoinChain;
import com.shop.query.chain.NameChain;
import com.shop.query.chain.OrderChain;
import com.shop.query.chain.PageChain;
import com.shop.query.chain.PriceChain;
import com.shop.query.chain.QueryChain;
import com.shop.query.chain.TableNameChain;
import com.shop.query.chain.WhereChain;
import com.shop.service.impl.CategoryServiceImpl;
import com.shop.service.impl.ManufacturerServiceImpl;
import com.shop.service.impl.ProductServiceImpl;
import com.shop.util.url.URLBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.shop.controller.constants.ControllerConstants.CATEGORY_LIST_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.MANUFACTURER_LIST_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.MAX_PRICE_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.MIN_PRICE_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.PAGES_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.PAGE_NUMBER_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.PAGE_ATTRIBUTE_KEY;
import static com.shop.controller.constants.ControllerConstants.PRODUCT_LIST_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.SELECTED_CATEGORIES_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.SELECTED_MANUFACTURERS_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.URL_MODEL_ATTRIBUTE;
import static com.shop.util.ViewPages.CATEGORY_PAGE;

@Controller
public class ProductsController {
    private final ProductServiceImpl productService;
    private final ManufacturerServiceImpl manufacturerService;
    private final CategoryServiceImpl categoryService;

    public ProductsController(ProductServiceImpl productService, ManufacturerServiceImpl manufacturerService,
                              CategoryServiceImpl categoryService) {
        this.productService = productService;
        this.manufacturerService = manufacturerService;
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String category(HttpServletRequest request, Model model) {
        QueryBuilder readQueryBuilder = new QueryBuilder();
        QueryBuilder countQueryBuilder = new QueryBuilder();
        QueryChain queryChain = initQueryChain();
        FilterDTO filterDTO = new FilterDTO();
        queryChain.buildQuery(filterDTO, request, readQueryBuilder, countQueryBuilder);

        int productsAmount = productService.countProductsAmountByQuery(countQueryBuilder);
        int pageNumber = Optional.ofNullable(request.getParameter(PAGE_ATTRIBUTE_KEY))
                .map(Integer::parseInt)
                .filter(pageNum -> pageNum >= 0)
                .orElse(0);
        int productAmountOnPage = filterDTO.getLimit();
        int pages = productsAmount / productAmountOnPage;
        if (productsAmount % productAmountOnPage > 0) {
            pages++;
        }
        model.addAttribute(CATEGORY_LIST_MODEL_ATTRIBUTE, categoryService.findAll());
        model.addAttribute(MANUFACTURER_LIST_MODEL_ATTRIBUTE, manufacturerService.findAll());
        model.addAttribute(PRODUCT_LIST_MODEL_ATTRIBUTE, productService.findProductListByQuery(readQueryBuilder));
        model.addAttribute(PAGE_NUMBER_MODEL_ATTRIBUTE, pageNumber);
        model.addAttribute(PAGES_MODEL_ATTRIBUTE, pages);
        model.addAttribute(URL_MODEL_ATTRIBUTE, URLBuilder.buildFilterURL(request));
        model.addAttribute(SELECTED_CATEGORIES_MODEL_ATTRIBUTE, filterDTO.getCategories());
        model.addAttribute(SELECTED_MANUFACTURERS_MODEL_ATTRIBUTE, filterDTO.getManufacturers());
        model.addAttribute(MIN_PRICE_MODEL_ATTRIBUTE, filterDTO.getMinPrice());
        model.addAttribute(MAX_PRICE_MODEL_ATTRIBUTE, filterDTO.getMaxPrice());
        return CATEGORY_PAGE;
    }

    private QueryChain initQueryChain() {
        return QueryChain.link(
                new TableNameChain(),
                new CategoryJoinChain(),
                new ManufacturerJoinChain(),
                new WhereChain(),
                new CategoryAndChain(),
                new ManufacturerAndChain(),
                new PriceChain(),
                new NameChain(),
                new OrderChain(),
                new PageChain());
    }
}
