package com.cgg.twdinspection.inspection.source.diet_issues;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "diet_list_info")
public class DietListEntity {

    @ColumnInfo()
    private int id;

    @NonNull
    @PrimaryKey
    @ColumnInfo()
    private String item_name;

    @ColumnInfo()
    private String ground_bal;

    @ColumnInfo()
    private String book_bal;

    @ColumnInfo()
    private String institute_id;

    @ColumnInfo()
    private String officerId;

    @ColumnInfo()
    private boolean flag_selected;

    public boolean isFlag_selected() {
        return flag_selected;
    }

    public void setFlag_selected(boolean flag_selected) {
        this.flag_selected = flag_selected;
    }

    public DietListEntity(@NotNull String item_name, String ground_bal, String book_bal, String institute_id, String officerId) {
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

    @NotNull
    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(@NotNull String item_name) {
        this.item_name = item_name;
    }

    public String getGround_bal() {
        return ground_bal;
    }

    public void setGround_bal(String ground_bal) {
        this.ground_bal = ground_bal;
    }

    public String getBook_bal() {
        return book_bal;
    }

    public void setBook_bal(String book_bal) {
        this.book_bal = book_bal;
    }
}
