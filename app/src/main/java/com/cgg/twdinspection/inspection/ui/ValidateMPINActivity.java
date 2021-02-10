package com.cgg.twdinspection.inspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityValidateMpinBinding;


public class ValidateMPINActivity extends AppCompatActivity {

    private Context context;
    private ActivityValidateMpinBinding binding;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String mpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ValidateMPINActivity.this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_validate_mpin);
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();

            binding.loggedIn.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            mpin = sharedPreferences.getString(AppConstants.MPIN, "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSharedpref();
            }
        });
        binding.tvNotYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSharedpref();
            }
        });

        binding.firstPinView.addTextChangedListener(new TextWatcher() {
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

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateMPIN();
            }
        });
    }

    private void clearSharedpref() {
        editor.clear();
        editor.commit();

        Intent newIntent = new Intent(context, LoginActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        binding.firstPinView.requestFocus();
        binding.firstPinView.setCursorVisible(true);
        Utils.showKeyboard(context);
    }

    public void validateMPIN() {
        if (binding.firstPinView.getText() == null ||
                TextUtils.isEmpty(binding.firstPinView.getText().toString())) {
            binding.firstPinView.setError(context.getString(R.string.enter_4_digit_mpin));
            binding.firstPinView.requestFocus();
        } else if (binding.firstPinView.getText().toString().length() < 4) {
            binding.firstPinView.setError(context.getString(R.string.please_enter_4_digit_mpin));
            binding.firstPinView.requestFocus();
        } else {
            binding.firstPinView.setError(null);
            if (!TextUtils.isEmpty(binding.firstPinView.getText().toString()) &&
                    binding.firstPinView.getText().toString().equalsIgnoreCase(mpin)) {
                startActivity(new Intent(ValidateMPINActivity.this, DashboardMenuActivity.class));
                finish();

            } else {
                Toast.makeText(context, getString(R.string.enter_valid_mpin), Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onBackPressed() {
        Intent newIntent = new Intent(ValidateMPINActivity.this, QuitAppActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
        finish();
    }
}
