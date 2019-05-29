package com.example.medaid.activities_fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.medaid.models.Prescription;
import com.example.medaid.R;
import com.example.medaid.persistence.PrescriptionRepository;

public class AddEditPrescriptionActivity extends AppCompatActivity {

    /*ui components*/
    private TextInputEditText mTitleEditText;
    private TextInputEditText mDescriptionEditText;
    private TextInputEditText mQuantityEditText;
    private FloatingActionButton savePrescriptionButton;

    /*vars*/
    private Prescription mPrescription;
    private PrescriptionRepository mPrescriptionRepository;

    // Get toolbar menu
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

        // Prescription Initialization
        mPrescriptionRepository = new PrescriptionRepository(this);
        mTitleEditText = findViewById(R.id.addEdit_title);
        mDescriptionEditText = findViewById(R.id.addEdit_description);

        if (getIntent().hasExtra("prescription")) {
            mPrescription = getIntent().getParcelableExtra("prescription");
            mTitleEditText.setText(mPrescription.getTitle());
            mDescriptionEditText.setText(mPrescription.getDescription());
        } else {
            mPrescription = new Prescription();
        }

        // UI Initialization
        savePrescriptionButton = findViewById(R.id.addEdit_savePrescription_button);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add/Edit");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Override toolbar onClick back
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Insert or update prescription
        savePrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPrescription.setTitle(mTitleEditText.getText().toString());
                mPrescription.setDescription(mDescriptionEditText.getText().toString());

                if (!getIntent().hasExtra("prescription")) {
                    mPrescriptionRepository.insertPrescriptionTask(mPrescription);
                } else {
                    mPrescriptionRepository.updatePrescriptionTask(mPrescription);
                }

                onBackPressed();
            }
        });
    }

    // Delete prescription
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
