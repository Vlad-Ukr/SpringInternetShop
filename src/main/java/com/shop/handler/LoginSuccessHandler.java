package com.shop.handler;

import com.shop.model.Product;
import com.shop.model.User;
import com.shop.service.CartService;
import com.shop.service.impl.CartServiceImpl;
import com.shop.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static com.shop.command.constanst.CartCommandConstants.CART_PARAMETER_KEY;
import static com.shop.config.SecurityConfiguration.LOGIN_REQUEST_PARAMETER;

@PropertySource("classpath:application.properties")
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final String COOKIE_MAX_AGE_PROPERTY = "${captcha.time.out}";
    private static final String LAST_USER_ID_COOKIE = "lastUserId";
    private static final String USER_ID_SESSION_ATTRIBUTE = "userID";
    private static final String USER_NAME_SESSION_ATTRIBUTE = "userName";
    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;
    @Value(COOKIE_MAX_AGE_PROPERTY)
    private int cookieMaxAge;

    public LoginSuccessHandler(UserServiceImpl userService, CartServiceImpl cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = request.getParameter(LOGIN_REQUEST_PARAMETER);
        User user = userService.findUserByEmail(email);
        if (user.getFailedAttempts() != 0) {
            userService.updateUserFailedAttempts(user.getId(), 0);
        }
        Cookie cookie = new Cookie(LAST_USER_ID_COOKIE, user.getId());
        cookie.setMaxAge(cookieMaxAge);
        response.addCookie(cookie);
        request.getSession().setAttribute(USER_ID_SESSION_ATTRIBUTE, user.getId());
        request.getSession().setAttribute(USER_NAME_SESSION_ATTRIBUTE, user.getName());
        cartService.setCart((HashMap<Product, Integer>) request.getSession().getAttribute(CART_PARAMETER_KEY));
        super.onAuthenticationSuccess(request, response, authentication);

    }
}
