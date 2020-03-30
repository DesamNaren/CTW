package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalIssues {

    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("headacheCount")
    @Expose
    private String headacheCount;
    @SerializedName("feverCount")
    @Expose
    private String feverCount;
    @SerializedName("anmWeeklyUpdated")
    @Expose
    private String anmWeeklyUpdated;
    @SerializedName("coldCount")
    @Expose
    private String coldCount;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("last_medical_checkup_date")
    @Expose
    private String lastMedicalCheckupDate;
    @SerializedName("medicalCheckUpDoneByWhom")
    @Expose
    private String medicalCheckUpDoneByWhom;
    @SerializedName("recorded_in_register")
    @Expose
    private String recordedInRegister;
    @SerializedName("othersCount")
    @Expose
    private String othersCount;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("callHealth100")
    @Expose
    private String callHealth100;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("diarrheaCount")
    @Expose
    private String diarrheaCount;
    @SerializedName("malariaCount")
    @Expose
    private String malariaCount;

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getHeadacheCount() {
        return headacheCount;
    }

    public void setHeadacheCount(String headacheCount) {
        this.headacheCount = headacheCount;
    }

    public String getFeverCount() {
        return feverCount;
    }

    public void setFeverCount(String feverCount) {
        this.feverCount = feverCount;
    }

    public String getAnmWeeklyUpdated() {
        return anmWeeklyUpdated;
    }

    public void setAnmWeeklyUpdated(String anmWeeklyUpdated) {
        this.anmWeeklyUpdated = anmWeeklyUpdated;
    }

    public String getColdCount() {
        return coldCount;
    }

    public void setColdCount(String coldCount) {
        this.coldCount = coldCount;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getLastMedicalCheckupDate() {
        return lastMedicalCheckupDate;
    }

    public void setLastMedicalCheckupDate(String lastMedicalCheckupDate) {
        this.lastMedicalCheckupDate = lastMedicalCheckupDate;
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

    public String getOthersCount() {
        return othersCount;
    }

    public void setOthersCount(String othersCount) {
        this.othersCount = othersCount;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getCallHealth100() {
        return callHealth100;
    }

    public void setCallHealth100(String callHealth100) {
        this.callHealth100 = callHealth100;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiarrheaCount() {
        return diarrheaCount;
    }

    public void setDiarrheaCount(String diarrheaCount) {
        this.diarrheaCount = diarrheaCount;
    }

    public String getMalariaCount() {
        return malariaCount;
    }

    public void setMalariaCount(String malariaCount) {
        this.malariaCount = malariaCount;
    }

}
