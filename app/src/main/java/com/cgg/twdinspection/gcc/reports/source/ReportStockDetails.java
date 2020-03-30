package com.cgg.twdinspection.gcc.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportStockDetails {


    @SerializedName("total_system_value")
    @Expose
    private String totalSystemValue;
    @SerializedName("total_physical_value")
    @Expose
    private String totalPhysicalValue;
    @SerializedName("essential_commodities")
    @Expose
    private List<ReportSubmitReqCommodities> essentialCommodities = null;
    @SerializedName("daily_requirements")
    @Expose
    private List<ReportSubmitReqCommodities> dailyRequirements = null;
    @SerializedName("empties")
    @Expose
    private List<ReportSubmitReqCommodities> empties = null;
    @SerializedName("mfp_commodities")
    @Expose
    private List<ReportSubmitReqCommodities> mfpCommodities = null;
    @SerializedName("processing_units")
    @Expose
    private List<ReportSubmitReqCommodities> processingUnits = null;

    public String getTotalSystemValue() {
        return totalSystemValue;
    }

    public void setTotalSystemValue(String totalSystemValue) {
        this.totalSystemValue = totalSystemValue;
    }

    public String getTotalPhysicalValue() {
        return totalPhysicalValue;
    }

    public void setTotalPhysicalValue(String totalPhysicalValue) {
        this.totalPhysicalValue = totalPhysicalValue;
    }

    public List<ReportSubmitReqCommodities> getEssentialCommodities() {
        return essentialCommodities;
    }

    public void setEssentialCommodities(List<ReportSubmitReqCommodities> essentialCommodities) {
        this.essentialCommodities = essentialCommodities;
    }

    public List<ReportSubmitReqCommodities> getDailyRequirements() {
        return dailyRequirements;
    }

    public void setDailyRequirements(List<ReportSubmitReqCommodities> dailyRequirements) {
        this.dailyRequirements = dailyRequirements;
    }

    public List<ReportSubmitReqCommodities> getEmpties() {
        return empties;
    }

    public void setEmpties(List<ReportSubmitReqCommodities> empties) {
        this.empties = empties;
    }

    public List<ReportSubmitReqCommodities> getMfpCommodities() {
        return mfpCommodities;
    }

    public void setMfpCommodities(List<ReportSubmitReqCommodities> mfpCommodities) {
        this.mfpCommodities = mfpCommodities;
    }

    public List<ReportSubmitReqCommodities> getProcessingUnits() {
        return processingUnits;
    }

    public void setProcessingUnits(List<ReportSubmitReqCommodities> processingUnits) {
        this.processingUnits = processingUnits;
    }
}
