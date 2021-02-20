package com.cgg.twdinspection.inspection.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    String stocksSupplied, haircut, studentsFoundAnemic, anemicStudCnt, attireOfStudents, cooksWearingCap,
            handsOfCookingStaff, attireOfStaff, remarks;
    String toilets, kitchen, dormitory, runningWater, classRooms, storeroom;
    String officerID, instID, insTime;
    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;
    private String gccDate, suppliedDate, hmhwoDate;

    public static final int SIGNATURE_ACTIVITY = 1;

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_general_comments, getResources().getString(R.string.title_general_comments));
        binding.signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralCommentsActivity.this, CaptureSignature.class);
                startActivityForResult(intent, SIGNATURE_ACTIVITY);
            }
        });

        TextView[] ids = new TextView[]{binding.slno1, binding.slno2, binding.slno3, binding.slno4, binding.slno5,
                binding.slno6};
        BaseActivity.setIds(ids, 85);

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
                    haircut = AppConstants.Yes;
                else
                    haircut = AppConstants.No;
            }
        });
        binding.rgStudentsFoundAnemic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStudentsFoundAnemic.getCheckedRadioButtonId();
                if (selctedItem == R.id.students_found_anemic_yes) {
                    studentsFoundAnemic = AppConstants.Yes;
                    binding.tvAnaemicStudCnt.setVisibility(View.VISIBLE);
                } else {
                    binding.etAnaemicStudCnt.setText("");
                    binding.tvAnaemicStudCnt.setVisibility(View.GONE);
                    studentsFoundAnemic = AppConstants.No;
                }
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
                    cooksWearingCap = AppConstants.Yes;
                else
                    cooksWearingCap = AppConstants.No;
            }
        });
        binding.rgHandsOfCookingStaff.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHandsOfCookingStaff.getCheckedRadioButtonId();
                if (selctedItem == R.id.hands_of_cooking_staff_yes)
                    handsOfCookingStaff = AppConstants.Yes;
                else
                    handsOfCookingStaff = AppConstants.No;
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
                anemicStudCnt = binding.etAnaemicStudCnt.getText().toString().trim();
                remarks = binding.etRemarks.getText().toString().trim();

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
            int localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<GeneralCommentsEntity> generalCommentsEntityLiveData = instMainViewModel.getGeneralCommentsInfoData(instID);
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
            ScrollToView(binding.rgFoodTimeFruits);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_fruit_ontime));
        } else if (TextUtils.isEmpty(foodTimeEggs)) {
            ScrollToView(binding.rgFoodTimeEggs);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_egg_ontime));
        } else if (TextUtils.isEmpty(foodTimeVeg)) {
            ScrollToView(binding.rgFoodTimeVeg);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_veg_ontime));
        } else if (TextUtils.isEmpty(foodTimeProvisions)) {
            ScrollToView(binding.rgFoodTimeProvisions);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_food_ontime));
        } else if (TextUtils.isEmpty(foodQualityFruits)) {
            ScrollToView(binding.rgFoodQualityFruits);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_qua_fruit));
        } else if (TextUtils.isEmpty(foodQualityEggs)) {
            ScrollToView(binding.rgFoodQualityEggs);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_qau_egg));
        } else if (TextUtils.isEmpty(foodQualityVeg)) {
            ScrollToView(binding.rgFoodQualityVeg);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_qua_veg));
        } else if (TextUtils.isEmpty(foodQualityProvisions)) {
            ScrollToView(binding.rgFoodQualityProvisions);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_qua_food));
        } else if (TextUtils.isEmpty(hmhwoDate)) {
            ScrollToView(binding.etHmHwoDate);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_hm));
        } else if (TextUtils.isEmpty(gccDate)) {
            ScrollToView(binding.etGccDate);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_food_supp));
        } else if (TextUtils.isEmpty(stocksSupplied)) {
            ScrollToView(binding.rgStocksSupplied);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_stock));
        } else if (TextUtils.isEmpty(suppliedDate)) {
            ScrollToView(binding.etSuppliedDate);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_sup_date));
        } else if (TextUtils.isEmpty(haircut)) {
            ScrollToView(binding.rgHaircut);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_haircut));
        } else if (TextUtils.isEmpty(studentsFoundAnemic)) {
            ScrollToView(binding.rgStudentsFoundAnemic);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_stu_ana));
        } else if (studentsFoundAnemic.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(anemicStudCnt)) {
            ScrollToView(binding.etAnaemicStudCnt);
            returnFlag = false;
            showSnackBar(getString(R.string.ane_stud_cnt));
        } else if (TextUtils.isEmpty(attireOfStudents)) {
            ScrollToView(binding.rgAttireOfStudents);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_stu_att));
        } else if (TextUtils.isEmpty(cooksWearingCap)) {
            ScrollToView(binding.rgCooksWearingCap);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_cook_cap));
        } else if (TextUtils.isEmpty(handsOfCookingStaff)) {
            ScrollToView(binding.rgHandsOfCookingStaff);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_cook_hand));
        } else if (TextUtils.isEmpty(attireOfStaff)) {
            ScrollToView(binding.rgAttireOfStaff);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_staff_att));
        } else if (TextUtils.isEmpty(toilets)) {
            ScrollToView(binding.rgToilets);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_toilet));
        } else if (TextUtils.isEmpty(kitchen)) {
            ScrollToView(binding.rgKitchen);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_kit));
        } else if (TextUtils.isEmpty(dormitory)) {
            ScrollToView(binding.rgDormitory);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_dor));
        } else if (TextUtils.isEmpty(classRooms)) {
            ScrollToView(binding.rgClassRooms);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_class));
        } else if (TextUtils.isEmpty(runningWater)) {
            ScrollToView(binding.rgRunningWater);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_water));
        } else if (TextUtils.isEmpty(storeroom)) {
            ScrollToView(binding.rgStoreroom);
            returnFlag = false;
            showSnackBar(getString(R.string.gen_store));
        } else if (TextUtils.isEmpty(remarks)) {
            ScrollToView(binding.etRemarks);
            returnFlag = false;
            showSnackBar(getString(R.string.sel_insp_remarks));
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
        generalCommentsEntity.setAnemic_stud_cnt(anemicStudCnt);
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
        generalCommentsEntity.setRemarks(remarks);

        long x = genCommentsViewModel.insertGeneralCommentsInfo(generalCommentsEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("GC");
                liveData.observe(GeneralCommentsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGNATURE_ACTIVITY) {
            if (resultCode == RESULT_OK) {

                Bundle bundle = data.getExtras();
                String status = bundle.getString("status");
                if (status.equalsIgnoreCase("done")) {
                    Toast toast = Toast.makeText(this, "Signature capture successful!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 105, 50);
                    toast.show();
                }
            }
        }

    }
}
