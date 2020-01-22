package com.example.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.example.twdinspection.inspection.source.MedicalDetailsBean;
import com.example.twdinspection.inspection.source.medical_and_health.CallHealthInfoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MedicalRecordsConverter {
    @TypeConverter
    public static List<MedicalDetailsBean> stringToMedical(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MedicalDetailsBean>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String medicalToString(List<MedicalDetailsBean> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MedicalDetailsBean>>() {}.getType();
        return gson.toJson(list, type);
    }
}
