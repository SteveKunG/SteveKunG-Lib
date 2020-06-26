package com.stevekung.stevekungslib.utils;

import java.text.DecimalFormat;

public class NumberUtils
{
    public static final DecimalFormat NUMBER_FORMAT = new DecimalFormat("#,###");
    public static final DecimalFormat NUMBER_FORMAT_WITH_DECIMAL = new DecimalFormat("#,###.#");
    public static final DecimalFormat NUMBER_FORMAT_WITH_OPERATORS = new DecimalFormat("+#;-#");

    public static String formatCompact(long number)
    {
        if (number < 1000)
        {
            return String.valueOf(number);
        }
        int exp = (int) (Math.log(number) / Math.log(1000));
        return String.format("%.1f%c", number / Math.pow(1000, exp), "kMGTPE".charAt(exp - 1));
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