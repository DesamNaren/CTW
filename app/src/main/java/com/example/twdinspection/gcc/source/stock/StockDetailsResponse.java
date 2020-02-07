package com.example.twdinspection.gcc.source.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockDetailsResponse {

    @SerializedName("ap_commodities")
    @Expose
    private List<CommonCommodity> apCommodities = null;
    @SerializedName("ecs_commodities")
    @Expose
    private List<CommonCommodity> ecsCommodities = null;
    @SerializedName("drs_commodities")
    @Expose
    private List<CommonCommodity> drsCommodities = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("empties_commodities")
    @Expose
    private List<CommonCommodity> emptiesCommodities = null;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("mfp_commodities")
    @Expose
    private List<CommonCommodity> mfpCommodities = null;

    public List<CommonCommodity> getApCommodities() {
        return apCommodities;
    }

    public void setApCommodities(List<CommonCommodity> apCommodities) {
        this.apCommodities = apCommodities;
    }

    public List<CommonCommodity> getEcsCommodities() {
        return ecsCommodities;
    }

    public void setEcsCommodities(List<CommonCommodity> ecsCommodities) {
        this.ecsCommodities = ecsCommodities;
    }

    public List<CommonCommodity> getDrsCommodities() {
        return drsCommodities;
    }

    public void setDrsCommodities(List<CommonCommodity> drsCommodities) {
        this.drsCommodities = drsCommodities;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<CommonCommodity> getEmptiesCommodities() {
        return emptiesCommodities;
    }

    public void setEmptiesCommodities(List<CommonCommodity> emptiesCommodities) {
        this.emptiesCommodities = emptiesCommodities;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<CommonCommodity> getMfpCommodities() {
        return mfpCommodities;
    }

    public void setMfpCommodities(List<CommonCommodity> mfpCommodities) {
        this.mfpCommodities = mfpCommodities;
    }
}
