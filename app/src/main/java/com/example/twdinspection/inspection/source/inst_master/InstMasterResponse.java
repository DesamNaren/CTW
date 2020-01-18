package com.example.twdinspection.inspection.source.inst_master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstMasterResponse {

    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("Institute_Info")
    @Expose
    private List<MasterInstituteInfo> instituteInfo = null;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<MasterInstituteInfo> getInstituteInfo() {
        return instituteInfo;
    }

    public void setInstituteInfo(List<MasterInstituteInfo> instituteInfo) {
        this.instituteInfo = instituteInfo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}


