package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.inspection.source.DiestIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.MedicalAndHealth.MedicalInfoEntity;
import com.example.twdinspection.inspection.viewmodel.DietIssuesCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.DietIsuuesViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalViewModel;

public class DietIssuesActivity extends AppCompatActivity {

    ActivityDietIssuesBinding binding;
    DietIsuuesViewModel dietIsuuesViewModel;
    DietIssuesEntity dietIssuesEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diet_issues);

        dietIsuuesViewModel = ViewModelProviders.of(DietIssuesActivity.this,
                new DietIssuesCustomViewModel(binding, this, getApplication())).get(DietIsuuesViewModel.class);
        binding.setViewModel(dietIsuuesViewModel);

        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Diet Issues");
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DietIssuesActivity.this, InfraActivity.class));
            }
        });
    }
}
