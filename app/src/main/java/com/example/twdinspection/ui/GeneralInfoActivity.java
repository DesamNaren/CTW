package com.example.twdinspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityGeneralInfoBinding;
import com.example.twdinspection.utils.AppConstants;
import com.example.twdinspection.viewmodel.GeneralInfoViewModel;

public class GeneralInfoActivity extends BaseActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityGeneralInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_general_info, getResources().getString(R.string.general_info));

        GeneralInfoViewModel viewModel = new GeneralInfoViewModel(getApplication());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();


        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
            binding.manNameTv.setText(sharedPreferences.getString(AppConstants.MAN_NAME, ""));
            binding.instNameTv.setText(sharedPreferences.getString(AppConstants.INST_NAME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeneralInfoActivity.this, StudentsAttendance_2.class));
            }
        });
    }

}
