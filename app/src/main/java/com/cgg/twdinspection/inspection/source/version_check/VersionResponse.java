package com.cgg.twdinspection.inspection.source.version_check;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VersionResponse {

    @SerializedName("Current_version")
    @Expose
    private String currentVersion;
    @SerializedName("Status_Code")
    @Expose
    private String statusCode;
    @SerializedName("Status_Message")
    @Expose
    private String statusMessage;

    @SerializedName("version_date")
    @Expose
    private String versionDate;

    public String getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(String versionDate) {
        this.versionDate = versionDate;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

}