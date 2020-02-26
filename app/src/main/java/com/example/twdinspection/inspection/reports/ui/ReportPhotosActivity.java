package com.example.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityViewPhotosBinding;
import com.example.twdinspection.gcc.reports.adapter.ViewPhotoAdapter;
import com.example.twdinspection.gcc.reports.source.ReportData;
import com.google.gson.Gson;

public class ReportPhotosActivity extends AppCompatActivity {

    ActivityViewPhotosBinding binding;
    ViewPhotoAdapter adapter;
    SharedPreferences sharedPreferences;
    ReportData reportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_photos);
        binding.executePendingBindings();
        binding.header.headerTitle.setText(getString(R.string.title_view_photos));
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportPhotosActivity.this, InstReportsMenuActivity.class));
            }
        });


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferences= TWDApplication.get(ReportPhotosActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.INSP_REP_DATA, "");
        reportData = gson.fromJson(data, ReportData.class);


        adapter = new ViewPhotoAdapter(this, reportData.getPhotos());
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerView.setAdapter(adapter);
    }
}
