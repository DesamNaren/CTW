package com.example.twdinspection.common.network;


import com.example.twdinspection.BuildConfig;
import com.example.twdinspection.inspection.source.EmployeeResponse;
import com.example.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.example.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.example.twdinspection.schemes.source.DMV.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryReport;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.example.twdinspection.schemes.source.schemes.SchemeResponse;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TWDService {
    class Factory {
        public static TWDService create(String type) {
            String BASEURL="";
            if(type.equals("school")){
                BASEURL = TWDURL.TWD_BASE_URL;
            }else {
                BASEURL = TWDURL.SCHEME_BASE_URL;
            }

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                // development build
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                // production build
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }

            httpClient.addInterceptor(interceptor);
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
            httpClient.writeTimeout(60, TimeUnit.SECONDS);

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
    Call<EmployeeResponse> getLoginResponse(@Query("username") String username, @Query("password") String password, @Query("deviceId") String deviceId);

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

    @POST("CTWSubmitDetails/submitSchemeInspectionDetails")
    Call<SchemeSubmitResponse> getSchemeSubmitResponse(@Body SchemeSubmitRequest schemeSubmitRequest);

    @Multipart
    @POST("upload/uploadSchemePhotos")
    Call<SchemePhotoSubmitResponse> uploadSchemeImageCall(@Part MultipartBody.Part image,@Part MultipartBody.Part image2);

    @Multipart
    @POST("upload/uploadPhotos")
    Call<SchemePhotoSubmitResponse> uploadSchemeImageCall(@Part MultipartBody.Part image,@Part MultipartBody.Part image1,@Part MultipartBody.Part image2,
                                                          @Part MultipartBody.Part image3,@Part MultipartBody.Part image4,@Part MultipartBody.Part image5,
                                                          @Part MultipartBody.Part image6,@Part MultipartBody.Part image7,@Part MultipartBody.Part image8);

    @GET("CTWServiceDetails/getDMVMasters")
    Call<SchoolDMVResponse> getSchoolDMV(@Query("userId") String officerId);

    @GET("CTWServiceDetails/getInstInfo")
    Call<InstMasterResponse> getInstMasterResponse();
    //------------------- Login & Logout ----------------------------------------

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



