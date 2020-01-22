package com.example.twdinspection.inspection.source.dietIssues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.twdinspection.inspection.source.converters.DietInfoConverter;

import java.util.List;

//@TypeConverters({DietInfoConverter.class})
@Entity(tableName = "DietIssuesInfo")
public class DietIssuesEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

//    @ColumnInfo()
//    private List<DietListEntity> dietList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(String inspection_time) {
        this.inspection_time = inspection_time;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

 /*   public List<DietListEntity> getDietList() {
        return dietList;
    }

    public void setDietList(List<DietListEntity> dietList) {
        this.dietList = dietList;
    }*/
}
