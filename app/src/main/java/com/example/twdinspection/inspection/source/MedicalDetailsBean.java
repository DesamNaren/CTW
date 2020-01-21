package com.example.twdinspection.inspection.source;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import afu.org.checkerframework.checker.igj.qual.I;

@Entity
public class MedicalDetailsBean {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String student_name;
    private String student_class;
    private String reason;
    private String admittedDate;
    private String hospitalName;
    private String name;
    private String designation;
    private String type;
    @Ignore
    private boolean is_expand;

    public boolean isIs_expand() {
        return is_expand;
    }

    public void setIs_expand(boolean is_expand) {
        this.is_expand = is_expand;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MedicalDetailsBean(String student_name, String student_class, String reason, String admittedDate, String hospitalName, String name, String designation, String type) {
        this.student_name = student_name;
        this.student_class = student_class;
        this.reason = reason;
        this.admittedDate = admittedDate;
        this.hospitalName = hospitalName;
        this.name = name;
        this.designation = designation;
        this.type = type;
    }

    public MedicalDetailsBean() {
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_class() {
        return student_class;
    }

    public void setStudent_class(String student_class) {
        this.student_class = student_class;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAdmittedDate() {
        return admittedDate;
    }

    public void setAdmittedDate(String admittedDate) {
        this.admittedDate = admittedDate;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
