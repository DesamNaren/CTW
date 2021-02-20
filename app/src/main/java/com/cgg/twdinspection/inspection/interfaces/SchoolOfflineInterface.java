package com.cgg.twdinspection.inspection.interfaces;

public interface SchoolOfflineInterface {
    void schoolRecCount(int cnt);

    void deletedSchoolCount(int cnt);

    void deletedSchoolCountSubmitted(int cnt, String msg);

}