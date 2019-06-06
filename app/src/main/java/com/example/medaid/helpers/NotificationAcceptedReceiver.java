package com.example.medaid.helpers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.medaid.models.Prescription;
import com.example.medaid.persistence.PrescriptionRepository;

import java.util.List;

public class NotificationAcceptedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int prescriptionID = intent.getIntExtra("prescriptionID", -1);
        int notificationID = intent.getIntExtra("notificationID", 0);
        int dose = intent.getIntExtra("dose", 0);

        new writeToDatabaseAsyncTask(context).execute(prescriptionID, dose);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationID);
    }


    private static class writeToDatabaseAsyncTask extends AsyncTask<Integer, Void, List<Prescription>> {
        // Init
        private PrescriptionRepository mPrescriptionRepository;
        private int prescriptionID;
        private int dose;

        public writeToDatabaseAsyncTask(Context context) {
            mPrescriptionRepository = new PrescriptionRepository(context);
        }

        @Override
        protected List<Prescription> doInBackground(Integer... integers) {
            prescriptionID = integers[0];
            dose = integers[1];
            return mPrescriptionRepository.retrievePrescriptionsTaskList();
        }

        @Override
        protected void onPostExecute(List<Prescription> mPrescriptions) {
            Log.d("Data", "PrescriptionID: " + prescriptionID);
            Log.d("Data", "Dose: " + dose);

            for (Prescription prescription : mPrescriptions) {
                Log.d("Data", prescription.getId() + ": " + prescription.getTitle() + ", " + prescription.getQuantity());
                if (prescription.getId() == prescriptionID) {
                    int newQuantity = prescription.getQuantity() - dose;
                    if (newQuantity >= 0) {
                        prescription.setQuantity(newQuantity);
                    } else {
                        prescription.setQuantity(0);
                    }

                    mPrescriptionRepository.updatePrescriptionTask(prescription);
                    Log.d("DataDetected", prescription.getId() + ": " + prescription.getTitle() + "");
                    Log.d("DataDetected", "New Dose: " + prescription.getQuantity());
                }
            }

        }

    }


}
