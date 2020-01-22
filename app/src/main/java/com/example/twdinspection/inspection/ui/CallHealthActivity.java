package com.example.twdinspection.inspection.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

public class CallHealthActivity extends AppCompatActivity implements MedicalInterface {

    private ActivityCallHealthBinding binding;
    private CallHealthViewModel viewModel;
    private CallHealthAdapter callHealthAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, (R.layout.activity_call_health));
        binding.appBarLayout.backBtn.setVisibility(View.VISIBLE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.call_health));

        binding.appBarLayout.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
