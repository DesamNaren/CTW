package com.example.twdinspection.inspection.interfaces;

import com.example.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

public interface InstSubmitInterface {
    void getSubmitData(InstSubmitResponse schemeSubmitResponse);
}
