package com.openclassrooms.safetynet.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class CalculateAge {
    public static Integer calculateAge(Instant birthdate) {
        return Period.between(birthdate
                .atZone(ZoneId.of("UTC"))
                .toLocalDate(), LocalDate.now()).getYears();
    }
}
