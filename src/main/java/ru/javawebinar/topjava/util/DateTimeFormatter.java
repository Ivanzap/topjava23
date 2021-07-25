package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class DateTimeFormatter {
    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String s, Locale locale) throws ParseException {
            return parseLocalDate(s);
        }

        @Override
        public String print(LocalDate localDate, Locale locale) {
            return localDate.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String s, Locale locale) throws ParseException {
            return parseLocalTime(s);
        }

        @Override
        public String print(LocalTime localTime, Locale locale) {
            return localTime.format(java.time.format.DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }
}
