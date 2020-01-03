package com.example.twdinspection.schemes.source;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "inspectionRemarks")
public class InspectionRemarksEntity {
    @PrimaryKey
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private int remark_id;

    @ColumnInfo()
    private String remark_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemark_id() {
        return remark_id;
    }

    public void setRemark_id(int remark_id) {
        this.remark_id = remark_id;
    }

    public String getRemark_type() {
        return remark_type;
    }

    public void setRemark_type(String remark_type) {
        this.remark_type = remark_type;
    }
}
