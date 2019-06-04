package com.example.medaid.activities_fragments;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SchedulePrescriptionActivity extends AppCompatActivity {

    /*ui components*/
    private List<TextView> textViewDays;
    private TextInputEditText mTimePicker;
    private TextInputLayout mDose;
    private Calendar mCalendar;
    private Button saveScheduleButton;
    private Toolbar toolbar;

    /*vars*/
    private Intent output;
    private List<String> days = Arrays.asList("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday");
    private static final String ID = "_schedulePrescription";


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


    private void initializeVariables() {
        // vars
        output = new Intent();

        // UI
        toolbar = findViewById(R.id.toolbar);
        saveScheduleButton = findViewById(R.id.saveSchedule_button);
        textViewDays = new ArrayList<>();
        mDose = findViewById(R.id.schedule_dose);

        // Get/set textViews
        for (String day : days) {
            int idRes = getResources().getIdentifier(day + ID, "id", getPackageName());
            TextView textViewDay = findViewById(idRes);

            // Highlight current day
            Calendar calendar = Calendar.getInstance();
            String calendarDay = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            if (calendarDay.matches(day.substring(0,1).toUpperCase() + day.substring(1))) {
                textViewDay.setSelected(true);
                textViewDay.setTextColor(getResources().getColor(R.color.colorWhite));
                textViewDay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }

            textViewDays.add(textViewDay);
        }

        // Calender
        mCalendar = Calendar.getInstance();
        mTimePicker = findViewById(R.id.schedule_timePicker);
        mTimePicker.setText(CalendarTypeConverter.getTimeFormat(mCalendar));

        // Set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Schedule");
    }

    private void initializeSetOnClick() {
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
                WeeklySchedule mWeeklySchedule = new WeeklySchedule();
                if (!mDose.getEditText().getText().toString().matches("")) {
                    mWeeklySchedule.setDose(Integer.valueOf(mDose.getEditText().getText().toString()));
                } else {
                    mWeeklySchedule.setDose(-1);
                }


                // Create new weekly schedule with selected days
                mWeeklySchedule.setTime(CalendarTypeConverter.getTimeFormat(mCalendar));
                for (TextView textViewDay : textViewDays) {
                    if (textViewDay.isSelected()) {
                        String day = getResources().getResourceName(textViewDay.getId()).split("_")[0];
                        mWeeklySchedule.insertDay(day.substring(0, 1).toUpperCase() + day.substring(1));
                    }
                }

                // Custom Toast Message if no days selected
                if (mWeeklySchedule.getSize() == 0) {
                    Toast toast = Toast.makeText(SchedulePrescriptionActivity.this, "Please select one or more days.", Toast.LENGTH_SHORT);
                    View toastView = toast.getView();
                    TextView toastMessage = toastView.findViewById(android.R.id.message);
                    toastMessage.setTextSize(18);
                    toastMessage.setTextColor(getResources().getColor(R.color.colorGray));
                    toastView.setBackgroundColor(Color.TRANSPARENT);
                    toast.setGravity(Gravity.CENTER, 0, 36);
                    toast.show();
                    return;
                }


                output.putExtra("WeeklySchedule", mWeeklySchedule);
                setResult(RESULT_OK, output);
                finish();
            }
        });


        // Day Listeners
        for (int i = 0; i < textViewDays.size(); i++) {
            final int position = i;
            textViewDays.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textViewDay = textViewDays.get(position);

                    textViewDay.setSelected(!textViewDay.isSelected());
                    if (textViewDay.isSelected()) {
                        textViewDay.setTextColor(getResources().getColor(R.color.colorWhite));
                        textViewDay.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        textViewDay.setTextColor(getResources().getColor(R.color.colorGray));
                        textViewDay.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    }
                }

            });
        }

    }

}
