package com.example.medaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AddEditPrescriptionFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        bottomNav.setVisibility(View.INVISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_addeditprescription, container, false);

        final TextInputEditText title = view.findViewById(R.id.addEdit_title);
        final TextView description = view.findViewById(R.id.addEdit_description);
        FloatingActionButton savePrescription = view.findViewById(R.id.addEdit_savePrescription_button);
        FloatingActionButton deletePrescription = view.findViewById(R.id.addEdit_deletePrescription_button);

        // if we clicked on an existing prescription
        if (getArguments() != null) {
            title.setText(getArguments().getString("TITLE"));
            description.setText(getArguments().getString("DESCRIPTION"));
        }

        // Insert item into database
        savePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    MainActivity.prescriptionDatabase.update(getArguments().getString("_id"), title.getText().toString(), description.getText().toString());
                } else {
                    MainActivity.prescriptionDatabase.insert(title.getText().toString(), description.getText().toString());
                }
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new CabinetFragment())
                        .commit();
            }
        });

        // Delete item from database
        deletePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    MainActivity.prescriptionDatabase.delete(getArguments().getString("_id"));
                }

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new CabinetFragment())
                        .commit();
            }
        });

        return view;
    }
}
