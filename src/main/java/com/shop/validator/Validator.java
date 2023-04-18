package com.shop.validator;

import com.shop.dto.UserDTO;
import com.shop.service.CaptchaService;
import com.shop.util.ValidatorConstants;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static com.shop.util.ValidatorConstants.INVALID_IMAGE_PARAMETERS;
import static com.shop.util.ValidatorConstants.INVALID_IMAGE_PARAMETERS_MESSAGE;
import static com.shop.util.ValidatorConstants.INVALID_IMAGE_TYPE;
import static com.shop.util.ValidatorConstants.INVALID_IMAGE_TYPE_MESSAGE;
import static com.shop.util.ValidatorConstants.JPG_IMAGE_TYPE;
import static com.shop.util.ValidatorConstants.PNG_IMAGE_TYPE;

public class Validator {
    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
    private static final int IMAGE_HEIGHT = 250;
    private static final int IMAGE_WIDTH = 250;
    private final Map<String, String> errorMap = new HashMap<>();
    private CaptchaService captchaService;

    public Validator(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    public Map<String, String> validate(UserDTO userDTO) {
        validatePassword(userDTO);
        validateCaptcha(userDTO);
        validateImage(userDTO);
        return errorMap;
    }

    private void validatePassword(UserDTO userDTO) {
        String password = userDTO.getPassword();
        String repeatPassword = userDTO.getRepeatPassword();
        if (isStringNullOrEmpty(password)) {
            errorMap.put(ValidatorConstants.PASSWORD_ERROR_KEY, ValidatorConstants.EMPTY_FIELD_MESSAGE);
        }
        if (!Objects.requireNonNull(password).matches(PASSWORD_PATTERN)) {
            errorMap.put(ValidatorConstants.PASSWORD_ERROR_KEY, ValidatorConstants.INVALID_PASSWORD_MESSAGE);
        }
        if (isStringNullOrEmpty(repeatPassword)) {
            errorMap.put(ValidatorConstants.R_PASSWORD_ERROR_KEY, ValidatorConstants.EMPTY_FIELD_MESSAGE);
        }
        if (!password.equals(repeatPassword)) {
            errorMap.put(ValidatorConstants.PASSWORD_ERROR_KEY, ValidatorConstants.DIFFERENT_PASSWORDS_MESSAGE);
        }
    }

    private void validateCaptcha(UserDTO userDTO) {
        String captcha = userDTO.getCaptcha();
        String rCaptcha = userDTO.getRepeatСaptcha();
        if (Objects.isNull(rCaptcha)) {
            errorMap.put(ValidatorConstants.CAPTCHA_ERROR_KEY, ValidatorConstants.EMPTY_CAPTCHA_MESSAGE);
        } else {
            try {
                if (!captchaService.validate(captcha, rCaptcha)) {
                    errorMap.put(ValidatorConstants.CAPTCHA_ERROR_KEY, ValidatorConstants.DIFFERENT_CAPTCHA_MESSAGE);
                }
            } catch (TimeoutException e) {
                errorMap.put(ValidatorConstants.CAPTCHA_ERROR_KEY, ValidatorConstants.EXPIRED_CAPTCHA_MESSAGE);
            }
        }
    }

    private void validateImage(UserDTO userDTO) {
        MultipartFile image = userDTO.getImage();
        if (Objects.isNull(image) && image.getOriginalFilename().isEmpty()) {
            errorMap.put(ValidatorConstants.BLANK_IMAGE, ValidatorConstants.BLANK_IMAGE_MESSAGE);
        }
        if (!(image.getOriginalFilename().endsWith(PNG_IMAGE_TYPE) || image.getOriginalFilename().endsWith(JPG_IMAGE_TYPE))) {
            errorMap.put(INVALID_IMAGE_TYPE, INVALID_IMAGE_TYPE_MESSAGE);
        }
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(image.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (bufferedImage.getHeight() != IMAGE_HEIGHT || bufferedImage.getWidth() != IMAGE_WIDTH) {
            errorMap.put(INVALID_IMAGE_PARAMETERS, INVALID_IMAGE_PARAMETERS_MESSAGE);
        }
    }

    private boolean isStringNullOrEmpty(String str) {
        return Objects.isNull(str) || str.equals(EMPTY_STRING);
    }
}
