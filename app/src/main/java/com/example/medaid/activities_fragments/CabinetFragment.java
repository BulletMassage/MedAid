package com.example.medaid.activities_fragments;

import android.arch.lifecycle.Observer;
import android.content.Intent;
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

import com.example.medaid.R;
import com.example.medaid.adapters.CabinetRecyclerAdapter;
import com.example.medaid.models.Prescription;
import com.example.medaid.persistence.PrescriptionRepository;

import java.util.ArrayList;
import java.util.List;

public class CabinetFragment extends Fragment implements CabinetRecyclerAdapter.OnPrescriptionListener {

    /*ui components*/
    private RecyclerView mRecyclerView;
    private BottomNavigationView bottomNav;
    private FloatingActionButton newPrescriptionButton;

    /*vars*/
    private ArrayList<Prescription> mPrescriptions = new ArrayList<>();
    private CabinetRecyclerAdapter mCabinetRecyclerAdapter;
    private PrescriptionRepository mPrescriptionRepository;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*init*/
        bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        mPrescriptionRepository = new PrescriptionRepository(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*init*/
        View view = inflater.inflate(R.layout.fragment_cabinet, container, false);
        mRecyclerView = view.findViewById(R.id.cabinet_recyclerview);
        newPrescriptionButton = view.findViewById(R.id.cabinet_newPrescription_button);

        /*attach RecyclerView and RecyclerAdapter*/
        mRecyclerView.setLayoutManager(new LinearLayoutManager( view.getContext() ) );
        mCabinetRecyclerAdapter = new CabinetRecyclerAdapter(mPrescriptions, this);
        mRecyclerView.setAdapter(mCabinetRecyclerAdapter);

        retrievePrescriptions();

        /*OnClick newPrescriptionButton*/
        newPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditPrescriptionActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                ((MainActivity)getActivity()).hideBottomNavigationView(bottomNav);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).showBottomNavigationView(bottomNav);
    }

    @Override
    public void onPrescriptionClick(int position) {
        Intent intent = new Intent(getContext(), AddEditPrescriptionActivity.class);
        intent.putExtra("prescription", mPrescriptions.get(position));
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    private void retrievePrescriptions() {
        mPrescriptionRepository.retrievePrescriptionsTaskLive().observe(this, new Observer<List<Prescription>>() {
            @Override
            public void onChanged(@Nullable List<Prescription> prescriptions) {
                if (mPrescriptions.size() > 0) {
                    mPrescriptions.clear();
                }
                if (prescriptions != null) {
                    mPrescriptions.addAll(prescriptions);
                }
                mCabinetRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
}
