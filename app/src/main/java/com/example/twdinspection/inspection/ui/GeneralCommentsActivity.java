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
import com.example.twdinspection.databinding.ActivityGeneralCommentsBinding;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.viewmodel.GenCommentsCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.GenCommentsViewModel;

import java.util.Calendar;

public class GeneralCommentsActivity extends AppCompatActivity {

    ActivityGeneralCommentsBinding binding;
    GenCommentsViewModel genCommentsViewModel;
    GeneralCommentsEntity generalCommentsEntity;
    String foodTimeFruits, foodTimeEggs, foodTimeVeg, foodTimeProvisions, foodQualityFruits, foodQualityEggs, foodQualityVeg, foodQualityProvisions;
    String stocksSupplied, haircut, studentsFoundAnemic, attireOfStudents, cooksWearingCap, handsOfCookingStaff, attireOfStaff;
    String toilets, kitchen, dormitory, runningWater, classRooms, storeroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_general_comments);

        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("General Comments");

        genCommentsViewModel = ViewModelProviders.of(GeneralCommentsActivity.this,
                new GenCommentsCustomViewModel(binding, this, getApplication())).get(GenCommentsViewModel.class);
        binding.setViewModel(genCommentsViewModel);

        binding.rgFoodTimeFruits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeFruits.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_fruits_yes)
                    foodTimeFruits = "YES";
                else
                    foodTimeFruits = "NO";
            }
        });
        binding.rgFoodTimeEggs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeEggs.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_eggs_yes)
                    foodTimeEggs = "YES";
                else
                    foodTimeEggs = "NO";
            }
        });
        binding.rgFoodTimeVeg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeVeg.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_veg_yes)
                    foodTimeVeg = "YES";
                else
                    foodTimeVeg = "NO";
            }
        });
        binding.rgFoodTimeProvisions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeProvisions.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_provisions_yes)
                    foodTimeProvisions = "YES";
                else
                    foodTimeProvisions = "NO";
            }
        });
        binding.rgFoodQualityFruits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityFruits.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_fruits_yes)
                    foodQualityFruits = "YES";
                else
                    foodQualityFruits = "NO";
            }
        });
        binding.rgFoodQualityEggs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityEggs.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_eggs_yes)
                    foodQualityEggs = "YES";
                else
                    foodQualityEggs = "NO";
            }
        });
        binding.rgFoodQualityVeg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityVeg.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_veg_yes)
                    foodQualityVeg = "YES";
                else
                    foodQualityVeg = "NO";
            }
        });
        binding.rgFoodQualityProvisions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityProvisions.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_provisions_yes)
                    foodQualityProvisions = "YES";
                else
                    foodQualityProvisions = "NO";
            }
        });
        binding.rgStocksSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStocksSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.stocks_supplied_yes)
                    stocksSupplied = "YES";
                else
                    stocksSupplied = "NO";
            }
        });
        binding.rgHaircut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHaircut.getCheckedRadioButtonId();
                if (selctedItem == R.id.haircut_yes)
                    haircut = "YES";
                else
                    haircut = "NO";
            }
        });
        binding.rgStudentsFoundAnemic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStudentsFoundAnemic.getCheckedRadioButtonId();
                if (selctedItem == R.id.students_found_anemic_yes)
                    studentsFoundAnemic = "YES";
                else
                    studentsFoundAnemic = "NO";
            }
        });
        binding.rgAttireOfStudents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttireOfStudents.getCheckedRadioButtonId();
                if (selctedItem == R.id.attire_of_students_yes)
                    attireOfStudents = "YES";
                else
                    attireOfStudents = "NO";
            }
        });
        binding.rgCooksWearingCap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCooksWearingCap.getCheckedRadioButtonId();
                if (selctedItem == R.id.cooks_wearing_cap_yes)
                    cooksWearingCap = "YES";
                else
                    cooksWearingCap = "NO";
            }
        });
        binding.rgHandsOfCookingStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHandsOfCookingStaff.getCheckedRadioButtonId();
                if (selctedItem == R.id.hands_of_cooking_staff_yes)
                    handsOfCookingStaff = "YES";
                else
                    handsOfCookingStaff = "NO";
            }
        });
        binding.rgAttireOfStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttireOfStaff.getCheckedRadioButtonId();
                if (selctedItem == R.id.attire_of_staff_yes)
                    attireOfStaff = "YES";
                else
                    attireOfStaff = "NO";
            }
        });
        binding.rgToilets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgToilets.getCheckedRadioButtonId();
                if (selctedItem == R.id.toilets_yes)
                    toilets = "YES";
                else
                    toilets = "NO";
            }
        });
        binding.rgKitchen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgKitchen.getCheckedRadioButtonId();
                if (selctedItem == R.id.kitchen_yes)
                    kitchen = "YES";
                else
                    kitchen = "NO";
            }
        });
        binding.rgDormitory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDormitory.getCheckedRadioButtonId();
                if (selctedItem == R.id.dormitory_yes)
                    dormitory = "YES";
                else
                    dormitory = "NO";
            }
        });
        binding.rgClassRooms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgClassRooms.getCheckedRadioButtonId();
                if (selctedItem == R.id.class_rooms_yes)
                    classRooms = "YES";
                else
                    classRooms = "NO";
            }
        });
        binding.rgRunningWater.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRunningWater.getCheckedRadioButtonId();
                if (selctedItem == R.id.running_water_yes)
                    runningWater = "YES";
                else
                    runningWater = "NO";
            }
        });
        binding.rgStoreroom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStoreroom.getCheckedRadioButtonId();
                if (selctedItem == R.id.storeroom_yes)
                    storeroom = "YES";
                else
                    storeroom = "NO";
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gccDate=binding.etGccDate.getText().toString().trim();
                String suppliedDate=binding.etSuppliedDate.getText().toString().trim();

                generalCommentsEntity = new GeneralCommentsEntity();
                generalCommentsEntity.setSupplied_on_time_fruits(foodTimeFruits);
                generalCommentsEntity.setSupplied_on_time_eggs(foodTimeFruits);
                generalCommentsEntity.setSupplied_on_time_vegetables(foodTimeFruits);
                generalCommentsEntity.setSupplied_on_time_food_provisions(foodTimeFruits);
                generalCommentsEntity.setQuality_of_food_fruits(foodTimeFruits);
                generalCommentsEntity.setQuality_of_food_eggs(foodTimeFruits);
                generalCommentsEntity.setQuality_of_food_vegetables(foodTimeFruits);
                generalCommentsEntity.setQuality_of_food_food_provisions(foodTimeFruits);
                generalCommentsEntity.setGcc_date(gccDate);
                generalCommentsEntity.setSupplied_date(suppliedDate);
                generalCommentsEntity.setStocksSupplied(stocksSupplied);
                generalCommentsEntity.setHair_cut_onTime(haircut);
                generalCommentsEntity.setStud_found_anemic(studentsFoundAnemic);
                generalCommentsEntity.setStud_attire(attireOfStudents);
                generalCommentsEntity.setCook_wearing_cap(cooksWearingCap);
                generalCommentsEntity.setStaff_hands_clean(handsOfCookingStaff);
                generalCommentsEntity.setStaff_attire(attireOfStaff);
                generalCommentsEntity.setToilets_not_clean(toilets);
                generalCommentsEntity.setKitchen_not_clean(kitchen);
                generalCommentsEntity.setDormitory_not_clean(dormitory);
                generalCommentsEntity.setClassroom_not_clean(classRooms);
                generalCommentsEntity.setWater_available(runningWater);
                generalCommentsEntity.setStoreroom_not_clean(storeroom);


                startActivity(new Intent(GeneralCommentsActivity.this, UploadedPhotoActivity.class));
            }
        });
        binding.etGccDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gccDateSelection();
            }
        });

        binding.etSuppliedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suppliedDateSelection();
            }
        });


    }

    private void gccDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.etGccDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void suppliedDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        binding.etSuppliedDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
