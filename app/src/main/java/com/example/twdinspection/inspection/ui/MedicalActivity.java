package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityMedicalBinding;
import com.example.twdinspection.inspection.source.MedicalAndHealth.MedicalInfoEntity;
import com.example.twdinspection.inspection.viewmodel.MedicalCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalViewModel;

import java.util.Calendar;

public class MedicalActivity extends AppCompatActivity {
    ActivityMedicalBinding binding;
    MedicalViewModel medicalViewModel;
    MedicalInfoEntity medicalInfoEntity;
    String recorderedInRegister = "", medicalCheckUpDoneByWhom = "", anmWeeklyUpdated = "", callHealth100 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_medical);

        medicalViewModel = ViewModelProviders.of(MedicalActivity.this,
                new MedicalCustomViewModel(binding, this, getApplication())).get(MedicalViewModel.class);
        binding.setViewModel(medicalViewModel);

        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Medical & Health Issues");
        binding.etMedicalCheckupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicalCheckupDateSelection();
            }
        });

        binding.rgMedicalCheckupDetails.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMedicalCheckupDetails.getCheckedRadioButtonId();
                if (selctedItem == R.id.medical_checkup_details_yes)
                    recorderedInRegister = "YES";
                else
                    recorderedInRegister = "NO";
            }
        });

        binding.rgMedicalCheckUpDoneByWhom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMedicalCheckUpDoneByWhom.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_medical_officer)
                    medicalCheckUpDoneByWhom = "MEDICAL OFFICER";
                else
                    medicalCheckUpDoneByWhom = "ANM";
            }
        });

        binding.rgAnmWeeklyUpdated.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAnmWeeklyUpdated.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_anm_weekly_updated)
                    anmWeeklyUpdated = "YES";
                else
                    anmWeeklyUpdated = "NO";
            }
        });

        binding.rgCallHealth100.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCallHealth100.getCheckedRadioButtonId();
                if (selctedItem == R.id.yes_call_health_100)
                    callHealth100 = "YES";
                else
                    callHealth100 = "NO";
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feverCount = binding.etFever.getText().toString().trim();
                String coldCount = binding.etCold.getText().toString().trim();
                String diarrheaCount = binding.etDiarrhea.getText().toString().trim();
                String headacheCount = binding.etHeadache.getText().toString().trim();
                String malariaCount = binding.etMalaria.getText().toString().trim();
                String othersCount = binding.etOthers.getText().toString().trim();
                String CheckupDate = binding.etMedicalCheckupDate.getText().toString().trim();

                if (feverCount.isEmpty())
                    feverCount = "0";
                if (coldCount.isEmpty())
                    coldCount = "0";
                if (diarrheaCount.isEmpty())
                    diarrheaCount = "0";
                if (headacheCount.isEmpty())
                    headacheCount = "0";
                if (malariaCount.isEmpty())
                    malariaCount = "0";
                if (othersCount.isEmpty())
                    othersCount = "0";


                medicalInfoEntity = new MedicalInfoEntity();

                medicalInfoEntity.setFeverCount(feverCount);
                medicalInfoEntity.setColdCount(coldCount);
                medicalInfoEntity.setHeadacheCount(headacheCount);
                medicalInfoEntity.setDiarrheaCount(diarrheaCount);
                medicalInfoEntity.setMalariaCount(malariaCount);
                medicalInfoEntity.setOthersCount(othersCount);
                medicalInfoEntity.setLast_medical_checkup_date(CheckupDate);
                medicalInfoEntity.setRecorded_in_register(recorderedInRegister);
                medicalInfoEntity.setMedicalCheckUpDoneByWhom(medicalCheckUpDoneByWhom);
                medicalInfoEntity.setAnmWeeklyUpdated(anmWeeklyUpdated);
                medicalInfoEntity.setCallHealth100(callHealth100);

                long x = medicalViewModel.insertMedicalInfo(medicalInfoEntity);
                Toast.makeText(MedicalActivity.this, "Inserted " + x, Toast.LENGTH_SHORT).show();

//                Utils.customAlert(MedicalActivity.this, "Data submitted successfully", AppConstants.SUCCESS, false);

//                if (binding.medicalCheckupDetailsYes.isChecked()) {
//                    startActivity(new Intent(MedicalActivity.this, MedicalDetailsActivity.class));
//                } else {
//                    startActivity(new Intent(MedicalActivity.this, DietIssuesActivity.class));
//                }
                startActivity(new Intent(MedicalActivity.this, DietIssuesActivity.class));

            }
        });


    }

    private void medicalCheckupDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        binding.etMedicalCheckupDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
