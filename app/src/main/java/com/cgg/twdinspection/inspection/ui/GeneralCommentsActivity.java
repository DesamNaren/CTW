package com.cgg.twdinspection.inspection.ui;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGeneralCommentsBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.general_comments.GeneralCommentsEntity;
import com.cgg.twdinspection.inspection.viewmodel.GenCommentsCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.GenCommentsViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class GeneralCommentsActivity extends BaseActivity implements SaveListener {

    ActivityGeneralCommentsBinding binding;
    GenCommentsViewModel genCommentsViewModel;
    GeneralCommentsEntity generalCommentsEntity;
    String foodTimeFruits, foodTimeEggs, foodTimeVeg, foodTimeProvisions, foodQualityFruits, foodQualityEggs, foodQualityVeg, foodQualityProvisions;
    String stocksSupplied, haircut, studentsFoundAnemic, attireOfStudents, cooksWearingCap, handsOfCookingStaff, attireOfStaff;
    String toilets, kitchen, dormitory, runningWater, classRooms, storeroom;
    String officerID, instID, insTime;
    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;
    private int localFlag = -1;
    private String gccDate, suppliedDate,hmhwoDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_general_comments, getResources().getString(R.string.title_general_comments));
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        genCommentsViewModel = ViewModelProviders.of(GeneralCommentsActivity.this,
                new GenCommentsCustomViewModel(binding, this, getApplication())).get(GenCommentsViewModel.class);
        binding.setViewModel(genCommentsViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.rgFoodTimeFruits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeFruits.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_fruits_yes)
                    foodTimeFruits = AppConstants.Yes;
                else
                    foodTimeFruits = AppConstants.No;
            }
        });
        binding.rgFoodTimeEggs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeEggs.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_eggs_yes)
                    foodTimeEggs = AppConstants.Yes;
                else
                    foodTimeEggs = AppConstants.No;
            }
        });
        binding.rgFoodTimeVeg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeVeg.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_veg_yes)
                    foodTimeVeg = AppConstants.Yes;
                else
                    foodTimeVeg = AppConstants.No;
            }
        });
        binding.rgFoodTimeProvisions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodTimeProvisions.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_time_provisions_yes)
                    foodTimeProvisions = AppConstants.Yes;
                else
                    foodTimeProvisions = AppConstants.No;
            }
        });
        binding.rgFoodQualityFruits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityFruits.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_fruits_yes)
                    foodQualityFruits = AppConstants.GOOD;
                else
                    foodQualityFruits = AppConstants.INFERIOR;
            }
        });
        binding.rgFoodQualityEggs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityEggs.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_eggs_yes)
                    foodQualityEggs = AppConstants.GOOD;
                else
                    foodQualityEggs = AppConstants.INFERIOR;
            }
        });
        binding.rgFoodQualityVeg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityVeg.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_veg_yes)
                    foodQualityVeg = AppConstants.GOOD;
                else
                    foodQualityVeg = AppConstants.INFERIOR;
            }
        });
        binding.rgFoodQualityProvisions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodQualityProvisions.getCheckedRadioButtonId();
                if (selctedItem == R.id.food_quality_provisions_yes)
                    foodQualityProvisions = AppConstants.GOOD;
                else
                    foodQualityProvisions = AppConstants.INFERIOR;
            }
        });
        binding.rgStocksSupplied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStocksSupplied.getCheckedRadioButtonId();
                if (selctedItem == R.id.stocks_supplied_yes)
                    stocksSupplied = AppConstants.Yes;
                else
                    stocksSupplied = AppConstants.No;
            }
        });
        binding.rgHaircut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHaircut.getCheckedRadioButtonId();
                if (selctedItem == R.id.haircut_yes)
                    haircut = AppConstants.GOOD;
                else
                    haircut = AppConstants.INFERIOR;
            }
        });
        binding.rgStudentsFoundAnemic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStudentsFoundAnemic.getCheckedRadioButtonId();
                if (selctedItem == R.id.students_found_anemic_yes)
                    studentsFoundAnemic = AppConstants.GOOD;
                else
                    studentsFoundAnemic = AppConstants.INFERIOR;
            }
        });
        binding.rgAttireOfStudents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttireOfStudents.getCheckedRadioButtonId();
                if (selctedItem == R.id.attire_of_students_yes)
                    attireOfStudents = AppConstants.GOOD;
                else
                    attireOfStudents = AppConstants.INFERIOR;
            }
        });
        binding.rgCooksWearingCap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCooksWearingCap.getCheckedRadioButtonId();
                if (selctedItem == R.id.cooks_wearing_cap_yes)
                    cooksWearingCap = AppConstants.GOOD;
                else
                    cooksWearingCap = AppConstants.INFERIOR;
            }
        });
        binding.rgHandsOfCookingStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHandsOfCookingStaff.getCheckedRadioButtonId();
                if (selctedItem == R.id.hands_of_cooking_staff_yes)
                    handsOfCookingStaff = AppConstants.GOOD;
                else
                    handsOfCookingStaff = AppConstants.INFERIOR;
            }
        });
        binding.rgAttireOfStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttireOfStaff.getCheckedRadioButtonId();
                if (selctedItem == R.id.attire_of_staff_yes)
                    attireOfStaff = AppConstants.GOOD;
                else
                    attireOfStaff = AppConstants.INFERIOR;
            }
        });
        binding.rgToilets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgToilets.getCheckedRadioButtonId();
                if (selctedItem == R.id.toilets_yes)
                    toilets = AppConstants.Yes;
                else
                    toilets = AppConstants.No;
            }
        });
        binding.rgKitchen.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgKitchen.getCheckedRadioButtonId();
                if (selctedItem == R.id.kitchen_yes)
                    kitchen = AppConstants.Yes;
                else
                    kitchen = AppConstants.No;
            }
        });
        binding.rgDormitory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDormitory.getCheckedRadioButtonId();
                if (selctedItem == R.id.dormitory_yes)
                    dormitory = AppConstants.Yes;
                else
                    dormitory = AppConstants.No;
            }
        });
        binding.rgClassRooms.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgClassRooms.getCheckedRadioButtonId();
                if (selctedItem == R.id.class_rooms_yes)
                    classRooms = AppConstants.Yes;
                else
                    classRooms = AppConstants.No;
            }
        });
        binding.rgRunningWater.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRunningWater.getCheckedRadioButtonId();
                if (selctedItem == R.id.running_water_yes)
                    runningWater = AppConstants.Yes;
                else
                    runningWater = AppConstants.No;
            }
        });
        binding.rgStoreroom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStoreroom.getCheckedRadioButtonId();
                if (selctedItem == R.id.storeroom_yes)
                    storeroom = AppConstants.Yes;
                else
                    storeroom = AppConstants.No;
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gccDate = binding.etGccDate.getText().toString().trim();
                suppliedDate = binding.etSuppliedDate.getText().toString().trim();
                hmhwoDate = binding.etHmHwoDate.getText().toString().trim();

                if (validate())
                    Utils.customSaveAlert(GeneralCommentsActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

            }
        });
        binding.etGccDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelection("gccDate");
            }
        });

        binding.etSuppliedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelection("suppliedDate");
            }
        });

       binding.etHmHwoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateSelection("hmhwoDate");
            }
        });


        try {
            localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<GeneralCommentsEntity> generalCommentsEntityLiveData = instMainViewModel.getGeneralCommentsInfoData();
                generalCommentsEntityLiveData.observe(GeneralCommentsActivity.this, new Observer<GeneralCommentsEntity>() {
                    @Override
                    public void onChanged(GeneralCommentsEntity generalCommentsEntity) {
                        generalCommentsEntityLiveData.removeObservers(GeneralCommentsActivity.this);
                        if (generalCommentsEntity != null) {
                            binding.setComments(generalCommentsEntity);
                            binding.executePendingBindings();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(foodTimeFruits)) {
            returnFlag = false;
            showSnackBar("Check whether fruits supplied on time");
        } else if (TextUtils.isEmpty(foodTimeEggs)) {
            returnFlag = false;
            showSnackBar("Check whether eggs supplied on time");
        } else if (TextUtils.isEmpty(foodTimeVeg)) {
            returnFlag = false;
            showSnackBar("Check whether vegetables supplied on time");
        } else if (TextUtils.isEmpty(foodTimeProvisions)) {
            returnFlag = false;
            showSnackBar("Check whether food provisions supplied on time");
        } else if (TextUtils.isEmpty(foodQualityFruits)) {
            returnFlag = false;
            showSnackBar("Check the quality of fruits supplied");
        } else if (TextUtils.isEmpty(foodQualityEggs)) {
            returnFlag = false;
            showSnackBar("Check the quality of eggs supplied");
        } else if (TextUtils.isEmpty(foodQualityVeg)) {
            returnFlag = false;
            showSnackBar("Check the quality of vegetables supplied");
        } else if (TextUtils.isEmpty(foodQualityProvisions)) {
            returnFlag = false;
            showSnackBar("Check the quality of food provisions supplied");
        } else if (TextUtils.isEmpty(hmhwoDate)) {
            returnFlag = false;
            showSnackBar("Select date of last indent raised by HM/ HWO");
        }  else if (TextUtils.isEmpty(gccDate)) {
            returnFlag = false;
            showSnackBar("Select date of supply of food provision by GCC and others");
        } else if (TextUtils.isEmpty(stocksSupplied)) {
            returnFlag = false;
            showSnackBar("Check whether stock is supplied as per the intent");
        } else if (TextUtils.isEmpty(suppliedDate)) {
            returnFlag = false;
            showSnackBar("Select capture supplied date");
        } else if (TextUtils.isEmpty(haircut)) {
            returnFlag = false;
            showSnackBar("Check whether haircut is on time");
        } else if (TextUtils.isEmpty(studentsFoundAnemic)) {
            returnFlag = false;
            showSnackBar("Check whether students found anemic");
        } else if (TextUtils.isEmpty(attireOfStudents)) {
            returnFlag = false;
            showSnackBar("Check the attire of students");
        } else if (TextUtils.isEmpty(cooksWearingCap)) {
            returnFlag = false;
            showSnackBar("Check whether cooks wear cap during cooking and serving");
        } else if (TextUtils.isEmpty(handsOfCookingStaff)) {
            returnFlag = false;
            showSnackBar("Check whether hands of cooking staff is clean");
        } else if (TextUtils.isEmpty(attireOfStaff)) {
            returnFlag = false;
            showSnackBar("Check whether attire of staff is appropriate");
        } else if (TextUtils.isEmpty(toilets)) {
            returnFlag = false;
            showSnackBar("Select toilets are clean");
        } else if (TextUtils.isEmpty(kitchen)) {
            returnFlag = false;
            showSnackBar("Select kitchen is clean");
        } else if (TextUtils.isEmpty(dormitory)) {
            returnFlag = false;
            showSnackBar("Select dormitory is clean");
        } else if (TextUtils.isEmpty(classRooms)) {
            returnFlag = false;
            showSnackBar("Select classrooms are clean");
        } else if (TextUtils.isEmpty(runningWater)) {
            returnFlag = false;
            showSnackBar("Check whether running water available to toilets");
        } else if (TextUtils.isEmpty(storeroom)) {
            returnFlag = false;
            showSnackBar("Select storerooms are clean");
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    private void dateSelection(String flag) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        if (flag.equalsIgnoreCase("gccDate"))
                            binding.etGccDate.setText(date);
                        else if (flag.equalsIgnoreCase("suppliedDate"))
                            binding.etSuppliedDate.setText(date);
                       else if (flag.equalsIgnoreCase("hmhwoDate"))
                            binding.etHmHwoDate.setText(date);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void submitData() {

        generalCommentsEntity = new GeneralCommentsEntity();
        generalCommentsEntity.setOfficer_id(officerID);
        generalCommentsEntity.setInspection_time(Utils.getCurrentDateTime());
        generalCommentsEntity.setInstitute_id(instID);
        generalCommentsEntity.setSupplied_on_time_fruits(foodTimeFruits);
        generalCommentsEntity.setSupplied_on_time_eggs(foodTimeFruits);
        generalCommentsEntity.setSupplied_on_time_vegetables(foodTimeFruits);
        generalCommentsEntity.setSupplied_on_time_food_provisions(foodTimeFruits);
        generalCommentsEntity.setQuality_of_food_fruits(foodQualityFruits);
        generalCommentsEntity.setQuality_of_food_eggs(foodQualityEggs);
        generalCommentsEntity.setQuality_of_food_vegetables(foodQualityVeg);
        generalCommentsEntity.setQuality_of_food_food_provisions(foodQualityProvisions);
        generalCommentsEntity.setHm_hwo_date(hmhwoDate);
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

        long x = genCommentsViewModel.insertGeneralCommentsInfo(generalCommentsEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("GC");
                liveData.observe(GeneralCommentsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        liveData.removeObservers(GeneralCommentsActivity.this);
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instID);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(GeneralCommentsActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
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
