package com.example.twdinspection.gcc.source.suppliers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DRDepotMasterResponse {

    @SerializedName("supplier_info")
    @Expose
    private List<DRGodowns> DRGodowns = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public List<DRGodowns> getDRGodowns() {
        return DRGodowns;
    }

    public void setDRGodowns(List<DRGodowns> DRGodowns) {
        this.DRGodowns = DRGodowns;
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

