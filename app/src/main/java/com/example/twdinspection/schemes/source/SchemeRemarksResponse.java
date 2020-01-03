package com.example.twdinspection.schemes.source;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchemeRemarksResponse {
    @SerializedName("schemes")
    @Expose
    private List<InspectionRemarksEntity> schemes = null;
    @SerializedName("status_Code")
    @Expose
    private String statusCode;
    @SerializedName("status_Message")
    @Expose
    private String statusMessage;

    public List<InspectionRemarksEntity> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<InspectionRemarksEntity> schemes) {
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
