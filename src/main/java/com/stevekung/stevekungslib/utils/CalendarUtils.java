package com.stevekung.stevekungslib.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class CalendarUtils
{
    public static boolean isHalloweenDay()
    {
        return month() == 10 && day() == 31;
    }

    public static boolean isChristmasDay()
    {
        return month() == 12 && day() >= 24 && day() <= 26;
    }

    public static boolean isMorePlanetsBirthDay()
    {
        return month() == 3 && day() == 31;
    }

    public static boolean isMyBirthDay()
    {
        return month() == 2 && day() >= 1 && day() <= 3;
    }

    public static int month()
    {
        return LocalDate.now().get(ChronoField.MONTH_OF_YEAR);
    }

    public static int day()
    {
        return LocalDate.now().get(ChronoField.DAY_OF_MONTH);
    }
}