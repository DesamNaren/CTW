package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.inspection.adapter.DietIssuesAdapter;
import com.example.twdinspection.inspection.interfaces.DietInterface;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietListEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterDietInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.viewmodel.DietIssuesCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.DietIsuuesViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DietIssuesActivity extends BaseActivity implements SaveListener, DietInterface {

    ActivityDietIssuesBinding binding;
    DietIsuuesViewModel dietIsuuesViewModel;
    InstMainViewModel instMainViewModel;
    DietIssuesEntity dietIssuesEntity;
    SharedPreferences sharedPreferences;
    private String officerID, instID, insTime;
    List<DietListEntity> dietInfoEntityListMain;
    DietIssuesAdapter adapter;
    MasterInstituteInfo masterInstituteInfos;
    String menu_chart_served, menu_chart_painted, menu_served, food_provisions, matching_with_samples, committee_exist, discussed_with_committee, maintaining_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_diet_issues, getResources().getString(R.string.diet_issues));

        dietIsuuesViewModel = ViewModelProviders.of(DietIssuesActivity.this,
                new DietIssuesCustomViewModel(binding, this, getApplication())).get(DietIsuuesViewModel.class);
        binding.setViewModel(dietIsuuesViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        dietInfoEntityListMain = new ArrayList<>();
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        dietIsuuesViewModel.getDietInfo(TWDApplication.get(DietIssuesActivity.this).getPreferences().getString(AppConstants.INST_ID, "")).observe(DietIssuesActivity.this, new Observer<List<DietListEntity>>() {
            @Override
            public void onChanged(List<DietListEntity> dietIssuesEntities) {
                if (dietIssuesEntities != null && dietIssuesEntities.size() > 0) {
                    dietInfoEntityListMain = dietIssuesEntities;
                    adapter = new DietIssuesAdapter(DietIssuesActivity.this, dietInfoEntityListMain);
                    binding.recyclerView.setLayoutManager(new GridLayoutManager(DietIssuesActivity.this, 2));
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = dietIsuuesViewModel.getMasterDietInfo(TWDApplication.get(DietIssuesActivity.this).getPreferences().getString(AppConstants.INST_ID, ""));
                    masterInstituteInfoLiveData.observe(DietIssuesActivity.this, new Observer<MasterInstituteInfo>() {
                        @Override
                        public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                            masterInstituteInfoLiveData.removeObservers(DietIssuesActivity.this);
                            DietIssuesActivity.this.masterInstituteInfos = masterInstituteInfos;
                            List<MasterDietInfo> masterDietInfos = masterInstituteInfos.getDietInfo();
                            if (masterDietInfos != null && masterDietInfos.size() > 0) {
                                for (int i = 0; i < masterDietInfos.size(); i++) {
                                    DietListEntity studAttendInfoEntity = new DietListEntity(masterDietInfos.get(i).getItemName(), masterDietInfos.get(i).getGroundBal(), masterDietInfos.get(i).getBookBal(), instID, officerID);
                                    dietInfoEntityListMain.add(studAttendInfoEntity);
                                }
                            }

                            if (dietInfoEntityListMain != null && dietInfoEntityListMain.size() > 0) {
                                dietIsuuesViewModel.insertDietInfo(dietInfoEntityListMain);
                            } else {
                                binding.emptyView.setVisibility(View.VISIBLE);
                                binding.recyclerView.setVisibility(View.GONE);
                            }
                        }
                    });

                }

            }
        });

        binding.rgMenuChartServed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMenuChartServed.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_menu_chart_served)
                    menu_chart_served = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_menu_chart_served)
                    menu_chart_served = AppConstants.No;
                else
                    menu_chart_served = null;
            }
        });

        binding.rgMenuChartPainted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMenuChartPainted.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_menu_chart_painted)
                    menu_chart_painted = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_menu_chart_painted)
                    menu_chart_painted = AppConstants.No;
                else
                    menu_chart_painted = null;
            }
        });

        binding.rgPrescribedMenuServed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPrescribedMenuServed.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_menu_served_yes)
                    menu_served = AppConstants.Yes;
                else if (selctedItem == R.id.rb_menu_served_no)
                    menu_served = AppConstants.No;
                else
                    menu_served = null;
            }
        });

        binding.rgFoodProvisions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodProvisions.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_food_provisions_yes)
                    food_provisions = AppConstants.Yes;
                else if (selctedItem == R.id.rb_food_provisions_no)
                    food_provisions = AppConstants.No;
                else
                    food_provisions = null;
            }
        });

        binding.rgMatchingWithSamples.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMatchingWithSamples.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_matching_with_samples_yes)
                    matching_with_samples = AppConstants.Yes;
                else if (selctedItem == R.id.rb_matching_with_samples_no)
                    matching_with_samples = AppConstants.No;
                else
                    matching_with_samples = null;
            }
        });

        binding.rgCommitteeExist.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCommitteeExist.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_committee_exist_yes) {
                    committee_exist = AppConstants.Yes;
                    binding.llCommittee.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.rb_committee_exist_no) {
                    committee_exist = AppConstants.No;
                    binding.llCommittee.setVisibility(View.GONE);
                    discussed_with_committee = null;
                    maintaining_register = null;
                } else {
                    committee_exist = null;
                    binding.llCommittee.setVisibility(View.GONE);
                }
            }
        });


        binding.rgDiscussedWithCommittee.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDiscussedWithCommittee.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_discussed_with_committee_yes)
                    discussed_with_committee = AppConstants.Yes;
                else if (selctedItem == R.id.rb_discussed_with_committee_no)
                    discussed_with_committee = AppConstants.No;
                else
                    discussed_with_committee = null;
            }
        });

        binding.rgMaintainingRegister.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMaintainingRegister.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_maintaining_register_yes)
                    maintaining_register = AppConstants.Yes;
                else if (selctedItem == R.id.rb_maintaining_register_no)
                    maintaining_register = AppConstants.No;
                else
                    maintaining_register = null;
            }
        });


        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateData()) {
                    dietIssuesEntity = new DietIssuesEntity();

                    dietIssuesEntity.setInstitute_id(instID);
                    dietIssuesEntity.setInspection_time(insTime);
                    dietIssuesEntity.setOfficer_id(officerID);
                    dietIssuesEntity.setMenu_chart_served(menu_chart_served);
                    dietIssuesEntity.setMenu_chart_painted(menu_chart_painted);
                    dietIssuesEntity.setMenu_served(menu_served);
                    dietIssuesEntity.setFood_provisions(food_provisions);
                    dietIssuesEntity.setMatching_with_samples(matching_with_samples);
                    dietIssuesEntity.setCommittee_exist(committee_exist);
                    dietIssuesEntity.setDiscussed_with_committee(discussed_with_committee);
                    dietIssuesEntity.setMaintaining_register(maintaining_register);

                    Utils.customSaveAlert(DietIssuesActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

                }
            }
        });
    }

    private boolean validateData() {

        if (TextUtils.isEmpty(menu_chart_served)) {
            showSnackBar(getResources().getString(R.string.select_menu_chart_served));
            return false;
        }

        if (TextUtils.isEmpty(menu_chart_painted)) {
            showSnackBar(getResources().getString(R.string.sel_menu_chart_painted));
            return false;
        }
        if (TextUtils.isEmpty(menu_served)) {
            showSnackBar(getResources().getString(R.string.sel_menu_served));
            return false;
        }

        if (TextUtils.isEmpty(food_provisions)) {
            showSnackBar(getResources().getString(R.string.sel_food_provisions));
            return false;
        }

        if (TextUtils.isEmpty(matching_with_samples)) {
            showSnackBar(getResources().getString(R.string.sel_matching_with_samples));
            return false;
        }

        if (TextUtils.isEmpty(committee_exist)) {
            showSnackBar(getResources().getString(R.string.sel_committee_exist));
            return false;
        }

        if (committee_exist.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(discussed_with_committee)) {
            showSnackBar(getResources().getString(R.string.sel_discussed_with_committee));
            return false;
        }

        if (committee_exist.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(maintaining_register)) {
            showSnackBar(getResources().getString(R.string.sel_maintaining_register));
            return false;
        }
        return true;
    }


    @Override
    public void submitData() {

        long x = dietIsuuesViewModel.updateDietIssuesInfo(dietIssuesEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Diet");;
                liveData.observe(DietIssuesActivity.this, new Observer<Integer>() {
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
                Utils.customSectionSaveAlert(DietIssuesActivity.this,getString(R.string.data_saved),getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.callBack();
    }

    @Override
    public void validate() {

    }
}
