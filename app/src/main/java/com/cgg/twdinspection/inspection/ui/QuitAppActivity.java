package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;

public class QuitAppActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences= TWDApplication.get(this).getPreferences();
        instMainViewModel=new InstMainViewModel(getApplication());

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        finish();
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

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
