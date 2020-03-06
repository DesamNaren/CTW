package com.example.twdinspection.engineeringWorks.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityEngDashboardBinding;
import com.example.twdinspection.engineeringWorks.source.SectorsEntity;
import com.example.twdinspection.engineeringWorks.viewmodels.EngDashboardCustomViewModel;
import com.example.twdinspection.engineeringWorks.viewmodels.EngDashboardViewModel;
import com.example.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.example.twdinspection.gcc.ui.gcc.GCCSyncActivity;
import com.example.twdinspection.gcc.viewmodel.GCCPhotoCustomViewModel;
import com.example.twdinspection.gcc.viewmodel.GCCPhotoViewModel;
import com.example.twdinspection.inspection.ui.DashboardActivity;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class EngineeringDashboardActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityEngDashboardBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EngDashboardViewModel viewModel;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eng_dashboard);

        viewModel = ViewModelProviders.of(this,
                new EngDashboardCustomViewModel(this,getApplication())).get(EngDashboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        customProgressDialog = new CustomProgressDialog(this);



        binding.header.headerTitle.setText(getString(R.string.engineering_works));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EngineeringDashboardActivity.this, DashboardActivity.class));
            }
        });
        binding.cvProfile.requestFocus();
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EngineeringDashboardActivity.this, InspectionDetailsActivity.class));
            }
        });
    }

    @Override
    public void handleError(Throwable e, Context context) {

    }
}
