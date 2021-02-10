package com.cgg.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.cgg.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AcademicGradeConverter {
    @TypeConverter
    public static List<AcademicGradeEntity> stringToGrade(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<AcademicGradeEntity>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String gradeToString(List<AcademicGradeEntity> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<AcademicGradeEntity>>() {
        }.getType();
        return gson.toJson(list, type);
    }
}
