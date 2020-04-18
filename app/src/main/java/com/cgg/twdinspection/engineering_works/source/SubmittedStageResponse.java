package com.cgg.twdinspection.engineering_works.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmittedStageResponse {

    @SerializedName("Status_Code ")
    @Expose
    private Integer statusCode;
    @SerializedName("Status_Message ")
    @Expose
    private String statusMessage;
    @SerializedName("stage_of_work")
    @Expose
    private String stageOfWork;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStageOfWork() {
        return stageOfWork;
    }

    public void setStageOfWork(String stageOfWork) {
        this.stageOfWork = stageOfWork;
    }
}
