package com.airensoft.skovp.sample.utils.time;

public class DateHelper {
    private static final long SECONND = 1000;
    private static final long MINUTE  = 60 * SECONND;
    private static final long HOUR    = 60 * MINUTE;

    public static long getHour(long timesInMilli) {
        return timesInMilli / HOUR;
    }

    public static long getMinute(long timesInMilli) {
        long hoursInMilli = getHour(timesInMilli) * HOUR;
        timesInMilli -= hoursInMilli;

        return timesInMilli / MINUTE;
    }

    public static long getSecond(long timesInMilli) {
        long hoursInMilli = getHour(timesInMilli) * HOUR;
        long minutesInMilli = getMinute(timesInMilli) * MINUTE;
        timesInMilli -= (hoursInMilli + minutesInMilli);
        return timesInMilli / SECONND;
    }


    public static String getHourString(long timesInMilli) {
        return String.format("%02d", getHour(timesInMilli));
    }

    public static String getMinuteString(long timesInMilli) {
        return String.format("%02d", getMinute(timesInMilli));
    }

    public static String getSecondString(long timesInMilli) {
        return String.format("%02d", getSecond(timesInMilli));
    }
}
