package com.shop.service.impl;


import com.shop.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@PropertySource("classpath:application.properties")
public class CaptchaServiceImpl implements CaptchaService {
    private final Map<String, Captcha> captchaMap = new HashMap<>();
    private static final String CAVOLINI_FONT = "Cavolini";
    private static final String TAHOMA_FONT = "Tahoma";
    private static final String IMG_TYPE = "jpeg";
    private static final String CAPTCHA_TIME_OUT_RESOURCE_KEY = "${captcha.time.out}";
    private static final String CAPTCHA_EXPIRED_MESSAGE = "Captcha is expired";
    private final int totalChars = 6;
    private final int iHeight = 60;
    private final int iWidth = 100;

    @Value(CAPTCHA_TIME_OUT_RESOURCE_KEY)
    private long captchaExpiredTime;

    @Override
    public String create() {
        String key = UUID.randomUUID().toString();
        Captcha captcha = new Captcha(key, getRandomString(), captchaExpiredTime);
        captchaMap.put(key, captcha);
        return key;
    }

    @Override
    public byte[] getImage(String uuid) {
        int fontSize = 30;
        Font font1 = new Font(CAVOLINI_FONT, Font.BOLD, fontSize);
        Font font2 = new Font(TAHOMA_FONT, Font.ITALIC, fontSize);
        String chars = captchaMap.get(uuid).getValue();
        BufferedImage img = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2dImage = (Graphics2D) img.getGraphics();
        g2dImage.setBackground(Color.WHITE);
        g2dImage.clearRect(0, 0, iWidth, iHeight);
        SecureRandom rnd = new SecureRandom();
        int colorBound = 255;
        int yCoordinate = 30;
        int xCoordinate = 15;
        int xCoordinateIteration = 5;
        for (int i = 0; i < chars.length(); i++) {
            g2dImage.setColor(new Color(rnd.nextInt(colorBound), rnd.nextInt(colorBound), rnd.nextInt(colorBound)));
            if (i % 2 == 0) {
                g2dImage.setFont(font1);
            } else {
                g2dImage.setFont(font2);
            }
            g2dImage.drawString(String.valueOf(chars.charAt(i)), xCoordinate * i + xCoordinateIteration, yCoordinate);
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, IMG_TYPE, os);
        } catch (IOException ignored) {
        }
        g2dImage.dispose();
        return os.toByteArray();
    }

    @Override
    public boolean validate(String uuid, String actual) throws TimeoutException {
        if (isExpired(uuid)) {
            captchaMap.remove(uuid);
            throw new TimeoutException(CAPTCHA_EXPIRED_MESSAGE);
        }
        return captchaMap.get(uuid).getValue().equals(actual);
    }

    @Override
    public boolean isExpired(String uuid) {
        return System.currentTimeMillis() > captchaMap.get(uuid).endDate.getTime();
    }


    private String getRandomString() {
        SecureRandom rnd = new SecureRandom();
        return String.valueOf(Math.abs(rnd.nextInt())).substring(0, totalChars);
    }

    private class Captcha {
        private final String uuid;
        private final String value;
        private final Date endDate;

        private long captchaTimeOut;

        public Captcha(String uuid, String value, long captchaTimeOut) {
            this.uuid = uuid;
            this.value = value;
            this.captchaTimeOut = captchaTimeOut;
            endDate = new Date(System.currentTimeMillis() + captchaTimeOut);
        }

        public String getUuid() {
            return uuid;
        }

        public String getValue() {
            return value;
        }

        public Date getEndDate() {
            return endDate;
        }

        private long getCaptchaTimeOut() {
            return captchaTimeOut;
        }
    }
}
