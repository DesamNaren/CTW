package com.cgg.twdinspection.gcc.source.offline.drgodown;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "DR_GoDown_Offline")

public class DrGodownOffline {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("divisionId")
    @Expose
    private String divisionId;
    @SerializedName("divisionName")
    @Expose
    private String divisionName;
    @SerializedName("societyId")
    @Expose
    private String societyId;
    @SerializedName("societyName")
    @Expose
    private String societyName;
    @SerializedName("drgownId")
    @Expose
    private String drgownId;
    @SerializedName("drgownName")
    @Expose
    private String drgownName;
    @SerializedName("essentials")
    @Expose
    private String essentials;
    @SerializedName("daily_req")
    @Expose
    private String dailyReq;
    @SerializedName("empties")
    @Expose
    private String empties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getSocietyId() {
        return societyId;
    }

    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getDrgownId() {
        return drgownId;
    }

    public void setDrgownId(String drgownId) {
        this.drgownId = drgownId;
    }

    public String getDrgownName() {
        return drgownName;
    }

    public void setDrgownName(String drgownName) {
        this.drgownName = drgownName;
    }

    public String getEssentials() {
        return essentials;
    }

    public void setEssentials(String essentials) {
        this.essentials = essentials;
    }

    public String getDailyReq() {
        return dailyReq;
    }

    public void setDailyReq(String dailyReq) {
        this.dailyReq = dailyReq;
    }

    public String getEmpties() {
        return empties;
    }

    public void setEmpties(String empties) {
        this.empties = empties;
    }
}
