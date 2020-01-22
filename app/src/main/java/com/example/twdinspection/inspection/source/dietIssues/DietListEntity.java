package com.example.twdinspection.inspection.source.dietIssues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Nullable;

@Entity(tableName = "DietListInfo")
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

    public DietListEntity(String item_name, Double ground_bal, Double book_bal) {
        this.item_name = item_name;
        this.ground_bal = ground_bal;
        this.book_bal = book_bal;
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
