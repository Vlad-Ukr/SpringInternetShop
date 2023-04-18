package com.shop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.shop.controller.constants.ControllerConstants.ERROR_CODE_MODEL_ATTRIBUTE;
import static com.shop.util.ViewPages.ERROR_PAGE;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (Objects.nonNull(status)) {
            model.addAttribute(ERROR_CODE_MODEL_ATTRIBUTE, status.toString());
        }
        return ERROR_PAGE;
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
