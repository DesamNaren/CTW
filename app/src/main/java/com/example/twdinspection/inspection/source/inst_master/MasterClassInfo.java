package com.example.twdinspection.inspection.source.inst_master;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterClassInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("class_id")
    @Expose
    private Integer classId;
    @SerializedName("student_count")
    @Expose
    private Integer studentCount;
    @SerializedName("Inst_Id")
    @Expose
    private Integer instId;
    @SerializedName("Inst_Name")
    @Expose
    private String instName;


    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

}
