package com.example.twdinspection.inspection.source.general_comments;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "general_comments_info")
public class GeneralCommentsEntity {

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
    private String supplied_on_time_fruits;

    @ColumnInfo()
    private String supplied_on_time_eggs;

    @ColumnInfo()
    private String supplied_on_time_vegetables;

    @ColumnInfo()
    private String supplied_on_time_food_provisions;

    @ColumnInfo()
    private String quality_of_food_fruits;

    @ColumnInfo()
    private String quality_of_food_eggs;

    @ColumnInfo()
    private String quality_of_food_vegetables;

    @ColumnInfo()
    private String quality_of_food_food_provisions;

    @ColumnInfo()
    private String gcc_date;

    @ColumnInfo()
    private String supplied_date;

    @ColumnInfo()
    private String stocksSupplied;

    @ColumnInfo()
    private String hair_cut_onTime;

    @ColumnInfo()
    private String stud_found_anemic;

    @ColumnInfo()
    private String stud_attire;

    @ColumnInfo()
    private String cook_wearing_cap;

    @ColumnInfo()
    private String staff_hands_clean;

    @ColumnInfo()
    private String staff_attire;

    @ColumnInfo()
    private String toilets_not_clean;

    @ColumnInfo()
    private String kitchen_not_clean;

    @ColumnInfo()
    private String dormitory_not_clean;

    @ColumnInfo()
    private String classroom_not_clean;

    @ColumnInfo()
    private String water_available;

    @ColumnInfo()
    private String storeroom_not_clean;


    public String getGcc_date() {
        return gcc_date;
    }

    public void setGcc_date(String gcc_date) {
        this.gcc_date = gcc_date;
    }

    public String getStocksSupplied() {
        return stocksSupplied;
    }

    public void setStocksSupplied(String stocksSupplied) {
        this.stocksSupplied = stocksSupplied;
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

    public String getInstitute_id() {
        return institute_id;
    }

    public void setInstitute_id(String institute_id) {
        this.institute_id = institute_id;
    }

    public String getSupplied_on_time_fruits() {
        return supplied_on_time_fruits;
    }

    public void setSupplied_on_time_fruits(String supplied_on_time_fruits) {
        this.supplied_on_time_fruits = supplied_on_time_fruits;
    }

    public String getSupplied_on_time_eggs() {
        return supplied_on_time_eggs;
    }

    public void setSupplied_on_time_eggs(String supplied_on_time_eggs) {
        this.supplied_on_time_eggs = supplied_on_time_eggs;
    }

    public String getSupplied_on_time_vegetables() {
        return supplied_on_time_vegetables;
    }

    public void setSupplied_on_time_vegetables(String supplied_on_time_vegetables) {
        this.supplied_on_time_vegetables = supplied_on_time_vegetables;
    }

    public String getSupplied_on_time_food_provisions() {
        return supplied_on_time_food_provisions;
    }

    public void setSupplied_on_time_food_provisions(String supplied_on_time_food_provisions) {
        this.supplied_on_time_food_provisions = supplied_on_time_food_provisions;
    }

    public String getQuality_of_food_fruits() {
        return quality_of_food_fruits;
    }

    public void setQuality_of_food_fruits(String quality_of_food_fruits) {
        this.quality_of_food_fruits = quality_of_food_fruits;
    }

    public String getQuality_of_food_eggs() {
        return quality_of_food_eggs;
    }

    public void setQuality_of_food_eggs(String quality_of_food_eggs) {
        this.quality_of_food_eggs = quality_of_food_eggs;
    }

    public String getQuality_of_food_vegetables() {
        return quality_of_food_vegetables;
    }

    public void setQuality_of_food_vegetables(String quality_of_food_vegetables) {
        this.quality_of_food_vegetables = quality_of_food_vegetables;
    }

    public String getQuality_of_food_food_provisions() {
        return quality_of_food_food_provisions;
    }

    public void setQuality_of_food_food_provisions(String quality_of_food_food_provisions) {
        this.quality_of_food_food_provisions = quality_of_food_food_provisions;
    }

    public String getSupplied_date() {
        return supplied_date;
    }

    public void setSupplied_date(String supplied_date) {
        this.supplied_date = supplied_date;
    }

    public String getHair_cut_onTime() {
        return hair_cut_onTime;
    }

    public void setHair_cut_onTime(String hair_cut_onTime) {
        this.hair_cut_onTime = hair_cut_onTime;
    }

    public String getStud_found_anemic() {
        return stud_found_anemic;
    }

    public void setStud_found_anemic(String stud_found_anemic) {
        this.stud_found_anemic = stud_found_anemic;
    }

    public String getStud_attire() {
        return stud_attire;
    }

    public void setStud_attire(String stud_attire) {
        this.stud_attire = stud_attire;
    }

    public String getCook_wearing_cap() {
        return cook_wearing_cap;
    }

    public void setCook_wearing_cap(String cook_wearing_cap) {
        this.cook_wearing_cap = cook_wearing_cap;
    }

    public String getStaff_hands_clean() {
        return staff_hands_clean;
    }

    public void setStaff_hands_clean(String staff_hands_clean) {
        this.staff_hands_clean = staff_hands_clean;
    }

    public String getStaff_attire() {
        return staff_attire;
    }

    public void setStaff_attire(String staff_attire) {
        this.staff_attire = staff_attire;
    }

    public String getToilets_not_clean() {
        return toilets_not_clean;
    }

    public void setToilets_not_clean(String toilets_not_clean) {
        this.toilets_not_clean = toilets_not_clean;
    }

    public String getKitchen_not_clean() {
        return kitchen_not_clean;
    }

    public void setKitchen_not_clean(String kitchen_not_clean) {
        this.kitchen_not_clean = kitchen_not_clean;
    }

    public String getDormitory_not_clean() {
        return dormitory_not_clean;
    }

    public void setDormitory_not_clean(String dormitory_not_clean) {
        this.dormitory_not_clean = dormitory_not_clean;
    }

    public String getClassroom_not_clean() {
        return classroom_not_clean;
    }

    public void setClassroom_not_clean(String classroom_not_clean) {
        this.classroom_not_clean = classroom_not_clean;
    }

    public String getWater_available() {
        return water_available;
    }

    public void setWater_available(String water_available) {
        this.water_available = water_available;
    }

    public String getStoreroom_not_clean() {
        return storeroom_not_clean;
    }

    public void setStoreroom_not_clean(String storeroom_not_clean) {
        this.storeroom_not_clean = storeroom_not_clean;
    }
}
