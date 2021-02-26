package com.cgg.twdinspection.inspection.source.diet_issues;

import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DietMasterResponse {

    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("Institute_Info")
    @Expose
    private List<MasterDietListInfo> instituteInfo = null;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<MasterDietListInfo> getInstituteInfo() {
        return instituteInfo;
    }

    public void setInstituteInfo(List<MasterDietListInfo> instituteInfo) {
        this.instituteInfo = instituteInfo;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}


