package com.openclassrooms.safetynet.utils;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculateAgeTests {
    @Test
    void calculateAge() {
        new CalculateAge(); // test coverage class
        Instant birthdate = Instant.parse("1994-07-22T00:00:00Z");
        int expectedAge = 28;

        int actualAge = CalculateAge.calculateAge(birthdate);
        assertEquals(expectedAge, actualAge);
    }
}
