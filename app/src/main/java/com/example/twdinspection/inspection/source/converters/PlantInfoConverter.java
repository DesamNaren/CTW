package com.example.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.example.twdinspection.inspection.source.cocurriularActivities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PlantInfoConverter {
    @TypeConverter
    public static List<PlantsEntity> stringToPlants(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<PlantsEntity>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String plantsToString(List<PlantsEntity> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<PlantsEntity>>() {}.getType();
        return gson.toJson(list, type);
    }
}
