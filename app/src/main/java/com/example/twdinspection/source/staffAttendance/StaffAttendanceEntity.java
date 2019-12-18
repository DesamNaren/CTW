package com.example.twdinspection.source.staffAttendance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Staff_Info")
public class StaffAttendanceEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String inst_name;

    @ColumnInfo()
    private String emp_id;

    @ColumnInfo()
    private String emp_presence;

    @ColumnInfo()
    private String emp_name;

    @ColumnInfo()
    private String designation;

    @ColumnInfo()
    private String leave_type;

    @ColumnInfo()
    private String yday_duty_allotted;

    @ColumnInfo()
    private String last_week_turn_duties_attended;

    @Ignore
    private boolean presentFlag;
    @Ignore
    private boolean absentFlag;
    @Ignore
    private boolean ondepFlag;

    public StaffAttendanceEntity() {
    }

    public String getInst_name() {
        return inst_name;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public boolean isPresentFlag() {
        return presentFlag;
    }

    public void setPresentFlag(boolean presentFlag) {
        this.presentFlag = presentFlag;
    }

    public boolean isAbsentFlag() {
        return absentFlag;
    }

    public void setAbsentFlag(boolean absentFlag) {
        this.absentFlag = absentFlag;
    }

    public boolean isOndepFlag() {
        return ondepFlag;
    }

    public void setOndepFlag(boolean ondepFlag) {
        this.ondepFlag = ondepFlag;
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

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_presence() {
        return emp_presence;
    }

    public void setEmp_presence(String emp_presence) {
        this.emp_presence = emp_presence;
    }

    public String getYday_duty_allotted() {
        return yday_duty_allotted;
    }

    public void setYday_duty_allotted(String yday_duty_allotted) {
        this.yday_duty_allotted = yday_duty_allotted;
    }

    public String getLast_week_turn_duties_attended() {
        return last_week_turn_duties_attended;
    }

    public void setLast_week_turn_duties_attended(String last_week_turn_duties_attended) {
        this.last_week_turn_duties_attended = last_week_turn_duties_attended;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
