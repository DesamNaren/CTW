package com.example.twdinspection.gcc.source.reports.scheme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchemeReportResponse {

    @SerializedName("data")
    @Expose
    private List<SchemeReportData> schemeReportData = null;

    public List<SchemeReportData> getSchemeReportData() {
        return schemeReportData;
    }

    public void setSchemeReportData(List<SchemeReportData> schemeReportData) {
        this.schemeReportData = schemeReportData;
    }
}
