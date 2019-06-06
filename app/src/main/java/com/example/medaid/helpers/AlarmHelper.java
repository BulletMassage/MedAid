package com.example.medaid.helpers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.medaid.models.Prescription;
import com.example.medaid.models.WeeklySchedule;

import java.util.Calendar;
import java.util.HashMap;

public class AlarmHelper {

    public static int requestCodeFromIdDate(String id, String day, String time) {
        return (id + "_" + day + time).hashCode();
    }

    public static void startAlarms(Context context, Prescription prescription, WeeklySchedule... weeklySchedules) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        for (WeeklySchedule weeklySchedule : weeklySchedules) {
            String time = weeklySchedule.getTime();

            // for each day in that schedule
            for (HashMap.Entry<String, Boolean> dayMap: weeklySchedule.getDays().entrySet()) {
                if (dayMap.getValue()) {
                    String dayNameFromBug = dayMap.getKey().split("/")[1];
                    String day = dayNameFromBug.substring(0, 1).toUpperCase() + dayNameFromBug.substring(1);
                    int notificationID = AlarmHelper.requestCodeFromIdDate(String.valueOf(prescription.getId()), dayMap.getKey(), time);

                    // Alarm init
                    Calendar calendar = CalendarTypeConverter.calendarFromDayTime(day, time);
                    Intent intent = new Intent(context, AlarmReceiver.class);
                    intent.putExtra("prescriptionID", prescription.getId());
                    intent.putExtra("notificationID", notificationID);
                    intent.putExtra("title", prescription.getTitle());
                    intent.putExtra("dose", weeklySchedule.getDose());
                    intent.putExtra("calendar", calendar.getTimeInMillis());

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    Log.d("AlarmStarted", CalendarTypeConverter.calendarToString(calendar));
                }
            }
        }
    }

    public static void cancelAlarms(Context context, Prescription prescription, WeeklySchedule... weeklySchedules) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        // for every weekly schedule
        for (WeeklySchedule weeklySchedule : weeklySchedules) {
            String time = weeklySchedule.getTime();

            // for each day in that schedule
            for (HashMap.Entry<String, Boolean> dayMap: weeklySchedule.getDays().entrySet()) {
                if (dayMap.getValue()) {
                    String dayNameFromBug = dayMap.getKey().split("/")[1];
                    String day = dayNameFromBug.substring(0, 1).toUpperCase() + dayNameFromBug.substring(1);
                    int requestCode = AlarmHelper.requestCodeFromIdDate(String.valueOf(prescription.getId()), dayMap.getKey(), time);

                    Calendar calendar = CalendarTypeConverter.calendarFromDayTime(day, time);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
                    alarmManager.cancel(pendingIntent);

                    Log.d("AlarmCanceled", CalendarTypeConverter.calendarToString(calendar));
                }

            }
        }
    }

}
