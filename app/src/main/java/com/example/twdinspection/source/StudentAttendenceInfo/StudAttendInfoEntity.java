package com.example.twdinspection.source.StudentAttendenceInfo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.twdinspection.source.GeneralInformation.GeneralInformationEntity;

@Entity( primaryKeys = { "officer_id", "institute_id","inspection_time" },
         foreignKeys = {
                @ForeignKey(entity = GeneralInformationEntity.class,
                        parentColumns = {"officer_id","institute_id","inspection_time"},
                        childColumns =  {"officer_id","institute_id","inspection_time"},
                        onDelete = ForeignKey.CASCADE)}
                        )
public class StudAttendInfoEntity {


    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String class_type;

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

    public String getVariance() {
        return variance;
    }

    public void setVariance(String variance) {
        this.variance = variance;
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




}
