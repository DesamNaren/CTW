package com.example.twdinspection.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.twdinspection.databinding.ActivityMpinBinding;
import com.example.twdinspection.ui.DMVSelectionActivity;
import com.google.android.material.snackbar.Snackbar;

public class MPINViewModel extends ViewModel {

    private ActivityMpinBinding binding;
    private Context context;

    public MutableLiveData<String> mpin = new MutableLiveData<>();

    MPINViewModel(ActivityMpinBinding binding, Context context) {
        this.binding = binding;
        this.context = context;
    }

    public void onVerifyClick() {
        if (TextUtils.isEmpty(mpin.getValue())) {
            showError();
        } else if (mpin.getValue().length() != 4) {
            showError();
        }

        context.startActivity(new Intent(context, DMVSelectionActivity.class));
    }

    private void showError() {

        binding.pinview.setEnabled(false);
        Snackbar snackbar = Snackbar
                .make(binding.pinview, "MPIN Required", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.pinview.setEnabled(true);
                    }
                });
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }
}
