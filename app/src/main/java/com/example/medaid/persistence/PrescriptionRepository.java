package com.example.medaid.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.medaid.async.DeleteAsyncTask;
import com.example.medaid.async.InsertAsyncTask;
import com.example.medaid.async.UpdateAsyncTask;
import com.example.medaid.models.Prescription;

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

    public Long insertPrescriptionTask(Prescription prescription) {
        Long id = (long)-1;
        try {
            id = insertAsyncTask.execute(prescription).get();
        } catch (Exception e) {}
        return id;
    }

    public void updatePrescriptionTask(Prescription prescription) {
        updateAsyncTask.execute(prescription);
    }

    public LiveData<List<Prescription>> retrievePrescriptionsTaskLive() {
        return mPrescriptionDatabase.getPrescriptionDao().getPrescriptions();
    }

    public List<Prescription> retrievePrescriptionsTaskList() {
        return mPrescriptionDatabase.getPrescriptionDao().getAllPrescriptions();
    }

    public void deletePrescriptionTask(Prescription prescription) {
        deleteAsyncTask.execute(prescription);
    }



}
