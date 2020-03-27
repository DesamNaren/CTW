package com.example.twdinspection.engineering_works.source;

import com.example.twdinspection.engineering_works.source.GrantScheme;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GrantSchemesResponse {

    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("schemes")
    @Expose
    private List<GrantScheme> schemes = null;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<GrantScheme> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<GrantScheme> schemes) {
        this.schemes = schemes;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}