package com.example.twdinspection.inspection.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityCallHealthBinding;
import com.example.twdinspection.inspection.adapter.CallHealthAdapter;
import com.example.twdinspection.inspection.interfaces.MedicalInterface;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.example.twdinspection.inspection.viewmodel.CallHealthCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.CallHealthViewModel;
import com.example.twdinspection.inspection.viewmodel.DMVDetailsViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CallHealthActivity extends BaseActivity implements MedicalInterface {

    private ActivityCallHealthBinding binding;
    private CallHealthViewModel viewModel;
    private CallHealthAdapter callHealthAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_call_health, getResources().getString(R.string.call_health));

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
}
