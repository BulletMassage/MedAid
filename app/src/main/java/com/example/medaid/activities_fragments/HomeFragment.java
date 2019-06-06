package com.example.medaid.activities_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medaid.R;
import com.example.medaid.adapters.HomeRecyclerAdapterOld;
import com.example.medaid.async.RetrievePrescriptionsAsyncTask;
import com.example.medaid.models.Prescription;
import com.example.medaid.models.WeeklySchedule;
import com.example.medaid.persistence.PrescriptionRepository;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    /*ui components*/
    private RecyclerView mRecyclerView;

    /*vars*/
    private List<Prescription> mPrescriptions;
    private PrescriptionRepository mPrescriptionRepository;
    private HomeRecyclerAdapterOld mHomeRecyclerAdapterOld;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initializeVariables(view);
        initializeRecyclerView(view);
        return view;
    }

    private void initializeVariables(View view) {
        RetrievePrescriptionsAsyncTask retrievePrescriptionsAsyncTask = new RetrievePrescriptionsAsyncTask(view.getContext());
        try {
            mPrescriptions = retrievePrescriptionsAsyncTask.execute().get();
        } catch (Exception e) {}

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        TextView date = view.findViewById(R.id.date_time);
        date.setText(currentDate);
    }

    private void initializeRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.home_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mHomeRecyclerAdapterOld = new HomeRecyclerAdapterOld(getWeeklyScheduleInOrder());
        mRecyclerView.setAdapter(mHomeRecyclerAdapterOld);
    }

    private List<Prescription> getWeeklyScheduleInOrder() {
        Calendar calendar = Calendar.getInstance();
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        Log.d("Sorting", "Today: " + dayLongName);

        List<String> prescriptionsInOrder = new ArrayList<>();


        for (Prescription prescription : mPrescriptions) {
            for (WeeklySchedule weeklySchedule : prescription.getSchedule()) {
                for (HashMap.Entry<String, Boolean> day : weeklySchedule.getDays().entrySet()) {
                    if (day.getKey().contains("thursday")) {
                        prescriptionsInOrder.add(weeklySchedule.getTime() + "_" + prescription.getId());
                    }
                }

            }
        }


        Log.d("Sorting", "BeforeSorting: " + prescriptionsInOrder);
        Collections.sort(prescriptionsInOrder);
        Log.d("Sorting", "AfterSorting: " + prescriptionsInOrder);


        List<Prescription> sortedPrescriptions = new ArrayList<>();

        for (String instance : prescriptionsInOrder) {
            for (Prescription prescription : mPrescriptions) {

                if (instance.split("_")[1].matches(prescription.getId() + "")) {
                    for (WeeklySchedule weeklySchedule : prescription.getSchedule()) {

                        if (instance.split("_")[0].matches(weeklySchedule.getTime())) {
                            Log.d("Sorting", "FCK: " + weeklySchedule.getTime());

                            Prescription tempPres = new Prescription(prescription);
                            WeeklySchedule tempWeekly = new WeeklySchedule();

                            tempWeekly.setTime(weeklySchedule.getTime());
                            tempWeekly.insertDay(dayLongName);

                            tempPres.addWeeklySchedule(tempWeekly);
                            sortedPrescriptions.add(tempPres);
                        }

                    }

                }
            }
        }


        for (Prescription prescription : sortedPrescriptions) {
            for (WeeklySchedule weeklySchedule : prescription.getSchedule()){
                Log.d("Sorting", "FINALSORTED: " + weeklySchedule.toString());
            }
        }


        return sortedPrescriptions;
    }

}
