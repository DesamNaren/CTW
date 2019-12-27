package com.example.twdinspection.schemes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityBeneficiaryReportBinding;
import com.example.twdinspection.databinding.ActivityStaffAttBinding;
import com.example.twdinspection.inspection.adapter.StudentsAttAdapter;
import com.example.twdinspection.inspection.ui.StudentsAttendance_2;
import com.example.twdinspection.schemes.adapter.BenReportAdapter;
import com.example.twdinspection.schemes.source.BeneficiaryReport;

import java.util.ArrayList;
import java.util.List;

public class BeneficiaryReportActivity extends AppCompatActivity {

    BenReportAdapter adapter;
    ActivityBeneficiaryReportBinding beneficiaryReportBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beneficiaryReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_beneficiary_report);
        adapter = new BenReportAdapter(this, getList());
        beneficiaryReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        beneficiaryReportBinding.recyclerView.setAdapter(adapter);
    }

    private List<BeneficiaryReport> getList() {
        ArrayList<BeneficiaryReport> list=new ArrayList<>();
        for(int i=1;i<10;i++){
            BeneficiaryReport beneficiaryReport=new BeneficiaryReport();
            beneficiaryReport.setBen_id(i);
            list.add(beneficiaryReport);
        }

        return list;
    }
}
