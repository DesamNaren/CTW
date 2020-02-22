package com.example.twdinspection.schemes.reports.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityReportSchemeDetailsActivtyBinding;
import com.example.twdinspection.schemes.reports.source.SchemeReportData;
import com.example.twdinspection.inspection.ui.DashboardActivity;
import com.google.gson.Gson;

public class SchemeReportDetailsActivity extends AppCompatActivity {

    ActivityReportSchemeDetailsActivtyBinding binding;
    private SharedPreferences sharedPreferences;
    private SchemeReportData schemeReportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_report_scheme_details_activty);
        binding.header.headerTitle.setText(getString(R.string.scheme_report_details));
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SchemeReportDetailsActivity.this, DashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        try {
            sharedPreferences = TWDApplication.get(SchemeReportDetailsActivity.this).getPreferences();
            Gson gson = new Gson();
            String data = sharedPreferences.getString(AppConstants.SCHEME_REP_DATA, "");
            schemeReportData = gson.fromJson(data, SchemeReportData.class);
            binding.setSchemeData(schemeReportData);
            binding.executePendingBindings();

        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        try {
            if(schemeReportData.getPhotos()!=null  && schemeReportData.getPhotos().size()>0) {

                if (schemeReportData.getPhotos().size()>=1 && schemeReportData.getPhotos().get(0) != null && schemeReportData.getPhotos().get(0) != null) {
                    binding.setImageUrl(schemeReportData.getPhotos().get(0).getFilePath());
                } else {
                    binding.ivCam1.setVisibility(View.GONE);
                }

                if (schemeReportData.getPhotos().size()>=2 && schemeReportData.getPhotos().get(1) != null && schemeReportData.getPhotos().get(1) != null) {
                    binding.setImageUrl2(schemeReportData.getPhotos().get(1).getFilePath());
                } else {
                    binding.ivCam2.setVisibility(View.GONE);
                }
            }else{
                binding.llPhotos.setVisibility(View.GONE);
            }

        }catch (Exception e){
            e.printStackTrace();

        }


    }

}
