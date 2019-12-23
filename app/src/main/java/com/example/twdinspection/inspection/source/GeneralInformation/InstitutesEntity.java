package com.example.twdinspection.inspection.source.GeneralInformation;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "InstInfo")
public class InstitutesEntity {

    @PrimaryKey
    @ColumnInfo()
    @NonNull
    private int id;

    @ColumnInfo()
    private String Inst_Id;

    @ColumnInfo()
    private String Inst_Name;

    public String getInst_Name() {
        return Inst_Name;
    }

    public void setInst_Name(String inst_Name) {
        this.Inst_Name = inst_Name;
    }

    @NonNull
    public String getInst_Id() {
        return Inst_Id;
    }

    public void setInst_Id(@NonNull String inst_Id) {
        this.Inst_Id = inst_Id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
