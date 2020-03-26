package com.example.twdinspection.common.network;


import com.example.twdinspection.engineering_works.source.SectorsResponse;
import com.example.twdinspection.engineering_works.source.WorksMasterResponse;
import com.example.twdinspection.gcc.source.divisions.GetOfficesResponse;
import com.example.twdinspection.gcc.reports.source.GCCReportResponse;
import com.example.twdinspection.schemes.reports.source.SchemeReportResponse;
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.example.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.example.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepotMasterResponse;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DRGoDownMasterResponse;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDownMasterResponse;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnitMasterResponse;
import com.example.twdinspection.inspection.source.login.LoginResponse;
import com.example.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.example.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.example.twdinspection.inspection.reports.source.InspReportResponse;
import com.example.twdinspection.inspection.reports.source.ReportCountsResponse;
import com.example.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.example.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.example.twdinspection.schemes.source.dmv.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.example.twdinspection.schemes.source.schemes.SchemeResponse;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

import org.checkerframework.common.reflection.qual.GetClass;

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
    Call<LoginResponse> getLoginResponse(@Query("username") String username, @Query("password") String password, @Query("deviceId") String deviceId);

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

    @POST("submitSchoolInspectionDetails")
    Call<InstSubmitResponse> getInstSubmitResponse(@Body InstSubmitRequest instSubmitRequest);

    @Multipart
    @POST("upload/uploadSchemePhotos")
    Call<SchemePhotoSubmitResponse> uploadSchemeImageCall(@Part MultipartBody.Part image, @Part MultipartBody.Part image2);

    @Multipart
    @POST("upload/uploadSchoolInspectionPhotos")
    Call<SchemePhotoSubmitResponse> uploadSchoolImageCall(@Part List<MultipartBody.Part> partList);
    @Multipart
    @POST("upload/uploadGCCInspectionPhotos")
    Call<GCCPhotoSubmitResponse> uploadGCCImageCall(@Part List<MultipartBody.Part> partList);

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


    @GET("CTWWorks/getWorksMaster")
    Call<SectorsResponse> getSectorsMaster();


    @POST("getGodowns/DR Depot")
    Call<DRDepotMasterResponse> getDRDepotMasterResponse();

    @POST("getGodowns/DR Godown")
    Call<DRGoDownMasterResponse> getDRGoDownMasterResponse();

    @POST("getGodowns/MFP Godown")
    Call<MFPGoDownMasterResponse> getMFPDownMasterResponse();

    @POST("getGodowns/Processing unit")
    Call<PUnitMasterResponse> getPUnitMasterResponse();


    @POST("getStockDetails/{id}")
    Call<StockDetailsResponse> getDRDepotMasterResponse(@Path("id") String id);

//    @POST("MasterData/ValidateUser")
//    Observable<ValidateUserResponse> getValidateUserResponse(@Body ValidateUserRequest validateUserRequest);


//    @POST("MasterData/LogoutService")
//    Observable<LogoutResponse> getLogoutResponse(@Body LogoutRequest logoutRequest);
//
//    //------------------- Registration ----------------------------------------
//
//
//    @POST("Farmer/GetFarmerDetailsData")
//    Observable<FarmerDetailsResponse> getFarRegResponse(@Body FarmerDetailsRequest farmerDetailsRequest);
//
//    @POST("Token/GetTokens")
//    Observable<GetTokensResponse> getTokensFarmerResponse(@Body GetTokensRequest getTokensFarmerRequest);
//
//    @POST("Procurement/SaveTokenRegistrationData")
//    Observable<FarmerSubmitResponse> farmerSubmitResponse(@Body FarmerSubmitRequest farmerSubmitRequest);
//
//    @POST("Token/SaveTokenData")
//    Observable<TokenGenerateResponse> tokenGenerateResponse(@Body TokenGenerationRequest tokenGenerationRequest);
//
//    @POST("Farmer/SaveandUpdateFarmerDetails")
//    Observable<FarmerUpdateResponse> getFarUpdateResponse(@Body FarmerUpdateRequest farmerUpdateRequest);
//
//    @POST("Farmer/GetFarmerDetailsData_New")
//    Observable<FarRegNew> getFarRegResponseNew(@Body FarmerDetailsRequest farmerDetailsRequest);
//
//    //------------------- Downloads ----------------------------------------
//
//
//    @POST("MasterData/Get_MasterBankDetails")
//    Observable<MasterBankMainResponse> getMasterBankResponse(@Body MasterBankRequest masterBankRequest);
//
//    @POST("MasterData/Get_MasterBankBranchDetails")
//    Observable<MasterBranchMainResponse> getMasterBranchResponse(@Body BankBranchRequest bankBranchRequest);
//
//    @POST("MasterData/Get_MasterDistrictDetails")
//    Observable<MasterDistrictMainResponse> getMasterDistrictResponse(@Body DMVRequest dmvRequest);
//
//    @POST("MasterData/Get_MasterMandalDetails")
//    Observable<MasterMandalMainResponse> getMasterMandalResponse(@Body DMVRequest dmvRequest);
//
//    @POST("MasterData/Get_MasterVillageDetails")
//    Observable<MasterVillageMainResponse> getMasterVillageResponse(@Body DMVRequest dmvRequest);
//
//    @POST("MasterData/Get_PaddyDetailsMaster")
//    Observable<PaddyTestResponse> getPaddyTestResponse(@Body PaddyTestRequest paddyTestRequest);
//
//    @POST("MasterData/GetSocialStatusDetails")
//    Observable<SocialStatusResponse> getSocialStatusResponse(@Body SocialStatusRequest socialStatusRequest);
//
//    //------------------- Rejected Tokens ----------------------------------------
//    @POST("Token/GetRejectTokens")
//    Observable<RejectedTokenResponse> getRejectedTokens(@Body RejectedTokenRequest rejectedTokenRequest);
//
//    @POST("FAQ/EnableToFAQ")
//    Observable<EnableFAQResponse> EnableToFAQ(@Body EnableFAQRequest enableFAQRequest);
//
//    //----------------- FAQ ----------------------------------------------------------
//
//    @POST("Token/GetTokens")
//    Observable<GetTokensResponse> getTokensFAQResponse(@Body GetTokensRequest getTokensFarmerRequest);
//
//    @POST("FAQ/SaveToFAQ")
//    Observable<FAQSubmitResponse> getFAQSubmitResponse(@Body FAQRequest faqRequest);
//
//    //----------------- Gunnys to Farmer ----------------------------------------------------------
//
//    @POST("Token/GetTokens")
//    Observable<GetTokensResponse> getTokensGunnyResponse(@Body GetTokensRequest getTokensGunnysRequest);
//
//    @POST("MasterData/Get_GunnyOrderDetails")
//    Observable<GunnyDetailsResponse> getGunnyDetails(@Body GunnyDetailsRequest gunnyDetailsRequest);
//
//    @POST("MasterData/SaveIssuingGunnyData")
//    Observable<GunnySubmitResponse> getGunnySubmitResponse(@Body GunnysSubmitRequest gunnysSubmitRequest);
//
//    //----------------- Paddy Procurement ----------------------------------------------------------
//
//    @POST("Token/GetTokens")
//    Observable<GetTokensResponse> getTokensProcurementResponse(@Body GetTokensRequest getTokensProcurementRequest);
//
//    @POST("MasterData/GetIssuedGunnyData")
//    Observable<IssuedGunnyDataResponse> GetIssuedGunnyDataResponse(@Body IssuedGunnyDataRequest issuedGunnyDataRequest);
//
//    @POST("Procurement/SaveProcurementData")
//    Observable<ProcurementSubmitResponse> SaveProcurementData(@Body PaddyProcurementSubmit paddyProcurementSubmit);
//
//
//
//    //----------------- Truckchit ----------------------------------------------------------
//
//
//    @POST("Procurement/GetOnlineTruckChitNo")
//    Observable<OnlineTCResponse> getOnlineTCResponse(@Body OnlineTCRequest onlineTCRequest);
//
//    @POST("Procurement/UniqueManualTruckchit")
//    Observable<ManualTCResponse> getManualTCResponse(@Body ManualTCRequest manualTCRequest);
//
//    @POST("MasterData/Get_MillerDetails")
//    Observable<MillerMainResponse> getMillerMasterResponse(@Body MillerMasterRequest millerMasterRequest);
//
//    @POST("Farmer/GetTransactions")
//    Observable<GetTransactionResponse> getTransactionResponse(@Body TransactionRequest transactionRequest);
//
//    @POST("MasterData/Get_TransportationDetails")
//    Observable<TransportResponse> getTransportResponse(@Body TransportDetailsRequest transportDetailsRequest);
//
//    @POST("MasterData/Get_VehicleDetails")
//    Observable<VehicleResponse> getVehicleResponse(@Body VehicleDetailsRequest vehicleDetailsRequest);
//
//    @POST("Procurement/SaveTruckchitData")
//    Observable<TCSubmitResponse> getTCSubmitResponse(@Body TCFinalSubmitRequest finalSubmitRequest);
//
//    @POST("MillerRO/GetROdetails")
//    Observable<ROInfoResponse> getROInfoResponse(@Body ROInfoRequest roInfoRequest);
//
//    //----------------- RePrint ----------------------------------------------------------
//
//
//    @POST("Procurement/PrintProcurementData")
//    Observable<ProcRePrintResponse> getProcRePrintResponse(@Body ProRePrintRequest proRePrintRequest);
//
//    @POST("Procurement/PrintTruckchitData")
//    Observable<TCRePrintResponse> getTCRePrintResponse(@Body TCRePrintRequest tcRePrintRequest);
//
//    @POST("Token/PrintToken")
//    Observable<TokenRePrintResponse> getTokenRePrintResponse(@Body TokenRePrintRequest tokenRePrintRequest);
//
//    //----------------- Reports ----------------------------------------------------------
//
//    @POST("Token/GetTokens")
//    Observable<GetTokensResponse> getTokensReportsResponse(@Body GetTokensRequest getTokensFarmerRequest);
//
//    @POST("Report/GetReportData")
//    Observable<ReportsDataResponce> getCommanReportsResponse(@Body CommonReportRequest commonReportRequest);
//
//    @POST("Farmer/GetTransactions")
//    Observable<TransactionReportResponseList> GetTransactionsResponse(@Body TransactionReportRequest transactionReportRequest);
//
//    @POST("MasterData/ChangePassword")
//    Observable<ChangePwdResponse> UpdatePwdResponse(@Body ChangePwdRequest changePwdRequest);
//
//
//    @POST("Report/GetPaddyPaymentReport")
//    Observable<PaddyPaymentInfoResponse> GetPaddyPaymentResponse(@Body PaymentInfoRequest paymentInfoRequest);

}



