package com.example.medaid.helpers;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.medaid.R;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        // Intent
        int prescriptionID = intent.getIntExtra("prescriptionID", 1);
        int notificationID = intent.getIntExtra("notificationID", 1);
        String title = intent.getStringExtra("title");
        int dose = intent.getIntExtra("dose", 0);

        // Calendar
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(intent.getLongExtra("calendar", 0));
        calendar.add(Calendar.DAY_OF_WEEK, 7);

        // Build String
        String contentText = "This is a friendly reminder to take";
        if (dose > 0) {
            contentText += " " + dose;
        }
        contentText += " " + title + ".";


        // Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Prescription Channel";
            String description = "Sends prescription reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(notificationID + "", name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.GREEN);
            notificationManager.createNotificationChannel(channel);
        }


        // onClick Accept
        Intent broadcastIntentAccept = new Intent(context, NotificationAcceptedReceiver.class);
        broadcastIntentAccept.putExtra("notificationID", notificationID);
        broadcastIntentAccept.putExtra("prescriptionID", prescriptionID);
        broadcastIntentAccept.putExtra("dose", dose);
        PendingIntent broadcastPendingIntentAccept = PendingIntent.getBroadcast(context, notificationID, broadcastIntentAccept,0);

        // onClick Dismiss
        Intent broadcastIntentDismiss = new Intent(context, NotificationDismissedReceiver.class);
        broadcastIntentDismiss.putExtra("notificationID", notificationID);
        PendingIntent broadcastPendingIntentDismiss = PendingIntent.getBroadcast(context, notificationID, broadcastIntentDismiss,0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationID + "")
                .setSmallIcon(R.drawable.ic_heartbeat)
                .setContentTitle(title)
                .setContentText(contentText)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_REMINDER)
                .setColor(Color.RED)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Dismiss", broadcastPendingIntentDismiss)
                .addAction(R.mipmap.ic_launcher, "Accept", broadcastPendingIntentAccept);

        notificationManager.notify(notificationID, builder.build());


        // Alarm
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("prescriptionID", prescriptionID);
        alarmIntent.putExtra("notificationID", notificationID);
        alarmIntent.putExtra("title", title);
        alarmIntent.putExtra("dose", dose);
        alarmIntent.putExtra("calendar", calendar.getTimeInMillis());

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}