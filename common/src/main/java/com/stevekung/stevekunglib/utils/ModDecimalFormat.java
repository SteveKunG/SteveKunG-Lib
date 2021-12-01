package com.stevekung.stevekunglib.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ModDecimalFormat extends DecimalFormat
{
    public ModDecimalFormat(String pattern)
    {
        super(pattern);
        var symbols = DecimalFormatSymbols.getInstance(Locale.ENGLISH);
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator('.');
        this.setDecimalFormatSymbols(symbols);
    }
}