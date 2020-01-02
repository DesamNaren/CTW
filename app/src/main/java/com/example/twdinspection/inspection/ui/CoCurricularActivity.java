package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityCoCurricularBinding;

import java.util.Calendar;

public class CoCurricularActivity extends AppCompatActivity {

    ActivityCoCurricularBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_co_curricular);

        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText(" Co Circulator & Extra Curricular Activities");
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoCurricularActivity.this, EntitlementsActivity.class));
            }
        });
        binding.rgSportMatRec.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sportRecYes) {
                    binding.llEnteredStock.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sportRecNo) {
                    binding.llEnteredStock.setVisibility(View.GONE);
                }
            }
        });
        binding.rgPlayGrAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_playGrAvailYes) {
                    binding.llPlanToProcure.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_playGrAvailNo) {
                    binding.llPlanToProcure.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rgPlanToProc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_planToProcYes) {
                    binding.measLandAvail.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_planToProcNo) {
                    binding.measLandAvail.setVisibility(View.GONE);
                }
            }
        });
        binding.rgScoutsImpl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_scoutsYes) {
                    binding.llScoutEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_scoutsNo) {
                    binding.llScoutEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.rgNccStudImpl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nccStudImpYes) {
                    binding.llNccEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nccStudImpNo) {
                    binding.llNccEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.rgSmcElections.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_smcElectionsYes) {
                    binding.llSmcElectionsEnties.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_smcElectionsNo) {
                    binding.llSmcElectionsEnties.setVisibility(View.GONE);
                }
            }
        });
        binding.rgKitchenGarAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_kitchenAvailYes) {
                    binding.llKitchenEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_kitchenAvailNo) {
                    binding.llKitchenEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.rgStudCounElect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_studCounElecYes) {
                    binding.llNameOfCommDisplayed.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_studCounElecNo) {
                    binding.llStudCommEntries.setVisibility(View.GONE);
                    binding.llNameOfCommDisplayed.setVisibility(View.GONE);
                }
            }
        });
        binding.rgNameOfCommDisp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nameOfCommYes) {
                    binding.llStudCommEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nameOfCommNo) {
                    binding.llStudCommEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.etMedicalCheckupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastMeetingDateSelection();
            }
        });
        binding.btnAddStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void lastMeetingDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(CoCurricularActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        binding.etMedicalCheckupDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
    }

}
