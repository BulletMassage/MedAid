package com.example.medaid.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarTypeConverter {
    public static String getTimeFormat (Calendar calendar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String time = simpleDateFormat.format(calendar.getTime());

        return time;
    }

}
