package com.example.twdinspection.inspection.source.diet_issues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nullable;

@Entity(tableName = "diet_list_info")
public class DietListEntity {

    @Nullable
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String item_name;

    @ColumnInfo()
    private Double ground_bal;

    @ColumnInfo()
    private Double book_bal;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String officerId;


    public DietListEntity( String item_name, Double ground_bal, Double book_bal, String institute_id, String officerId) {
        this.item_name = item_name;
        this.ground_bal = ground_bal;
        this.book_bal = book_bal;
        this.institute_id = institute_id;
        this.officerId = officerId;
    }

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Double getGround_bal() {
        return ground_bal;
    }

    public void setGround_bal(Double ground_bal) {
        this.ground_bal = ground_bal;
    }

    public Double getBook_bal() {
        return book_bal;
    }

    public void setBook_bal(Double book_bal) {
        this.book_bal = book_bal;
    }
}
