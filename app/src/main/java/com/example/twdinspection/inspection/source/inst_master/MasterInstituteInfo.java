package com.example.twdinspection.inspection.source.inst_master;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.twdinspection.inspection.source.converters.ClassInfoConverter;
import com.example.twdinspection.inspection.source.converters.DietInfoConverter;
import com.example.twdinspection.inspection.source.converters.StaffInfoConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@TypeConverters({ClassInfoConverter.class, DietInfoConverter.class, StaffInfoConverter.class})
@Entity
public class MasterInstituteInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("Inst_Id")
    @Expose
    private Integer instId;

    @SerializedName("Diet_Info")
    @Expose
    private List<MasterDietInfo> dietInfo = null;

    @SerializedName("Class_Info")
    @Expose
    private List<MasterClassInfo> classInfo = null;

    @SerializedName("Staff_Info")
    @Expose
    private List<MasterStaffInfo> staffInfo = null;


    @SerializedName("Inst_Name")
    @Expose
    private String instName;

    @SerializedName("mandalName")
    @Expose
    private String mandalName;

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("districtName")
    @Expose
    private String districtName;

    @SerializedName("villageName")
    @Expose
    private String villageName;

    @SerializedName("mandalId")
    @Expose
    private int mandalId;

    @SerializedName("districtID")
    @Expose
    private int districtID;

    @SerializedName("villageId")
    @Expose
    private int villageId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public int getMandalId() {
        return mandalId;
    }

    public void setMandalId(int mandalId) {
        this.mandalId = mandalId;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public int getVillageId() {
        return villageId;
    }

    public void setVillageId(int villageId) {
        this.villageId = villageId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public List<MasterDietInfo> getDietInfo() {
        return dietInfo;
    }

    public void setDietInfo(List<MasterDietInfo> dietInfo) {
        this.dietInfo = dietInfo;
    }

    public List<MasterClassInfo> getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(List<MasterClassInfo> classInfo) {
        this.classInfo = classInfo;
    }

    public List<MasterStaffInfo> getStaffInfo() {
        return staffInfo;
    }

    public void setStaffInfo(List<MasterStaffInfo> staffInfo) {
        this.staffInfo = staffInfo;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

}
