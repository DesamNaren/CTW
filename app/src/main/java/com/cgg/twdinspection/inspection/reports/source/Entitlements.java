package com.cgg.twdinspection.inspection.reports.source;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Entitlements {
    @SerializedName("inspection_time")
    @Expose
    private String inspectionTime;
    @SerializedName("carpets")
    @Expose
    private String carpets;
    @SerializedName("entitlements_provided")
    @Expose
    private String entitlementsProvided;
    @SerializedName("schoolBags")
    @Expose
    private String schoolBags;
    @SerializedName("hair_cut_complted")
    @Expose
    private String hairCutComplted;
    @SerializedName("last_haircut_date")
    @Expose
    private String lastHaircutDate;
    @SerializedName("slippers")
    @Expose
    private String slippers;
    @SerializedName("uniforms")
    @Expose
    private String uniforms;
    @SerializedName("PairOfDressDistributedCount")
    @Expose
    private String pairOfDressDistributedCount;
    @SerializedName("sanitaryNapkins")
    @Expose
    private String sanitaryNapkins;
    @SerializedName("sportsDress")
    @Expose
    private String sportsDress;
    @SerializedName("institute_id")
    @Expose
    private String instituteId;
    @SerializedName("officer_id")
    @Expose
    private String officerId;
    @SerializedName("bedSheets")
    @Expose
    private String bedSheets;
    @SerializedName("cosmetic_distributed")
    @Expose
    private String cosmeticDistributed;
    @SerializedName("nightDress")
    @Expose
    private String nightDress;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("uniforms_provided_quality")
    @Expose
    private String uniformsProvidedQuality;
    @SerializedName("notesSupplied")
    @Expose
    private String notesSupplied;

    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getCarpets() {
        return carpets;
    }

    public void setCarpets(String carpets) {
        this.carpets = carpets;
    }

    public String getEntitlementsProvided() {
        return entitlementsProvided;
    }

    public void setEntitlementsProvided(String entitlementsProvided) {
        this.entitlementsProvided = entitlementsProvided;
    }

    public String getSchoolBags() {
        return schoolBags;
    }

    public void setSchoolBags(String schoolBags) {
        this.schoolBags = schoolBags;
    }

    public String getHairCutComplted() {
        return hairCutComplted;
    }

    public void setHairCutComplted(String hairCutComplted) {
        this.hairCutComplted = hairCutComplted;
    }

    public String getLastHaircutDate() {
        return lastHaircutDate;
    }

    public void setLastHaircutDate(String lastHaircutDate) {
        this.lastHaircutDate = lastHaircutDate;
    }

    public String getSlippers() {
        return slippers;
    }

    public void setSlippers(String slippers) {
        this.slippers = slippers;
    }

    public String getUniforms() {
        return uniforms;
    }

    public void setUniforms(String uniforms) {
        this.uniforms = uniforms;
    }

    public String getPairOfDressDistributedCount() {
        return pairOfDressDistributedCount;
    }

    public void setPairOfDressDistributedCount(String pairOfDressDistributedCount) {
        this.pairOfDressDistributedCount = pairOfDressDistributedCount;
    }

    public String getSanitaryNapkins() {
        return sanitaryNapkins;
    }

    public void setSanitaryNapkins(String sanitaryNapkins) {
        this.sanitaryNapkins = sanitaryNapkins;
    }

    public String getSportsDress() {
        return sportsDress;
    }

    public void setSportsDress(String sportsDress) {
        this.sportsDress = sportsDress;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public String getBedSheets() {
        return bedSheets;
    }

    public void setBedSheets(String bedSheets) {
        this.bedSheets = bedSheets;
    }

    public String getCosmeticDistributed() {
        return cosmeticDistributed;
    }

    public void setCosmeticDistributed(String cosmeticDistributed) {
        this.cosmeticDistributed = cosmeticDistributed;
    }

    public String getNightDress() {
        return nightDress;
    }

    public void setNightDress(String nightDress) {
        this.nightDress = nightDress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniformsProvidedQuality() {
        return uniformsProvidedQuality;
    }

    public void setUniformsProvidedQuality(String uniformsProvidedQuality) {
        this.uniformsProvidedQuality = uniformsProvidedQuality;
    }

    public String getNotesSupplied() {
        return notesSupplied;
    }

    public void setNotesSupplied(String notesSupplied) {
        this.notesSupplied = notesSupplied;
    }
}
