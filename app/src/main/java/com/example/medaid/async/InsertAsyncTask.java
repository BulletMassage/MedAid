package com.example.medaid.async;

import android.os.AsyncTask;

import com.example.medaid.models.Prescription;
import com.example.medaid.persistence.PrescriptionDao;

public class InsertAsyncTask extends AsyncTask<Prescription, Void, Void> {

    private PrescriptionDao mPrescriptionDao;

    public InsertAsyncTask(PrescriptionDao dao) {
        mPrescriptionDao = dao;
    }

    @Override
    protected Void doInBackground(Prescription... prescriptions) {
        mPrescriptionDao.insertPrescriptions(prescriptions);
        return null;
    }
}
