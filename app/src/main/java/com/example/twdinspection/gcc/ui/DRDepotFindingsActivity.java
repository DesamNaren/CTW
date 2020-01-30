package com.example.twdinspection.gcc.ui;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityGccFindingsBinding;
import com.example.twdinspection.inspection.ui.LocBaseActivity;

public class DRDepotFindingsActivity extends LocBaseActivity {

    ActivityGccFindingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_dr_depot_findings);
        binding.header.headerTitle.setText(getResources().getString(R.string.ins_off_fin));
    }

}
