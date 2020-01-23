package com.example.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StudentAchConverter {
    @TypeConverter
    public static List<StudAchievementEntity> stringToAch(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<StudAchievementEntity>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String achToString(List<StudAchievementEntity> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<StudAchievementEntity>>() {}.getType();
        return gson.toJson(list, type);
    }
}
