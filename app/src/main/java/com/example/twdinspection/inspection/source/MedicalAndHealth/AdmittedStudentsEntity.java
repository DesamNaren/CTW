package com.example.twdinspection.inspection.source.MedicalAndHealth;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class AdmittedStudentsEntity {
    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String name;

    @ColumnInfo()
    private String type;

    @ColumnInfo()
    private String stud_class;

    @ColumnInfo()
    private String reason;

    @ColumnInfo()
    private String admitted_date;

    @ColumnInfo()
    private String hospital_name;

    @ColumnInfo()
    private String accompanied_by_name;

    @ColumnInfo()
    private String accompanied_by_des;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStud_class() {
        return stud_class;
    }

    public void setStud_class(String stud_class) {
        this.stud_class = stud_class;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdmitted_date() {
        return admitted_date;
    }

    public void setAdmitted_date(String admitted_date) {
        this.admitted_date = admitted_date;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getAccompanied_by_name() {
        return accompanied_by_name;
    }

    public void setAccompanied_by_name(String accompanied_by_name) {
        this.accompanied_by_name = accompanied_by_name;
    }

    public String getAccompanied_by_des() {
        return accompanied_by_des;
    }

    public void setAccompanied_by_des(String accompanied_by_des) {
        this.accompanied_by_des = accompanied_by_des;
    }
}
