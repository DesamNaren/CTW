package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityCocurricularAchDetailsBinding;
import com.cgg.twdinspection.inspection.adapter.StudAchievementsAdapter;
import com.cgg.twdinspection.inspection.interfaces.StudAchievementsInterface;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StudAchCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StudAchViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class CocurricularStudAchActivity extends AppCompatActivity implements StudAchievementsInterface {

    private ActivityCocurricularAchDetailsBinding binding;
    private StudAchViewModel viewModel;
    private StudAchievementsAdapter studAchievementsAdapter;
    InstMainViewModel instMainViewModel;
    private String fromClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, (R.layout.activity_cocurricular_ach_details));
        binding.appBarLayout.backBtn.setVisibility(View.GONE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.stu_ach));
        SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
        viewModel = ViewModelProviders.of(this,
                new StudAchCustomViewModel(binding, this, getApplication())).get(StudAchViewModel.class);
        binding.setViewModel(viewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        Gson gson = new Gson();

        fromClass = getIntent().getStringExtra(AppConstants.FROM_CLASS);
        if (fromClass != null && fromClass.equalsIgnoreCase(AppConstants.COCAR)) {
            calCoCir();
        } else if (fromClass != null && fromClass.equalsIgnoreCase(AppConstants.REPORT_COCAR)) {
            String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
            InspReportData reportData = gson.fromJson(data, InspReportData.class);
            if (reportData != null && reportData.getCoCurricularInfo() != null) {
                List<StudAchievementEntity> studAchievementEntities = reportData.getCoCurricularInfo().getStudAchievementEntities();
                if (studAchievementEntities != null && studAchievementEntities.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.callRv.setVisibility(View.VISIBLE);
                    studAchievementsAdapter = new StudAchievementsAdapter(CocurricularStudAchActivity.this, studAchievementEntities, fromClass);
                    binding.callRv.setLayoutManager(new LinearLayoutManager(CocurricularStudAchActivity.this));
                    binding.callRv.setAdapter(studAchievementsAdapter);
                } else {
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.callRv.setVisibility(View.GONE);
                }
            } else {
                binding.noDataTv.setVisibility(View.VISIBLE);
                binding.callRv.setVisibility(View.GONE);
            }
        }


    }

    private void calCoCir() {
        viewModel.getStudAchievementsData().observe(CocurricularStudAchActivity.this, new Observer<List<StudAchievementEntity>>() {
            @Override
            public void onChanged(List<StudAchievementEntity> studAchievementEntities) {
                if (studAchievementEntities != null && studAchievementEntities.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.callRv.setVisibility(View.VISIBLE);
                    studAchievementsAdapter = new StudAchievementsAdapter(CocurricularStudAchActivity.this, studAchievementEntities, fromClass);
                    binding.callRv.setLayoutManager(new LinearLayoutManager(CocurricularStudAchActivity.this));
                    binding.callRv.setAdapter(studAchievementsAdapter);
                } else {
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.callRv.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAchievementRecord(StudAchievementEntity studAchievementEntity) {
        long x = viewModel.deleteAchievementsInfo(studAchievementEntity);
        if (x >= 0) {
            showBottomSheetSnackBar(getResources().getString(R.string.record_deleted));
        }
    }

    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }
}
