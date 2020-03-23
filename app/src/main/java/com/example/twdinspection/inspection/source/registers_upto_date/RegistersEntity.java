package com.example.twdinspection.inspection.source.registers_upto_date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "registers_info")
public class RegistersEntity {

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
    private String admission_reg;

    @ColumnInfo()
    private String attendance_reg;


    @ColumnInfo()
    private String boarder_movement_reg;

    @ColumnInfo()
    private String daily_purchase_reg;

    @ColumnInfo()
    private String cash_book_reg;

    @ColumnInfo()
    private String daily_menu_reg;

    @ColumnInfo()
    private String attend_staff_reg;

    @ColumnInfo()
    private String staff_order_book;

    @ColumnInfo()
    private String stock_issue_prov;

    @ColumnInfo()
    private String permanent_article;

    @ColumnInfo()
    private String budget_watch;

    @ColumnInfo()
    private String acquaintance_reg;

    @ColumnInfo()
    private String acquaintance_dress;

    @ColumnInfo()
    private String acquiantance_cosmetic;

    @ColumnInfo()
    private String paybill_reg;

    @ColumnInfo()
    private String treasury_bill;

    @ColumnInfo()
    private String contingent_bill;

    @ColumnInfo()
    private String daily_movement;

    @ColumnInfo()
    private String teacher_movement;

    @ColumnInfo()
    private String CL_account;

    @ColumnInfo()
    private String parents_meeting;

    @ColumnInfo()
    private String inspection_reg;

    @ColumnInfo()
    private String visit_book;

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

    public String getAdmission_reg() {
        return admission_reg;
    }

    public void setAdmission_reg(String admission_reg) {
        this.admission_reg = admission_reg;
    }

    public String getAttendance_reg() {
        return attendance_reg;
    }

    public void setAttendance_reg(String attendance_reg) {
        this.attendance_reg = attendance_reg;
    }

    public String getBoarder_movement_reg() {
        return boarder_movement_reg;
    }

    public void setBoarder_movement_reg(String boarder_movement_reg) {
        this.boarder_movement_reg = boarder_movement_reg;
    }

    public String getDaily_purchase_reg() {
        return daily_purchase_reg;
    }

    public void setDaily_purchase_reg(String daily_purchase_reg) {
        this.daily_purchase_reg = daily_purchase_reg;
    }

    public String getCash_book_reg() {
        return cash_book_reg;
    }

    public void setCash_book_reg(String cash_book_reg) {
        this.cash_book_reg = cash_book_reg;
    }

    public String getDaily_menu_reg() {
        return daily_menu_reg;
    }

    public void setDaily_menu_reg(String daily_menu_reg) {
        this.daily_menu_reg = daily_menu_reg;
    }

    public String getAttend_staff_reg() {
        return attend_staff_reg;
    }

    public void setAttend_staff_reg(String attend_staff_reg) {
        this.attend_staff_reg = attend_staff_reg;
    }

    public String getStaff_order_book() {
        return staff_order_book;
    }

    public void setStaff_order_book(String staff_order_book) {
        this.staff_order_book = staff_order_book;
    }

    public String getStock_issue_prov() {
        return stock_issue_prov;
    }

    public void setStock_issue_prov(String stock_issue_prov) {
        this.stock_issue_prov = stock_issue_prov;
    }

    public String getPermanent_article() {
        return permanent_article;
    }

    public void setPermanent_article(String permanent_article) {
        this.permanent_article = permanent_article;
    }

    public String getBudget_watch() {
        return budget_watch;
    }

    public void setBudget_watch(String budget_watch) {
        this.budget_watch = budget_watch;
    }

    public String getAcquaintance_reg() {
        return acquaintance_reg;
    }

    public void setAcquaintance_reg(String acquaintance_reg) {
        this.acquaintance_reg = acquaintance_reg;
    }

    public String getAcquaintance_dress() {
        return acquaintance_dress;
    }

    public void setAcquaintance_dress(String acquaintance_dress) {
        this.acquaintance_dress = acquaintance_dress;
    }

    public String getAcquiantance_cosmetic() {
        return acquiantance_cosmetic;
    }

    public void setAcquiantance_cosmetic(String acquiantance_cosmetic) {
        this.acquiantance_cosmetic = acquiantance_cosmetic;
    }

    public String getPaybill_reg() {
        return paybill_reg;
    }

    public void setPaybill_reg(String paybill_reg) {
        this.paybill_reg = paybill_reg;
    }

    public String getTreasury_bill() {
        return treasury_bill;
    }

    public void setTreasury_bill(String treasury_bill) {
        this.treasury_bill = treasury_bill;
    }

    public String getContingent_bill() {
        return contingent_bill;
    }

    public void setContingent_bill(String contingent_bill) {
        this.contingent_bill = contingent_bill;
    }

    public String getDaily_movement() {
        return daily_movement;
    }

    public void setDaily_movement(String daily_movement) {
        this.daily_movement = daily_movement;
    }

    public String getTeacher_movement() {
        return teacher_movement;
    }

    public void setTeacher_movement(String teacher_movement) {
        this.teacher_movement = teacher_movement;
    }

    public String getCL_account() {
        return CL_account;
    }

    public void setCL_account(String CL_account) {
        this.CL_account = CL_account;
    }

    public String getParents_meeting() {
        return parents_meeting;
    }

    public void setParents_meeting(String parents_meeting) {
        this.parents_meeting = parents_meeting;
    }

    public String getInspection_reg() {
        return inspection_reg;
    }

    public void setInspection_reg(String inspection_reg) {
        this.inspection_reg = inspection_reg;
    }

    public String getVisit_book() {
        return visit_book;
    }

    public void setVisit_book(String visit_book) {
        this.visit_book = visit_book;
    }
}
