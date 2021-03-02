package com.cgg.twdinspection.inspection.source.diet_issues;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cgg.twdinspection.inspection.source.converters.ClassInfoConverter;
import com.cgg.twdinspection.inspection.source.converters.DietInfoConverter;
import com.cgg.twdinspection.inspection.source.converters.StaffInfoConverter;
import com.cgg.twdinspection.inspection.source.inst_master.MasterDietInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@TypeConverters({ DietInfoConverter.class})
@Entity(tableName = "master_diet_info")

public class MasterDietListInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("Inst_Id")
    @Expose
    private Integer instId;

    @SerializedName("Diet_Info")
    @Expose
    private List<MasterDietInfo> dietInfo = null;

    @SerializedName("Inst_Name")
    @Expose
    private String instName;

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

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }
}
