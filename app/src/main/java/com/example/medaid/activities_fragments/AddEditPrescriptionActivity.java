package com.example.medaid.activities_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.medaid.R;
import com.example.medaid.adapters.ScheduleRecyclerAdapter;
import com.example.medaid.models.Prescription;
import com.example.medaid.models.WeeklySchedule;
import com.example.medaid.persistence.PrescriptionRepository;

public class AddEditPrescriptionActivity extends AppCompatActivity {

    /*ui components*/
    private TextInputLayout mTitleEditText;
    private TextInputLayout mDescriptionEditText;
    private TextInputLayout mQuantityEditText;

    Toolbar toolbar;
    private FloatingActionButton savePrescriptionButton;
    private Button newScheduleButton;

    private RecyclerView mRecyclerView;

    /*vars*/
    private Prescription mPrescription;
    private PrescriptionRepository mPrescriptionRepository;
    private ScheduleRecyclerAdapter mScheduleRecyclerAdapter;

    private static final int REQUEST_CODE = 1;
    private static final int RESULT_DELETE = 2;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_edit_prescription, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_prescription);

        initializeVariables();
        initializeRecyclerView();
        initializeSetOnClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && data != null) {
            WeeklySchedule mWeeklySchedule = data.getParcelableExtra("WeeklySchedule");
            if (resultCode == RESULT_OK) {
                if (!mPrescription.timeExists(mWeeklySchedule.getTime())) {
                    mPrescription.addWeeklySchedule(mWeeklySchedule);
                } else {
                    // entry with duplicate time
                }
            } else if (requestCode == RESULT_DELETE) {
                mPrescription.deleteWeeklySchedule(mWeeklySchedule);
            }
            mScheduleRecyclerAdapter.notifyDataSetChanged();
        }
    }

    public void initializeVariables() {
        mPrescriptionRepository = new PrescriptionRepository(this);

        // UI Initialization
        mTitleEditText = findViewById(R.id.addEdit_title);

        mDescriptionEditText = findViewById(R.id.addEdit_description);
        mQuantityEditText = findViewById(R.id.addEdit_quantity);

        toolbar = findViewById(R.id.toolbar);
        savePrescriptionButton = findViewById(R.id.addEdit_savePrescription_button);
        newScheduleButton = findViewById(R.id.newSchedule_button);

        // Set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set TextInputEditText
        if (getIntent().hasExtra("prescription")) {
            mPrescription = getIntent().getParcelableExtra("prescription");
            mTitleEditText.getEditText().setText(mPrescription.getTitle());
            mDescriptionEditText.getEditText().setText(mPrescription.getDescription());

            if (mPrescription.getQuantity() >= 0) {
                mQuantityEditText.getEditText().setText(mPrescription.getQuantity() + "");
            }
            getSupportActionBar().setTitle("Edit");
        } else {
            mPrescription = new Prescription();
            getSupportActionBar().setTitle("Add");
        }
    }

    public void initializeRecyclerView() {
        mRecyclerView = findViewById(R.id.addEditSchedule_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(AddEditPrescriptionActivity.this));
        mScheduleRecyclerAdapter = new ScheduleRecyclerAdapter(mPrescription.getSchedule());
        mRecyclerView.setAdapter(mScheduleRecyclerAdapter);

        mScheduleRecyclerAdapter.setOnItemClickListener(new ScheduleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                mPrescription.deleteWeeklySchedule(mPrescription.getSchedule().get(position));
                mScheduleRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    public void initializeSetOnClick() {
        // Override toolbar onClick back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Save Prescription
        savePrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mPrescription.setTitle(mTitleEditText.getEditText().getText().toString());
            mPrescription.setDescription(mDescriptionEditText.getEditText().getText().toString());

            if (mTitleEditText.getEditText().getText().toString().matches("")) {
                mTitleEditText.setErrorEnabled(true);
                mTitleEditText.setError("Title required");
                return;
            }

            if (!mQuantityEditText.getEditText().getText().toString().matches("")) {
                mPrescription.setQuantity(Integer.valueOf(mQuantityEditText.getEditText().getText().toString()));
            }
            if (!getIntent().hasExtra("prescription")) {
                mPrescriptionRepository.insertPrescriptionTask(mPrescription);
            } else {
                mPrescriptionRepository.updatePrescriptionTask(mPrescription);
            }

            onBackPressed();
            }
        });

        // New Schedule
        newScheduleButton
            .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SchedulePrescriptionActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                }
            });

        mTitleEditText.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && mTitleEditText.isErrorEnabled() || !hasFocus && !mTitleEditText.getEditText().toString().matches("")) {
                    mTitleEditText.setErrorEnabled(false);
                }
            }
        });
    }

    // OnClick delete from menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_prescription_menu) {
            if (getIntent().hasExtra("prescription")) {
                mPrescriptionRepository.deletePrescriptionTask(mPrescription);
            }
            onBackPressed();
            return true;
        }
        return false;
    }

    // Override onBackPressed() for animations
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
