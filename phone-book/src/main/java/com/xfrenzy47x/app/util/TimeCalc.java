package com.xfrenzy47x.app.util;

import java.util.concurrent.TimeUnit;

public class TimeCalc {
    private TimeCalc() {}
    static long min;
    static long sec;
    static long ms;
    private static void init(long start, long end) {
        long duration = end-start;

        min = TimeUnit.MILLISECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toMillis(min);
        sec = TimeUnit.MILLISECONDS.toSeconds(duration);
        duration -= TimeUnit.SECONDS.toMillis(min);
        ms = duration;
    }
    public static boolean tookLong(long start, long end) {
        init(start, end);
        return (min > 10) || (min == 10 && (sec > 0 || ms > 0));
    }
    public static String getTimeTaken(long start, long end) {
        init(start, end);
        String stringInterval = "%02d min. %02d sec. %03d ms.";
        return String.format(stringInterval , min, sec, ms);
    }
}
