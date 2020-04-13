package com.cgg.twdinspection.gcc.reports.interfaces;

import com.cgg.twdinspection.engineering_works.reports.source.ReportWorkDetails;
import com.cgg.twdinspection.gcc.reports.source.ReportData;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportData;

public interface ReportClickCallback {
    void onItemClick(ReportData reportData);
    void onItemClick(SchemeReportData schemeReportData);
}
