package com.example.twdinspection.inspection.source.studentAttendenceInfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ClassInfo")
public class StudAttendInfoEntity {
    @PrimaryKey(autoGenerate = true)
    public int r_id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private int flag_completed;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String class_type;

    @ColumnInfo()
    private int class_id;

    @ColumnInfo()
    private String total_students;

    @ColumnInfo()
    private String attendence_marked;


    @ColumnInfo()
    private String student_count_in_register;

    @ColumnInfo()
    private String student_count_during_inspection;

    @ColumnInfo()
    private String variance;

    public StudAttendInfoEntity(String officer_id, int flag_completed, String institute_id, String class_type, int class_id, String total_students) {
        this.officer_id = officer_id;
        this.flag_completed = flag_completed;
        this.institute_id = institute_id;
        this.class_type = class_type;
        this.class_id = class_id;
        this.total_students = total_students;
    }

    public String getVariance() {
        return variance;
    }

    public void setVariance(String variance) {
        this.variance = variance;
    }

    public int getFlag_completed() {
        return flag_completed;
    }

    public void setFlag_completed(int flag_completed) {
        this.flag_completed = flag_completed;
    }

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }

    public String getAttendence_marked() {
        return attendence_marked;
    }

    public void setAttendence_marked(String attendence_marked) {
        this.attendence_marked = attendence_marked;
    }

    public String getTotal_students() {
        return total_students;
    }

    public void setTotal_students(String total_students) {
        this.total_students = total_students;
    }

    public String getStudent_count_in_register() {
        return student_count_in_register;
    }

    public void setStudent_count_in_register(String student_count_in_register) {
        this.student_count_in_register = student_count_in_register;
    }

    public String getStudent_count_during_inspection() {
        return student_count_during_inspection;
    }

    public void setStudent_count_during_inspection(String student_count_during_inspection) {
        this.student_count_during_inspection = student_count_during_inspection;
    }


    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getInspection_time() {
        return inspection_time;
    }

    public void setInspection_time(String inspection_time) {
        this.inspection_time = inspection_time;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
