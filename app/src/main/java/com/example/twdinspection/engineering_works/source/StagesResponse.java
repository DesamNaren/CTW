package com.example.twdinspection.engineering_works.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StagesResponse {

    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("stages")
    @Expose
    private List<Stage> stages = null;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}