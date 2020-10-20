package com.stevekung.stevekungslib.utils;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.NavigableMap;

import com.google.common.collect.Maps;

public class NumberUtils
{
    private static final NavigableMap<Long, String> SUFFIXES = Maps.newTreeMap();

    public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###");
    public static final DecimalFormat NUMBER_FORMAT_WITH_DECIMAL = new DecimalFormat("#,###.#");
    public static final DecimalFormat NUMBER_FORMAT_WITH_OPERATORS = new DecimalFormat("+#;-#");

    static
    {
        SUFFIXES.put(1_000L, "K");
        SUFFIXES.put(1_000_000L, "M");
        SUFFIXES.put(1_000_000_000L, "G");
        SUFFIXES.put(1_000_000_000_000L, "T");
        SUFFIXES.put(1_000_000_000_000_000L, "P");
        SUFFIXES.put(1_000_000_000_000_000_000L, "E");
    }

    public static String formatCompact(long value)
    {
        if (value == Long.MIN_VALUE)
        {
            return NumberUtils.formatCompact(Long.MIN_VALUE + 1);
        }
        if (value < 0)
        {
            return "-" + NumberUtils.formatCompact(-value);
        }
        if (value < 1000)
        {
            return Long.toString(value);
        }
        Map.Entry<Long, String> entry = NumberUtils.SUFFIXES.floorEntry(value);
        Long divideBy = entry.getKey();
        String suffix = entry.getValue();
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && truncated / 10D != truncated / 10;
        return hasDecimal ? truncated / 10D + suffix : truncated / 10 + suffix;
    }

    public static boolean isNumeric(CharSequence cs)
    {
        int sz = cs.length();

        for (int i = 0; i < sz; i++)
        {
            if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != '.')
            {
                return false;
            }
        }
        return true;
    }

    public static String intToRoman(int num)
    {
        StringBuilder sb = new StringBuilder();
        int times = 0;
        String[] romans = new String[] { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
        int[] ints = new int[] { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };

        for (int i = ints.length - 1; i >= 0; i--)
        {
            times = num / ints[i];
            num %= ints[i];

            while (times > 0)
            {
                sb.append(romans[i]);
                times--;
            }
        }
        return sb.toString();
    }
}