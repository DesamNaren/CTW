package com.example.twdinspection.schemes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.schemes.adapter.BenReportAdapter;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryRequest;
import com.example.twdinspection.schemes.viewmodel.BenReportViewModel;



public class BeneficiaryReportActivity extends AppCompatActivity {

    BenReportViewModel viewModel;
    BenReportAdapter adapter;
    ActivityBeneficiaryReportBinding beneficiaryReportBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beneficiaryReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_beneficiary_report);
        beneficiaryReportBinding.header.headerTitle.setText(getString(R.string.ben_report));

        viewModel = new BenReportViewModel(getApplication());
        beneficiaryReportBinding.setViewModel(viewModel);
        beneficiaryReportBinding.executePendingBindings();

        BeneficiaryRequest beneficiaryRequest = new BeneficiaryRequest();
        beneficiaryRequest.setDistId(1);
        beneficiaryRequest.setMandalId(3);
        beneficiaryRequest.setVillageId(2);
        StringBuilder str = new StringBuilder();
        str.append("'");
        beneficiaryRequest.setFinYearId(str+"2017-18"+str);

        viewModel.getBeneficiaryInfo(beneficiaryRequest).observe(this, beneficiaryDetails -> {
            adapter = new BenReportAdapter(this,beneficiaryDetails);
            beneficiaryReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            beneficiaryReportBinding.recyclerView.setAdapter(adapter);
        });
    }
}
