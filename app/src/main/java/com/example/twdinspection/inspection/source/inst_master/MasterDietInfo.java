package com.example.twdinspection.inspection.source.inst_master;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MasterDietInfo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("ground_bal")
    @Expose
    private Double groundBal;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("book_bal")
    @Expose
    private Double bookBal;

    @SerializedName("Inst_Id")
    @Expose
    private Integer instId;
    @SerializedName("Inst_Name")
    @Expose
    private String instName;

    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getGroundBal() {
        return groundBal;
    }

    public void setGroundBal(Double groundBal) {
        this.groundBal = groundBal;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getBookBal() {
        return bookBal;
    }

    public void setBookBal(Double bookBal) {
        this.bookBal = bookBal;
    }

}
