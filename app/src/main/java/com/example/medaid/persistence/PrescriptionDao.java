package com.example.medaid.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.medaid.models.Prescription;

import java.util.List;

@Dao
public interface PrescriptionDao {
    @Insert
    void insertPrescriptions(Prescription... prescriptions);

    @Query("SELECT * FROM prescriptions")
    LiveData<List<Prescription>> getPrescriptions();

    @Delete
    void deletePrescription(Prescription... prescriptions);

    @Update
    void updatePrescription(Prescription... prescriptions);
}
