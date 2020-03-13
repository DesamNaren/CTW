package com.example.twdinspection.inspection.source.academic_overview;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "academic_grade")
public class AcademicGradeEntity {
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

    @NotNull
    @PrimaryKey
    @ColumnInfo()
    private String class_id;

    @ColumnInfo()
    private String total_students;

    @ColumnInfo()
    private String grade_a_stu_count;

    @ColumnInfo()
    private String grade_b_stu_count;

    @ColumnInfo()
    private String grade_c_stu_count;

    @ColumnInfo()
    private String grade_d_stu_count;


    @ColumnInfo()
    private String grade_e_stu_count;


    @ColumnInfo()
    private String grade_aplus_stu_count;


    @ColumnInfo()
    private String grade_bplus_stu_count;

    public AcademicGradeEntity(String officer_id, String institute_id, String inspection_time, String class_type, @NotNull String class_id, String total_students) {
        this.officer_id = officer_id;
        this.institute_id = institute_id;
        this.inspection_time = inspection_time;
        this.class_type = class_type;
        this.class_id = class_id;
        this.total_students = total_students;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getOfficer_id() {
        return officer_id;
    }

    public void setOfficer_id(String officer_id) {
        this.officer_id = officer_id;
    }

    public int getFlag_completed() {
        return flag_completed;
    }

    public void setFlag_completed(int flag_completed) {
        this.flag_completed = flag_completed;
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

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }

    @NotNull
    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(@NotNull String class_id) {
        this.class_id = class_id;
    }

    public String getTotal_students() {
        return total_students;
    }

    public void setTotal_students(String total_students) {
        this.total_students = total_students;
    }

    public String getGrade_a_stu_count() {
        return grade_a_stu_count;
    }

    public void setGrade_a_stu_count(String grade_a_stu_count) {
        this.grade_a_stu_count = grade_a_stu_count;
    }

    public String getGrade_b_stu_count() {
        return grade_b_stu_count;
    }

    public void setGrade_b_stu_count(String grade_b_stu_count) {
        this.grade_b_stu_count = grade_b_stu_count;
    }

    public String getGrade_c_stu_count() {
        return grade_c_stu_count;
    }

    public void setGrade_c_stu_count(String grade_c_stu_count) {
        this.grade_c_stu_count = grade_c_stu_count;
    }

    public String getGrade_d_stu_count() {
        return grade_d_stu_count;
    }

    public void setGrade_d_stu_count(String grade_d_stu_count) {
        this.grade_d_stu_count = grade_d_stu_count;
    }

    public String getGrade_e_stu_count() {
        return grade_e_stu_count;
    }

    public void setGrade_e_stu_count(String grade_e_stu_count) {
        this.grade_e_stu_count = grade_e_stu_count;
    }

    public String getGrade_aplus_stu_count() {
        return grade_aplus_stu_count;
    }

    public void setGrade_aplus_stu_count(String grade_aplus_stu_count) {
        this.grade_aplus_stu_count = grade_aplus_stu_count;
    }

    public String getGrade_bplus_stu_count() {
        return grade_bplus_stu_count;
    }

    public void setGrade_bplus_stu_count(String grade_bplus_stu_count) {
        this.grade_bplus_stu_count = grade_bplus_stu_count;
    }
}
