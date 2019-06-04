package com.example.medaid.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WeeklySchedule implements Parcelable{
    private HashMap<String, Boolean> days;
    private String time;

    public WeeklySchedule() {
        days = new HashMap<>();
        time = "";

        List<String> keys = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
        for(String day : keys) {
            days.put(day, false);
        }
    }

    public HashMap<String, Boolean> getDays() {
        return days;
    }

    public Boolean hasDay(String day) {
        return days.get(day);
    }

    public void insertDay(String day) {
        days.put(day, true);
    }

    public void removeDay(String day) {
        days.put(day, false);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSize() {
        int size = 0;
        for (Boolean containsDay : days.values()) {
            if (containsDay) {
                size++;
            }
        }
        return size;
    }

    @Override
    public String toString() {
        return time + " " + days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.days);
        dest.writeString(this.time);
    }

    protected WeeklySchedule(Parcel in) {
        this.days = (HashMap<String, Boolean>) in.readSerializable();
        this.time = in.readString();
    }

    public static final Creator<WeeklySchedule> CREATOR = new Creator<WeeklySchedule>() {
        @Override
        public WeeklySchedule createFromParcel(Parcel source) {
            return new WeeklySchedule(source);
        }

        @Override
        public WeeklySchedule[] newArray(int size) {
            return new WeeklySchedule[size];
        }
    };
}
