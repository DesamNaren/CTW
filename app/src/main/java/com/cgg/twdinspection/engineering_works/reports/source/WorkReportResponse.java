package com.cgg.twdinspection.engineering_works.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorkReportResponse {

    @SerializedName("data")
    @Expose
    private List<ReportWorkDetails> data = null;

    public List<ReportWorkDetails> getData() {
        return data;
    }

    public void setData(List<ReportWorkDetails> data) {
        this.data = data;
    }

}
