package com.example.medaid.activities_fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.medaid.R;
import com.example.medaid.async.RetrievePrescriptionsAsyncTask;
import com.example.medaid.helpers.AlarmHelper;
import com.example.medaid.models.Prescription;
import com.example.medaid.models.WeeklySchedule;
import com.example.medaid.persistence.PrescriptionRepository;

import java.util.List;

public class ProfileFragment extends Fragment {

    private static final String TAG = "HOME";
    private Context mContext;
    private PrescriptionRepository mPrescriptionRepository;
    private List<Prescription> mPrescriptions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = view.getContext();
        mPrescriptionRepository = new PrescriptionRepository(mContext);
        RetrievePrescriptionsAsyncTask retrievePrescriptionsAsyncTask = new RetrievePrescriptionsAsyncTask(mContext);
        try {
            mPrescriptions = retrievePrescriptionsAsyncTask.execute().get();
        } catch (Exception e) {}
        LinearLayout deleteAll = view.findViewById(R.id.deleteAll);

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog("Delete All", "This operation is irreversible. Are you sure you want to continue?","No", "Yes, delete everything");
            }
        });

        return view;
    }


    public void customDialog(String title, String message, final String cancelMethod, final String okMethod){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(mContext);
        builderSingle.setIcon(R.drawable.ic_error_black_24dp);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: Cancel Called.");

                    }
                });

        builderSingle.setPositiveButton(
                "YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: OK Called.");

                        for (Prescription prescription : mPrescriptions) {
                            AlarmHelper.cancelAlarms(mContext, prescription, prescription.getSchedule().toArray(new WeeklySchedule[0]));
                        }

                        mPrescriptionRepository.deletePrescriptionsListTask(mPrescriptions.toArray(new Prescription[mPrescriptions.size()]));
                    }
                });


        builderSingle.show();
    }

}
