package com.cgg.twdinspection.engineering_works.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityUploadEngPhotosBinding;
import com.cgg.twdinspection.engineering_works.interfaces.UploadEngPhotosSubmitInterface;
import com.cgg.twdinspection.engineering_works.source.SubmitEngWorksRequest;
import com.cgg.twdinspection.engineering_works.source.SubmitEngWorksResponse;
import com.cgg.twdinspection.engineering_works.viewmodels.UploadEngPhotoCustomViewModel;
import com.cgg.twdinspection.engineering_works.viewmodels.UploadEngPhotoViewModel;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class UploadEngPhotosActivity extends LocBaseActivity implements UploadEngPhotosSubmitInterface, ErrorHandlerInterface {

    ActivityUploadEngPhotosBinding binding;
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    Bitmap bm;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    int flag_elevation = 0, flag_sideview = 0, flag_3dView = 0, flag_rearView = 0;
    File file_elevation,file_sideView, file_3DView, file_rearView;
    String FilePath;
    public static final String IMAGE_DIRECTORY_NAME = "ENGINEERING_WORKS_IMAGES";
    String officerID,randomNo;
    SharedPreferences sharedPreferences;
    private CustomProgressDialog customProgressDialog;
    UploadEngPhotoViewModel viewModel;
    SubmitEngWorksRequest submitEngWorksRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_eng_photos);
        binding.header.headerTitle.setText("WORKS - UPLOAD PHOTOS");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.btnLayout.btnNext.setText(getString(R.string.submit));
        customProgressDialog = new CustomProgressDialog(this,"");


        viewModel = ViewModelProviders.of(this,
                new UploadEngPhotoCustomViewModel(this)).get(UploadEngPhotoViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        try {
            sharedPreferences= TWDApplication.get(this).getPreferences();
            officerID=sharedPreferences.getString(AppConstants.OFFICER_ID,"");
        }catch (Exception e){
            e.printStackTrace();
        }
        randomNo=Utils.getRandomNumberString();

        String request=sharedPreferences.getString(AppConstants.EngSubmitRequest,"");
        Gson gson=new Gson();
        if(!TextUtils.isEmpty(request))
            submitEngWorksRequest=gson.fromJson(request,SubmitEngWorksRequest.class);

        binding.ivElevation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.ELEVATION;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivSideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.SIDEVIEW;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivRearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.REARVIEW;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivThreeD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.THREE_D_VIEW;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    if (Utils.checkInternetConnection(UploadEngPhotosActivity.this)) {
                        customSaveAlert(UploadEngPhotosActivity.this, getString(R.string.app_name), getString(R.string.do_you_want));
                    } else {
                        Utils.customWarningAlert(UploadEngPhotosActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                    }
                }
            }
        });
    }


    public void customSaveAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_confirmation);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
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


                        callDataSubmit();
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean validate() {
        boolean returnFlag=true;
        if(flag_elevation==0){
            returnFlag=false;
            showSnackBar("Please capture elevation image");
        }else if(flag_sideview==0){
            returnFlag=false;
            showSnackBar("Please capture side view image");
        }else if(flag_3dView==0){
            returnFlag=false;
            showSnackBar("Please capture 3D view image");
        }else if(flag_rearView==0) {
            returnFlag = false;
            showSnackBar("Please capture rear view image");
        }
        return returnFlag;
    }

    private void callDataSubmit() {
        submitEngWorksRequest.setPhotoKeyId(randomNo);
        customProgressDialog.show();
        viewModel.submitEngWorksDetails(submitEngWorksRequest);
    }
    private void callPhotoSubmit() {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_elevation);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file_elevation.getName(), requestFile);
        RequestBody requestFile1 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_3DView);
        MultipartBody.Part body1 =
                MultipartBody.Part.createFormData("image", file_3DView.getName(), requestFile1);
        RequestBody requestFile2 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_rearView);
        MultipartBody.Part body2 =
                MultipartBody.Part.createFormData("image", file_rearView.getName(), requestFile2);
        RequestBody requestFile3 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_sideView);
        MultipartBody.Part body3 =
                MultipartBody.Part.createFormData("image", file_sideView.getName(), requestFile3);


        customProgressDialog.show();


        List<MultipartBody.Part> partList = new ArrayList<>();
        partList.add(body);
        partList.add(body1);
        partList.add(body2);
        partList.add(body3);

        viewModel.UploadImageServiceCall(partList);
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_NAME;
                FilePath = FilePath + "/" + Image_name;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                if (PIC_TYPE.equals(AppConstants.ELEVATION)) {
                    flag_elevation = 1;
                    binding.ivElevation.setPadding(0, 0, 0, 0);
                    binding.ivElevation.setBackgroundColor(getResources().getColor(R.color.white));
                    file_elevation = new File(FilePath);
                    Glide.with(UploadEngPhotosActivity.this).load(file_elevation).into(binding.ivElevation);

                } else if (PIC_TYPE.equals(AppConstants.SIDEVIEW)) {
                    flag_sideview = 1;
                    binding.ivSideView.setPadding(0, 0, 0, 0);
                    binding.ivSideView.setBackgroundColor(getResources().getColor(R.color.white));
                    file_sideView = new File(FilePath);
                    Glide.with(UploadEngPhotosActivity.this).load(file_sideView).into(binding.ivSideView);
                } else if (PIC_TYPE.equals(AppConstants.REARVIEW)) {
                    flag_rearView = 1;
                    binding.ivRearView.setPadding(0, 0, 0, 0);
                    binding.ivRearView.setBackgroundColor(getResources().getColor(R.color.white));
                    file_rearView = new File(FilePath);
                    Glide.with(UploadEngPhotosActivity.this).load(file_rearView).into(binding.ivRearView);
                } else if (PIC_TYPE.equals(AppConstants.THREE_D_VIEW)) {
                    flag_3dView = 1;
                    binding.ivThreeD.setPadding(0, 0, 0, 0);
                    binding.ivThreeD.setBackgroundColor(getResources().getColor(R.color.white));
                    file_3DView = new File(FilePath);
                    Glide.with(UploadEngPhotosActivity.this).load(file_3DView).into(binding.ivThreeD);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    UploadEngPhotosActivity.this,
                    BuildConfig.APPLICATION_ID +".provider", //(use your app signature + ".provider" )
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            String deviceId=Utils.getDeviceID(this);
            String versionName=Utils.getVersionName(this);
            PIC_NAME =  PIC_TYPE + "~"+officerID + "~" + submitEngWorksRequest.getWorkId() + "~" + Utils.getCurrentDateTimeFormat() + "~" +deviceId + "~" +versionName + "~" +randomNo+".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
        } else {
            return null;
        }

        return mediaFile;
    }
    @Override
    public void getData(SubmitEngWorksResponse engWorksResponse) {
        customProgressDialog.hide();
        if (engWorksResponse != null && engWorksResponse.getStatusCode() != null && engWorksResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            callPhotoSubmit();
        } else if (engWorksResponse != null && engWorksResponse.getStatusCode() != null && engWorksResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            Snackbar.make(binding.root, engWorksResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getPhotoData(GCCPhotoSubmitResponse gccPhotoSubmitResponse) {
        customProgressDialog.hide();
        if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            CallSuccessAlert(gccPhotoSubmitResponse.getStatusMessage());
        } else if (gccPhotoSubmitResponse != null && gccPhotoSubmitResponse.getStatusCode() != null && gccPhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            Snackbar.make(binding.root, gccPhotoSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }
    private void CallSuccessAlert(String msg) {
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        showSnackBar(errMsg);
    }
}
