package com.cgg.twdinspection.engineering_works.reports.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityViewEngPhotosBinding;
import com.cgg.twdinspection.engineering_works.reports.source.ReportWorkDetails;
import com.cgg.twdinspection.gcc.reports.adapter.ViewPhotoAdapter;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.inspection.reports.ui.InstReportsMenuActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportPhotosActivity;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.google.gson.Gson;

public class ViewEngPhotosActivity extends AppCompatActivity {

    ActivityViewEngPhotosBinding binding;
    SharedPreferences sharedPreferences;
    ReportWorkDetails reportData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_eng_photos);
        binding.executePendingBindings();
        binding.header.headerTitle.setText("WORKS PHOTOS");
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewEngPhotosActivity.this, DashboardMenuActivity.class));
            }
        });

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreferences= TWDApplication.get(ViewEngPhotosActivity.this).getPreferences();
        Gson gson = new Gson();
        String data = sharedPreferences.getString(AppConstants.ENG_REPORT_DATA, "");
        reportData = gson.fromJson(data, ReportWorkDetails.class);
        if(reportData.getPhotos().size()>0){
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.tvEmpty.setVisibility(View.GONE);
            ViewPhotoAdapter adapter = new ViewPhotoAdapter(ViewEngPhotosActivity.this, reportData.getPhotos());
            binding.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            binding.recyclerView.setAdapter(adapter);
        }else{
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        }

    }
}
