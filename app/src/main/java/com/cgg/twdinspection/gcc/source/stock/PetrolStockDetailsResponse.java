package com.cgg.twdinspection.gcc.source.stock;


import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PetrolStockDetailsResponse {

    @SerializedName("lpgppdata")
    @Expose
    private List<CommonCommodity> commonCommodities = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public List<CommonCommodity> getCommonCommodities() {
        return commonCommodities;
    }

    public void setCommonCommodities(List<CommonCommodity> commonCommodities) {
        this.commonCommodities = commonCommodities;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
