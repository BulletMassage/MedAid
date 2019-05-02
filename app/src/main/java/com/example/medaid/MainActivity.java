package com.example.medaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /*--------------Lifecycle Callbacks--------------*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* if profile was not created (first time launching app) start profile activity
        if (name == NULL) {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
        }
        */
    }

    /*--------------My Functions--------------*/
    public void onClickProfile(View view) {
        Intent intent = new Intent(MainActivity.this, Profile.class);
        startActivity(intent);
    }

    public void onClickCabinet(View view) {
        Intent intent = new Intent(MainActivity.this, Cabinet.class);
        startActivity(intent);
    }
}
