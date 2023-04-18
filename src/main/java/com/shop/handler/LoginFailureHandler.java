package com.shop.handler;

import com.shop.model.User;
import com.shop.service.impl.UserServiceImpl;
import com.shop.util.DateConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.shop.config.SecurityConfiguration.LOGIN_REQUEST_PARAMETER;
import static com.shop.util.UserErrors.USER_BAN_ERROR_MESSAGE;
import static com.shop.util.UserErrors.USER_LOGIN_ERROR_MESSAGE;
import static com.shop.util.UserErrors.USER_NOT_FOUND_ERROR_MESSAGE;

@Component
@PropertySource("classpath:application.properties")
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final String BAN_TIME_PROPERTY = "${ban.millis}";
    private static final String MAX_FAILED_ATTEMPTS_AMOUNT_PROPERTY = "${failed.attempts}";
    private final UserServiceImpl userService;
    @Value(BAN_TIME_PROPERTY)
    private long banTime;
    @Value(MAX_FAILED_ATTEMPTS_AMOUNT_PROPERTY)
    private int maxFailedAttempts;


    public LoginFailureHandler(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter(LOGIN_REQUEST_PARAMETER);
        User user = userService.findUserByEmail(email);
        if (Objects.isNull(user)) {
            exception = new UsernameNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE);
        } else if (!DateConverter.isUserBanned(user)) {
            userService.updateUserFailedAttemptsAndBanDate(user, maxFailedAttempts, banTime);
            exception = new BadCredentialsException(USER_LOGIN_ERROR_MESSAGE);
        } else if (DateConverter.isUserBanned(user)) {
            exception = new LockedException(USER_BAN_ERROR_MESSAGE + user.getBanDate());
        }
        super.onAuthenticationFailure(request, response, exception);
    }

}
