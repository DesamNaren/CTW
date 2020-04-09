package com.cgg.twdinspection.engineering_works.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitEngWorksResponse {

    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("works_id")
    @Expose
    private Integer worksId;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Integer getWorksId() {
        return worksId;
    }

    public void setWorksId(Integer worksId) {
        this.worksId = worksId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}