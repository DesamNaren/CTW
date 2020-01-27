package com.example.twdinspection.inspection.source.submit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstSubmitResponse {
    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("inspection_id")
    @Expose
    private String inspectionId;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}

