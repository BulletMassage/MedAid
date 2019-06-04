package com.example.medaid;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MedAid extends Application {
    public static final String PRESCRIPTION_CHANNEL= "prescriptionChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel prescriptionChannel = new NotificationChannel(
                    PRESCRIPTION_CHANNEL,
                    "Prescription Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            prescriptionChannel.setDescription("This is for prescription notifications");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(prescriptionChannel);
        }
    }

}
