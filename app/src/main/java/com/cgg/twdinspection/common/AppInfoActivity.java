package com.cgg.twdinspection.common;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityAppInfoBinding;


public class AppInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAppInfoBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_app_info);

        binding.header.ivHome.setVisibility(View.VISIBLE);
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.header.headerTitle.setText(getString(R.string.app_info));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvVersion.setText(Utils.getVersionName(this));
        binding.tvUpdatedOn.setText(AppConstants.VERSION_DATE);

    }
}