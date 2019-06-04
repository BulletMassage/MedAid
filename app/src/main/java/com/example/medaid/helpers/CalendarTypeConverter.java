package com.example.medaid.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarTypeConverter {
    public static String getTimeFormat (Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String time = simpleDateFormat.format(calendar.getTime());

        return time;
    }

    public static Calendar calendarFromDayTime (String day, String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE hh:mm a", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String dayTimeFormat = day + " " + time;

        try {
            Date date = simpleDateFormat.parse(dayTimeFormat);
            calendar.setTime(date);

            calendar.set(Calendar.YEAR,  Calendar.getInstance().get(Calendar.YEAR));
            calendar.set(Calendar.MONTH,  Calendar.getInstance().get(Calendar.MONTH));
            calendar.set(Calendar.WEEK_OF_MONTH,  Calendar.getInstance().get(Calendar.WEEK_OF_MONTH));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DAY_OF_WEEK, 7);
        }
        return calendar;
    }

    public static String calendarToString(Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMMM dd, hh:mm a", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

}
