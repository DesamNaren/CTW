package com.example.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityMpinBinding;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.MPINCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.MPINViewModel;

public class MPINActivity extends AppCompatActivity {
    private String cacheDate, currentDate;
    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMpinBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mpin);
        MPINViewModel mpinViewModel = ViewModelProviders.of(this, new MPINCustomViewModel(binding, this)).get(MPINViewModel.class);
        instMainViewModel=new InstMainViewModel(getApplication());
        binding.setViewmodel(mpinViewModel);
        sharedPreferences= TWDApplication.get(this).getPreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {
                    instMainViewModel.deleteAllInspectionData();
                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
    }
}
