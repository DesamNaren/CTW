package com.example.twdinspection.gcc.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityMfpGodownBinding;
import com.example.twdinspection.databinding.ActivityPUnitBinding;

public class PUnitActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    ActivityPUnitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_p_unit);
        binding.header.headerTitle.setText(getResources().getString(R.string.p_unit));

        binding.includeBasicLayout.drGodownNameTV.setText(getString(R.string.man_unit_name));
        binding.includeBasicLayout.socLL.setVisibility(View.VISIBLE);
        binding.includeBasicLayout.salesLL.setVisibility(View.VISIBLE);

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onBackPressed() {
        Utils.customExitAlert(this,
                getResources().getString(R.string.app_name),
                getString(R.string.exit_msg));
    }
}
