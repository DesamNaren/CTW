package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityCallHealthBinding;
import com.cgg.twdinspection.inspection.adapter.CallHealthAdapter;
import com.cgg.twdinspection.inspection.interfaces.MedicalInterface;
import com.cgg.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.cgg.twdinspection.inspection.viewmodel.CallHealthCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.CallHealthViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CallHealthActivity extends AppCompatActivity implements MedicalInterface {

    private ActivityCallHealthBinding binding;
    private CallHealthViewModel viewModel;
    private CallHealthAdapter callHealthAdapter;
    private String cacheDate, currentDate;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, (R.layout.activity_call_health));
        binding.appBarLayout.backBtn.setVisibility(View.VISIBLE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.call_health));
        instMainViewModel=new InstMainViewModel(getApplication());

        sharedPreferences= TWDApplication.get(this).getPreferences();
        editor= sharedPreferences.edit();
        binding.appBarLayout.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel = ViewModelProviders.of(CallHealthActivity.this,
                new CallHealthCustomViewModel( this)).get(CallHealthViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getCallHealthData().observe(CallHealthActivity.this, new Observer<List<CallHealthInfoEntity>>() {
            @Override
            public void onChanged(List<CallHealthInfoEntity> callHealthInfoEntities) {
                if (callHealthInfoEntities != null && callHealthInfoEntities.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.callRv.setVisibility(View.VISIBLE);
                    callHealthAdapter = new CallHealthAdapter(CallHealthActivity.this, callHealthInfoEntities);
                    binding.callRv.setLayoutManager(new LinearLayoutManager(CallHealthActivity.this));
                    binding.callRv.setAdapter(callHealthAdapter);
                } else {
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.callRv.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void deleteCallRecord(CallHealthInfoEntity callHealthInfoEntity) {
        long x = viewModel.deleteCallInfo(callHealthInfoEntity);
        if (x >= 0) {
            showBottomSheetSnackBar(getResources().getString(R.string.record_deleted));
        }
    }

    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
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

                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re), instMainViewModel);
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
