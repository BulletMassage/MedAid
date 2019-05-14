package com.example.medaid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddEditPrescriptionFragment extends Fragment {

    View view;

    // Hide bottom navigation drawer
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        ((MainActivity)getActivity()).hideBottomNavigationView(bottomNav);
    }

    // Get toolbar menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_add_edit_prescription, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            // Delete item from database
            case R.id.delete_prescription_menu:
                if (getArguments() != null) {
                    MainActivity.prescriptionDatabase.delete(getArguments().getString("_id"));
                }
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new CabinetFragment())
                        .commit();

                return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_addeditprescription, container, false);

        // Get toolbar
        Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // Edit toolbar
        toolbar.setTitle("Add/Edit");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        // Configure toolbar onclick back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        // Get text fields
        final TextInputEditText title = view.findViewById(R.id.addEdit_title);
        final TextView description = view.findViewById(R.id.addEdit_description);

        // if we clicked on an existing prescription get title and description from bundle via getArguments()
        if (getArguments() != null) {
            title.setText(getArguments().getString("TITLE"));
            description.setText(getArguments().getString("DESCRIPTION"));
        }


        // Get Action Buttons
        FloatingActionButton savePrescription = view.findViewById(R.id.addEdit_savePrescription_button);
        //FloatingActionButton deletePrescription = view.findViewById(R.id.addEdit_deletePrescription_button);

        // Insert new item into database or update existing entry
        savePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null) {
                    MainActivity.prescriptionDatabase.update(getArguments().getString("_id"), title.getText().toString(), description.getText().toString());
                } else {
                    MainActivity.prescriptionDatabase.insert(title.getText().toString(), description.getText().toString());
                }

                // Go to CabinetFragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, new CabinetFragment())
                        .commit();
            }
        });

        return view;
    }
}
