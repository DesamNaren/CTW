package com.cgg.twdinspection.gcc.source.suppliers.depot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DRDepotMasterResponse {

    @SerializedName("supplier_info")
    @Expose
    private List<DRDepots> DRDepots = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public List<DRDepots> getDRDepots() {
        return DRDepots;
    }

    public void setDRDepots(List<DRDepots> DRDepots) {
        this.DRDepots = DRDepots;
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

