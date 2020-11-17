package com.plaid.reit.util;

import java.util.Random;

public class AccountNumberUtil {

    private AccountNumberUtil() {}

    public static String generateRandom() {
        Random random = new Random();
        char[] digits = new char[12];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < 12; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }

}
