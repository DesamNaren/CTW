package com.cgg.twdinspection.engineering_works.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SectorsResponse {

    @SerializedName("sectors")
    @Expose
    private List<SectorsEntity> SectorsEntitys = null;
    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;

    public List<SectorsEntity> getSectorsEntitys() {
        return SectorsEntitys;
    }

    public void setSectorsEntitys(List<SectorsEntity> SectorsEntitys) {
        this.SectorsEntitys = SectorsEntitys;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
