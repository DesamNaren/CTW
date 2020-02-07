package com.example.twdinspection.gcc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDivisionSelectoionBinding;
import com.example.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.example.twdinspection.inspection.viewmodel.DivisionSelectionViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class DivisionSelectionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityDivisionSelectoionBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_division_selectoion);
        context = DivisionSelectionActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.general_info));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewModel = new DivisionSelectionViewModel(getApplication());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }


        LiveData<List<String>> divisionLiveData= viewModel.getAllDivisions();
        divisionLiveData.observe(this, divisions -> {
            divisionLiveData.removeObservers(DivisionSelectionActivity.this);
            customProgressDialog.dismiss();
            if (divisions != null && divisions.size() > 0) {
                ArrayList<String> divisionNames = new ArrayList<>();
                divisionNames.add("-Select-");
                divisionNames.addAll(divisions);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, divisionNames
                );
                binding.spDivision.setAdapter(adapter);
            }
        });

        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    startActivity(new Intent(DivisionSelectionActivity.this, DRGodownActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private boolean validateFields() {
        if (TextUtils.isEmpty(selectedDivId)) {
            showSnackBar("Please select division");
            return false;
        }
        else if (TextUtils.isEmpty(selectedSocietyId)) {
            showSnackBar("Please select society");
            return false;
        }
        return true;
    }


    private void showSnackBar(String str) {
        Snackbar snackbar = Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.sp_division) {
            selectedSocietyId = "";
            selectedDivId = "";
            divisionsInfos = new ArrayList<>();
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString()).observe(DivisionSelectionActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        if (str != null) {
                            selectedDivId = str;
                            viewModel.getSocieties(selectedDivId).observe(DivisionSelectionActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    divisionsInfos.addAll(divisionsInfoList);
                                    if (divisionsInfos != null && divisionsInfos.size() > 0) {
                                        for (int i = 0; i < divisionsInfos.size(); i++) {
                                            if (!TextUtils.isEmpty(divisionsInfos.get(i).getSocietyName()))
                                                societies.add(divisionsInfos.get(i).getSocietyName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, societies);
                                        binding.spSociety.setAdapter(adapter);
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedDivId = "";
                binding.spSociety.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            if (position != 0) {
                selectedSocietyId = "";
                viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString()).observe(DivisionSelectionActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        if (str != null) {
                            selectedSocietyId = str;
                        }
                    }
                });
            } else {
                selectedSocietyId = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
