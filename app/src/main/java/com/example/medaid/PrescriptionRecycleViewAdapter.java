package com.example.medaid;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class PrescriptionRecycleViewAdapter extends RecyclerView.Adapter<PrescriptionRecycleViewAdapter.PrescriptionViewHolder> {
    Context ctx;
    CursorAdapter cursorAdapter;

    public PrescriptionRecycleViewAdapter(Context context, Cursor cursor) {
        ctx = context;

        cursorAdapter = new CursorAdapter(context, cursor, 0) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                LayoutInflater layoutInflater = LayoutInflater.from(ctx);
                View view = layoutInflater.inflate(R.layout.layout_prescription_item, viewGroup, false);
                return view;
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView itemTitle = view.findViewById(R.id.item_title);
                TextView  itemDescription = view.findViewById(R.id.item_description);
                TextView itemID = view.findViewById(R.id.item_ID);

                itemTitle.setText(cursor.getString(cursor.getColumnIndex("TITLE")));
                itemDescription.setText(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));

                itemID.setText("(" + cursor.getString(cursor.getColumnIndex("_id")) + ")");

            }
        };
    }

    public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
        public PrescriptionViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return cursorAdapter.getCount();
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = cursorAdapter.newView(ctx, cursorAdapter.getCursor(), parent);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder prescriptionViewHolder, int pos) {
        cursorAdapter.getCursor().moveToPosition(pos);
        cursorAdapter.bindView(prescriptionViewHolder.itemView, ctx, cursorAdapter.getCursor());
    }
}


