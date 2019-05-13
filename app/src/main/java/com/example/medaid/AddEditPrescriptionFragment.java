package com.example.medaid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AddEditPrescriptionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_addeditprescription, container, false);

        // Insert item into database
        final TextView title = view.findViewById(R.id.addEdit_title);
        final TextView description = view.findViewById(R.id.addEdit_description);
        Button savePrescription = view.findViewById(R.id.addEdit_savePrescription_button);
        savePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.prescriptionDatabase.insert(title.getText().toString(), description.getText().toString());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CabinetFragment()).commit();
            }
        });

        return view;
    }
}
