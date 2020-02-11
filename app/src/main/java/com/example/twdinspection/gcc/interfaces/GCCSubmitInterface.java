package com.example.twdinspection.gcc.interfaces;

import com.example.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.example.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

public interface GCCSubmitInterface {
    void getData(GCCSubmitResponse gccSubmitResponse);
    void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse);
}
