package nl.rubixstudios.gangsturfs.utils;

import java.text.DecimalFormat;

public class RoundUtil {

    public static final DecimalFormat format = new DecimalFormat("#.00");

    public static double roundDecimals(double kdr) {
        return Math.round(kdr * 1000.00) / 1000.00;
    }
}