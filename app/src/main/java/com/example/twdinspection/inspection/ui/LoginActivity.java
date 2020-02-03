package com.example.twdinspection.inspection.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityLoginCreBinding;
import com.example.twdinspection.inspection.source.EmployeeResponse;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.LoginCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.LoginViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class LoginActivity extends LocBaseActivity implements ErrorHandlerInterface {
    ActivityLoginCreBinding binding;
    private SharedPreferences.Editor editor;
    InstMainViewModel instMainViewModel;
    private String cacheDate, currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instMainViewModel = new InstMainViewModel(getApplication());

        instMainViewModel.getAllSections().observe(this, new Observer<List<InstMenuInfoEntity>>() {
            @Override
            public void onChanged(List<InstMenuInfoEntity> instMenuInfoEntities) {
                if (instMenuInfoEntities != null && instMenuInfoEntities.size() > 0) {

                    boolean flag = false;
                    for (int i = 0; i < instMenuInfoEntities.size(); i++) {
                        if (instMenuInfoEntities.get(i).getFlag_completed() == 1) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        startActivity(new Intent(LoginActivity.this, InstMenuMainActivity.class));
                        finish();
                    } else {
                        callLoginProcess();
                    }
                } else {
                    callLoginProcess();
                }
            }
        });
    }

    private void callLoginProcess() {
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login_cre);
        LoginViewModel loginViewModel =
                ViewModelProviders.of(LoginActivity.this,
                        new LoginCustomViewModel(binding, LoginActivity.this)).get(LoginViewModel.class);

        binding.setViewModel(loginViewModel);

        try {
            editor = TWDApplication.get(LoginActivity.this).getPreferences().edit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        loginViewModel.geListLiveData().observe(LoginActivity.this, new Observer<EmployeeResponse>() {
            @Override
            public void onChanged(EmployeeResponse employeeResponses) {

                if (employeeResponses != null && employeeResponses.getStatusCode() != null) {
                    if (Integer.valueOf(employeeResponses.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                        editor.putString(AppConstants.OFFICER_ID, employeeResponses.getUserId());
                        editor.putString(AppConstants.OFFICER_NAME, employeeResponses.getUserName());
                        editor.putString(AppConstants.OFFICER_DES, employeeResponses.getDesignation());
                        editor.commit();

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    } else if (Integer.valueOf(employeeResponses.getStatusCode()) == AppConstants.FAILURE_CODE) {
                        Snackbar.make(binding.rlRoot, employeeResponses.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                    } else {
                        callSnackBar(getString(R.string.something));
                    }
                } else {
                    callSnackBar(getString(R.string.something));

                }
            }
        });

        binding.etName.addTextChangedListener(new GenericTextWatcher(binding.etName));
        binding.etPwd.addTextChangedListener(new GenericTextWatcher(binding.etPwd));
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.rlRoot, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.et_name:
                    if (text.length() != 0)
                        binding.tName.setError(null);
                    break;
                case R.id.et_pwd:
                    if (text.length() != 0)
                        binding.tPwd.setError(null);
                    break;

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mGpsSwitchStateReceiver);
    }

    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent != null && intent.getAction() != null &&
                        intent.getAction().matches(LocationManager.MODE_CHANGED_ACTION)) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
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
        if (mRequestingLocationUpdates) {
            stopLocationUpdates();
        }
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
        super.onPause();
    }
}
