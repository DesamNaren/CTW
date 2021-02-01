package com.cgg.twdinspection.inspection.source.academic_overview;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cgg.twdinspection.inspection.source.converters.AcademicGradeConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AcademicSubjectsEntity {

    private String name;
    private boolean status;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
