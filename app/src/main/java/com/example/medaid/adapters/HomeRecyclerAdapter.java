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

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ScheduleViewHolder> {
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
        private TextView textViewDaily;
        private TextView textViewDose;
        private List<String> days = Arrays.asList("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday");
        private static final String ID = "_addEdit";
        private TextView time;
        private ImageView pillIcon;
        private ImageView deleteWeeklySchedule;


        public ScheduleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            time = itemView.findViewById(R.id.time_addEdit);
            pillIcon = itemView.findViewById(R.id.pillIcon_addEdit);
            deleteWeeklySchedule = itemView.findViewById(R.id.delete_weeklySchedule);
            textViewDaily = itemView.findViewById(R.id.daily_addEdit);
            textViewDose = itemView.findViewById(R.id.dose_addEdit);
            textViewDays = new ArrayList<>();


            // Init for textViewDays
            for (String days : days) {
                int idRes = itemView.getResources().getIdentifier(days + ID, "id", itemView.getContext().getPackageName());
                TextView textViewDay = itemView.findViewById(idRes);
                textViewDay.setVisibility(View.GONE);
                textViewDays.add(textViewDay);
            }
            textViewDaily.setVisibility(View.GONE);

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

    public HomeRecyclerAdapter(List<WeeklySchedule> mScheduleList) {
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
        WeeklySchedule weeklySchedule = mScheduleList.get(position);

        holder.time.setText(weeklySchedule.getTime());

        if (weeklySchedule.getDose() > 0) {
            holder.textViewDose.setText("(" + weeklySchedule.getDose() + ")");
            holder.pillIcon.setVisibility(View.VISIBLE);
        } else {
            holder.textViewDose.setText("");
            holder.pillIcon.setVisibility(View.INVISIBLE);
        }

        if (weeklySchedule.getSize() == 7) {
            holder.textViewDaily.setVisibility(View.VISIBLE);
        } else {
            // Set textView to visible for the days in the weekly schedule.
            for (HashMap.Entry<String, Boolean> day : weeklySchedule.getDays().entrySet()) {
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
    }

    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }
}
