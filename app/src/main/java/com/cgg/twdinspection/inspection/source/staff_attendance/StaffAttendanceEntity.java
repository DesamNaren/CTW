package com.cgg.twdinspection.inspection.source.staff_attendance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "staff_info")
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

    @ColumnInfo()
    private String category;

    @ColumnInfo()
    private String leaves_availed;

    @ColumnInfo()
    private String leaves_taken;

    @ColumnInfo()
    private String leaves_bal;

    @ColumnInfo()
    private String acad_panel_grade;

    @ColumnInfo()
    private boolean is_teaching_staff;

    @ColumnInfo()
    private String role_name;

    @ColumnInfo()
    private int acad_panel_grade_pos;

    @ColumnInfo()
    private int categ_pos;

    public boolean getIs_teaching_staff() {
        return is_teaching_staff;
    }

    public void setIs_teaching_staff(boolean is_teaching_staff) {
        this.is_teaching_staff = is_teaching_staff;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public int getAcad_panel_grade_pos() {
        return acad_panel_grade_pos;
    }

    public void setAcad_panel_grade_pos(int acad_panel_grade_pos) {
        this.acad_panel_grade_pos = acad_panel_grade_pos;
    }

    public int getCateg_pos() {
        return categ_pos;
    }

    public void setCateg_pos(int categ_pos) {
        this.categ_pos = categ_pos;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLeaves_availed() {
        return leaves_availed;
    }

    public void setLeaves_availed(String leaves_availed) {
        this.leaves_availed = leaves_availed;
    }

    public String getLeaves_taken() {
        return leaves_taken;
    }

    public void setLeaves_taken(String leaves_taken) {
        this.leaves_taken = leaves_taken;
    }

    public String getLeaves_bal() {
        return leaves_bal;
    }

    public void setLeaves_bal(String leaves_bal) {
        this.leaves_bal = leaves_bal;
    }

    public String getAcad_panel_grade() {
        return acad_panel_grade;
    }

    public void setAcad_panel_grade(String acad_panel_grade) {
        this.acad_panel_grade = acad_panel_grade;
    }

    @Ignore
    private boolean presentFlag;
    @Ignore
    private boolean absentFlag;
    @Ignore
    private boolean ondepFlag;
    @Ignore
    private boolean leavesFlag;


    public StaffAttendanceEntity(String officer_id, String institute_id, String inst_name, String emp_id,
                                 String emp_name, String designation, boolean is_teaching_staff, String role_name) {
        this.officer_id = officer_id;
        this.institute_id = institute_id;
        this.inst_name = inst_name;
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.designation = designation;
        this.is_teaching_staff = is_teaching_staff;
        this.role_name = role_name;
    }

    public boolean isLeavesFlag() {
        return leavesFlag;
    }

    public void setLeavesFlag(boolean leavesFlag) {
        this.leavesFlag = leavesFlag;
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
