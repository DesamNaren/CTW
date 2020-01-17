package com.example.twdinspection.inspection.source.medical_and_health;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MedicalInfo")
public class MedicalInfoEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String feverCount;

    @ColumnInfo()
    private String coldCount;

    @ColumnInfo()
    private String headacheCount;

    @ColumnInfo()
    private String diarrheaCount;

    @ColumnInfo()
    private String malariaCount;

    @ColumnInfo()
    private String othersCount;

    @ColumnInfo()
    private String last_medical_checkup_date;

    @ColumnInfo()
    private String recorded_in_register;

    @ColumnInfo()
    private String medicalCheckUpDoneByWhom;

    @ColumnInfo()
    private String anmWeeklyUpdated;

    @ColumnInfo()
    private String callHealth100;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(String inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getFeverCount() {
        return feverCount;
    }

    public void setFeverCount(String feverCount) {
        this.feverCount = feverCount;
    }

    public String getColdCount() {
        return coldCount;
    }

    public void setColdCount(String coldCount) {
        this.coldCount = coldCount;
    }

    public String getHeadacheCount() {
        return headacheCount;
    }

    public void setHeadacheCount(String headacheCount) {
        this.headacheCount = headacheCount;
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

    public String getOthersCount() {
        return othersCount;
    }

    public void setOthersCount(String othersCount) {
        this.othersCount = othersCount;
    }

    public String getLast_medical_checkup_date() {
        return last_medical_checkup_date;
    }

    public void setLast_medical_checkup_date(String last_medical_checkup_date) {
        this.last_medical_checkup_date = last_medical_checkup_date;
    }

    public String getRecorded_in_register() {
        return recorded_in_register;
    }

    public void setRecorded_in_register(String recorded_in_register) {
        this.recorded_in_register = recorded_in_register;
    }

    public String getMedicalCheckUpDoneByWhom() {
        return medicalCheckUpDoneByWhom;
    }

    public void setMedicalCheckUpDoneByWhom(String medicalCheckUpDoneByWhom) {
        this.medicalCheckUpDoneByWhom = medicalCheckUpDoneByWhom;
    }

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
}
