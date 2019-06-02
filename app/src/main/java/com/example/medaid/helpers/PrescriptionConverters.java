package com.example.medaid.helpers;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import com.example.medaid.models.WeeklySchedule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PrescriptionConverters {
    @TypeConverter
    public static String weeklyScheduleListToString (List<WeeklySchedule> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static List<WeeklySchedule> stringToWeeklyScheduleList(String value) {
        Type listType = new TypeToken<List<WeeklySchedule>>(){}.getType();
        List<WeeklySchedule> mSchedule = new Gson().fromJson(value, listType);
        return mSchedule;
    }

}
