package com.cgg.twdinspection.engineering_works.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WorksMasterResponse {

    @SerializedName("work_details")
    @Expose
    private List<WorkDetail> workDetails = null;
    @SerializedName("Status_msg")
    @Expose
    private String statusMsg;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public List<WorkDetail> getWorkDetails() {
        return workDetails;
    }

    public void setWorkDetails(List<WorkDetail> workDetails) {
        this.workDetails = workDetails;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}