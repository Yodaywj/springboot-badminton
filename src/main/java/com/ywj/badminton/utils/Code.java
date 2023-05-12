package com.ywj.badminton.utils;

import java.util.Random;

public class Code {
    public static String generateCode(int length) {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // 生成0-9之间的随机数字
            codeBuilder.append(digit);
        }

        return codeBuilder.toString();
    }
}
