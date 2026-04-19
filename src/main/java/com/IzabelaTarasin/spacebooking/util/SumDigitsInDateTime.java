package com.IzabelaTarasin.spacebooking.util;

import java.time.LocalDateTime;

public final class SumDigitsInDateTime {
    private SumDigitsInDateTime(){}

    public static int sumDigits(LocalDateTime date) {
        int sum = 0;
        for (char c : date.toString().toCharArray()) {
            if (Character.isDigit(c)) {
                sum += c - '0';
            }
        }
        return sum;
    }
}
