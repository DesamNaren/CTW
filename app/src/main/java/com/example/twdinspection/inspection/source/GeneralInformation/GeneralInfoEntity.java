package com.example.twdinspection.inspection.source.GeneralInformation;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "general_info")
public class GeneralInfoEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String HM_HWO_presence;

    @ColumnInfo()
    private String having_headquarters;

    @ColumnInfo()
    private String leaveType;

    @ColumnInfo()
    private String capturetype;

    @ColumnInfo()
    private String movementRegisterEntry;

    @ColumnInfo()
    private String captureDistance;

    @ColumnInfo()
    private String staffQuarters;

    @ColumnInfo()
    private String stayingFacilitiesType;

    @Ignore
    private String mandalName;
    @Ignore
    private String instName;


    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getCapturetype() {
        return capturetype;
    }

    public void setCapturetype(String capturetype) {
        this.capturetype = capturetype;
    }

    public String getMovementRegisterEntry() {
        return movementRegisterEntry;
    }

    public void setMovementRegisterEntry(String movementRegisterEntry) {
        this.movementRegisterEntry = movementRegisterEntry;
    }

    public String getCaptureDistance() {
        return captureDistance;
    }

    public void setCaptureDistance(String captureDistance) {
        this.captureDistance = captureDistance;
    }

    public String getStaffQuarters() {
        return staffQuarters;
    }

    public void setStaffQuarters(String staffQuarters) {
        this.staffQuarters = staffQuarters;
    }

    public String getStayingFacilitiesType() {
        return stayingFacilitiesType;
    }

    public void setStayingFacilitiesType(String stayingFacilitiesType) {
        this.stayingFacilitiesType = stayingFacilitiesType;
    }

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

}
