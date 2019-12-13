package com.example.twdinspection.source.DistManVillage;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Villages {
    @PrimaryKey
    public int id;
    public int dist_id;

    public int mandal_id;
    public int village_id;
    public String village_name;

    public int getVillage_id() {
        return village_id;
    }

    public void setVillage_id(int village_id) {
        this.village_id = village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

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

}
