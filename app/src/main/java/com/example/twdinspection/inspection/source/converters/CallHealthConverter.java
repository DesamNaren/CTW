package com.example.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CallHealthConverter {
    @TypeConverter
    public static List<CallHealthInfoEntity> stringToCallHealth(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<CallHealthInfoEntity>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String callHealthToString(List<CallHealthInfoEntity> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<CallHealthInfoEntity>>() {}.getType();
        return gson.toJson(list, type);
    }
}
