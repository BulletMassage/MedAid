package com.example.medaid.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.medaid.R;
import com.example.medaid.models.Prescription;
import com.example.medaid.models.WeeklySchedule;

import java.util.List;

public class HomeRecyclerAdapterOld extends RecyclerView.Adapter<HomeRecyclerAdapterOld.ViewHolder>{

    private List<Prescription> mPrescriptions;

    public HomeRecyclerAdapterOld(List<Prescription> prescriptions) {
        this.mPrescriptions = prescriptions;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView description;
        private TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            description = itemView.findViewById(R.id.item_description);
            time = itemView.findViewById(R.id.time_home);
        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_home_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mPrescriptions.get(position).getTitle());

        for (WeeklySchedule weeklySchedule : mPrescriptions.get(position).getSchedule()) {
            holder.time.setText(weeklySchedule.getTime());
        }

        String descriptionAsString = mPrescriptions.get(position).getDescription();

        if (descriptionAsString.matches("")) {
            holder.description.setVisibility(View.GONE);
        } else {
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(descriptionAsString);
        }
    }

    @Override
    public int getItemCount() {
        return mPrescriptions.size();
    }

}