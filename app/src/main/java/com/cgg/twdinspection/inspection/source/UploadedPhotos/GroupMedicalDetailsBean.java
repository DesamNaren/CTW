package com.cgg.twdinspection.inspection.source.UploadedPhotos;

import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;

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
