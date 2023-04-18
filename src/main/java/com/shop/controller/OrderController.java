package com.shop.controller;

import com.shop.model.Order;
import com.shop.model.OrderedProductsMap;
import com.shop.model.PaymentsDetails;
import com.shop.model.enums.OrderStatus;
import com.shop.model.enums.PaymentType;
import com.shop.service.impl.CartServiceImpl;
import com.shop.service.impl.OrderServiceImpl;
import com.shop.util.DateConverter;
import com.shop.util.PaymentDetailsPool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;
import java.util.UUID;

import static com.shop.controller.constants.ControllerConstants.DATE_REQUEST_PARAMETER;
import static com.shop.controller.constants.ControllerConstants.EMPTY_CART_ERROR;
import static com.shop.controller.constants.ControllerConstants.EMPTY_CART_ERROR_MESSAGE;
import static com.shop.controller.constants.ControllerConstants.ERROR_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.INVALID_PAYMENT_DETAILS_ERROR_MESSAGE;
import static com.shop.controller.constants.ControllerConstants.ORDER_DERAILS_REQUEST_PARAMETER;
import static com.shop.controller.constants.ControllerConstants.ORDER_DETAILS_SESSION_PARAMETER;
import static com.shop.controller.constants.ControllerConstants.PAYMENT_DETAILS_MODEL_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.PAYMENT_REQUEST_PARAMETER;
import static com.shop.controller.constants.ControllerConstants.SUCCESS_MESSAGE;
import static com.shop.controller.constants.ControllerConstants.SUCCESS_MESSAGE_ATTRIBUTE;
import static com.shop.controller.constants.ControllerConstants.TOTAL_PRICE_MODEL_ATTRIBUTE_KEY;
import static com.shop.controller.constants.ControllerConstants.USER_ID_ATTRIBUTE;
import static com.shop.util.ViewPages.CHECKOUT_PAGE;
import static com.shop.util.ViewPages.PAYMENT_PAGE;
import static com.shop.util.ViewPages.REDIRECT_PREFIX;

@Controller
public class OrderController {
    private final CartServiceImpl cartService;
    private final OrderServiceImpl orderService;
    private final PaymentDetailsPool paymentDetailsPool;

    public OrderController(CartServiceImpl cartService, OrderServiceImpl orderService,
                           PaymentDetailsPool paymentDetailsPool) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.paymentDetailsPool = paymentDetailsPool;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        if (Objects.nonNull(session.getAttribute(SUCCESS_MESSAGE_ATTRIBUTE))) {
            model.addAttribute(SUCCESS_MESSAGE_ATTRIBUTE, session.getAttribute(SUCCESS_MESSAGE_ATTRIBUTE));
            session.removeAttribute(SUCCESS_MESSAGE_ATTRIBUTE);
        }
        model.addAttribute(TOTAL_PRICE_MODEL_ATTRIBUTE_KEY, cartService.getTotalPrice());
        return CHECKOUT_PAGE;
    }

    @PostMapping("/order")
    public String order(@RequestParam(name = ORDER_DERAILS_REQUEST_PARAMETER, required = false) String orderDetails,
                        HttpSession session) {
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.ACCEPTED);
        if (!orderDetails.isEmpty()) {
            order.setDetailing(orderDetails);
        }
        order.setUserID((String) session.getAttribute(USER_ID_ATTRIBUTE));
        order.setOrderedProductsMap(new OrderedProductsMap(cartService.getCart()));
        order.setDate(DateConverter.convertLongToTimeStamp(System.currentTimeMillis()));
        cartService.clearCart();
        orderService.saveOrder(order);
        session.setAttribute(SUCCESS_MESSAGE_ATTRIBUTE, SUCCESS_MESSAGE);
        return REDIRECT_PREFIX + CHECKOUT_PAGE;
    }

    @GetMapping("/payment")
    public String payment(@RequestParam(name = PAYMENT_REQUEST_PARAMETER) int payment, HttpSession session,
                          @RequestParam(name = ORDER_DERAILS_REQUEST_PARAMETER, required = false) String orderDetails,
                          @ModelAttribute(value = PAYMENT_DETAILS_MODEL_ATTRIBUTE) PaymentsDetails paymentsDetails,
                          Model model) {
        if (cartService.getCart().isEmpty()) {
            model.addAttribute(EMPTY_CART_ERROR, EMPTY_CART_ERROR_MESSAGE);
            model.addAttribute(TOTAL_PRICE_MODEL_ATTRIBUTE_KEY, cartService.getTotalPrice());
            return CHECKOUT_PAGE;
        }
        if (Objects.nonNull(session.getAttribute(ERROR_ATTRIBUTE))) {
            model.addAttribute((String) session.getAttribute(ERROR_ATTRIBUTE), ERROR_ATTRIBUTE);
            session.removeAttribute(ERROR_ATTRIBUTE);
        }
        if (payment == PaymentType.CASH.ordinal()) {
            return order(orderDetails, session);
        }
        model.addAttribute(TOTAL_PRICE_MODEL_ATTRIBUTE_KEY, cartService.getTotalPrice());
        session.setAttribute(ORDER_DETAILS_SESSION_PARAMETER, orderDetails);
        return PAYMENT_PAGE;
    }

    @PostMapping("/payment")
    public String paymentPost(@RequestParam(name = DATE_REQUEST_PARAMETER, required = false) String date,
                              HttpSession session,
                              @ModelAttribute(value = PAYMENT_DETAILS_MODEL_ATTRIBUTE)
                              @Valid PaymentsDetails paymentsDetails,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(TOTAL_PRICE_MODEL_ATTRIBUTE_KEY, cartService.getTotalPrice());
            model.addAttribute(TOTAL_PRICE_MODEL_ATTRIBUTE_KEY, cartService.getTotalPrice());
            return PAYMENT_PAGE;
        }
        paymentsDetails.setDate(date);
        if (!paymentDetailsPool.isPaymentDetailsInPool(paymentsDetails)) {
            model.addAttribute(ERROR_ATTRIBUTE, INVALID_PAYMENT_DETAILS_ERROR_MESSAGE);
            model.addAttribute(TOTAL_PRICE_MODEL_ATTRIBUTE_KEY, cartService.getTotalPrice());
            return PAYMENT_PAGE;
        }
        String orderDetails = (String) session.getAttribute(ORDER_DETAILS_SESSION_PARAMETER);
        session.removeAttribute(ORDER_DETAILS_SESSION_PARAMETER);
        return order(orderDetails, session);
    }
}
