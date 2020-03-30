package com.cgg.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DietIssuesConverter {
    @TypeConverter
    public static List<DietListEntity> stringToDiet(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DietListEntity>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String dietToString(List<DietListEntity> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DietListEntity>>() {}.getType();
        return gson.toJson(list, type);
    }
}
