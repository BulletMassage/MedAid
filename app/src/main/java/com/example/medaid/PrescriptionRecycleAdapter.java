package com.example.medaid;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class PrescriptionRecycleAdapter extends RecyclerView.Adapter<PrescriptionRecycleAdapter.PrescriptionViewHolder> {
    Context ctx;
    CursorAdapter cursorAdapter;

    public PrescriptionRecycleAdapter(Context context, Cursor cursor) {
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
                TextView itemDescription = view.findViewById(R.id.item_description);

                itemTitle.setText(cursor.getString(cursor.getColumnIndex("TITLE")));
                itemDescription.setText(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));

                //TextView itemID = view.findViewById(R.id.item_ID);
                //itemID.setText("(" + cursor.getString(cursor.getColumnIndex("_id")) + ")");

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
    public void onBindViewHolder(PrescriptionViewHolder holder, final int position) {
        final Cursor cursor = cursorAdapter.getCursor();

        cursor.moveToPosition(position);
        cursorAdapter.bindView(holder.itemView, ctx, cursor);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cursor.moveToPosition(position);
                Bundle args = new Bundle();
                args.putString("_id", cursor.getString(cursor.getColumnIndex("_id")));
                args.putString("TITLE", cursor.getString(cursor.getColumnIndex("TITLE")));
                args.putString("DESCRIPTION", cursor.getString(cursor.getColumnIndex("DESCRIPTION")));

                //Toast.makeText(ctx,  cursor.getString(cursor.getColumnIndex("_id")) + " " + cursor.getString(cursor.getColumnIndex("TITLE")), Toast.LENGTH_LONG).show();

                AddEditPrescriptionFragment fragment = new AddEditPrescriptionFragment();
                fragment.setArguments(args);

                ((FragmentActivity)ctx).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });


    }
}


