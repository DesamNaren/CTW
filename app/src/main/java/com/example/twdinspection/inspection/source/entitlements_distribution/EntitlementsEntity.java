package com.example.twdinspection.inspection.source.entitlements_distribution;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "entitlements_info")
public class EntitlementsEntity {


    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @NotNull
    @PrimaryKey
    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String bedSheets;

    @ColumnInfo()
    private String carpets;

    @ColumnInfo()
    private String uniforms;

    @ColumnInfo()
    private String sportsDress;

    @ColumnInfo()
    private String slippers;

    @ColumnInfo()
    private String nightDress;

    @ColumnInfo()
    private String schoolBags;

    @ColumnInfo()
    private String sanitary_napkins_supplied;

    @ColumnInfo()
    private String sanitaryNapkins;

    @ColumnInfo()
    private String sanitary_napkins_reason;

    @ColumnInfo()
    private String notesSupplied;

    @ColumnInfo()
    private String notes;

    @ColumnInfo()
    private String notes_reason;

    @ColumnInfo()
    private String PairOfDressDistributedCount;

    @ColumnInfo()
    private String cosmetic_distributed;

    @ColumnInfo()
    private String cosmetic_distributed_upto_month;

    @ColumnInfo()
    private String cosmetic_reason;

    @ColumnInfo()
    private String uniforms_provided_quality;

    @ColumnInfo()
    private String hair_cut_complted;

    @ColumnInfo()
    private String last_haircut_date;


    public String getCosmetic_distributed_upto_month() {
        return cosmetic_distributed_upto_month;
    }

    public void setCosmetic_distributed_upto_month(String cosmetic_distributed_upto_month) {
        this.cosmetic_distributed_upto_month = cosmetic_distributed_upto_month;
    }

    public String getCosmetic_reason() {
        return cosmetic_reason;
    }

    public void setCosmetic_reason(String cosmetic_reason) {
        this.cosmetic_reason = cosmetic_reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes_reason() {
        return notes_reason;
    }

    public void setNotes_reason(String notes_reason) {
        this.notes_reason = notes_reason;
    }

    public String getSanitary_napkins_supplied() {
        return sanitary_napkins_supplied;
    }

    public void setSanitary_napkins_supplied(String sanitary_napkins_supplied) {
        this.sanitary_napkins_supplied = sanitary_napkins_supplied;
    }

    public String getSanitary_napkins_reason() {
        return sanitary_napkins_reason;
    }

    public void setSanitary_napkins_reason(String sanitary_napkins_reason) {
        this.sanitary_napkins_reason = sanitary_napkins_reason;
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

    public String getBedSheets() {
        return bedSheets;
    }

    public void setBedSheets(String bedSheets) {
        this.bedSheets = bedSheets;
    }

    public String getCarpets() {
        return carpets;
    }

    public void setCarpets(String carpets) {
        this.carpets = carpets;
    }

    public String getUniforms() {
        return uniforms;
    }

    public void setUniforms(String uniforms) {
        this.uniforms = uniforms;
    }

    public String getSportsDress() {
        return sportsDress;
    }

    public void setSportsDress(String sportsDress) {
        this.sportsDress = sportsDress;
    }

    public String getSlippers() {
        return slippers;
    }

    public void setSlippers(String slippers) {
        this.slippers = slippers;
    }

    public String getNightDress() {
        return nightDress;
    }

    public void setNightDress(String nightDress) {
        this.nightDress = nightDress;
    }

    public String getSchoolBags() {
        return schoolBags;
    }

    public void setSchoolBags(String schoolBags) {
        this.schoolBags = schoolBags;
    }

    public String getSanitaryNapkins() {
        return sanitaryNapkins;
    }

    public void setSanitaryNapkins(String sanitaryNapkins) {
        this.sanitaryNapkins = sanitaryNapkins;
    }

    public String getNotesSupplied() {
        return notesSupplied;
    }

    public void setNotesSupplied(String notesSupplied) {
        this.notesSupplied = notesSupplied;
    }

    public String getPairOfDressDistributedCount() {
        return PairOfDressDistributedCount;
    }

    public void setPairOfDressDistributedCount(String pairOfDressDistributedCount) {
        PairOfDressDistributedCount = pairOfDressDistributedCount;
    }

    public String getUniforms_provided_quality() {
        return uniforms_provided_quality;
    }

    public void setUniforms_provided_quality(String uniforms_provided_quality) {
        this.uniforms_provided_quality = uniforms_provided_quality;
    }

    public String getHair_cut_complted() {
        return hair_cut_complted;
    }

    public void setHair_cut_complted(String hair_cut_complted) {
        this.hair_cut_complted = hair_cut_complted;
    }

    public String getLast_haircut_date() {
        return last_haircut_date;
    }

    public void setLast_haircut_date(String last_haircut_date) {
        this.last_haircut_date = last_haircut_date;
    }

    public String getCosmetic_distributed() {
        return cosmetic_distributed;
    }

    public void setCosmetic_distributed(String cosmetic_distributed) {
        this.cosmetic_distributed = cosmetic_distributed;
    }
}
