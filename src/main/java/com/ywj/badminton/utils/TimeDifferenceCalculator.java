package com.ywj.badminton.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDifferenceCalculator {

        public static String calculate(String time, LocalDateTime now){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
                if (dateTime.isBefore(now)) {
                        return "0";
                } else {
                        Duration duration = Duration.between(now, dateTime);
                        long difference = duration.getSeconds();
                        return Long.toString(difference);
                }
        }

}