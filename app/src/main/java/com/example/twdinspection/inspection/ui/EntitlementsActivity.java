package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityEntitlementsBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.viewmodel.EntitilementsCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.EntitlementsViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class EntitlementsActivity extends BaseActivity implements SaveListener {
    ActivityEntitlementsBinding binding;
    EntitlementsViewModel entitlementsViewModel;
    EntitlementsEntity entitlementsEntity;
    String entitlementsProvidedToStudents, bedSheets, carpets, uniforms, sportsDress, slippers, nightDress, sanitaryNapkins, schoolBags, notesSupplied, cosmetics, hair_cut_complted, entitlementsUniforms;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    String instId,officerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_entitlements,getResources().getString(R.string.title_entitlements));

        entitlementsViewModel = ViewModelProviders.of(EntitlementsActivity.this,
                new EntitilementsCustomViewModel(binding, this, getApplication())).get(EntitlementsViewModel.class);
        binding.setViewModel(entitlementsViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        sharedPreferences=TWDApplication.get(this).getPreferences();
        instId=sharedPreferences.getString(AppConstants.INST_ID, "");
        officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        binding.etEntitlementsHaircutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entitlementsHaircutDateSelection();
            }
        });

        binding.rgEntitlementsProvidedToStudents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEntitlementsProvidedToStudents.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_entitlements_provided_to_students)
                    entitlementsProvidedToStudents = AppConstants.Yes;
                else
                    entitlementsProvidedToStudents = AppConstants.No;
            }
        });
        binding.rgBedsheets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBedsheets.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_bedsheets)
                    bedSheets = AppConstants.Yes;
                else
                    bedSheets = AppConstants.No;
            }
        });

        binding.rgCarpets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCarpets.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_carpets)
                    carpets = AppConstants.Yes;
                else
                    carpets = AppConstants.No;
            }
        });

        binding.rgUniforms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgUniforms.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_uniforms)
                    uniforms = AppConstants.Yes;
                else
                    uniforms = AppConstants.No;
            }
        });
        binding.rgSportsDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSportsDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_sports_dress)
                    sportsDress = AppConstants.Yes;
                else
                    sportsDress = AppConstants.No;
            }
        });
        binding.rgSlippers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSlippers.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_slippers)
                    slippers = AppConstants.Yes;
                else
                    slippers = AppConstants.No;
            }
        });

        binding.rgNightDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgNightDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_night_dress)
                    nightDress = AppConstants.Yes;
                else
                    nightDress = AppConstants.No;
            }
        });

        binding.rgSchoolBags.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSchoolBags.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_school_bags)
                    schoolBags = AppConstants.Yes;
                else
                    schoolBags = AppConstants.No;
            }
        });

        binding.rgSanitaryNapkins.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSanitaryNapkins.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_sanitary_napkins)
                    sanitaryNapkins = AppConstants.Yes;
                else
                    sanitaryNapkins = AppConstants.No;
            }
        });

        binding.rgNotesSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgNotesSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_notes_supplied)
                    notesSupplied = AppConstants.Yes;
                else
                    notesSupplied = AppConstants.No;
            }
        });
        binding.rgCosmetics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCosmetics.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_cosmetics)
                    cosmetics = AppConstants.Yes;
                else
                    cosmetics = AppConstants.No;
            }
        });

        binding.rgEntitlementsUniforms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEntitlementsUniforms.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_entitlements_uniforms)
                    entitlementsUniforms = AppConstants.Yes;
                else
                    entitlementsUniforms = AppConstants.No;
            }
        });

        binding.rgHaircut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHaircut.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_haircut)
                    hair_cut_complted = AppConstants.Yes;
                else
                    hair_cut_complted = AppConstants.No;
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {

                    Utils.customSaveAlert(EntitlementsActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

                }
            }
        });
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(entitlementsProvidedToStudents)) {
            showSnackBar("Check weather entitlements provided to students");
            returnFlag=false;
        } else if (TextUtils.isEmpty(bedSheets)) {
            returnFlag=false;
            showSnackBar("Check weather bedsheets provided to students");
        } else if(TextUtils.isEmpty(carpets)) {
            returnFlag=false;
            showSnackBar("Check weather carpets provided to students");
        } else if (TextUtils.isEmpty(uniforms)) {
            returnFlag=false;
            showSnackBar("Check weather uniforms provided to students");
        } else if(TextUtils.isEmpty(sportsDress)) {
            returnFlag=false;
            showSnackBar("Check weather sportsDress provided to students");
        } else if (TextUtils.isEmpty(slippers)) {
            returnFlag=false;
            showSnackBar("Check weather slippers provided to students");
        } else if(TextUtils.isEmpty(nightDress)) {
            returnFlag=false;
            showSnackBar("Check weather nightDress provided to students");
        } else if(TextUtils.isEmpty(schoolBags)) {
            returnFlag=false;
            showSnackBar("Check weather schoolBags provided to students");
        } else if(TextUtils.isEmpty(sanitaryNapkins)) {
            returnFlag=false;
            showSnackBar("Check weather sanitaryNapkins provided to students");
        } else if (TextUtils.isEmpty(notesSupplied)) {
            returnFlag=false;
            showSnackBar("Check weather notesSupplied to students");
        } else if(TextUtils.isEmpty(cosmetics)) {
            returnFlag=false;
            showSnackBar("Check weather cosmetics distributed upto month");
        } else if (TextUtils.isEmpty(binding.etPairOfDressDistributed.getText().toString())) {
            returnFlag=false;
            showSnackBar("Enter no of pair of dress distributed to each student");
        } else if(TextUtils.isEmpty(entitlementsUniforms)) {
            returnFlag=false;
            showSnackBar("Check weather uniforms provided are in good quality");
        } else if(TextUtils.isEmpty(hair_cut_complted)) {
            returnFlag=false;
            showSnackBar("Check weather haircut is completed");
        } else if ((binding.etEntitlementsHaircutDate.getText().toString().equals(getResources().getString(R.string.select_date)))) {
            returnFlag=false;
            showSnackBar("Select date of last haircut");
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    private void entitlementsHaircutDateSelection() {
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

                        binding.etEntitlementsHaircutDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void submitData() {
        String pair_of_dress_distributed = binding.etPairOfDressDistributed.getText().toString().trim();
        String haircutDate = binding.etEntitlementsHaircutDate.getText().toString().trim();

        entitlementsEntity = new EntitlementsEntity();
        entitlementsEntity.setOfficer_id(officerID);
        entitlementsEntity.setInstitute_id(instId);
        entitlementsEntity.setInspection_time(Utils.getCurrentDateTime());
        entitlementsEntity.setEntitlements_provided(entitlementsProvidedToStudents);
        entitlementsEntity.setBedSheets(bedSheets);
        entitlementsEntity.setCarpets(carpets);
        entitlementsEntity.setUniforms(uniforms);
        entitlementsEntity.setSportsDress(sportsDress);
        entitlementsEntity.setSlippers(slippers);
        entitlementsEntity.setNightDress(nightDress);
        entitlementsEntity.setSchoolBags(schoolBags);
        entitlementsEntity.setSanitaryNapkins(sanitaryNapkins);
        entitlementsEntity.setNotesSupplied(notesSupplied);
        entitlementsEntity.setPairOfDressDistributedCount(pair_of_dress_distributed);
        entitlementsEntity.setEntitlementsUniforms(entitlementsUniforms);
        entitlementsEntity.setHair_cut_complted(hair_cut_complted);
        entitlementsEntity.setLast_haircut_date(haircutDate);
        entitlementsEntity.setCosmetic_distributed(cosmetics);

        long x = entitlementsViewModel.insertEntitlementsInfo(entitlementsEntity);
        if (x >= 0) {
            long z = 0;
            try {
                z = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), 9,instId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z >= 0) {
                showSnackBar(getString(R.string.data_saved));
                startActivity(new Intent(EntitlementsActivity.this, RegistersActivity.class));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }
    @Override
    public void onBackPressed() {
        super.callBack();
    }
}
