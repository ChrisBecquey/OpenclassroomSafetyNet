package com.openclassrooms.safetynet.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CalculateAge {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static Integer calculateAge(Instant birthdate) {
        return Period.between(birthdate
                .atZone(ZoneId.of("UTC"))
                .toLocalDate(), LocalDate.now()).getYears();
    }
}
