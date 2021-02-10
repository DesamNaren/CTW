package com.cgg.twdinspection.schemes.source.remarks;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class InspectionRemarksEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @SerializedName("remark_type")
    @Expose
    private String remark_type;
    @SerializedName("remark_id")
    @Expose
    private String remark_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark_type() {
        return remark_type;
    }

    public void setRemark_type(String remark_type) {
        this.remark_type = remark_type;
    }

    public String getRemark_id() {
        return remark_id;
    }

    public void setRemark_id(String remark_id) {
        this.remark_id = remark_id;
    }
}
