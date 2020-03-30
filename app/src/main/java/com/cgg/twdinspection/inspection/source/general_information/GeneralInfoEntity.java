package com.cgg.twdinspection.inspection.source.general_information;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "general_info")
public class GeneralInfoEntity {


    private int id;

    @NotNull
    @PrimaryKey
    private String institute_id;

    private String inspection_time;

    private String officer_id;

    @Ignore
    private String mandalName;
    @Ignore
    private String instName;


    private String hmPresence;

    private String hmReasonType;

    private String hmcaptureleavetype;

    private String hmCaptureodtype;

    private String hmMovementRegisterEntry;


    private String hwoPresence;

    private String hwoReasonType;

    private String hwocaptureodtype;

    private String hwocaptureleavetype;

    private String hwomovementRegisterEntry;



    private String hmheadquarters;

    private String hmcaptureDistance;

    private String hmstayingFacilitiesType;


    private String hwoheadquarters;

    private String hwocaptureDistance;

    private String hwostayingFacilitiesType;


    private String staffQuarters;


    public String getHmheadquarters() {
        return hmheadquarters;
    }

    public void setHmheadquarters(String hmheadquarters) {
        this.hmheadquarters = hmheadquarters;
    }

    public String getHmcaptureDistance() {
        return hmcaptureDistance;
    }

    public void setHmcaptureDistance(String hmcaptureDistance) {
        this.hmcaptureDistance = hmcaptureDistance;
    }

    public String getHmstayingFacilitiesType() {
        return hmstayingFacilitiesType;
    }

    public void setHmstayingFacilitiesType(String hmstayingFacilitiesType) {
        this.hmstayingFacilitiesType = hmstayingFacilitiesType;
    }

    public String getHmReasonType() {
        return hmReasonType;
    }

    public void setHmReasonType(String hmReasonType) {
        this.hmReasonType = hmReasonType;
    }

    public String getHwocaptureleavetype() {
        return hwocaptureleavetype;
    }

    public void setHwocaptureleavetype(String hwocaptureleavetype) {
        this.hwocaptureleavetype = hwocaptureleavetype;
    }

    public String getHmcaptureleavetype() {
        return hmcaptureleavetype;
    }

    public void setHmcaptureleavetype(String hmcaptureleavetype) {
        this.hmcaptureleavetype = hmcaptureleavetype;
    }

    public String getHmCaptureodtype() {
        return hmCaptureodtype;
    }

    public void setHmCaptureodtype(String hmCaptureodtype) {
        this.hmCaptureodtype = hmCaptureodtype;
    }

    public String getHmMovementRegisterEntry() {
        return hmMovementRegisterEntry;
    }

    public void setHmMovementRegisterEntry(String hmMovementRegisterEntry) {
        this.hmMovementRegisterEntry = hmMovementRegisterEntry;
    }

    public String getHmPresence() {
        return hmPresence;
    }

    public void setHmPresence(String hmPresence) {
        this.hmPresence = hmPresence;
    }

    public String getHwoPresence() {
        return hwoPresence;
    }

    public void setHwoPresence(String hwoPresence) {
        this.hwoPresence = hwoPresence;
    }

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

    public String getHwoReasonType() {
        return hwoReasonType;
    }

    public void setHwoReasonType(String hwoReasonType) {
        this.hwoReasonType = hwoReasonType;
    }

    public String getHwocaptureodtype() {
        return hwocaptureodtype;
    }

    public void setHwocaptureodtype(String hwocaptureodtype) {
        this.hwocaptureodtype = hwocaptureodtype;
    }

    public String getHwomovementRegisterEntry() {
        return hwomovementRegisterEntry;
    }

    public void setHwomovementRegisterEntry(String hwomovementRegisterEntry) {
        this.hwomovementRegisterEntry = hwomovementRegisterEntry;
    }

    public String getHwocaptureDistance() {
        return hwocaptureDistance;
    }

    public void setHwocaptureDistance(String hwocaptureDistance) {
        this.hwocaptureDistance = hwocaptureDistance;
    }

    public String getStaffQuarters() {
        return staffQuarters;
    }

    public void setStaffQuarters(String staffQuarters) {
        this.staffQuarters = staffQuarters;
    }

    public String getHwostayingFacilitiesType() {
        return hwostayingFacilitiesType;
    }

    public void setHwostayingFacilitiesType(String hwostayingFacilitiesType) {
        this.hwostayingFacilitiesType = hwostayingFacilitiesType;
    }


    public String getHwoheadquarters() {
        return hwoheadquarters;
    }

    public void setHwoheadquarters(String hwoheadquarters) {
        this.hwoheadquarters = hwoheadquarters;
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

}
