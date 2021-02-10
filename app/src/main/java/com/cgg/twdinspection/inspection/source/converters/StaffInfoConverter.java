package com.cgg.twdinspection.inspection.source.converters;

import androidx.room.TypeConverter;

import com.cgg.twdinspection.inspection.source.inst_master.MasterStaffInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StaffInfoConverter {
    @TypeConverter
    public static List<MasterStaffInfo> stringToStaff(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MasterStaffInfo>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @TypeConverter
    public static String staffToString(List<MasterStaffInfo> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<MasterStaffInfo>>() {
        }.getType();
        return gson.toJson(list, type);
    }
}
