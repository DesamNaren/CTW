package com.example.twdinspection.schemes.source.dmv;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class SchemeDistrict {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("distId")
    @Expose
    private String distId;
    @SerializedName("distName")
    @Expose
    private String distName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
