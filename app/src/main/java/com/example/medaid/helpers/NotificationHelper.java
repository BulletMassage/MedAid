package com.example.medaid.helpers;

public class NotificationHelper {

    public static int requestCodeFromIdDate(String id, String day, String time) {
        return (id + "_" + day + time).hashCode();
    }

}
