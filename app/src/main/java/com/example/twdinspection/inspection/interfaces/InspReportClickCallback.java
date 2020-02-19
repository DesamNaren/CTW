package com.example.twdinspection.inspection.interfaces;

import com.example.twdinspection.gcc.source.reports.ReportData;
import com.example.twdinspection.inspection.source.reports.InspReportData;

public interface InspReportClickCallback {
    void onItemClick(InspReportData reportData);
}
