package com.example.medaid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CabinetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);

        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        ((MainActivity)getActivity()).showBottomNavigationView(bottomNav);

        getActivity().getSupportFragmentManager().popBackStack();

        // Go to add/edit prescription fragment
        FloatingActionButton addPrescription = view.findViewById(R.id.cabinet_newPrescription_button);
        addPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, new AddEditPrescriptionFragment())
                        .commit();
            }
        });

        // Populate RecycleView with prescription items
        RecyclerView recyclerView = view.findViewById(R.id.cabinet_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CabinetAdapter adapter = new CabinetAdapter(getActivity(), MainActivity.prescriptionDatabase.getCursorAll());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
