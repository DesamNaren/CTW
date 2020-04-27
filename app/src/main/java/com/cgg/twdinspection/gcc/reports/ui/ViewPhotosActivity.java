package com.cgg.twdinspection.gcc.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityViewPhotosBinding;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapter;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class ViewPhotosActivity extends AppCompatActivity {

    ActivityViewPhotosBinding binding;
    ViewPhotoAdapter adapter;
    SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_photos);
        binding.executePendingBindings();
        if (getIntent() != null)
            binding.header.headerTitle.setText(getIntent().getStringExtra(AppConstants.PHOTO_TITLE));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPhotosActivity.this, GCCReportsDashboard.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        sharedPreferences = TWDApplication.get(ViewPhotosActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);


        String jsonObject = gson.toJson(reportData.getPhotos());
        if (!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            adapter = new ViewPhotoAdapter(this, reportData.getPhotos());
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            binding.recyclerView.setAdapter(adapter);
        }


    }
}
