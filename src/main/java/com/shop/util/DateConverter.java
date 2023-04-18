package com.shop.util;

import com.shop.model.User;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateConverter {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String convertLongToTimeStamp(long millis) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date(millis);
        return formatter.format(date);
    }
    public static boolean isUserBanned(User user){
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime banDate = LocalDateTime.parse(user.getBanDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        return banDate.isAfter(currentDate);
    }
}
