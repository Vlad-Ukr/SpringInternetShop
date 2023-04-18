package com.shop.service;

import java.util.concurrent.TimeoutException;

public interface CaptchaService {
  String create();

  byte[] getImage(String uuid);

  boolean validate(String uuid,String actual) throws TimeoutException;

  boolean isExpired(String uuid);
}
