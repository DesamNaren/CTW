package com.example.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterDietInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DietInfoConverter {
    @TypeConverter
    public static List<MasterDietInfo> stringToDiet(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MasterDietInfo>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String dietToString(List<MasterDietInfo> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MasterDietInfo>>() {}.getType();
        return gson.toJson(list, type);
    }
}
