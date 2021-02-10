package com.cgg.twdinspection.gcc.source.suppliers.punit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PUnitMasterResponse {

    @SerializedName("supplier_info")
    @Expose
    private List<PUnits> pUnits = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public List<PUnits> getpUnits() {

        return pUnits;
    }

    public void setpUnits(List<PUnits> pUnits) {
        this.pUnits = pUnits;
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

