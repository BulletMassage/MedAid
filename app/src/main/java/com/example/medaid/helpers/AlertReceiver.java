package com.example.medaid.helpers;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationManagerCompat;

import com.example.medaid.MedAid;
import com.example.medaid.R;

public class AlertReceiver extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        int notificationID = intent.getIntExtra("notificationID", 1);
        String title = intent.getStringExtra("title");
        int quanitity = intent.getIntExtra("quantity", 0);

        String contentText = "This is a friendly reminder to take";
        if (quanitity > 0) {
            contentText += " " + quanitity;
        }
        contentText += " " + title + ".";

        Notification notification = new Notification.Builder(context, MedAid.PRESCRIPTION_CHANNEL)
                .setSmallIcon(R.drawable.ic_prescription_bottle)
                .setContentTitle(title)
                .setContentText(contentText)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_REMINDER)
                .build();

        notificationManager.notify(notificationID, notification);
    }
}