package com.cgg.twdinspection.common.network;


import com.cgg.twdinspection.engineering_works.source.GrantSchemesResponse;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.StagesResponse;
import com.cgg.twdinspection.engineering_works.source.SubmitEngWorksRequest;
import com.cgg.twdinspection.engineering_works.source.WorksMasterResponse;
import com.cgg.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.cgg.twdinspection.gcc.reports.source.GCCReportResponse;
import com.cgg.twdinspection.gcc.source.suppliers.lpg.LPGMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolPumpMasterResponse;
import com.cgg.twdinspection.inspection.source.version_check.VersionResponse;
import com.cgg.twdinspection.schemes.reports.source.SchemeReportResponse;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.cgg.twdinspection.gcc.source.suppliers.depot.DRDepotMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.dr_godown.DRGoDownMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDownMasterResponse;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnitMasterResponse;
import com.cgg.twdinspection.inspection.source.login.LoginResponse;
import com.cgg.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.cgg.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.cgg.twdinspection.inspection.reports.source.InspReportResponse;
import com.cgg.twdinspection.inspection.reports.source.ReportCountsResponse;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.cgg.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.cgg.twdinspection.schemes.source.dmv.SchemeDMVResponse;
import com.cgg.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.cgg.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.cgg.twdinspection.schemes.source.schemes.SchemeResponse;
import com.cgg.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TWDService {
    class Factory {
        public static TWDService create(String type) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
            httpClient.writeTimeout(60, TimeUnit.SECONDS);

            String BASEURL = "";
            if (type.equals("school")) {
                BASEURL = TWDURL.TWD_BASE_URL;
            } else if (type.equals("gcc")) {
//                httpClient.addInterceptor(new BasicAuthInterceptor(AppConstants.GCC_AUTH_USER, AppConstants.GCC_AUTH_PWD));
                BASEURL = TWDURL.GCC_BASE_URL;
            } else {
                BASEURL = TWDURL.SCHEME_BASE_URL;
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();
            return retrofit.create(TWDService.class);
        }

    }

    @GET("CTWServiceDetails/validateLogin")
    Call<LoginResponse> getLoginResponse(@Query("username") String username, @Query("password") String password, @Query("Device_Id") String deviceId, @Query("Version_No") String versionNo);


    @GET("CTWServiceDetails/getAppVersion")
    Call<VersionResponse> getVersionCheck();

    @GET("CTWServiceDetails/getSummaryInfo")
    Call<ReportCountsResponse> getReportCounts(@Query("officerId") String username);

    @GET("CTWServiceDetails/getGCCDetails")
    Call<GCCReportResponse> getGCCReports(@Query("officerId") String username);

    @GET("CTWServiceDetails/getSchemeDetails") //
    Call<SchemeReportResponse> getSchemeReports(@Query("officerId") String username);

   @GET("CTWServiceDetails/getSchoolDetails")
    Call<InspReportResponse> getInspectionReports(@Query("officerId") String username);

    @GET("getBenificiaryDetails")
    Call<BeneficiaryReport> getBeneficiaryDetails(@Query("distId") int distId, @Query("mandalId") int mandalId, @Query("villageId") int villageId, @Query("finYearId") String finYearId);

    @GET("getInspectionRemarks")
    Call<InspectionRemarkResponse> getInspectionRemarks();

    @GET("getDMVMasters")
    Call<SchemeDMVResponse> getSchemeDMV();

    @GET("getFinancialYears")
    Call<FinancialYearResponse> getFinancialYears();

    @GET("getSchemes")
    Call<SchemeResponse> getSchemeResponse();

    @POST("submitSchemeInspectionDetails")
    Call<SchemeSubmitResponse> getSchemeSubmitResponse(@Body SchemeSubmitRequest schemeSubmitRequest);

    @POST("submitGCCInspectionDetails")
    Call<GCCSubmitResponse> getGCCSubmitResponse(@Body GCCSubmitRequest gccSubmitRequest);

    @POST("CTWWorks/submitWorkInspectionDetails")
    Call<GCCSubmitResponse> getEngWorksSubmitResponse(@Body SubmitEngWorksRequest gccSubmitRequest);

    @POST("submitSchoolInspectionDetails")
    Call<InstSubmitResponse> getInstSubmitResponse(@Body InstSubmitRequest instSubmitRequest);

    @Multipart
    @POST("upload/uploadSchemeInspectionPhotos")
    Call<SchemePhotoSubmitResponse> uploadSchemeImageCall(@Part MultipartBody.Part image, @Part MultipartBody.Part image2);

    @Multipart
    @POST("upload/uploadSchoolInspectionPhotos")
    Call<SchemePhotoSubmitResponse> uploadSchoolImageCall(@Part List<MultipartBody.Part> partList);

    @Multipart
    @POST("upload/uploadGCCInspectionPhotos")
    Call<GCCPhotoSubmitResponse> uploadGCCImageCall(@Part List<MultipartBody.Part> partList);

    @Multipart
    @POST("upload/uploadEngineeringWorksPhotos")
    Call<GCCPhotoSubmitResponse> uploadEngPhotoCall(@Part List<MultipartBody.Part> partList);

    @GET("CTWServiceDetails/getDMVMasters")
    Call<SchoolDMVResponse> getSchoolDMV(@Query("userId") String officerId);

    @GET("CTWServiceDetails/getInstInfo")
    Call<InstMasterResponse> getInstMasterResponse();
    //------------------- Login & Logout ----------------------------------------


    //------------------- GCC ----------------------------------------

    @POST("getOffices")
    Call<GetOfficesResponse> getDivisionMasterResponse();


    @GET("CTWWorks/getWorksMaster")
    Call<WorksMasterResponse> getWorksMaster();


    @GET("CTWWorks/getSectors")
    Call<SectorsResponse> getSectorsMaster();


    @GET("CTWWorks/getGrantSandSchemes")
    Call<GrantSchemesResponse> getGrantSandSchemes();

    @GET("CTWWorks/getStages")
    Call<StagesResponse> getStages(@Query("sector_id")int sectorId);

    @POST("getGodowns/DR Depot")
    Call<DRDepotMasterResponse> getDRDepotMasterResponse();

    @POST("getGodowns/DR Godown")
    Call<DRGoDownMasterResponse> getDRGoDownMasterResponse();

    @POST("getGodowns/MFP Godown")
    Call<MFPGoDownMasterResponse> getMFPDownMasterResponse();

    @POST("getGodowns/Processing unit")
    Call<PUnitMasterResponse> getPUnitMasterResponse();


    @POST("getGodowns/Petrolpump")
    Call<PetrolPumpMasterResponse> getPetrolPumpMasterResponse();

    @POST("getGodowns/LPG")
    Call<LPGMasterResponse> getLPGMasterResponse();

    @POST("getStockDetails/{id}")
    Call<StockDetailsResponse> getDRDepotMasterResponse(@Path("id") String id);


}



