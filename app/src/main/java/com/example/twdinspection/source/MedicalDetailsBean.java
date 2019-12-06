package com.example.twdinspection.source;

public class MedicalDetailsBean {
    private String student_name;
    private String student_class;
    private String reason;
    private String admittedDate;
    private String hospitalName;
    private String name;
    private String designation;

    public MedicalDetailsBean() {
    }

    public MedicalDetailsBean(String student_name, String student_class, String reason, String admittedDate, String hospitalName, String name, String designation) {
        this.student_name = student_name;
        this.student_class = student_class;
        this.reason = reason;
        this.admittedDate = admittedDate;
        this.hospitalName = hospitalName;
        this.name = name;
        this.designation = designation;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getStudent_class() {
        return student_class;
    }

    public String getReason() {
        return reason;
    }

    public String getAdmittedDate() {
        return admittedDate;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }
}
