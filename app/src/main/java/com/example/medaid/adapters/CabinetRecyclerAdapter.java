package com.example.medaid.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medaid.models.Prescription;
import com.example.medaid.R;

import java.util.ArrayList;

public class CabinetRecyclerAdapter extends RecyclerView.Adapter<CabinetRecyclerAdapter.ViewHolder>{

    private ArrayList<Prescription> mPrescriptions;
    private OnPrescriptionListener mPrescriptionListener;


    public CabinetRecyclerAdapter(ArrayList<Prescription> prescriptions, OnPrescriptionListener onPrescriptionListener) {
        this.mPrescriptions = prescriptions;
        this.mPrescriptionListener = onPrescriptionListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, description;
        OnPrescriptionListener onPrescriptionListener;

        public ViewHolder(@NonNull View itemView, OnPrescriptionListener onPrescriptionListener) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_description);

            this.onPrescriptionListener = onPrescriptionListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPrescriptionListener.onPrescriptionClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_prescription_item, viewGroup, false);
        return new ViewHolder(view, mPrescriptionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mPrescriptions.get(i).getTitle());
        viewHolder.description.setText(mPrescriptions.get(i).getDescription());
        int quantity = mPrescriptions.get(i).getQuantity();

        /*
        if (quantity > 12) {GREEN icon}
        else if (quantity >= 8 quantity <= 12) {ORANGE icon}
        else if (quantity > 0 quantity < 8) {RED icon}
        else {BLACK icon}
         */
    }

    @Override
    public int getItemCount() {
        return mPrescriptions.size();
    }

    public interface OnPrescriptionListener {
        void onPrescriptionClick(int position);
    }
}