package com.example.medaid.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.medaid.helpers.PrescriptionConverters;
import com.example.medaid.models.Prescription;
import com.example.medaid.models.WeeklySchedule;

@Database(entities = {Prescription.class}, version = 1)
@TypeConverters({PrescriptionConverters.class})
public abstract class PrescriptionDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "prescription_database";

    private static PrescriptionDatabase instance;

    static PrescriptionDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    PrescriptionDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract PrescriptionDao getPrescriptionDao();
}