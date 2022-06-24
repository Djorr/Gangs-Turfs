package nl.rubixstudios.gangsturfs.utils;

public class TimeUtil {

    public static String formatMillisecondsToSeconds1(Long l) {
        float f = ((float) l + 0.0F) / 1000.0F;
        String string = String.format("%1$.0f", f);
        return string.replace(",", ".");
    }

    public static String formatMillisecondsToSeconds2(Long l) {
        float f = ((float) l + 0.0F) / 1000.0F;
        String string = String.format("%1$.1f", f);
        return string.replace(",", ".");
    }

    public static String formatMillisecondsToMinutes(Long l) {
        int m1 = (int)(l / 1000L) % 60;
        int m2 = (int)(l / 60000L % 60L);
        return String.format("%02d:%02d", m2, m1);
    }

    public static String formatMillisecondsToHours(Long l) {
        int h1 = (int)(l / 1000L) % 60;
        int h2 = (int)(l / 60000L % 60L);
        int h3 = (int)(l / 3600000L % 24L);
        return String.format("%02d:%02d:%02d", h3, h2, h1);
    }
}
