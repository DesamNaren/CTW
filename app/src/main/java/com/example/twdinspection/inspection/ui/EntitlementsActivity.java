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

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityEntitlementsBinding;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.viewmodel.EntitilementsCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.EntitlementsViewModel;

import java.util.Calendar;

public class EntitlementsActivity extends AppCompatActivity {
    ActivityEntitlementsBinding binding;
    EntitlementsViewModel entitlementsViewModel;
    EntitlementsEntity entitlementsEntity;
    String entitlementsProvidedToStudents, bedSheets, carpets, uniforms, sportsDress, slippers, nightDress, sanitaryNapkins, schoolBags, notesSupplied, cosmetics, hair_cut_complted, entitlementsUniforms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_entitlements);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Entitlements Distributions");

        entitlementsViewModel = ViewModelProviders.of(EntitlementsActivity.this,
                new EntitilementsCustomViewModel(binding, this, getApplication())).get(EntitlementsViewModel.class);
        binding.setViewModel(entitlementsViewModel);

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
                    entitlementsProvidedToStudents = "YES";
                else
                    entitlementsProvidedToStudents = "NO";
            }
        });
        binding.rgBedsheets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBedsheets.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_bedsheets)
                    bedSheets = "YES";
                else
                    bedSheets = "NO";
            }
        });

        binding.rgCarpets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCarpets.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_carpets)
                    carpets = "YES";
                else
                    carpets = "NO";
            }
        });

        binding.rgUniforms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgUniforms.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_uniforms)
                    uniforms = "YES";
                else
                    uniforms = "NO";
            }
        });
        binding.rgSportsDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSportsDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_sports_dress)
                    sportsDress = "YES";
                else
                    sportsDress = "NO";
            }
        });
        binding.rgSlippers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSlippers.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_slippers)
                    slippers = "YES";
                else
                    slippers = "NO";
            }
        });

        binding.rgNightDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgNightDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_night_dress)
                    nightDress = "YES";
                else
                    nightDress = "NO";
            }
        });

        binding.rgSchoolBags.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSchoolBags.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_school_bags)
                    schoolBags = "YES";
                else
                    schoolBags = "NO";
            }
        });

        binding.rgSanitaryNapkins.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSanitaryNapkins.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_sanitary_napkins)
                    sanitaryNapkins = "YES";
                else
                    sanitaryNapkins = "NO";
            }
        });

        binding.rgNotesSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgNotesSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_notes_supplied)
                    notesSupplied = "YES";
                else
                    notesSupplied = "NO";
            }
        });
        binding.rgCosmetics.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCosmetics.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_cosmetics)
                    cosmetics = "YES";
                else
                    cosmetics = "NO";
            }
        });

        binding.rgEntitlementsUniforms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEntitlementsUniforms.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_entitlements_uniforms)
                    entitlementsUniforms = "YES";
                else
                    entitlementsUniforms = "NO";
            }
        });

        binding.rgHaircut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHaircut.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_haircut)
                    hair_cut_complted = "YES";
                else
                    hair_cut_complted = "NO";
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pair_of_dress_distributed = binding.etPairOfDressDistributed.getText().toString().trim();
                String haircutDate = binding.etEntitlementsHaircutDate.getText().toString().trim();

                entitlementsEntity = new EntitlementsEntity();
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

                startActivity(new Intent(EntitlementsActivity.this, RegistersActivity.class));
            }
        });
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
        datePickerDialog.show();
    }

}
