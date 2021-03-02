package com.cgg.twdinspection.inspection.ui;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityLoginCreBinding;
import com.cgg.twdinspection.inspection.source.login.LoginResponse;
import com.cgg.twdinspection.inspection.viewmodel.LoginCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.LoginViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends LocBaseActivity implements ErrorHandlerInterface {
    ActivityLoginCreBinding binding;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();

            String mpin = sharedPreferences.getString(AppConstants.MPIN, "");

            if (!TextUtils.isEmpty(mpin)) {
                startActivity(new Intent(LoginActivity.this, ValidateMPINActivity.class));
                finish();
            } else {
                callLoginProcess();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void callLoginProcess() {
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login_cre);
        LoginViewModel loginViewModel =
                ViewModelProviders.of(LoginActivity.this,
                        new LoginCustomViewModel(binding, LoginActivity.this)).get(LoginViewModel.class);

        binding.setViewModel(loginViewModel);

        loginViewModel.geListLiveData().observe(LoginActivity.this, new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse employeeResponses) {

                if (employeeResponses != null && employeeResponses.getStatusCode() != null) {
                    if (Integer.parseInt(employeeResponses.getStatusCode()) == AppConstants.SUCCESS_CODE) {

                        try {
                            AppConstants.DISTANCE = Float.parseFloat(employeeResponses.getRadius());
                            AppConstants.TIMER = employeeResponses.getTimer();
                        } catch (Exception e) {
                            AppConstants.TIMER = "48";
                        }finally {
                            editor.putString(AppConstants.OFFICER_ID, employeeResponses.getUserId());
                            editor.putString(AppConstants.OFFICER_NAME, employeeResponses.getUserName());
                            editor.putString(AppConstants.OFFICER_DES, employeeResponses.getDesignation());
                            editor.putString(AppConstants.OFF_PLACE_OF_WORK, employeeResponses.getPlaceOfWork());
                            editor.putLong(AppConstants.LOGIN_DIST_ID, employeeResponses.getDistrictId());
                            editor.putLong(AppConstants.LOGIN_DIV_ID, employeeResponses.getDivisionId());
                            editor.putString(AppConstants.LOGIN_DIST_NAME, employeeResponses.getDistrictName());
                            editor.putString(AppConstants.LOGIN_DIV_NAME, employeeResponses.getDivisionName());
                            editor.putLong(AppConstants.ROLEID, employeeResponses.getRoleId());
                            editor.commit();

                            startActivity(new Intent(LoginActivity.this, GenerateMPINActivity.class));
                            finish();
                        }

                    } else if (Integer.parseInt(employeeResponses.getStatusCode()) == AppConstants.FAILURE_CODE) {
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

        private final View view;

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

    private final BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
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
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
