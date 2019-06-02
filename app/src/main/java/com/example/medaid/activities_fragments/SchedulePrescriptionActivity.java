package com.example.medaid.activities_fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medaid.R;
import com.example.medaid.helpers.CalendarTypeConverter;
import com.example.medaid.models.WeeklySchedule;

import java.util.Calendar;

public class SchedulePrescriptionActivity extends AppCompatActivity {

    /*ui components*/
    private TextInputEditText mTimePicker;
    private Calendar mCalendar;
    private Button saveScheduleButton;
    private TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
    Toolbar toolbar;

    /*vars*/
    Intent output;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_edit_prescription, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_prescription);

        initializeVariables();
        initializeSetOnClick();
    }


    public void initializeVariables() {
        // Intent
        output = new Intent();

        // UI
        toolbar = findViewById(R.id.toolbar);
        saveScheduleButton = findViewById(R.id.saveSchedule_button);
        sunday = findViewById(R.id.sunday);
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);

        // Calender
        mCalendar = Calendar.getInstance();
        mTimePicker = findViewById(R.id.schedule_timePicker);
        mTimePicker.setText(CalendarTypeConverter.getTimeFormat(mCalendar));

        // Set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Schedule");
    }

    public void initializeSetOnClick() {
        // TimePicker
        mTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(SchedulePrescriptionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        mCalendar.set(Calendar.MINUTE, minutes);
                        mTimePicker.setText(CalendarTypeConverter.getTimeFormat(mCalendar));
                    }
                }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });


        // Toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Save Button
        saveScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Custom Toast Message if no days selected
                if (!sunday.isSelected() && !monday.isSelected() && !tuesday.isSelected() && !wednesday.isSelected() && !thursday.isSelected() && !friday.isSelected() && !saturday.isSelected()) {
                    Toast toast = Toast.makeText(SchedulePrescriptionActivity.this, "Please select one or more days.", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    TextView toastMessage = toastView.findViewById(android.R.id.message);
                    toastMessage.setTextSize(18);
                    toastMessage.setTextColor(getResources().getColor(R.color.colorGray));
                    toastView.setBackgroundColor(Color.TRANSPARENT);
                    toast.show();
                    return;
                }

                // Create new weekly schedule with selected days
                WeeklySchedule mWeeklySchedule = new WeeklySchedule();
                mWeeklySchedule.setTime(CalendarTypeConverter.getTimeFormat(mCalendar));
                if (sunday.isSelected()) {
                    mWeeklySchedule.insertDay("Sunday");
                }
                if (monday.isSelected()) {
                    mWeeklySchedule.insertDay("Monday");
                }
                if (tuesday.isSelected()) {
                    mWeeklySchedule.insertDay("Tuesday");
                }
                if (wednesday.isSelected()) {
                    mWeeklySchedule.insertDay("Wednesday");
                }
                if (thursday.isSelected()) {
                    mWeeklySchedule.insertDay("Thursday");
                }
                if (friday.isSelected()) {
                    mWeeklySchedule.insertDay("Friday");
                }
                if (saturday.isSelected()) {
                    mWeeklySchedule.insertDay("Saturday");
                }
                output.putExtra("WeeklySchedule", mWeeklySchedule);
                setResult(RESULT_OK, output);
                finish();
            }
        });


        // Day Listeners
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (sunday.isSelected()) {
                    sunday.setTextColor(getResources().getColor(R.color.colorWhite));
                    sunday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    sunday.setTextColor(getResources().getColor(R.color.colorGray));
                    sunday.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (monday.isSelected()) {
                    monday.setTextColor(getResources().getColor(R.color.colorWhite));
                    monday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    monday.setTextColor(getResources().getColor(R.color.colorGray));
                    monday.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (tuesday.isSelected()) {
                    tuesday.setTextColor(getResources().getColor(R.color.colorWhite));
                    tuesday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    tuesday.setTextColor(getResources().getColor(R.color.colorGray));
                    tuesday.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (wednesday.isSelected()) {
                    wednesday.setTextColor(getResources().getColor(R.color.colorWhite));
                    wednesday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    wednesday.setTextColor(getResources().getColor(R.color.colorGray));
                    wednesday.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (thursday.isSelected()) {
                    thursday.setTextColor(getResources().getColor(R.color.colorWhite));
                    thursday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    thursday.setTextColor(getResources().getColor(R.color.colorGray));
                    thursday.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (friday.isSelected()) {
                    friday.setTextColor(getResources().getColor(R.color.colorWhite));
                    friday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    friday.setTextColor(getResources().getColor(R.color.colorGray));
                    friday.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                if (saturday.isSelected()) {
                    saturday.setTextColor(getResources().getColor(R.color.colorWhite));
                    saturday.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    saturday.setTextColor(getResources().getColor(R.color.colorGray));
                    saturday.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }
        });
    }
}
