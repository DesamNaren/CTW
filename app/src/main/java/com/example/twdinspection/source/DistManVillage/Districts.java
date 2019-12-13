package com.example.twdinspection.source.DistManVillage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Districts {
    @PrimaryKey
    public int id;
    public String dist_name;

    public int dist_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDist_id() {
        return dist_id;
    }

    public void setDist_id(int dist_id) {
        this.dist_id = dist_id;
    }

    public String getDist_name() {
        return dist_name;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }
}
