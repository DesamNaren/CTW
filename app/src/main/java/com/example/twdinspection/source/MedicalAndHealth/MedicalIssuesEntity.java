package com.example.twdinspection.source.MedicalAndHealth;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class MedicalIssuesEntity {
    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String fever;

    @ColumnInfo()
    private String cold_cough;


    @ColumnInfo()
    private String headache;

    @ColumnInfo()
    private String diarrhea;

    @ColumnInfo()
    private String malaria;

    @ColumnInfo()
    private String others;

    @ColumnInfo()
    private String last_medical_checkup_date;

    @ColumnInfo()
    private String recorded_in_register;

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getCold_cough() {
        return cold_cough;
    }

    public void setCold_cough(String cold_cough) {
        this.cold_cough = cold_cough;
    }

    public String getHeadache() {
        return headache;
    }

    public void setHeadache(String headache) {
        this.headache = headache;
    }

    public String getDiarrhea() {
        return diarrhea;
    }

    public void setDiarrhea(String diarrhea) {
        this.diarrhea = diarrhea;
    }

    public String getMalaria() {
        return malaria;
    }

    public void setMalaria(String malaria) {
        this.malaria = malaria;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
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
}
