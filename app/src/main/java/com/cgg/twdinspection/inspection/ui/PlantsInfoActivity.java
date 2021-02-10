package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.cgg.twdinspection.databinding.ActivityPlantsInfoBinding;
import com.cgg.twdinspection.inspection.adapter.PlantsInfoAdapter;
import com.cgg.twdinspection.inspection.interfaces.PlantsInfoInterface;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.PlantsInfoCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.PlantsInfoViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class PlantsInfoActivity extends AppCompatActivity implements PlantsInfoInterface {

    private ActivityPlantsInfoBinding binding;
    private PlantsInfoViewModel viewModel;
    private PlantsInfoAdapter plantsInfoAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InstMainViewModel instMainViewModel;
    private String fromClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, (R.layout.activity_plants_info));
        binding.appBarLayout.backBtn.setVisibility(View.GONE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.title_plant_info));
        sharedPreferences= TWDApplication.get(this).getPreferences();
        instMainViewModel=new InstMainViewModel(getApplication());

        viewModel = ViewModelProviders.of(this,
                new PlantsInfoCustomViewModel(binding, this, getApplication())).get(PlantsInfoViewModel.class);
        binding.setViewModel(viewModel);

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
                List<PlantsEntity> plantsEntities = reportData.getCoCurricularInfo().getPlantsEntities();
                if (plantsEntities != null && plantsEntities.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.callRv.setVisibility(View.VISIBLE);
                    plantsInfoAdapter = new PlantsInfoAdapter(PlantsInfoActivity.this, plantsEntities, fromClass);
                    binding.callRv.setLayoutManager(new LinearLayoutManager(PlantsInfoActivity.this));
                    binding.callRv.setAdapter(plantsInfoAdapter);
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
    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void deletePlantRecord(PlantsEntity plantsEntity) {
        long x = viewModel.deleteAchievementsInfo(plantsEntity);
        if (x >= 0) {
            showBottomSheetSnackBar(getResources().getString(R.string.record_deleted));
        }
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

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void calCoCir(){
        viewModel.getPlantsData().observe(PlantsInfoActivity.this, new Observer<List<PlantsEntity>>() {
            @Override
            public void onChanged(List<PlantsEntity> plantsEntities) {
                if (plantsEntities != null && plantsEntities.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.callRv.setVisibility(View.VISIBLE);
                    plantsInfoAdapter = new PlantsInfoAdapter(PlantsInfoActivity.this, plantsEntities, fromClass);
                    binding.callRv.setLayoutManager(new LinearLayoutManager(PlantsInfoActivity.this));
                    binding.callRv.setAdapter(plantsInfoAdapter);
                } else {
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.callRv.setVisibility(View.GONE);
                }
            }
        });
    }
}
