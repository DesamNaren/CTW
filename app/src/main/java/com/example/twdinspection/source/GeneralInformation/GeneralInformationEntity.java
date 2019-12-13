package com.example.twdinspection.source.GeneralInformation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.RoomDatabase;

@Entity(primaryKeys = {"officer_id", "institute_id","inspection_time"})
public class GeneralInformationEntity {
    @ColumnInfo()
    private String HM_HWO_presence;

    @ColumnInfo()
    private String having_headquarters;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String officer_designation;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String mandal_id;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String dist_id;

    @ColumnInfo()
    private String village_id;

    public String getHM_HWO_presence() {
        return HM_HWO_presence;
    }

    public void setHM_HWO_presence(String HM_HWO_presence) {
        this.HM_HWO_presence = HM_HWO_presence;
    }

    public String getHaving_headquarters() {
        return having_headquarters;
    }

    public void setHaving_headquarters(String having_headquarters) {
        this.having_headquarters = having_headquarters;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getOfficer_designation() {
        return officer_designation;
    }

    public void setOfficer_designation(String officer_designation) {
        this.officer_designation = officer_designation;
    }

    public String getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(String inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getMandal_id() {
        return mandal_id;
    }

    public void setMandal_id(String mandal_id) {
        this.mandal_id = mandal_id;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(String village_id) {
        this.village_id = village_id;
    }
}