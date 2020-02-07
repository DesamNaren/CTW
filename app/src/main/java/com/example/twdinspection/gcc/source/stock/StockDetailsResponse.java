package com.example.twdinspection.gcc.source.stock;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StockDetailsResponse {

    @SerializedName("ap_commodities")
    @Expose
    private List<Object> apCommodities = null;
    @SerializedName("ecs_commodities")
    @Expose
    private List<EcsCommodity> ecsCommodities = null;
    @SerializedName("drs_commodities")
    @Expose
    private List<Object> drsCommodities = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("empties_commodities")
    @Expose
    private List<Object> emptiesCommodities = null;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;
    @SerializedName("mfp_commodities")
    @Expose
    private List<MfpCommodity> mfpCommodities = null;

    public List<Object> getApCommodities() {
        return apCommodities;
    }

    public void setApCommodities(List<Object> apCommodities) {
        this.apCommodities = apCommodities;
    }

    public List<EcsCommodity> getEcsCommodities() {
        return ecsCommodities;
    }

    public void setEcsCommodities(List<EcsCommodity> ecsCommodities) {
        this.ecsCommodities = ecsCommodities;
    }

    public List<Object> getDrsCommodities() {
        return drsCommodities;
    }

    public void setDrsCommodities(List<Object> drsCommodities) {
        this.drsCommodities = drsCommodities;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Object> getEmptiesCommodities() {
        return emptiesCommodities;
    }

    public void setEmptiesCommodities(List<Object> emptiesCommodities) {
        this.emptiesCommodities = emptiesCommodities;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<MfpCommodity> getMfpCommodities() {
        return mfpCommodities;
    }

    public void setMfpCommodities(List<MfpCommodity> mfpCommodities) {
        this.mfpCommodities = mfpCommodities;
    }

}
