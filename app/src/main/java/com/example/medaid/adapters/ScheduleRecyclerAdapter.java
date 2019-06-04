package com.example.medaid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medaid.R;
import com.example.medaid.models.WeeklySchedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.ScheduleViewHolder> {
    private List<WeeklySchedule> mScheduleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        private List<TextView> textViewDays;
        private List<String> days = Arrays.asList("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday");
        private static final String ID = "_addEdit";
        private TextView time;
        private ImageView deleteWeeklySchedule;

        public ScheduleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            time = itemView.findViewById(R.id.item_time);
            deleteWeeklySchedule = itemView.findViewById(R.id.delete_weeklySchedule);
            textViewDays = new ArrayList<>();

            // Init for textViewDays
            for (String days : days) {
                int idRes = itemView.getResources().getIdentifier(days + ID, "id", itemView.getContext().getPackageName());
                TextView textViewDay = itemView.findViewById(idRes);
                textViewDay.setVisibility(View.GONE);
                textViewDays.add(textViewDay);
            }

            // OnClick
            deleteWeeklySchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public ScheduleRecyclerAdapter(List<WeeklySchedule> mScheduleList) {
        this.mScheduleList = mScheduleList;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_schedule_item, parent, false);
        ScheduleViewHolder viewHolder = new ScheduleViewHolder(v, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleViewHolder holder, int position) {
        Context context = holder.time.getContext();
        holder.time.setText(mScheduleList.get(position).getTime());

        // Set textView to visible for the days in the weekly schedule.
        for (HashMap.Entry<String, Boolean> day: mScheduleList.get(position).getDays().entrySet()) {
            if (day.getValue()) {
                for (TextView textViewDay : holder.textViewDays) {
                    String dayID = context.getResources().getResourceName(textViewDay.getId());
                    if (dayID.contains(day.getKey().toLowerCase())) {
                        textViewDay.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }
}
