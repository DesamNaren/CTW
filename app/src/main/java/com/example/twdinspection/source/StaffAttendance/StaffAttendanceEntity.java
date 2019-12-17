package com.example.twdinspection.source.StaffAttendance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class StaffAttendanceEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private String id;
    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String emp_id;

    @ColumnInfo(name = "inst_id")
    private String hostel_id;

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

    public StaffAttendanceEntity() {
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
