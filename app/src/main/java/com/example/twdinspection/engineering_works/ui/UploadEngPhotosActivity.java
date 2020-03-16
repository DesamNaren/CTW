package com.example.twdinspection.engineering_works.ui;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.twdinspection.BuildConfig;
import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityUploadEngPhotosBinding;
import com.example.twdinspection.inspection.ui.LocBaseActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class UploadEngPhotosActivity extends LocBaseActivity {

    ActivityUploadEngPhotosBinding binding;
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    Bitmap bm;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    int flag_elevation = 0, flag_sideview = 0, flag_3dView = 0, flag_rearView = 0;
    File file_elevation,file_sideView, file_3DView, file_rearView;
    String FilePath;
    public static final String IMAGE_DIRECTORY_NAME = "ENGINEERING_WORKS_IMAGES";
    String officerID,engWorkId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_eng_photos);
        binding.header.headerTitle.setText("Upload Photos");
        binding.header.ivHome.setVisibility(View.GONE);
        binding.btnLayout.btnNext.setText(getString(R.string.submit));

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        try {
            sharedPreferences= TWDApplication.get(this).getPreferences();
            officerID=sharedPreferences.getString(AppConstants.OFFICER_ID,"");
            officerID=sharedPreferences.getString(AppConstants.ENG_WORK_ID,"");
        }catch (Exception e){
            e.printStackTrace();
        }
        
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
            PIC_NAME = officerID + "~" + engWorkId + "~" + Utils.getCurrentDateTime() + "~" + PIC_TYPE + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
        } else {
            return null;
        }

        return mediaFile;
    }

}
