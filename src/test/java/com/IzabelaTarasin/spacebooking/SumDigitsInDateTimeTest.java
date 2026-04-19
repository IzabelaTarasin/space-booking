package com.IzabelaTarasin.spacebooking;

import com.IzabelaTarasin.spacebooking.util.SumDigitsInDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumDigitsInDateTimeTest {
    @ParameterizedTest(name = "[{index}] {0} -> {1}")
    @CsvSource(delimiter = '|', textBlock = """
            2030-01-01T00:00:00 | 7
            2000-12-31T12:30:00 | 15
            1999-06-15T09:00:00 | 49
            """)
    public void sumDigits_matchesExpected(String isoDateTime, int expectedSum) {
        LocalDateTime date = LocalDateTime.parse(isoDateTime);
        assertEquals(expectedSum, SumDigitsInDateTime.sumDigits(date));
    }
}
