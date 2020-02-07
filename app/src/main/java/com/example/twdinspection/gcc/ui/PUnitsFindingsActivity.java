package com.example.twdinspection.gcc.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityGccFindingsBinding;
import com.example.twdinspection.databinding.ActivityGccPunitFindingsBinding;
import com.example.twdinspection.inspection.ui.LocBaseActivity;

public class PUnitsFindingsActivity extends LocBaseActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ActivityGccPunitFindingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_punit_findings);
        binding.header.headerTitle.setText(getString(R.string.ins_off_fin));

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
