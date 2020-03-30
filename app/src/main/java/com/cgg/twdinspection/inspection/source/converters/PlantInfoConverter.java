package com.cgg.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
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
