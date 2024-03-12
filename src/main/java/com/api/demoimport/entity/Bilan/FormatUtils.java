package com.api.demoimport.entity.Bilan;

public class FormatUtils {
    public static Double formatDecimal(double number) {
        try {
            return Math.round(number * 100.0) / 100.0;
        }catch (RuntimeException e){
            throw new RuntimeException("Null value !");
        }

    }
}
