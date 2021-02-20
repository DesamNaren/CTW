//package com.cgg.twdinspection.offline;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.res.Resources;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.Window;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.databinding.DataBindingUtil;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.cgg.twdinspection.R;
//import com.cgg.twdinspection.common.application.TWDApplication;
//import com.cgg.twdinspection.common.utils.AppConstants;
//import com.cgg.twdinspection.common.utils.CustomProgressDialog;
//import com.cgg.twdinspection.common.utils.ErrorHandler;
//import com.cgg.twdinspection.common.utils.Utils;
//import com.cgg.twdinspection.databinding.ActivitySchoolsOfflineReportBinding;
//import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
//import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
//import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
//import com.cgg.twdinspection.gcc.source.offline.SchoolsOfflineEntity;
//import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
//import com.cgg.twdinspection.gcc.source.submit.GCCSubmitRequest;
//import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;
//import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
//import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoCustomViewModel;
//import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoViewModel;
//import com.cgg.twdinspection.inspection.interfaces.SchoolsOfflineSubmitInterface;
//import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
//import com.cgg.twdinspection.inspection.room.repository.SchoolsOfflineRepository;
//import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
//import com.cgg.twdinspection.inspection.viewmodel.SchoolsOfflineViewModel;
//import com.cgg.twdinspection.offline.interfaces.GCCOfflineSubmitInterface;
//import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//
//public class SchoolOfflineDataActivity extends AppCompatActivity implements SchoolsOfflineSubmitInterface,
//        GCCOfflineInterface, GCCSubmitInterface, ErrorHandlerInterface {
//
//    ActivitySchoolsOfflineReportBinding offlineReportBinding;
//    private CustomProgressDialog customProgressDialog;
//    SharedPreferences sharedPreferences;
//    SharedPreferences.Editor editor;
//    private SchoolsOfflineRepository schoolsOfflineRepository;
//    private GCCPhotoViewModel gccPhotoViewModel;
//    private SchoolsOfflineEntity schoolsOfflineEntity;
//    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_IMAGES";
//    public static String IMAGE_DIRECTORY_NAME_MODE = AppConstants.OFFLINE;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        customProgressDialog = new CustomProgressDialog(this);
//        sharedPreferences = TWDApplication.get(this).getPreferences();
//        editor = sharedPreferences.edit();
//
//        offlineReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_schools_offline_report);
//        offlineReportBinding.executePendingBindings();
//        offlineReportBinding.header.headerTitle.setText(getString(R.string.schools_reports_offline_data));
//
//        offlineReportBinding.header.ivHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SchoolOfflineDataActivity.this, DashboardMenuActivity.class)
//                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//                finish();
//            }
//        });
//        schoolsOfflineRepository = new SchoolsOfflineRepository(getApplication());
//        gccPhotoViewModel = ViewModelProviders.of(this,
//                new GCCPhotoCustomViewModel(this)).get(GCCPhotoViewModel.class);
//        offlineReportBinding.setViewModel(gccPhotoViewModel);
//        offlineReportBinding.executePendingBindings();
//
//        offlineReportBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        try {
//            SchoolsOfflineViewModel schoolsOfflineViewModel = new SchoolsOfflineViewModel(getApplication());
//            LiveData<List<SchoolsOfflineEntity>> listLiveData = schoolsOfflineViewModel.getSchoolsOffline();
//            listLiveData.observe(SchoolOfflineDataActivity.this, new Observer<List<SchoolsOfflineEntity>>() {
//                @Override
//                public void onChanged(List<SchoolsOfflineEntity> offlineEntities) {
//                    listLiveData.removeObservers(SchoolOfflineDataActivity.this);
//                    if (offlineEntities != null && offlineEntities.size() > 0) {
//                        offlineReportBinding.tvEmpty.setVisibility(View.GONE);
//                        offlineReportBinding.recyclerView.setVisibility(View.VISIBLE);
//
//                        SchoolsOfflineDataAdapter adapter = new SchoolsOfflineDataAdapter(SchoolOfflineDataActivity.this, offlineEntities);
//                        offlineReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(SchoolOfflineDataActivity.this));
//                        offlineReportBinding.recyclerView.setAdapter(adapter);
//                    } else {
//                        offlineReportBinding.tvEmpty.setVisibility(View.VISIBLE);
//                        offlineReportBinding.recyclerView.setVisibility(View.GONE);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//        }
//        return true;
//    }
//
//    void callSnackBar(String msg) {
//        Snackbar snackbar = Snackbar.make(offlineReportBinding.root, msg, Snackbar.LENGTH_INDEFINITE);
//        snackbar.setActionTextColor(getResources().getColor(R.color.white));
//        snackbar.setAction("OK", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                snackbar.dismiss();
//            }
//        });
//        snackbar.show();
//    }
//
//    @Override
//    public void submitRecord(SchoolsOfflineEntity entity) {
//        customSubmitAlert(getResources().getString(R.string.are_you_sure), true, entity);
//    }
//
//    @Override
//    public void deleteRecord(SchoolsOfflineEntity entity) {
//        customSubmitAlert(getResources().getString(R.string.are_you_sure_remove), false, entity);
//    }
//
//    private void customSubmitAlert(String msg, boolean flag, SchoolsOfflineEntity entity) {
//        try {
//            final Dialog dialog = new Dialog(SchoolOfflineDataActivity.this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
//                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.setContentView(R.layout.custom_alert_confirmation);
//                dialog.setCancelable(false);
//                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
//                dialogTitle.setText(getString(R.string.app_name));
//                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
//                dialogMessage.setText(msg);
//                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
//                btDialogNo.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                    }
//                });
//
//                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
//                btDialogYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (dialog.isShowing()) {
//                            dialog.dismiss();
//                        }
//                        if (flag) {
//                            if (Utils.checkInternetConnection(SchoolOfflineDataActivity.this)) {
//                                schoolsOfflineEntity = entity;
//                                GCCSubmitRequest request = new Gson().fromJson(schoolsOfflineEntity.getData(), GCCSubmitRequest.class);
//
//                                customProgressDialog.show();
//                                gccPhotoViewModel.submitGCCDetails(request);
//                            } else {
//                                Utils.customWarningAlert(SchoolOfflineDataActivity.this, getResources().getString(R.string.app_name), "Please check internet");
//                            }
//                        } else {
//                            File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME +
//                                    "/" + IMAGE_DIRECTORY_NAME_MODE + "/" + fromClass + "_" + entity.getDrgownId());
//
//                            if (mediaStorageDir.isDirectory()) {
//                                String[] children = mediaStorageDir.list();
//                                for (int i = 0; i < children.length; i++)
//                                    new File(mediaStorageDir, children[i]).delete();
//                                mediaStorageDir.delete();
//                            }
//                            schoolsOfflineRepository.deleteGCCRecord(SchoolOfflineDataActivity.this, entity);
//                        }
//                    }
//                });
//
//                if (!dialog.isShowing())
//                    dialog.show();
//            }
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    public void gccRecCount(int cnt) {
//
//    }
//
//    @Override
//    public void deletedrGoDownCount(int cnt) {
//        try {
//            if (cnt > 0) {
//                Utils.customSyncOfflineSuccessAlert(SchoolOfflineDataActivity.this, getResources().getString(R.string.app_name),
//                        "Data deleted successfully");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void deletedrGoDownCountSubmitted(int cnt, String msg) {
//        try {
//            if (cnt > 0) {
//                Utils.customSyncOfflineSuccessAlert(this, getResources().getString(R.string.app_name), msg);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void getData(GCCSubmitResponse gccSubmitResponse) {
//        customProgressDialog.hide();
//        if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
//            callPhotoSubmit();
//        } else if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
//            showSnackBar(gccSubmitResponse.getStatusMessage());
//        } else {
//            showSnackBar(getString(R.string.something));
//        }
//    }
//
//    private void callPhotoSubmit() {
//        if (Utils.checkInternetConnection(SchoolOfflineDataActivity.this)) {
//            Type listType = new TypeToken<ArrayList<File>>() {
//            }.getType();
//            List<MultipartBody.Part> partList = new ArrayList<>();
//            List<File> photosList = new Gson().fromJson(schoolsOfflineEntity.getPhotos(), listType);
//            for (int i = 0; i < photosList.size(); i++) {
//                RequestBody requestFile =
//                        RequestBody.create(MediaType.parse("multipart/form-data"), photosList.get(i));
//                MultipartBody.Part body =
//                        MultipartBody.Part.createFormData("image", photosList.get(i).getName(), requestFile);
//                partList.add(body);
//            }
//
//            customProgressDialog.show();
//            gccPhotoViewModel.UploadImageServiceCall(partList);
//        } else {
//            Utils.customWarningAlert(SchoolOfflineDataActivity.this, getResources().getString(R.string.app_name), "Please check internet");
//        }
//    }
//
//    @Override
//    public void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse) {
//        customProgressDialog.hide();
//        if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
//            CallSuccessAlert(gccPhotoSubmitResponse.getStatusMessage());
//        } else if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
//            showSnackBar(gccPhotoSubmitResponse.getStatusMessage());
//        } else {
//            showSnackBar(getString(R.string.something));
//        }
//    }
//
//    private void CallSuccessAlert(String msg) {
//        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME +
//                "/" + IMAGE_DIRECTORY_NAME_MODE + "/" + fromClass + "_" + schoolsOfflineEntity.getDrgownId());
//
//        if (mediaStorageDir.isDirectory()) {
//            String[] children = mediaStorageDir.list();
//            for (int i = 0; i < children.length; i++)
//                new File(mediaStorageDir, children[i]).delete();
//            mediaStorageDir.delete();
//        }
//        schoolsOfflineRepository.deleteGCCRecordSubmitted(SchoolOfflineDataActivity.this, schoolsOfflineEntity, msg);
//
//    }
//
//    private void showSnackBar(String str) {
//        Snackbar.make(offlineReportBinding.cl, str, Snackbar.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void handleError(Throwable e, Context context) {
//        customProgressDialog.hide();
//        String errMsg;
//        if (e instanceof FileNotFoundException) {
//            errMsg = "Failed, No Images found in storage";
//        } else {
//            errMsg = ErrorHandler.handleError(e, context);
//        }
//        showSnackBar(errMsg);
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//}
