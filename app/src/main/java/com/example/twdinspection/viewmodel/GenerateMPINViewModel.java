package com.example.twdinspection.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityGenerateMpinBinding;
import com.example.twdinspection.ui.MPINActivity;
import com.google.android.material.snackbar.Snackbar;

public class GenerateMPINViewModel extends ViewModel {

    private ActivityGenerateMpinBinding binding;
    private Context context;

    public MutableLiveData<String> mpin = new MutableLiveData<>();
    public MutableLiveData<String> cmfMPin = new MutableLiveData<>();

    GenerateMPINViewModel(ActivityGenerateMpinBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    public void onVerifyClick() {
        if (TextUtils.isEmpty(mpin.getValue())) {
            showError("Enter 4 digit mPIN");
        }
        else if (mpin.getValue().length()!=4) {
            showError("Enter 4 digit mPIN");
        }

        else if (TextUtils.isEmpty(cmfMPin.getValue())) {
            showError("Enter 4 digit confirm mPIN");
        }
        else if (cmfMPin.getValue().length()!=4) {
            showError("Enter 4 digit confirm mPIN");
        }

        else if(!mpin.getValue().equals(cmfMPin.getValue())){
            showError("mPIN's mismatched");
        }else {
            context.startActivity(new Intent(context, MPINActivity.class));
        }

    }

    private void showError(String msg){
        binding.pinview.setEnabled(false);
        binding.cnfPinview.setEnabled(false);
        Snackbar snackbar = Snackbar
                .make(binding.pinview, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.pinview.setEnabled(true);
                        binding.cnfPinview.setEnabled(true);
                    }
                });
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }
}
