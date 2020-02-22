package com.example.twdinspection.gcc.source.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class StockSubmitRequest {


    @SerializedName("total_system_value")
    @Expose
    private Double totalSystemValue;
    @SerializedName("total_physical_value")
    @Expose
    private Double totalPhysicalValue;
    @SerializedName("essential_commodities")
    @Expose
    private List<SubmitReqCommodities> essentialCommodities = null;
    @SerializedName("daily_requirements")
    @Expose
    private List<SubmitReqCommodities> dailyRequirements = null;
    @SerializedName("empties")
    @Expose
    private List<SubmitReqCommodities> empties = null;
    @SerializedName("mfp_commodities")
    @Expose
    private List<SubmitReqCommodities> mfpCommodities = null;
    @SerializedName("processing_unit")
    @Expose
    private List<SubmitReqCommodities> processingUnits = null;

    public Double getTotalPhysicalValue() {
        return totalPhysicalValue;
    }

    public void setTotalPhysicalValue(Double totalPhysicalValue) {
        this.totalPhysicalValue = totalPhysicalValue;
    }

    public Double getTotalSystemValue() {
        return totalSystemValue;
    }

    public void setTotalSystemValue(Double totalSystemValue) {
        this.totalSystemValue = totalSystemValue;
    }

    public List<SubmitReqCommodities> getEssentialCommodities() {
        return essentialCommodities;
    }

    public void setEssentialCommodities(List<SubmitReqCommodities> essentialCommodities) {
        this.essentialCommodities = essentialCommodities;
    }

    public List<SubmitReqCommodities> getDailyRequirements() {
        return dailyRequirements;
    }

    public void setDailyRequirements(List<SubmitReqCommodities> dailyRequirements) {
        this.dailyRequirements = dailyRequirements;
    }

    public List<SubmitReqCommodities> getEmpties() {
        return empties;
    }

    public void setEmpties(List<SubmitReqCommodities> empties) {
        this.empties = empties;
    }

    public List<SubmitReqCommodities> getMfpCommodities() {
        return mfpCommodities;
    }

    public void setMfpCommodities(List<SubmitReqCommodities> mfpCommodities) {
        this.mfpCommodities = mfpCommodities;
    }

    public List<SubmitReqCommodities> getProcessingUnits() {
        return processingUnits;
    }

    public void setProcessingUnits(List<SubmitReqCommodities> processingUnits) {
        this.processingUnits = processingUnits;
    }
}