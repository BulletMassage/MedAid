package com.example.medaid.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.medaid.models.Prescription;
import com.example.medaid.persistence.PrescriptionRepository;

import java.util.List;

public class RetrievePrescriptionsAsyncTask extends AsyncTask<Void, Void, List<Prescription>> {
    PrescriptionRepository mPrescriptionRepository;

    public RetrievePrescriptionsAsyncTask(Context context) {
        mPrescriptionRepository = new PrescriptionRepository(context);
    }

    @Override
    protected List<Prescription> doInBackground(Void... voids) {
        return mPrescriptionRepository.retrievePrescriptionsTaskList();
    }

}
