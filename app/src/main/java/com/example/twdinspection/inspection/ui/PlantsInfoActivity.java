package com.example.twdinspection.inspection.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityPlantsInfoBinding;
import com.example.twdinspection.inspection.adapter.PlantsInfoAdapter;
import com.example.twdinspection.inspection.adapter.StudAchievementsAdapter;
import com.example.twdinspection.inspection.interfaces.PlantsInfoInterface;
import com.example.twdinspection.inspection.interfaces.StudAchievementsInterface;
import com.example.twdinspection.inspection.source.cocurriularActivities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.viewmodel.PlantsInfoCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.PlantsInfoViewModel;
import com.example.twdinspection.inspection.viewmodel.StudAchCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.StudAchViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class PlantsInfoActivity extends BaseActivity implements PlantsInfoInterface {

    private ActivityPlantsInfoBinding binding;
    private PlantsInfoViewModel viewModel;
    private PlantsInfoAdapter plantsInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_plants_info,getResources().getString(R.string.title_plant_info));

        viewModel = ViewModelProviders.of(this,
                new PlantsInfoCustomViewModel(binding, this, getApplication())).get(PlantsInfoViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getPlantsData().observe(PlantsInfoActivity.this, new Observer<List<PlantsEntity>>() {
            @Override
            public void onChanged(List<PlantsEntity> plantsEntities) {
                if (plantsEntities != null && plantsEntities.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.callRv.setVisibility(View.VISIBLE);
                    plantsInfoAdapter = new PlantsInfoAdapter(PlantsInfoActivity.this, plantsEntities);
                    binding.callRv.setLayoutManager(new LinearLayoutManager(PlantsInfoActivity.this));
                    binding.callRv.setAdapter(plantsInfoAdapter);
                } else {
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.callRv.setVisibility(View.GONE);
                }
            }
        });

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
}
