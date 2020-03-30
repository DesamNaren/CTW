package com.cgg.twdinspection.schemes.source.schemes;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchemeResponse {

    @SerializedName("schemes")
    @Expose
    private List<SchemeEntity> schemes = null;
    @SerializedName("status_Code")
    @Expose
    private String statusCode;

    @SerializedName("status_Message")
    @Expose
    private String statusMessage;

    public List<SchemeEntity> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<SchemeEntity> schemes) {
        this.schemes = schemes;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}
