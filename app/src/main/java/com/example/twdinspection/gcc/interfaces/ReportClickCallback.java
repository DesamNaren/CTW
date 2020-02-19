package com.example.twdinspection.gcc.interfaces;

import com.example.twdinspection.gcc.source.reports.gcc.ReportData;
import com.example.twdinspection.gcc.source.reports.scheme.SchemeReportData;

public interface ReportClickCallback {
    void onItemClick(ReportData reportData);
    void onItemClick(SchemeReportData schemeReportData);
}
