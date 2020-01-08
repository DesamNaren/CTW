package com.example.twdinspection.inspection.source.AcademicOverview;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "AcademicInfo")
public class AcademicOveriewEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String highest_class_syllabus_completed;

    @ColumnInfo()
    private String strength_accomodated_asper_infrastructure;

    @ColumnInfo()
    private String staff_accomodated_asper_stud_strength;

    @ColumnInfo()
    private String plan_prepared;

    @ColumnInfo()
    private String highest_class_gradeA;

    @ColumnInfo()
    private String highest_class_gradeB;


    @ColumnInfo()
    private String highest_class_gradeC;


    @ColumnInfo()
    private String highest_class_total;

    @ColumnInfo()
    private String assessment_test_conducted;


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

    public String getHighest_class_syllabus_completed() {
        return highest_class_syllabus_completed;
    }

    public void setHighest_class_syllabus_completed(String highest_class_syllabus_completed) {
        this.highest_class_syllabus_completed = highest_class_syllabus_completed;
    }

    public String getStrength_accomodated_asper_infrastructure() {
        return strength_accomodated_asper_infrastructure;
    }

    public void setStrength_accomodated_asper_infrastructure(String strength_accomodated_asper_infrastructure) {
        this.strength_accomodated_asper_infrastructure = strength_accomodated_asper_infrastructure;
    }

    public String getStaff_accomodated_asper_stud_strength() {
        return staff_accomodated_asper_stud_strength;
    }

    public void setStaff_accomodated_asper_stud_strength(String staff_accomodated_asper_stud_strength) {
        this.staff_accomodated_asper_stud_strength = staff_accomodated_asper_stud_strength;
    }

    public String getPlan_prepared() {
        return plan_prepared;
    }

    public void setPlan_prepared(String plan_prepared) {
        this.plan_prepared = plan_prepared;
    }

    public String getHighest_class_gradeA() {
        return highest_class_gradeA;
    }

    public void setHighest_class_gradeA(String highest_class_gradeA) {
        this.highest_class_gradeA = highest_class_gradeA;
    }

    public String getHighest_class_gradeB() {
        return highest_class_gradeB;
    }

    public void setHighest_class_gradeB(String highest_class_gradeB) {
        this.highest_class_gradeB = highest_class_gradeB;
    }

    public String getHighest_class_gradeC() {
        return highest_class_gradeC;
    }

    public void setHighest_class_gradeC(String highest_class_gradeC) {
        this.highest_class_gradeC = highest_class_gradeC;
    }

    public String getHighest_class_total() {
        return highest_class_total;
    }

    public void setHighest_class_total(String highest_class_total) {
        this.highest_class_total = highest_class_total;
    }

    public String getAssessment_test_conducted() {
        return assessment_test_conducted;
    }

    public void setAssessment_test_conducted(String assessment_test_conducted) {
        this.assessment_test_conducted = assessment_test_conducted;
    }
}
