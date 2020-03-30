package com.cgg.twdinspection.inspection.reports.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.inspection.reports.adapter.ReportsMenuSectionsAdapter;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.example.twdinspection.databinding.ReportsInstMenuActivityBinding;

import java.util.ArrayList;
import java.util.Arrays;


public class InstReportsMenuActivity extends LocBaseActivity {
    ReportsInstMenuActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.reports_inst_menu_activity);
        binding.actionBar.headerTitle.setText(getString(R.string.insp_reports));
        binding.actionBar.ivHome.setVisibility(View.GONE);
        binding.actionBar.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InstReportsMenuActivity.this, InspectionReportsDashboard.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });


        String[] stringArray = getResources().getStringArray(R.array.inst_sections);
        ArrayList<String> sections = new ArrayList<>(Arrays.asList(stringArray));
        sections.set(sections.size()-1, "View Photographs");
        if (sections.size() > 0) {
            ReportsMenuSectionsAdapter adapter = new ReportsMenuSectionsAdapter(InstReportsMenuActivity.this, sections);
            binding.rvMenu.setLayoutManager(new LinearLayoutManager(InstReportsMenuActivity.this));
            binding.rvMenu.setAdapter(adapter);
        }
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(InstReportsMenuActivity.this, InspectionReportsDashboard.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));;
    }
}

