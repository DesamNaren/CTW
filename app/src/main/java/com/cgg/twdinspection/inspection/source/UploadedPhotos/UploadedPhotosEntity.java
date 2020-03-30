package com.cgg.twdinspection.inspection.source.UploadedPhotos;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.sql.Blob;

@Entity
public class UploadedPhotosEntity {

    @ColumnInfo()
    private Blob storeroom;

    @ColumnInfo()
    private Blob varandah;

    @ColumnInfo()
    private Blob playground;

    @ColumnInfo()
    private Blob diningHall;

    @ColumnInfo()
    private Blob dormitory;

    @ColumnInfo()
    private Blob mainBulding;

    @ColumnInfo()
    private Blob toilet;

    @ColumnInfo()
    private Blob kitchen;

    @ColumnInfo()
    private Blob classroom;

    @ColumnInfo()
    private Blob dietIssues_menu;

    @ColumnInfo()
    private Blob dietIssues_inspectingOfficer;

    @ColumnInfo()
    private Blob infra_ROPlant;

    public Blob getStoreroom() {
        return storeroom;
    }

    public void setStoreroom(Blob storeroom) {
        this.storeroom = storeroom;
    }

    public Blob getVarandah() {
        return varandah;
    }

    public void setVarandah(Blob varandah) {
        this.varandah = varandah;
    }

    public Blob getPlayground() {
        return playground;
    }

    public void setPlayground(Blob playground) {
        this.playground = playground;
    }

    public Blob getDiningHall() {
        return diningHall;
    }

    public void setDiningHall(Blob diningHall) {
        this.diningHall = diningHall;
    }

    public Blob getDormitory() {
        return dormitory;
    }

    public void setDormitory(Blob dormitory) {
        this.dormitory = dormitory;
    }

    public Blob getMainBulding() {
        return mainBulding;
    }

    public void setMainBulding(Blob mainBulding) {
        this.mainBulding = mainBulding;
    }

    public Blob getToilet() {
        return toilet;
    }

    public void setToilet(Blob toilet) {
        this.toilet = toilet;
    }

    public Blob getKitchen() {
        return kitchen;
    }

    public void setKitchen(Blob kitchen) {
        this.kitchen = kitchen;
    }

    public Blob getClassroom() {
        return classroom;
    }

    public void setClassroom(Blob classroom) {
        this.classroom = classroom;
    }

    public Blob getDietIssues_menu() {
        return dietIssues_menu;
    }

    public void setDietIssues_menu(Blob dietIssues_menu) {
        this.dietIssues_menu = dietIssues_menu;
    }

    public Blob getDietIssues_inspectingOfficer() {
        return dietIssues_inspectingOfficer;
    }

    public void setDietIssues_inspectingOfficer(Blob dietIssues_inspectingOfficer) {
        this.dietIssues_inspectingOfficer = dietIssues_inspectingOfficer;
    }

    public Blob getInfra_ROPlant() {
        return infra_ROPlant;
    }

    public void setInfra_ROPlant(Blob infra_ROPlant) {
        this.infra_ROPlant = infra_ROPlant;
    }
}
