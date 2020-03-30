package com.cgg.twdinspection.gcc.source.suppliers.dr_godown;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DRGoDownMasterResponse {

    @SerializedName("supplier_info")
    @Expose
    private List<DrGodowns> godowns = null;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public List<DrGodowns> getGodowns() {
        return godowns;
    }

    public void setGodowns(List<DrGodowns> godowns) {
        this.godowns = godowns;
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

