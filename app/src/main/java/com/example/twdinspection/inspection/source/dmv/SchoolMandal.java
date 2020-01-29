package com.example.twdinspection.inspection.source.dmv;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "mandal_info")
public class SchoolMandal {
    @PrimaryKey(autoGenerate = true)
    private int r_id;
    @SerializedName("dist_id")
    @Expose
    private String distId;
    @SerializedName("mandal_id")
    @Expose
    private String mandalID;
    @SerializedName("mandal_name")
    @Expose
    private String mandalName;


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

    public String getMandalID() {
        return mandalID;
    }

    public void setMandalID(String mandalID) {
        this.mandalID = mandalID;
    }

    public String getMandalName() {
        return mandalName;
    }

    public void setMandalName(String mandalName) {
        this.mandalName = mandalName;
    }
}