package com.example.twdinspection.gcc;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityGccFindingsBinding;
import com.example.twdinspection.databinding.ActivityPUnitBinding;
import com.example.twdinspection.inspection.ui.LocBaseActivity;
import com.example.twdinspection.inspection.viewmodel.GenCommentsCustomViewModel;

public class GCCOfficerFindingsActivity extends LocBaseActivity {

    ActivityGccFindingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_gcc_findings);
        binding.header.headerTitle.setText(getResources().getString(R.string.ins_off_fin));
    }

}
