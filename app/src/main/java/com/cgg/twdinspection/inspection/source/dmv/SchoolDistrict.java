package com.cgg.twdinspection.inspection.source.dmv;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "districts_info")
public class SchoolDistrict {
    @PrimaryKey(autoGenerate = true)
    private int r_id;
    @SerializedName("dist_id")
    @Expose
    private String distId;
    @SerializedName("dist_name")
    @Expose
    private String distName;

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getDistName() {
        return distName;
    }

    public void setDistName(String distName) {
        this.distName = distName;
    }

}
