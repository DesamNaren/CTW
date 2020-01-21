package com.example.twdinspection.inspection.source.UploadedPhotos;

import com.example.twdinspection.inspection.source.MedicalDetailsBean;

import java.util.List;

public class GroupMedicalDetailsBean {
    private String type;
    private MedicalDetailsBean medicalDetailsBean;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MedicalDetailsBean getMedicalDetailsBean() {
        return medicalDetailsBean;
    }

    public void setMedicalDetailsBean(MedicalDetailsBean medicalDetailsBean) {
        this.medicalDetailsBean = medicalDetailsBean;
    }
}
