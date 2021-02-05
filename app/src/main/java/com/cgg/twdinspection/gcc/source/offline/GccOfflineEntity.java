package com.cgg.twdinspection.gcc.source.offline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "GCC_Offline")

public class GccOfflineEntity {
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

    @SerializedName("mfp_commodities")
    @Expose
    private String mfpCommodities;
    @SerializedName("processing_uniit")
    @Expose
    private String processingUniit;
    @SerializedName("empties")
    @Expose
    private String empties;

    @SerializedName("petrol_commodities")
    @Expose
    private String petrolCommodities;
    @SerializedName("lph_commodities")
    @Expose
    private String lpgCommodities;


    public String getPetrolCommodities() {
        return petrolCommodities;
    }

    public void setPetrolCommodities(String petrolCommodities) {
        this.petrolCommodities = petrolCommodities;
    }

    public String getLpgCommodities() {
        return lpgCommodities;
    }

    public void setLpgCommodities(String lpgCommodities) {
        this.lpgCommodities = lpgCommodities;
    }

    public String getMfpCommodities() {
        return mfpCommodities;
    }

    public void setMfpCommodities(String mfpCommodities) {
        this.mfpCommodities = mfpCommodities;
    }

    public String getProcessingUniit() {
        return processingUniit;
    }

    public void setProcessingUniit(String processingUniit) {
        this.processingUniit = processingUniit;
    }

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("photos")
    @Expose
    private String photos;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("time")
    @Expose
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

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
