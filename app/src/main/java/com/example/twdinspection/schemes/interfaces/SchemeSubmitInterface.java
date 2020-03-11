package com.example.twdinspection.schemes.interfaces;

import android.content.Context;

import com.example.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

public interface SchemeSubmitInterface {
    void getData(SchemeSubmitResponse schemeSubmitResponse);
    void getPhotoData(SchemePhotoSubmitResponse schemePhotoSubmitResponse);
}
