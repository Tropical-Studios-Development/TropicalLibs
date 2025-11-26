package org.tropicalstudios.tropicalLibs.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtil {

    /**
     * Gets individual date components for a specific timezone
     * @param timezone The timezone string
     * @return Array containing [day, month, year]
     */
    public static int[] getDate(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);

            return new int[] {
                    zonedDateTime.getDayOfMonth(),
                    zonedDateTime.getMonthValue(),
                    zonedDateTime.getYear()
            };
        } catch (Exception e) {
            return getDate("UTC");
        }
    }

    /**
     * Gets the year for a specific timezone
     * @param timezone The timezone string
     * @return The year (e.g., 2025)
     */
    public static int getYear(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            return ZonedDateTime.now(zoneId).getYear();
        } catch (Exception e) {
            return getYear("UTC");
        }
    }

    /**
     * Gets the month for a specific timezone
     * @param timezone The timezone string
     * @return The month (1-12)
     */
    public static int getMonth(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            return ZonedDateTime.now(zoneId).getMonthValue();
        } catch (Exception e) {
            return getMonth("UTC");
        }
    }

    /**
     * Gets the day of the month for a specific timezone
     * @param timezone The timezone string
     * @return The day of the month (1-31)
     */
    public static int getDay(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            return ZonedDateTime.now(zoneId).getDayOfMonth();
        } catch (Exception e) {
            return getDay("UTC");
        }
    }

    /**
     * Gets individual time components for a specific timezone
     * @param timezone The timezone string
     * @return Array containing [hour, minute, second]
     */
    public static int[] getTime(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);

            return new int[] {
                    zonedDateTime.getHour(),
                    zonedDateTime.getMinute(),
                    zonedDateTime.getSecond()
            };
        } catch (Exception e) {
            return getTime("UTC");
        }
    }

    /**
     * Gets the hour for a specific timezone (24-hour format)
     * @param timezone The timezone string
     * @return The hour (0-23)
     */
    public static int getHour(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            return ZonedDateTime.now(zoneId).getHour();
        } catch (Exception e) {
            return getHour("UTC");
        }
    }

    /**
     * Gets the minute for a specific timezone
     * @param timezone The timezone string
     * @return The minute (0-59)
     */
    public static int getMinute(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            return ZonedDateTime.now(zoneId).getMinute();
        } catch (Exception e) {
            return getMinute("UTC");
        }
    }

    /**
     * Gets the second for a specific timezone
     * @param timezone The timezone string
     * @return The second (0-59)
     */
    public static int getSecond(String timezone) {
        try {
            ZoneId zoneId = ZoneId.of(timezone);
            return ZonedDateTime.now(zoneId).getSecond();
        } catch (Exception e) {
            return getSecond("UTC");
        }
    }

    /**
     * Utility method to check if a timezone is valid
     * @param timezone The timezone string to validate
     * @return true if the timezone is valid, false otherwise
     */
    public static boolean isValidTimezone(String timezone) {
        try {
            ZoneId.of(timezone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
