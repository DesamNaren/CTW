package com.cgg.twdinspection.gcc.source.divisions;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity (tableName = "Divisions")
public class DivisionsInfo {
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

}
