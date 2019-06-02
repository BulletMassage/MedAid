package com.example.medaid.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medaid.R;
import com.example.medaid.helpers.CalendarTypeConverter;
import com.example.medaid.models.WeeklySchedule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ScheduleRecyclerAdapter extends RecyclerView.Adapter<ScheduleRecyclerAdapter.ScheduleViewHolder> {
    private List<WeeklySchedule> mScheduleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        public TextView sunday, monday, tuesday, wednesday, thursday, friday, saturday;
        public TextView time;
        public ImageView deleteWeeklySchedule;

        public ScheduleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            sunday = itemView.findViewById(R.id.sunday);
            monday = itemView.findViewById(R.id.monday);
            tuesday = itemView.findViewById(R.id.tuesday);
            wednesday = itemView.findViewById(R.id.wednesday);
            thursday = itemView.findViewById(R.id.thursday);
            friday = itemView.findViewById(R.id.friday);
            saturday = itemView.findViewById(R.id.saturday);

            time = itemView.findViewById(R.id.item_time);
            deleteWeeklySchedule = itemView.findViewById(R.id.delete_weeklySchedule);

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
        if (!mScheduleList.get(position).getDay("Sunday")) {
            holder.sunday.setVisibility(View.GONE);
        } else {
            holder.sunday.setVisibility(View.VISIBLE);
        }
        if (!mScheduleList.get(position).getDay("Monday")) {
            holder.monday.setVisibility(View.GONE);
        } else {
            holder.monday.setVisibility(View.VISIBLE);
        }
        if (!mScheduleList.get(position).getDay("Tuesday")) {
            holder.tuesday.setVisibility(View.GONE);
        } else {
            holder.tuesday.setVisibility(View.VISIBLE);
        }
        if (!mScheduleList.get(position).getDay("Wednesday")) {
            holder.wednesday.setVisibility(View.GONE);
        } else {
            holder.wednesday.setVisibility(View.VISIBLE);
        }
        if (!mScheduleList.get(position).getDay("Thursday")) {
            holder.thursday.setVisibility(View.GONE);
        } else {
            holder.thursday.setVisibility(View.VISIBLE);
        }
        if (!mScheduleList.get(position).getDay("Friday")) {
            holder.friday.setVisibility(View.GONE);
        } else {
            holder.friday.setVisibility(View.VISIBLE);
        }
        if (!mScheduleList.get(position).getDay("Saturday")) {
            holder.saturday.setVisibility(View.GONE);
        } else {
            holder.saturday.setVisibility(View.VISIBLE);
        }

        holder.time.setText(mScheduleList.get(position).getTime());
    }



    @Override
    public int getItemCount() {
        return mScheduleList.size();
    }
}
