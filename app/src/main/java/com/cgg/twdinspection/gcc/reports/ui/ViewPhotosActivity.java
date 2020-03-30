package com.cgg.twdinspection.gcc.reports.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapter;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.example.twdinspection.databinding.ActivityViewPhotosBinding;
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
        binding.header.headerTitle.setText(getString(R.string.gcc_reports));
        binding.header.ivHome.setVisibility(View.GONE);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferences= TWDApplication.get(ViewPhotosActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);


        String jsonObject  = gson.toJson(reportData.getPhotos());
        if(!TextUtils.isEmpty(jsonObject) && !jsonObject.equalsIgnoreCase("{}")) {
            adapter = new ViewPhotoAdapter(this, reportData.getPhotos());
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            binding.recyclerView.setAdapter(adapter);
        }


    }
}
