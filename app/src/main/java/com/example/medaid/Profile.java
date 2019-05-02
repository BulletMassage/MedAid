package com.example.medaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    String name, email, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void onClickAccept(View view) {
        /*
        Commit profile information to database (save the stuff)
        */

        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
    }
}
