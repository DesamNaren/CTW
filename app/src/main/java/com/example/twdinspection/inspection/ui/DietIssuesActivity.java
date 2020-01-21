package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.viewmodel.DietIssuesCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.DietIsuuesViewModel;

public class DietIssuesActivity extends BaseActivity implements SaveListener {

    ActivityDietIssuesBinding binding;
    DietIsuuesViewModel dietIsuuesViewModel;
    //    DietIssuesEntity dietIssuesEntity;
    SharedPreferences sharedPreferences;
    private String officerID, instID, insTime;
    //    List<DietListEntity> dietInfoEntityListMain;
//    DietIssuesAdapter adapter;
//    MasterInstituteInfo masterInstituteInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_diet_issues, getResources().getString(R.string.diet_issues));

        dietIsuuesViewModel = ViewModelProviders.of(DietIssuesActivity.this,
                new DietIssuesCustomViewModel(binding, this, getApplication())).get(DietIsuuesViewModel.class);
        binding.setViewModel(dietIsuuesViewModel);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

     /*   dietIsuuesViewModel.getDietInfo(TWDApplication.get(DietIssuesActivity.this).getPreferences().getString(AppConstants.INST_ID, "")).observe(DietIssuesActivity.this, new Observer<List<DietListEntity>>() {
            @Override
            public void onChanged(List<DietListEntity> dietIssuesEntities) {
                if (dietIssuesEntities != null && dietIssuesEntities.size() > 0) {
                    dietInfoEntityListMain=dietIssuesEntities;
                    adapter = new DietIssuesAdapter(DietIssuesActivity.this, dietInfoEntityListMain);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(DietIssuesActivity.this));
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
                                    DietListEntity studAttendInfoEntity = new DietListEntity(masterDietInfos.get(i).getItemName(),masterDietInfos.get(i).getGroundBal(),masterDietInfos.get(i).getBookBal());
                                    dietInfoEntityListMain.add(studAttendInfoEntity);
                                }
                            }

                            if (dietInfoEntityListMain != null && dietInfoEntityListMain.size() > 0) {
                                dietIsuuesViewModel.insertDietInfo(dietInfoEntityListMain);
                            }else {
//                                binding.emptyView.setVisibility(View.VISIBLE);
                                binding.recyclerView.setVisibility(View.GONE);
                            }

                        }
                    });
                }

            }
        });
*/

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DietIssuesActivity.this, InfraActivity.class));
            }
        });
    }

    @Override
    public void submitData() {

    }
}
