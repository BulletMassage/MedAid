package com.example.medaid.activities_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.medaid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottom drawer fragment navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_cabinet:
                            selectedFragment = new CabinetFragment();
                            break;
                        case R.id.nav_profile:
                            selectedFragment = new ProfileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    public void hideBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(view.getHeight()).setDuration(300);
    }

    public void showBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(0).setDuration(300);
    }


/*    public void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) MainActivity.this.getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, AddEditPrescriptionActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        //Create Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Test";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123", name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(R.color.colorPrimary);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            notificationManager.createNotificationChannel(channel);
        }
        
        //Create Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"123")
                .setContentTitle("Test")
                .setContentText("Hi this is first notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.ic_prescription_bottle)
                .setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }*/

}
