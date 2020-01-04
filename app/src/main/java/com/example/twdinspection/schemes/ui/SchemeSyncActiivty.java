package com.example.twdinspection.schemes.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemesDmvBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.schemes.source.FinancialYrsEntity;
import com.example.twdinspection.schemes.source.InspectionRemarksEntity;
import com.example.twdinspection.schemes.viewmodel.SchemesDMVViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SchemeSyncActiivty extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        schemesDMVActivityBinding = putContentView(R.layout.activity_schemes_dmv, getResources().getString(R.string.general_info));
//
//        viewModel = new SchemesDMVViewModel(getApplication());
//        schemesDMVActivityBinding.setViewModel(viewModel);
//        schemesDMVActivityBinding.executePendingBindings();
    }

}
