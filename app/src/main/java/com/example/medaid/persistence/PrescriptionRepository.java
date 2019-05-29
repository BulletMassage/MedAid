package com.example.medaid.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.medaid.async.DeleteAsyncTask;
import com.example.medaid.async.InsertAsyncTask;
import com.example.medaid.models.Prescription;
import com.example.medaid.async.UpdateAsyncTask;

import java.util.List;

public class PrescriptionRepository {

    private PrescriptionDatabase mPrescriptionDatabase;
    private InsertAsyncTask insertAsyncTask;
    private UpdateAsyncTask updateAsyncTask;
    private DeleteAsyncTask deleteAsyncTask;

    public PrescriptionRepository(Context context) {
        mPrescriptionDatabase = PrescriptionDatabase.getInstance(context);
        insertAsyncTask = new InsertAsyncTask(mPrescriptionDatabase.getPrescriptionDao());
        updateAsyncTask = new UpdateAsyncTask(mPrescriptionDatabase.getPrescriptionDao());
        deleteAsyncTask = new DeleteAsyncTask(mPrescriptionDatabase.getPrescriptionDao());
    }

    public void insertPrescriptionTask(Prescription prescription) {
        insertAsyncTask.execute(prescription);
    }

    public void updatePrescriptionTask(Prescription prescription) {
        updateAsyncTask.execute(prescription);
    }

    public LiveData<List<Prescription>> retrievePrescriptionsTask() {
        return mPrescriptionDatabase.getPrescriptionDao().getPrescriptions();
    }

    public void deletePrescriptionTask(Prescription prescription) {
        deleteAsyncTask.execute(prescription);
    }

}
