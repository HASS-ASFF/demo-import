package com.api.demoimport.entity.BilanActif;

import java.text.DecimalFormat;

public class FormatUtils {
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static Double formatDecimal(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
}
