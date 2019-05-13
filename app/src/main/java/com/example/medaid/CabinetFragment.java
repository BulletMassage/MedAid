package com.example.medaid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CabinetFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);

        // Go to add/edit prescription fragment
        Button addPrescription = view.findViewById(R.id.cabinet_newPrescription_button);
        addPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddEditPrescriptionFragment()).commit();
            }
        });

        // Populate RecycleView with prescription items
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cabinet_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        PrescriptionRecycleViewAdapter adapter = new PrescriptionRecycleViewAdapter(getActivity(), MainActivity.prescriptionDatabase.getCursorAll());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
