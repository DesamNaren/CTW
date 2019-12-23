package com.example.twdinspection.inspection.source.DistManVillage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Mandals {
    @PrimaryKey
    public int id;
    public int dist_id;

    public int mandal_id;
    public String mandal_name;

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

    public int getMandal_id() {
        return mandal_id;
    }

    public void setMandal_id(int mandal_id) {
        this.mandal_id = mandal_id;
    }

    public String getMandal_name() {
        return mandal_name;
    }

    public void setMandal_name(String mandal_name) {
        this.mandal_name = mandal_name;
    }
}
