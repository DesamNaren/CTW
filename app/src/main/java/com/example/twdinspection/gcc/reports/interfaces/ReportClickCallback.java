package com.example.twdinspection.gcc.reports.interfaces;

import com.example.twdinspection.gcc.reports.source.ReportData;
import com.example.twdinspection.schemes.reports.source.SchemeReportData;

public interface ReportClickCallback {
    void onItemClick(ReportData reportData);
    void onItemClick(SchemeReportData schemeReportData);
}
