package net.climaxmc.Administration.Punishments;

import lombok.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum Time {
    SECONDS(1000, 's'),
    MINUTES(SECONDS.milliseconds * 60, 'm'),
    HOURS(MINUTES.milliseconds * 60, 'h'),
    DAYS(HOURS.milliseconds * 24, 'd');

    @Getter
    private final long milliseconds;
    private final char id;

    public static Time fromId(char id) {
        for (Time time : values()) {
            if (id == time.id) {
                return time;
            }
        }
        return null;
    }

    public static String toString(long milliseconds) {
        long seconds = (milliseconds / SECONDS.milliseconds) % 60;
        long minutes = (milliseconds / MINUTES.milliseconds) % 60;
        long hours = (milliseconds / HOURS.milliseconds) % 24;
        long days = (milliseconds / DAYS.milliseconds) % 24;

        if (days <= 0) {
            if (hours <= 0) {
                if (minutes <= 0) {
                    return seconds + " seconds";
                }
                return minutes + " minutes and " + seconds + " seconds";
            }
            return hours + " hours, " + minutes + " minutes, and " + seconds + " seconds";
        }
        return days + " days, " + hours + " hours, " + minutes + " minutes, and " + seconds + " seconds";
    }
}