package com.example.twdinspection.inspection.source.medical_and_health;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.twdinspection.inspection.source.MedicalDetailsBean;
import com.example.twdinspection.inspection.source.converters.CallHealthConverter;
import com.example.twdinspection.inspection.source.converters.MedicalRecordsConverter;

import java.util.List;

@TypeConverters({CallHealthConverter.class, MedicalRecordsConverter.class})
@Entity
public class MedicalInfoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String officer_id;
    private String inspection_time;
    private String institute_id;
    private int feverCount;
    private int coldCount;
    private int headacheCount;
    private int diarrheaCount;
    private int malariaCount;
    private int othersCount;
    private String last_medical_checkup_date;
    private String recorded_in_register;
    private String medicalCheckUpDoneByWhom;
    private String anmWeeklyUpdated;
    private String callHealth100;

    @ColumnInfo(name = "callHealthRecords")
    private List<CallHealthInfoEntity> callHealthInfoEntities = null;

    @ColumnInfo(name = "medicalRecords")
    private List<MedicalDetailsBean> medicalDetails = null;

    public List<MedicalDetailsBean> getMedicalDetails() {
        return medicalDetails;
    }

    public void setMedicalDetails(List<MedicalDetailsBean> medicalDetails) {
        this.medicalDetails = medicalDetails;
    }

    public List<CallHealthInfoEntity> getCallHealthInfoEntities() {
        return callHealthInfoEntities;
    }

    public void setCallHealthInfoEntities(List<CallHealthInfoEntity> callHealthInfoEntities) {
        this.callHealthInfoEntities = callHealthInfoEntities;
    }

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

    public int getFeverCount() {
        return feverCount;
    }

    public void setFeverCount(int feverCount) {
        this.feverCount = feverCount;
    }

    public int getColdCount() {
        return coldCount;
    }

    public void setColdCount(int coldCount) {
        this.coldCount = coldCount;
    }

    public int getHeadacheCount() {
        return headacheCount;
    }

    public void setHeadacheCount(int headacheCount) {
        this.headacheCount = headacheCount;
    }

    public int getDiarrheaCount() {
        return diarrheaCount;
    }

    public void setDiarrheaCount(int diarrheaCount) {
        this.diarrheaCount = diarrheaCount;
    }

    public int getMalariaCount() {
        return malariaCount;
    }

    public void setMalariaCount(int malariaCount) {
        this.malariaCount = malariaCount;
    }

    public int getOthersCount() {
        return othersCount;
    }

    public void setOthersCount(int othersCount) {
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
