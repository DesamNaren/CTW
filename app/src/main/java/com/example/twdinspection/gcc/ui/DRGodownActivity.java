package com.example.twdinspection.gcc.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDrGodownBinding;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.google.gson.Gson;

public class DRGodownActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    ActivityDrGodownBinding binding;
    private DrGodowns drGodowns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dr_godown);
        binding.header.headerTitle.setText(getResources().getString(R.string.dr_godown));
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


        try{
            Gson gson = new Gson();
            String str = sharedPreferences.getString(AppConstants.DR_GODOWN_DATA,"");
            drGodowns = gson.fromJson(str, DrGodowns.class);
            if(drGodowns!=null) {
                binding.includeBasicLayout.divName.setText(drGodowns.getDivisionName());
                binding.includeBasicLayout.socName.setText(drGodowns.getSocietyName());
                binding.includeBasicLayout.drGodownName.setText(drGodowns.getGodownName());
                binding.includeBasicLayout.inchargeName.setText(drGodowns.getIncharge());
                binding.includeBasicLayout.dateTv.setText(Utils.getCurrentDateTimeDisplay());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
