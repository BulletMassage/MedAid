package com.example.medaid.async;

import android.os.AsyncTask;

import com.example.medaid.models.Prescription;
import com.example.medaid.persistence.PrescriptionDao;

public class UpdateAsyncTask extends AsyncTask<Prescription, Void, Void> {

    private PrescriptionDao mPrescriptionDao;

    public UpdateAsyncTask(PrescriptionDao dao) {
        mPrescriptionDao = dao;
    }

    @Override
    protected Void doInBackground(Prescription... prescriptions) {
        mPrescriptionDao.updatePrescription(prescriptions);
        return null;
    }
}
