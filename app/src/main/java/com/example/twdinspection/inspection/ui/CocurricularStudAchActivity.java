package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityCocurricularAchDetailsBinding;
import com.example.twdinspection.inspection.adapter.StudAchievementsAdapter;
import com.example.twdinspection.inspection.interfaces.StudAchievementsInterface;
import com.example.twdinspection.inspection.reports.source.InspReportData;
import com.example.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.StudAchCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.StudAchViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class CocurricularStudAchActivity extends AppCompatActivity implements StudAchievementsInterface {

    private ActivityCocurricularAchDetailsBinding binding;
    private StudAchViewModel viewModel;
    private StudAchievementsAdapter studAchievementsAdapter;
    private String cacheDate, currentDate;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InstMainViewModel instMainViewModel;
    private String fromClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, (R.layout.activity_cocurricular_ach_details));
        binding.appBarLayout.backBtn.setVisibility(View.GONE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.stu_ach));
        sharedPreferences = TWDApplication.get(this).getPreferences();
        viewModel = ViewModelProviders.of(this,
                new StudAchCustomViewModel(binding, this, getApplication())).get(StudAchViewModel.class);
        binding.setViewModel(viewModel);
        instMainViewModel=new InstMainViewModel(getApplication());

        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        Gson gson = new Gson();

        fromClass = getIntent().getStringExtra(AppConstants.FROM_CLASS);
        if(fromClass!=null && fromClass.equalsIgnoreCase(AppConstants.COCAR)){
            calCoCir();
        }else if(fromClass!=null && fromClass.equalsIgnoreCase(AppConstants.REPORT_COCAR)){
            String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
            InspReportData reportData = gson.fromJson(data, InspReportData.class);
            if(reportData!=null && reportData.getCoCurricularInfo()!=null) {
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
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {

                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re), instMainViewModel);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
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
