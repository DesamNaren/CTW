package com.example.twdinspection.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityLoginCreBinding;
import com.example.twdinspection.source.EmployeeResponse;
import com.example.twdinspection.utils.AppConstants;
import com.example.twdinspection.viewmodel.LoginCustomViewModel;
import com.example.twdinspection.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends LocBaseActivity {
    ActivityLoginCreBinding binding;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_cre);
        LoginViewModel loginViewModel =
                ViewModelProviders.of(this,
                        new LoginCustomViewModel(binding, this)).get(LoginViewModel.class);
        binding.setViewModel(loginViewModel);


        try {
            editor = TWDApplication.get(this).getPreferences().edit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loginViewModel.geListLiveData().observe(this, new Observer<EmployeeResponse>() {
            @Override
            public void onChanged(EmployeeResponse employeeResponses) {
                if (employeeResponses != null && employeeResponses.getStatusCode() != null) {
                    if (Integer.valueOf(employeeResponses.getStatusCode()) == AppConstants.SUCCESS_CODE) {
                        editor.putString(AppConstants.OFFICER_ID, employeeResponses.getUserId());
                        editor.putString(AppConstants.OFFICER_NAME, employeeResponses.getUserName());
                        editor.putString(AppConstants.OFFICER_DES, employeeResponses.getDesignation());
                        editor.commit();

                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    } else {
                        Snackbar.make(binding.rlRoot, employeeResponses.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.etName.addTextChangedListener(new GenericTextWatcher(binding.etName));
        binding.etPwd.addTextChangedListener(new GenericTextWatcher(binding.etPwd));

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
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
