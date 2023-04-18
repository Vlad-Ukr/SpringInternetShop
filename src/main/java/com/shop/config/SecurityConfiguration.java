package com.shop.config;

import com.shop.config.listener.CartSessionListener;
import com.shop.handler.LoginFailureHandler;
import com.shop.handler.LoginSuccessHandler;
import com.shop.service.impl.CartServiceImpl;
import com.shop.service.impl.UserServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpSessionListener;

import static com.shop.util.ViewPages.LOGIN_ERROR;
import static com.shop.util.ViewPages.LOGIN_PAGE_PATH;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;
    private final CartServiceImpl cartService;
    public static final String LOGIN_REQUEST_PARAMETER = "login";
    public static final String PASSWORD_REQUEST_PARAMETER = "password";
    public static final String INDEX_PAGE_MAPPING = "/index";

    public SecurityConfiguration(UserServiceImpl userService,CartServiceImpl cartService) {
        this.userService = userService;
        this.cartService=cartService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/registration", "/captcha").not().fullyAuthenticated()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/category", "/product", "/cart", "/last-user-avatar", "/image","/index")
                .permitAll()
                .mvcMatchers(LOGIN_ERROR).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE_PATH)
                .usernameParameter(LOGIN_REQUEST_PARAMETER)
                .passwordParameter(PASSWORD_REQUEST_PARAMETER)
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setDefaultRolePrefix(Strings.EMPTY);
        return defaultWebSecurityExpressionHandler;
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new LoginSuccessHandler(userService,cartService);
        simpleUrlAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(false);
        simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl(INDEX_PAGE_MAPPING);

        return simpleUrlAuthenticationSuccessHandler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler = new LoginFailureHandler(userService);
        simpleUrlAuthenticationFailureHandler.setDefaultFailureUrl(LOGIN_ERROR);
        return simpleUrlAuthenticationFailureHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new CartSessionListener());
    }

}