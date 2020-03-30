package com.cgg.twdinspection.inspection.source.inst_master;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterStaffInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("emp_name")
    @Expose
    private String empName;
    @SerializedName("hostel_id")
    @Expose
    private Integer hostelId;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("emp_id")
    @Expose
    private Integer empId;

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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getHostelId() {
        return hostelId;
    }

    public void setHostelId(Integer hostelId) {
        this.hostelId = hostelId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

}
