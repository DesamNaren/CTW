package com.example.twdinspection.engineering_works.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityEngDashboardBinding;
import com.example.twdinspection.engineering_works.source.SectorsEntity;
import com.example.twdinspection.engineering_works.source.SectorsResponse;
import com.example.twdinspection.engineering_works.source.WorksMasterResponse;
import com.example.twdinspection.engineering_works.viewmodels.EngDashboardCustomViewModel;
import com.example.twdinspection.engineering_works.viewmodels.EngDashboardViewModel;
import com.example.twdinspection.inspection.ui.DashboardActivity;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.List;

public class EngineeringDashboardActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityEngDashboardBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EngDashboardViewModel viewModel;
    CustomProgressDialog customProgressDialog;
    int sectorsCnt;
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
                if(TextUtils.isEmpty(binding.etEngId.getText().toString())){
                    callSnackBar("Please enter engineering work ID");
                }else{
                    startActivity(new Intent(EngineeringDashboardActivity.this, InspectionDetailsActivity.class));
//                    viewModel.getWorksMaster().observe(EngineeringDashboardActivity.this, new Observer<WorksMasterResponse>() {
//                        @Override
//                        public void onChanged(WorksMasterResponse worksMasterResponses) {
//                            if(worksMasterResponses!=null){
//                                Gson gson=new Gson();
//                                String worksMaster =gson.toJson(worksMasterResponses);
//                                editor.putString(AppConstants.ENGWORKSMASTER,worksMaster);
//                                editor.commit();
//                                startActivity(new Intent(EngineeringDashboardActivity.this, InspectionDetailsActivity.class));
//                            }
//                        }
//                    });
                }
            }
        });
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }
    @Override
    public void handleError(Throwable e, Context context) {

    }
}
