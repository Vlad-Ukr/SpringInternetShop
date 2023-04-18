package com.shop.controller;

import com.shop.dto.UserDTO;
import com.shop.model.User;
import com.shop.model.enums.UserRole;
import com.shop.service.CaptchaService;
import com.shop.service.RoleService;
import com.shop.service.impl.ImageServiceImpl;
import com.shop.service.impl.RoleServiceImpl;
import com.shop.service.impl.UserServiceImpl;
import com.shop.validator.Validator;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import static com.shop.util.ViewPages.INDEX_PAGE;
import static com.shop.util.ViewPages.LOGIN_PAGE;
import static com.shop.util.ViewPages.LOGIN_PAGE_PATH;
import static com.shop.util.ViewPages.REDIRECT_PREFIX;
import static com.shop.util.ViewPages.REGISTRATION_PAGE;

@Controller
public class RegistrationController {
    private static final String IMG_CONTENT_TYPE = "image/jpg";
    private static final String CAPTCHA_ID_ATTRIBUTE = "captchaID";
    private static final String USER_DTO_PARAMETER_KEY = "userDTO";
    private static final String IMAGE_PARAMETER_KEY = "image";
    private static final String ERROR_MAP_ATTRIBUTE_KEY = "errorMap";
    private static final String ERROR_ATTRIBUTE_KEY = "error";
    private final UserServiceImpl userService;
    private final CaptchaService captchaService;
    private final ImageServiceImpl imageService;


    public RegistrationController(UserServiceImpl userDetailsService, CaptchaService captchaService
            , ImageServiceImpl imageService){
        this.userService = userDetailsService;
        this.captchaService = captchaService;
        this.imageService = imageService;
            }

    @GetMapping("/registration")
    public String registrationGet(@ModelAttribute(value = USER_DTO_PARAMETER_KEY) UserDTO userDTO) {
        return REGISTRATION_PAGE;
    }

    @PostMapping("/registration")
    public String registrationPost(HttpServletRequest request,
                                   @RequestParam(name = IMAGE_PARAMETER_KEY, required = false) MultipartFile image,
                                   @ModelAttribute(USER_DTO_PARAMETER_KEY) @Valid UserDTO userDTO,
                                   BindingResult bindingResult, Model model) {
        Validator userDTOValidator = new Validator(captchaService);
        userDTO.setCaptcha((String) request.getSession().getAttribute(CAPTCHA_ID_ATTRIBUTE));
        userDTO.setImage(image);
        Map<String, String> errorMap = userDTOValidator.validate(userDTO);
        if (errorMap.isEmpty() && !bindingResult.hasErrors()) {
            User user = new User(userDTO);
            userService.createUser(user, UserRole.USER.ordinal());
            imageService.saveProfilePicture(user, image);
            return REDIRECT_PREFIX + LOGIN_PAGE;
        } else {
            model.addAttribute(ERROR_MAP_ATTRIBUTE_KEY, errorMap);
            return REGISTRATION_PAGE;
        }
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest req, HttpServletResponse resp) {
        String captchaID = captchaService.create();
        req.getSession().setAttribute(CAPTCHA_ID_ATTRIBUTE, captchaID);
        OutputStream outputStream;
        resp.setContentType(IMG_CONTENT_TYPE);
        try {
            outputStream = resp.getOutputStream();
            outputStream.write(captchaService.getImage((String) req.getSession().getAttribute(CAPTCHA_ID_ATTRIBUTE)));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @GetMapping("/last-user-avatar")
    public void avatar(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String imgName = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("lastUserId")) {
                imgName = cookie.getValue();
                break;
            }
        }
        if (!imgName.isEmpty()) {
            OutputStream outputStream;
            try {
                outputStream = response.getOutputStream();
                outputStream.write(imageService.findProfilePictureById(imgName));
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    @GetMapping("/index")
    public String index() {
        return INDEX_PAGE;
    }

    @GetMapping("/login-error")
    public String error(HttpServletRequest request, Model model) {
        Exception exception = (Exception) request.getSession()
                .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        model.addAttribute(ERROR_ATTRIBUTE_KEY, exception.getMessage());
        return LOGIN_PAGE_PATH;
    }
}
