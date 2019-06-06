package com.example.medaid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medaid.R;
import com.example.medaid.models.Prescription;

import java.util.ArrayList;

public class CabinetRecyclerAdapter extends RecyclerView.Adapter<CabinetRecyclerAdapter.ViewHolder>{

    private ArrayList<Prescription> mPrescriptions;
    private OnPrescriptionListener mPrescriptionListener;


    public CabinetRecyclerAdapter(ArrayList<Prescription> prescriptions, OnPrescriptionListener onPrescriptionListener) {
        this.mPrescriptions = prescriptions;
        this.mPrescriptionListener = onPrescriptionListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView bottle;
        OnPrescriptionListener onPrescriptionListener;

        public ViewHolder(@NonNull View itemView, OnPrescriptionListener onPrescriptionListener) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            bottle = itemView.findViewById(R.id.item_bottleDrawable);

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_prescription_item, viewGroup, false);
        return new ViewHolder(view, mPrescriptionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.title.getContext();
        holder.title.setText(mPrescriptions.get(position).getTitle());
        int quantity = mPrescriptions.get(position).getQuantity();

        if (quantity >= 16 || quantity < 0) {
            holder.bottle.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorTintGreen));
        }
        else if (quantity >= 6 && quantity < 16) {
            holder.bottle.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorTintOrange));
        }
        else if (quantity >= 0 && quantity < 6) {
            holder.bottle.setBackgroundTintList(context.getResources().getColorStateList(R.color.colorTintRed));
        }

    }

    @Override
    public int getItemCount() {
        return mPrescriptions.size();
    }

    public interface OnPrescriptionListener {
        void onPrescriptionClick(int position);
    }
}