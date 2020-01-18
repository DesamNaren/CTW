package com.example.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ClassInfoConverter {
    @TypeConverter
    public static List<MasterClassInfo> stringToClasses(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MasterClassInfo>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String classesToString(List<MasterClassInfo> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MasterClassInfo>>() {}.getType();
        return gson.toJson(list, type);
    }
}
