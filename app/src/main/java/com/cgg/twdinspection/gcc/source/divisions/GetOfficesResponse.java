package com.cgg.twdinspection.gcc.source.divisions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOfficesResponse {

    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;
    @SerializedName("divisions")
    @Expose
    private List<DivisionsInfo> divisions = null;
    @SerializedName("statusCode")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<DivisionsInfo> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<DivisionsInfo> divisions) {
        this.divisions = divisions;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}