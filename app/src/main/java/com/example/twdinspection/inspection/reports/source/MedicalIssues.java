package com.example.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalIssues {

    @SerializedName("anmWeeklyUpdated")
    @Expose
    private String anmWeeklyUpdated;
    @SerializedName("callHealth100")
    @Expose
    private String callHealth100;
    @SerializedName("last_medical_checkup_date")
    @Expose
    private String lastMedicalCheckupDate;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("medicalCheckUpDoneByWhom")
    @Expose
    private String medicalCheckUpDoneByWhom;
    @SerializedName("recorded_in_register")
    @Expose
    private String recordedInRegister;
    @SerializedName("coldCount")
    @Expose
    private String coldCount;
    @SerializedName("diarrheaCount")
    @Expose
    private String diarrheaCount;
    @SerializedName("feverCount")
    @Expose
    private String feverCount;
    @SerializedName("headacheCount")
    @Expose
    private String headacheCount;
    @SerializedName("malariaCount")
    @Expose
    private String malariaCount;
    @SerializedName("othersCount")
    @Expose
    private String othersCount;

    public String getAnmWeeklyUpdated() {
        return anmWeeklyUpdated;
    }

    public void setAnmWeeklyUpdated(String anmWeeklyUpdated) {
        this.anmWeeklyUpdated = anmWeeklyUpdated;
    }

    public String getCallHealth100() {
        return callHealth100;
    }

    public void setCallHealth100(String callHealth100) {
        this.callHealth100 = callHealth100;
    }

    public String getLastMedicalCheckupDate() {
        return lastMedicalCheckupDate;
    }

    public void setLastMedicalCheckupDate(String lastMedicalCheckupDate) {
        this.lastMedicalCheckupDate = lastMedicalCheckupDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicalCheckUpDoneByWhom() {
        return medicalCheckUpDoneByWhom;
    }

    public void setMedicalCheckUpDoneByWhom(String medicalCheckUpDoneByWhom) {
        this.medicalCheckUpDoneByWhom = medicalCheckUpDoneByWhom;
    }

    public String getRecordedInRegister() {
        return recordedInRegister;
    }

    public void setRecordedInRegister(String recordedInRegister) {
        this.recordedInRegister = recordedInRegister;
    }

    public String getColdCount() {
        return coldCount;
    }

    public void setColdCount(String coldCount) {
        this.coldCount = coldCount;
    }

    public String getDiarrheaCount() {
        return diarrheaCount;
    }

    public void setDiarrheaCount(String diarrheaCount) {
        this.diarrheaCount = diarrheaCount;
    }

    public String getFeverCount() {
        return feverCount;
    }

    public void setFeverCount(String feverCount) {
        this.feverCount = feverCount;
    }

    public String getHeadacheCount() {
        return headacheCount;
    }

    public void setHeadacheCount(String headacheCount) {
        this.headacheCount = headacheCount;
    }

    public String getMalariaCount() {
        return malariaCount;
    }

    public void setMalariaCount(String malariaCount) {
        this.malariaCount = malariaCount;
    }

    public String getOthersCount() {
        return othersCount;
    }

    public void setOthersCount(String othersCount) {
        this.othersCount = othersCount;
    }
}
