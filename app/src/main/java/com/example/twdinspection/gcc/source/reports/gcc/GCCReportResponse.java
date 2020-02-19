package com.example.twdinspection.gcc.source.reports.gcc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GCCReportResponse {

    @SerializedName("data")
    @Expose
    private List<ReportData> data = null;

    public List<ReportData> getData() {
        return data;
    }

    public void setData(List<ReportData> data) {
        this.data = data;
    }
}






