package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityCocurricularAchDetailsBinding;
import com.example.twdinspection.inspection.adapter.StudAchievementsAdapter;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.viewmodel.StudAchCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.StudAchViewModel;

import java.util.List;

public class CocurricularStudAchActivity extends BaseActivity {

    private ActivityCocurricularAchDetailsBinding binding;
    private StudAchViewModel viewModel;
    private StudAchievementsAdapter studAchievementsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_cocurricular_ach_details,"Student Achievements");

        viewModel = ViewModelProviders.of(this,
                new StudAchCustomViewModel(binding, this, getApplication())).get(StudAchViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getStudAchievementsData().observe(CocurricularStudAchActivity.this, new Observer<List<StudAchievementEntity>>() {
            @Override
            public void onChanged(List<StudAchievementEntity> studAchievementEntities) {
                if (studAchievementEntities != null && studAchievementEntities.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.callRv.setVisibility(View.VISIBLE);
                    studAchievementsAdapter = new StudAchievementsAdapter(CocurricularStudAchActivity.this, studAchievementEntities);
                    binding.callRv.setLayoutManager(new LinearLayoutManager(CocurricularStudAchActivity.this));
                    binding.callRv.setAdapter(studAchievementsAdapter);
                } else {
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.callRv.setVisibility(View.GONE);
                }
            }
        });

    }
}
