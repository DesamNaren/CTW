package com.example.twdinspection.inspection.source.diet_issues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.twdinspection.inspection.source.converters.DietInfoConverter;
import com.example.twdinspection.inspection.source.converters.DietIssuesConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@TypeConverters({DietIssuesConverter.class})
@Entity(tableName = "diet_issues_info")
public class DietIssuesEntity {

    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @NotNull
    @PrimaryKey
    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String menu_chart_served;

    @ColumnInfo()
    private String menu_chart_painted;

    @ColumnInfo()
    private String menu_served;

    @ColumnInfo()
    private String food_provisions;

    @ColumnInfo()
    private String matching_with_samples;

    @ColumnInfo()
    private String committee_exist;

    @ColumnInfo()
    private String discussed_with_committee;

    @ColumnInfo()
    private String maintaining_register;

    @ColumnInfo(name = "diet_provisions")
    private List<DietListEntity> dietListEntities;

    public List<DietListEntity> getDietListEntities() {
        return dietListEntities;
    }

    public void setDietListEntities(List<DietListEntity> dietListEntities) {
        this.dietListEntities = dietListEntities;
    }

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

    @NotNull
    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(@NotNull String institute_id) {
        this.institute_id = institute_id;
    }

    public String getMenu_chart_served() {
        return menu_chart_served;
    }

    public void setMenu_chart_served(String menu_chart_served) {
        this.menu_chart_served = menu_chart_served;
    }

    public String getMenu_chart_painted() {
        return menu_chart_painted;
    }

    public void setMenu_chart_painted(String menu_chart_painted) {
        this.menu_chart_painted = menu_chart_painted;
    }

    public String getMenu_served() {
        return menu_served;
    }

    public void setMenu_served(String menu_served) {
        this.menu_served = menu_served;
    }

    public String getFood_provisions() {
        return food_provisions;
    }

    public void setFood_provisions(String food_provisions) {
        this.food_provisions = food_provisions;
    }

    public String getMatching_with_samples() {
        return matching_with_samples;
    }

    public void setMatching_with_samples(String matching_with_samples) {
        this.matching_with_samples = matching_with_samples;
    }

    public String getCommittee_exist() {
        return committee_exist;
    }

    public void setCommittee_exist(String committee_exist) {
        this.committee_exist = committee_exist;
    }

    public String getDiscussed_with_committee() {
        return discussed_with_committee;
    }

    public void setDiscussed_with_committee(String discussed_with_committee) {
        this.discussed_with_committee = discussed_with_committee;
    }

    public String getMaintaining_register() {
        return maintaining_register;
    }

    public void setMaintaining_register(String maintaining_register) {
        this.maintaining_register = maintaining_register;
    }


    /*   public List<DietListEntity> getDietList() {
        return dietList;
    }

    public void setDietList(List<DietListEntity> dietList) {
        this.dietList = dietList;
    }*/


}
