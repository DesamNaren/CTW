package com.example.twdinspection.inspection.source.cocurriularActivities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class PlantsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @Ignore
    @ColumnInfo()
    private int sl_no;


    @ColumnInfo()
    private String plantType;

    @ColumnInfo()
    private String plant_cnt;

    public int getSl_no() {
        return sl_no;
    }

    public void setSl_no(int sl_no) {
        this.sl_no = sl_no;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getPlant_cnt() {
        return plant_cnt;
    }

    public void setPlant_cnt(String plant_cnt) {
        this.plant_cnt = plant_cnt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
