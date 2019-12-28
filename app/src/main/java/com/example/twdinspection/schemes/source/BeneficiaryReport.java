package com.example.twdinspection.schemes.source;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class BeneficiaryReport {

    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private String officer_id;

    @ColumnInfo()
    private String inspection_time;

    @ColumnInfo()
    private int ben_id;

    @ColumnInfo()
    private String ben_name;

    @ColumnInfo()
    private String activity;

    @ColumnInfo()
    private String unit_cost;

    @ColumnInfo()
    private String subsidy;

    @ColumnInfo()
    private String bank_loan;

    @ColumnInfo()
    private String contribution;

    @ColumnInfo()
    private String status;

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

    public int getBen_id() {
        return ben_id;
    }

    public void setBen_id(int ben_id) {
        this.ben_id = ben_id;
    }

    public String getBen_name() {
        return ben_name;
    }

    public void setBen_name(String ben_name) {
        this.ben_name = ben_name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getUnit_cost() {
        return unit_cost;
    }

    public void setUnit_cost(String unit_cost) {
        this.unit_cost = unit_cost;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getBank_loan() {
        return bank_loan;
    }

    public void setBank_loan(String bank_loan) {
        this.bank_loan = bank_loan;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
