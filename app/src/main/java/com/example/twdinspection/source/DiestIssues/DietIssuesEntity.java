package com.example.twdinspection.source.DiestIssues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class DietIssuesEntity {
    @ColumnInfo()
    private String rice_book_bal;

    @ColumnInfo()
    private String rice_ground_bal;


    @ColumnInfo()
    private String rg_dal_book_bal;


    @ColumnInfo()
    private String rg_dal_ground_bal;

    @ColumnInfo()
    private String palm_oil_book_bal;


    @ColumnInfo()
    private String palm_oil_ground_bal;


    @ColumnInfo()
    private String eggs_book_bal;


    @ColumnInfo()
    private String eggs_ground_bal;


    @ColumnInfo()
    private String fruits_book_bal;


    @ColumnInfo()
    private String fruits_ground_bal;

    @ColumnInfo()
    private String menu_chart_painted;

    @ColumnInfo()
    private String prescribed_menu_served;

    @ColumnInfo()
    private String sample_food_provisions_provided;

    @ColumnInfo()
    private String food_provisions_matching;

    @ColumnInfo()
    private String mess_committee_exist;

    @ColumnInfo()
    private String discussed_mess_committee;

    @ColumnInfo()
    private String maintainnig_mess_register;

    public String getRice_book_bal() {
        return rice_book_bal;
    }

    public void setRice_book_bal(String rice_book_bal) {
        this.rice_book_bal = rice_book_bal;
    }

    public String getRice_ground_bal() {
        return rice_ground_bal;
    }

    public void setRice_ground_bal(String rice_ground_bal) {
        this.rice_ground_bal = rice_ground_bal;
    }

    public String getRg_dal_book_bal() {
        return rg_dal_book_bal;
    }

    public void setRg_dal_book_bal(String rg_dal_book_bal) {
        this.rg_dal_book_bal = rg_dal_book_bal;
    }

    public String getRg_dal_ground_bal() {
        return rg_dal_ground_bal;
    }

    public void setRg_dal_ground_bal(String rg_dal_ground_bal) {
        this.rg_dal_ground_bal = rg_dal_ground_bal;
    }

    public String getPalm_oil_book_bal() {
        return palm_oil_book_bal;
    }

    public void setPalm_oil_book_bal(String palm_oil_book_bal) {
        this.palm_oil_book_bal = palm_oil_book_bal;
    }

    public String getPalm_oil_ground_bal() {
        return palm_oil_ground_bal;
    }

    public void setPalm_oil_ground_bal(String palm_oil_ground_bal) {
        this.palm_oil_ground_bal = palm_oil_ground_bal;
    }

    public String getEggs_book_bal() {
        return eggs_book_bal;
    }

    public void setEggs_book_bal(String eggs_book_bal) {
        this.eggs_book_bal = eggs_book_bal;
    }

    public String getEggs_ground_bal() {
        return eggs_ground_bal;
    }

    public void setEggs_ground_bal(String eggs_ground_bal) {
        this.eggs_ground_bal = eggs_ground_bal;
    }

    public String getFruits_book_bal() {
        return fruits_book_bal;
    }

    public void setFruits_book_bal(String fruits_book_bal) {
        this.fruits_book_bal = fruits_book_bal;
    }

    public String getFruits_ground_bal() {
        return fruits_ground_bal;
    }

    public void setFruits_ground_bal(String fruits_ground_bal) {
        this.fruits_ground_bal = fruits_ground_bal;
    }

    public String getMenu_chart_painted() {
        return menu_chart_painted;
    }

    public void setMenu_chart_painted(String menu_chart_painted) {
        this.menu_chart_painted = menu_chart_painted;
    }

    public String getPrescribed_menu_served() {
        return prescribed_menu_served;
    }

    public void setPrescribed_menu_served(String prescribed_menu_served) {
        this.prescribed_menu_served = prescribed_menu_served;
    }

    public String getSample_food_provisions_provided() {
        return sample_food_provisions_provided;
    }

    public void setSample_food_provisions_provided(String sample_food_provisions_provided) {
        this.sample_food_provisions_provided = sample_food_provisions_provided;
    }

    public String getFood_provisions_matching() {
        return food_provisions_matching;
    }

    public void setFood_provisions_matching(String food_provisions_matching) {
        this.food_provisions_matching = food_provisions_matching;
    }

    public String getMess_committee_exist() {
        return mess_committee_exist;
    }

    public void setMess_committee_exist(String mess_committee_exist) {
        this.mess_committee_exist = mess_committee_exist;
    }

    public String getDiscussed_mess_committee() {
        return discussed_mess_committee;
    }

    public void setDiscussed_mess_committee(String discussed_mess_committee) {
        this.discussed_mess_committee = discussed_mess_committee;
    }

    public String getMaintainnig_mess_register() {
        return maintainnig_mess_register;
    }

    public void setMaintainnig_mess_register(String maintainnig_mess_register) {
        this.maintainnig_mess_register = maintainnig_mess_register;
    }
}
