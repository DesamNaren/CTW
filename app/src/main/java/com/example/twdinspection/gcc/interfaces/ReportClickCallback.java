package com.example.twdinspection.gcc.interfaces;

import com.example.twdinspection.gcc.source.reports.ReportData;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;

public interface ReportClickCallback {
    void onItemClick(ReportData reportData);
}
