package com.cgg.twdinspection.gcc.source.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockDetailsResponse {

    @SerializedName("ap_commodities")
    @Expose
    private List<CommonCommodity> processing_units = null;
    @SerializedName("ecs_commodities")
    @Expose
    private List<CommonCommodity> essential_commodities = null;
    @SerializedName("drs_commodities")
    @Expose
    private List<CommonCommodity> dialy_requirements = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("empties_commodities")
    @Expose
    private List<CommonCommodity> empties = null;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("mfp_commodities")
    @Expose
    private List<CommonCommodity> mfp_commodities = null;

    public List<CommonCommodity> getProcessing_units() {
        return processing_units;
    }

    public void setProcessing_units(List<CommonCommodity> processing_units) {
        this.processing_units = processing_units;
    }

    public List<CommonCommodity> getEssential_commodities() {
        return essential_commodities;
    }

    public void setEssential_commodities(List<CommonCommodity> essential_commodities) {
        this.essential_commodities = essential_commodities;
    }

    public List<CommonCommodity> getDialy_requirements() {
        return dialy_requirements;
    }

    public void setDialy_requirements(List<CommonCommodity> dialy_requirements) {
        this.dialy_requirements = dialy_requirements;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<CommonCommodity> getEmpties() {
        return empties;
    }

    public void setEmpties(List<CommonCommodity> empties) {
        this.empties = empties;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<CommonCommodity> getMfp_commodities() {
        return mfp_commodities;
    }

    public void setMfp_commodities(List<CommonCommodity> mfp_commodities) {
        this.mfp_commodities = mfp_commodities;
    }
}
