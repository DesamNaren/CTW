package com.example.twdinspection.inspection.source.reports;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InspReportResponse {

    @SerializedName("data")
    @Expose
    private List<InspReportData> data = null;

    public List<InspReportData> getData() {
        return data;
    }

    public void setData(List<InspReportData> data) {
        this.data = data;
    }
}






