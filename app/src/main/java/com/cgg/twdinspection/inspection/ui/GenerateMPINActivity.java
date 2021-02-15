package com.cgg.twdinspection.inspection.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGenerateMpinBinding;
import com.google.android.material.snackbar.Snackbar;

public class GenerateMPINActivity extends AppCompatActivity {

    private Context context;
    private ActivityGenerateMpinBinding binding;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = GenerateMPINActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_mpin);

        try {
            SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            String userName = sharedPreferences.getString(AppConstants.OFFICER_NAME, "");
            binding.loggedIn.setText(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            binding.enterMPIN.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(binding.enterMPIN, InputMethodManager.SHOW_IMPLICIT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateMPIN();
            }
        });

        binding.enterMPIN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    String mPIN = s.toString();
                    if (mPIN.length() == 4) {
                        binding.confirmMPIN.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        binding.confirmMPIN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    String mPIN = s.toString();
                    if (mPIN.length() == 4) {
                        Utils.hideKeyboard(context, binding.btnSubmit);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void validateMPIN() {
        if (TextUtils.isEmpty(binding.enterMPIN.getText())) {
            binding.enterMPIN.setError(context.getString(R.string.please_enter_4_digit_mpin));
            binding.enterMPIN.requestFocus();
            return;

        } else if (binding.enterMPIN.getText().toString().length() < 4) {
            binding.enterMPIN.setError(context.getString(R.string.please_enter_4_digit_mpin));
            binding.enterMPIN.requestFocus();
            return;

        } else {
            binding.enterMPIN.setError(null);
        }

        if (TextUtils.isEmpty(binding.confirmMPIN.getText())) {
            binding.confirmMPIN.setError(context.getString(R.string.please_enter_4_digit_confirm_mpin));
            binding.confirmMPIN.requestFocus();
            return;

        } else if (binding.confirmMPIN.getText().toString().length() < 4) {
            binding.confirmMPIN.setError(context.getString(R.string.please_enter_4_digit_confirm_mpin));
            binding.confirmMPIN.requestFocus();
            return;

        } else {
            binding.confirmMPIN.setError(null);
        }

        VerifyMPIN(binding.enterMPIN.getText().toString(), binding.confirmMPIN.getText().toString());

    }

    private void VerifyMPIN(String mPin, String conMPin) {
        Utils.hideKeyboard(context, binding.btnSubmit);
        if (mPin.equals(conMPin)) {
            Utils.customMPINSuccessAlert(GenerateMPINActivity.this, getString(R.string.app_name),
                    getString(R.string.mpin_generated_successfully), mPin, editor);
        } else {
            Snackbar.make(binding.rootCl, R.string.confirm_mpin, Snackbar.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Utils.customCancelAlert(GenerateMPINActivity.this, getResources().getString(R.string.app_name),
                getString(R.string.cancel_otp_process), editor);
    }

}
