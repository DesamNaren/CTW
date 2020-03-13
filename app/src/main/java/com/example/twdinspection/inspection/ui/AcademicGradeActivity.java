package com.example.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityAcademicGradeBinding;
import com.example.twdinspection.inspection.adapter.AcademicGradeAdapter;
import com.example.twdinspection.inspection.interfaces.AcademicGradeInterface;
import com.example.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.viewmodel.AcademicViewModel;
import com.example.twdinspection.inspection.viewmodel.StudentsAttndViewModel;

import java.util.ArrayList;
import java.util.List;

public class AcademicGradeActivity extends AppCompatActivity implements AcademicGradeInterface {
    ActivityAcademicGradeBinding binding;
    StudentsAttndViewModel studentsAttndViewModel;
    private String instId, officerId;
    private SharedPreferences sharedPreferences;
    private AcademicGradeAdapter academicGradeAdapter;
    private List<AcademicGradeEntity> academicGradeActivitiesMain;
    private AcademicViewModel academicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_academic_grade);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        binding.btnLayout.btnNext.setText("Submit");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.headerTitle.setText(getString(R.string.title_academic));

        academicGradeActivitiesMain = new ArrayList<>();

        academicViewModel = new AcademicViewModel(AcademicGradeActivity.this);
        studentsAttndViewModel = new StudentsAttndViewModel(AcademicGradeActivity.this);

         academicViewModel.getAcademicGradeInfo(instId).observe(AcademicGradeActivity.this, new Observer<List<AcademicGradeEntity>>() {
            @Override
            public void onChanged(List<AcademicGradeEntity> academicGradeEntities) {

                if (academicGradeEntities != null && academicGradeEntities.size() > 0) {
                    academicGradeAdapter = new AcademicGradeAdapter(AcademicGradeActivity.this, academicGradeEntities);
                    binding.gradeRV.setLayoutManager(new LinearLayoutManager(AcademicGradeActivity.this));
                    binding.gradeRV.setAdapter(academicGradeAdapter);
                } else {
                    LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = studentsAttndViewModel.getMasterClassInfo(
                            instId);
                    masterInstituteInfoLiveData.observe(AcademicGradeActivity.this, new Observer<MasterInstituteInfo>() {
                        @Override
                        public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                            masterInstituteInfoLiveData.removeObservers(AcademicGradeActivity.this);
                            List<MasterClassInfo> masterClassInfos = masterInstituteInfos.getClassInfo();
                            if (masterClassInfos != null && masterClassInfos.size() > 0) {
                                binding.emptyView.setVisibility(View.GONE);
                                binding.gradeRV.setVisibility(View.VISIBLE);

                                for (int i = 0; i < masterClassInfos.size(); i++) {
                                    if (masterClassInfos.get(i).getStudentCount() > 0) {
                                        AcademicGradeEntity academicGradeEntity = new AcademicGradeEntity(officerId, instId,
                                                Utils.getCurrentDateTime(),
                                                masterClassInfos.get(i).getType(),
                                                String.valueOf(masterClassInfos.get(i).getClassId()),
                                                String.valueOf(masterClassInfos.get(i).getStudentCount()));
                                        academicGradeActivitiesMain.add(academicGradeEntity);
                                    }
                                }
                            }

                            if (academicGradeActivitiesMain != null && academicGradeActivitiesMain.size() > 0) {
                                academicViewModel.insertAcademicGradeInfo(academicGradeActivitiesMain);
                            } else {
                                binding.emptyView.setVisibility(View.VISIBLE);
                                binding.gradeRV.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
       Utils.customDiscardAlert(AcademicGradeActivity.this, getString(R.string.app_name), getString(R.string.go_back));
    }

    @Override
    public void submitData(List<AcademicGradeEntity> academicGradeEntities) {
        if (academicGradeEntities != null && academicGradeEntities.size() > 0) {
            academicViewModel.insertAcademicGradeInfo(academicGradeEntities);
            Utils.customAcademicSectionSaveAlert(AcademicGradeActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
        } else {
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.gradeRV.setVisibility(View.GONE);
        }
    }
}
