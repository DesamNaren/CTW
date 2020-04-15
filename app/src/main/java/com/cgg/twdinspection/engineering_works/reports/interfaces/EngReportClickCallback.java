package com.cgg.twdinspection.engineering_works.reports.interfaces;

import com.cgg.twdinspection.engineering_works.reports.source.ReportWorkDetails;
import com.cgg.twdinspection.inspection.reports.source.InspReportData;

public interface EngReportClickCallback {
    void onItemClick(ReportWorkDetails reportData);
}
