package com.cgg.twdinspection.inspection.source.medical_and_health;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cgg.twdinspection.inspection.source.converters.CallHealthConverter;
import com.cgg.twdinspection.inspection.source.converters.MedicalRecordsConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@TypeConverters({CallHealthConverter.class, MedicalRecordsConverter.class})
@Entity(tableName = "medical_info")
public class MedicalInfoEntity {

    private int id;
    private String officer_id;
    private String inspection_time;
    @NotNull
    @PrimaryKey
    private String institute_id;
    private String sickBoarders;
    private String sickBoarders_area;
    private String feverCount;
    private String coldCount;
    private String headacheCount;
    private String diarrheaCount;
    private String malariaCount;
    private String othersCount;
    private String last_medical_checkup_date;
    private String last_medical_checkup_date_anm;
    private String recorded_in_register;
    private String medicalCheckUpDoneByWhom;
    private String medicalCheckUpDoneByWhom_anm;
    private String anmWeeklyUpdated;
    private String callHealth100;
    private String screenedByCallHealth;
    private String leftForscreening;

    public String getLast_medical_checkup_date_anm() {
        return last_medical_checkup_date_anm;
    }

    public void setLast_medical_checkup_date_anm(String last_medical_checkup_date_anm) {
        this.last_medical_checkup_date_anm = last_medical_checkup_date_anm;
    }

    public String getMedicalCheckUpDoneByWhom_anm() {
        return medicalCheckUpDoneByWhom_anm;
    }

    public void setMedicalCheckUpDoneByWhom_anm(String medicalCheckUpDoneByWhom_anm) {
        this.medicalCheckUpDoneByWhom_anm = medicalCheckUpDoneByWhom_anm;
    }

    public String getScreenedByCallHealth() {
        return screenedByCallHealth;
    }

    public void setScreenedByCallHealth(String screenedByCallHealth) {
        this.screenedByCallHealth = screenedByCallHealth;
    }

    public String getLeftForscreening() {
        return leftForscreening;
    }

    public void setLeftForscreening(String leftForscreening) {
        this.leftForscreening = leftForscreening;
    }

    public String getSickBoarders() {
        return sickBoarders;
    }

    public void setSickBoarders(String sickBoarders) {
        this.sickBoarders = sickBoarders;
    }

    public String getSickBoarders_area() {
        return sickBoarders_area;
    }

    public void setSickBoarders_area(String sickBoarders_area) {
        this.sickBoarders_area = sickBoarders_area;
    }

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

    @NotNull
    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(@NotNull String institute_id) {
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
