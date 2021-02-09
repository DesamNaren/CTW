package com.cgg.twdinspection.offline;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGccOfflineReportBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoCustomViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.offline.interfaces.GCCOfflineSubmitInterface;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class GCCOfflineDataActivity extends AppCompatActivity implements GCCOfflineSubmitInterface, GCCOfflineInterface, GCCSubmitInterface, ErrorHandlerInterface {

    ActivityGccOfflineReportBinding gccReportBinding;
    private CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private GCCOfflineRepository gccOfflineRepository;
    private GCCPhotoViewModel gccPhotoViewModel;
    private GccOfflineEntity gccOfflineEntity;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    public static String IMAGE_DIRECTORY_NAME_MODE = AppConstants.OFFLINE;
    private String fromClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(this);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();

        gccReportBinding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_offline_report);
        gccReportBinding.executePendingBindings();
        gccReportBinding.header.headerTitle.setText(getString(R.string.gcc_reports_offline_data));

        gccReportBinding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GCCOfflineDataActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        gccPhotoViewModel = ViewModelProviders.of(this,
                new GCCPhotoCustomViewModel(this)).get(GCCPhotoViewModel.class);
        gccReportBinding.setViewModel(gccPhotoViewModel);
        gccReportBinding.executePendingBindings();

        gccReportBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
            fromClass = getIntent().getStringExtra(AppConstants.FROM_CLASS);
            LiveData<List<GccOfflineEntity>> drGodownLiveData = gccOfflineViewModel.getGoDownsOfflineCount(fromClass);
            drGodownLiveData.observe(GCCOfflineDataActivity.this, new Observer<List<GccOfflineEntity>>() {
                @Override
                public void onChanged(List<GccOfflineEntity> drGodownData) {
                    drGodownLiveData.removeObservers(GCCOfflineDataActivity.this);
                    if (drGodownData != null && drGodownData.size() > 0) {
                        gccReportBinding.tvEmpty.setVisibility(View.GONE);
                        gccReportBinding.recyclerView.setVisibility(View.VISIBLE);

                        GCCOfflineDataAdapter adapter = new GCCOfflineDataAdapter(GCCOfflineDataActivity.this, drGodownData);
                        gccReportBinding.recyclerView.setLayoutManager(new LinearLayoutManager(GCCOfflineDataActivity.this));
                        gccReportBinding.recyclerView.setAdapter(adapter);
                    } else {
                        gccReportBinding.tvEmpty.setVisibility(View.VISIBLE);
                        gccReportBinding.recyclerView.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(gccReportBinding.root, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    @Override
    public void submitRecord(GccOfflineEntity entity) {
        customSubmitAlert(getResources().getString(R.string.are_you_sure), true, entity);
    }

    @Override
    public void deleteRecord(GccOfflineEntity entity) {
        customSubmitAlert(getResources().getString(R.string.are_you_sure_remove), false, entity);
    }

    private void customSubmitAlert(String msg, boolean flag, GccOfflineEntity entity) {
        try {
            final Dialog dialog = new Dialog(GCCOfflineDataActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_confirmation);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(getString(R.string.app_name));
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (flag) {
                            if (Utils.checkInternetConnection(GCCOfflineDataActivity.this)) {
                                gccOfflineEntity = entity;
                                GCCSubmitRequest request = new Gson().fromJson(gccOfflineEntity.getData(), GCCSubmitRequest.class);

                                customProgressDialog.show();
                                gccPhotoViewModel.submitGCCDetails(request);
                            } else {
                                Utils.customWarningAlert(GCCOfflineDataActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                            }
                        } else {
                            File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME +
                                    "/" + IMAGE_DIRECTORY_NAME_MODE + "/" + fromClass + "_" + entity.getDrgownId());

                            if (mediaStorageDir.isDirectory()) {
                                String[] children = mediaStorageDir.list();
                                for (int i = 0; i < children.length; i++)
                                    new File(mediaStorageDir, children[i]).delete();
                                mediaStorageDir.delete();
                            }
                            gccOfflineRepository.deleteGCCRecord(GCCOfflineDataActivity.this, entity);
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void gccRecCount(int cnt) {

    }

    @Override
    public void deletedrGoDownCount(int cnt) {
        try {
            if (cnt > 0) {
                Utils.customSyncOfflineSuccessAlert(GCCOfflineDataActivity.this, getResources().getString(R.string.app_name),
                        "Data deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCountSubmitted(int cnt, String msg) {
        try {
            if (cnt > 0) {
                Utils.customSyncOfflineSuccessAlert(this, getResources().getString(R.string.app_name), msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getData(GCCSubmitResponse gccSubmitResponse) {
        customProgressDialog.hide();
        if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            callPhotoSubmit();
        } else if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            showSnackBar(gccSubmitResponse.getStatusMessage());
        } else {
            showSnackBar(getString(R.string.something));
        }
    }

    private void callPhotoSubmit() {
        if (Utils.checkInternetConnection(GCCOfflineDataActivity.this)) {
            Type listType = new TypeToken<ArrayList<File>>() {
            }.getType();
            List<MultipartBody.Part> partList = new ArrayList<>();
            List<File> photosList = new Gson().fromJson(gccOfflineEntity.getPhotos(), listType);
            for (int i = 0; i < photosList.size(); i++) {
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), photosList.get(i));
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("image", photosList.get(i).getName(), requestFile);
                partList.add(body);
            }

            customProgressDialog.show();
            gccPhotoViewModel.UploadImageServiceCall(partList);
        } else {
            Utils.customWarningAlert(GCCOfflineDataActivity.this, getResources().getString(R.string.app_name), "Please check internet");
        }
    }

    @Override
    public void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse) {
        customProgressDialog.hide();
        if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            CallSuccessAlert(gccPhotoSubmitResponse.getStatusMessage());
        } else if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            showSnackBar(gccPhotoSubmitResponse.getStatusMessage());
        } else {
            showSnackBar(getString(R.string.something));
        }
    }

    private void CallSuccessAlert(String msg) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME +
                "/" + IMAGE_DIRECTORY_NAME_MODE + "/" + fromClass + "_" + gccOfflineEntity.getDrgownId());

        if (mediaStorageDir.isDirectory()) {
            String[] children = mediaStorageDir.list();
            for (int i = 0; i < children.length; i++)
                new File(mediaStorageDir, children[i]).delete();
            mediaStorageDir.delete();
        }
        gccOfflineRepository.deleteGCCRecordSubmitted(GCCOfflineDataActivity.this, gccOfflineEntity, msg);

    }

    private void showSnackBar(String str) {
        Snackbar.make(gccReportBinding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg;
        if (e instanceof FileNotFoundException) {
            errMsg = "Failed, No Images found in storage";
        } else {
            errMsg = ErrorHandler.handleError(e, context);
        }
        showSnackBar(errMsg);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
