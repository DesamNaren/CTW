package com.example.twdinspection.schemes.source;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "financialYrs")
public class FinancialYrsEntity {

    @ColumnInfo()
    private int id;

    @ColumnInfo()
    private int fin_year;

    @ColumnInfo()
    private int fin_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFin_year() {
        return fin_year;
    }

    public void setFin_year(int fin_year) {
        this.fin_year = fin_year;
    }

    public int getFin_id() {
        return fin_id;
    }

    public void setFin_id(int fin_id) {
        this.fin_id = fin_id;
    }
}
