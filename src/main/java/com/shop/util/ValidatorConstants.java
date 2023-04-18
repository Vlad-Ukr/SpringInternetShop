package com.shop.util;

public class ValidatorConstants {

    public static final String PASSWORD_ERROR_KEY = "passwordError";
    public static final String R_PASSWORD_ERROR_KEY = "rpasswordError";
    public static final String CAPTCHA_ERROR_KEY = "captcha";
    public static final String EMPTY_FIELD_MESSAGE = "Cannot be blank";
    public static final String INVALID_PASSWORD_MESSAGE = " Password must contain minimum eight characters, " +
            "at least one uppercase letter, " +
            "one lowercase letter, " +
            "one number and one special character";
    public static final String DIFFERENT_PASSWORDS_MESSAGE = "Passwords does not match";
    public static final String EMPTY_CAPTCHA_MESSAGE = "Cannot be blank";
    public static final String DIFFERENT_CAPTCHA_MESSAGE = "Captcha value does not match";
    public static final String EXPIRED_CAPTCHA_MESSAGE = "Captcha is expired";
    public static final String BLANK_IMAGE = "imageError";
    public static final String BLANK_IMAGE_MESSAGE = "Image fila cannot be blank";
    public static final String INVALID_IMAGE_TYPE = "imageTypeError";
    public static final String INVALID_IMAGE_TYPE_MESSAGE = "Image type should be '.png' or '.jpg'";
    public static final String INVALID_IMAGE_PARAMETERS = "imageParametersError";
    public static final String INVALID_IMAGE_PARAMETERS_MESSAGE = "Image height and width should equals 250";

    public static final String PNG_IMAGE_TYPE=".png";
    public static final String JPG_IMAGE_TYPE=".jpg";
}
