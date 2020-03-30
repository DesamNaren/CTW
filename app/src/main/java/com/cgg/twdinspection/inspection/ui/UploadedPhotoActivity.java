package com.cgg.twdinspection.inspection.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityUploadedPhotoBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class UploadedPhotoActivity extends LocBaseActivity implements SaveListener {

    private List<UploadPhoto> uploadPhotos;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    ActivityUploadedPhotoBinding binding;
    int flag_classroom = 0, flag_storeroom = 0, flag_varandah = 0, flag_playGround = 0, flag_diningHall = 0, flag_dormitory = 0, flag_mainBuilding = 0, flag_toilet = 0, flag_kitchen = 0;
    public Uri fileUri;
    String PIC_NAME, PIC_TYPE;
    UploadPhotoViewModel viewModel;
    String officerId, instId;
    Bitmap bm;
    String FilePath;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    File file_storeroom, file_varandah, file_playGround, file_diningHall, file_dormitory, file_mainBulding, file_toilet, file_kitchen, file_classroom, file_tds, file_menu, file_officer;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String officerID, instID, insTime;
    private CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        customProgressDialog = new CustomProgressDialog(UploadedPhotoActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_uploaded_photo);
        binding.header.headerTitle.setText(getString(R.string.upload_photos));
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        instMainViewModel = new InstMainViewModel(getApplication());

        viewModel = ViewModelProviders.of(this,
                new UploadPhotoCustomViewModel(this)).get(UploadPhotoViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.btnLayout.btnNext.setText(getResources().getString(R.string.save));

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            LiveData<List<UploadPhoto>> listLiveData = viewModel.getPhotos();
            listLiveData.observe(UploadedPhotoActivity.this, new Observer<List<UploadPhoto>>() {
                @Override
                public void onChanged(List<UploadPhoto> uploadPhotos) {
                    listLiveData.removeObservers(UploadedPhotoActivity.this);
                    UploadedPhotoActivity.this.uploadPhotos = uploadPhotos;
                    if (uploadPhotos != null && uploadPhotos.size() > 0) {
                        for (int z = 0; z < uploadPhotos.size(); z++) {
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.STOREROOM)) {
                                flag_storeroom = 1;
                                file_storeroom = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivStoreRoom.setPadding(0, 0, 0, 0);
                                binding.ivStoreRoom.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_storeroom).into(binding.ivStoreRoom);
                            }

                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.VARANDAH)) {
                                flag_varandah = 1;
                                file_varandah = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivVarandah.setPadding(0, 0, 0, 0);
                                binding.ivVarandah.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_varandah).into(binding.ivVarandah);
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.DININGHALL)) {
                                flag_diningHall = 1;
                                file_diningHall = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivDiningHall.setPadding(0, 0, 0, 0);
                                binding.ivDiningHall.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_diningHall).into(binding.ivDiningHall);
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.DORMITORY)) {
                                flag_dormitory = 1;
                                file_dormitory = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivDormitory.setPadding(0, 0, 0, 0);
                                binding.ivDormitory.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_dormitory).into(binding.ivDormitory);
                            }

                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.MAINBUILDING)) {
                                flag_mainBuilding = 1;
                                file_mainBulding = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivMainBuilding.setPadding(0, 0, 0, 0);
                                binding.ivMainBuilding.setBackgroundColor(getResources().getColor(R.color.white));

                                Glide.with(UploadedPhotoActivity.this).load(file_mainBulding).into(binding.ivMainBuilding);
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.TOILET)) {
                                flag_toilet = 1;
                                file_toilet = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivToilet.setPadding(0, 0, 0, 0);
                                binding.ivToilet.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_toilet).into(binding.ivToilet);
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.KITCHEN)) {
                                flag_kitchen = 1;
                                file_kitchen = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivKitchen.setPadding(0, 0, 0, 0);
                                binding.ivKitchen.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_kitchen).into(binding.ivKitchen);
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.CLASSROOM)) {
                                flag_classroom = 1;
                                file_classroom = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivClassroom.setPadding(0, 0, 0, 0);
                                binding.ivClassroom.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_classroom).into(binding.ivClassroom);
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.PLAYGROUND)) {
                                flag_playGround = 1;
                                file_playGround = new File(uploadPhotos.get(z).getPhoto_path());
                                binding.ivPlaygound.setPadding(0, 0, 0, 0);
                                binding.ivPlaygound.setBackgroundColor(getResources().getColor(R.color.white));
                                Glide.with(UploadedPhotoActivity.this).load(file_playGround).into(binding.ivPlaygound);
                            }

                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.TDS)) {
                                file_tds = new File(uploadPhotos.get(z).getPhoto_path());
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.MENU)) {
                                file_menu = new File(uploadPhotos.get(z).getPhoto_path());
                            }
                            if (uploadPhotos.get(z).getPhoto_name().equalsIgnoreCase(AppConstants.OFFICER)) {
                                file_officer = new File(uploadPhotos.get(z).getPhoto_path());
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.customHomeAlert(UploadedPhotoActivity.this, getString(R.string.app_name), getString(R.string.go_home));
            }
        });

        callPermissions();

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhotos = new ArrayList<>();

                if (flag_storeroom == 0) {
                    showSnackBar("Please capture storeroom image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.STOREROOM, String.valueOf(file_storeroom));
                }
                if (flag_varandah == 0) {
                    showSnackBar("Please capture varandah image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.VARANDAH, String.valueOf(file_varandah));
                }
                if (flag_playGround == 0) {
                    showSnackBar("Please capture playground image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.PLAYGROUND, String.valueOf(file_playGround));
                }
                if (flag_diningHall == 0) {
                    showSnackBar("Please capture dining hall image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.DININGHALL, String.valueOf(file_diningHall));
                }
                if (flag_dormitory == 0) {
                    showSnackBar("Please capture dormitory image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.DORMITORY, String.valueOf(file_dormitory));
                }
                if (flag_mainBuilding == 0) {
                    showSnackBar("Please capture main building image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.MAINBUILDING, String.valueOf(file_mainBulding));
                }
                if (flag_toilet == 0) {
                    showSnackBar("Please capture toilet image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.TOILET, String.valueOf(file_toilet));
                }
                if (flag_kitchen == 0) {
                    showSnackBar("Please capture kitchen image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.KITCHEN, String.valueOf(file_kitchen));
                }
                if (flag_classroom == 0) {
                    showSnackBar("Please capture classroom image");
                    return;
                } else {
                    addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.CLASSROOM, String.valueOf(file_classroom));
                }

                Utils.customSaveAlert(UploadedPhotoActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

            }
        });

        sharedPreferences = TWDApplication.get(this).getPreferences();
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");

        binding.ivStoreRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.STOREROOM;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.STOREROOM;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivVarandah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.VARANDAH;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.VARANDAH;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }

                }
            }
        });
        binding.ivPlaygound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.PLAYGROUND;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.PLAYGROUND;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }

                }
            }
        });
        binding.ivDiningHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.DININGHALL;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.DININGHALL;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }

                }
            }
        });
        binding.ivDormitory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.DORMITORY;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.DORMITORY;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }

                }
            }
        });
        binding.ivMainBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.MAINBUILDING;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.MAINBUILDING;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }

                }

            }
        });
        binding.ivToilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.TOILET;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.TOILET;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.KITCHEN;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.KITCHEN;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.CLASSROOM;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.CLASSROOM;
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(UploadedPhotoActivity.this,
                    Manifest.permission.CAMERA)) {
                customPerAlert();
            } else {
                callSettings();
            }
        }
    }

    private void callSettings() {
        Snackbar snackbar = Snackbar.make(binding.cl, getString(R.string.all_cam_per_setting), Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                Utils.openSettings(UploadedPhotoActivity.this);
            }
        });

        snackbar.show();
    }

    private void addPhoto(String instID, String secId, String currentDateTime, String typeOfImage, String valueOfImage) {
        UploadPhoto uploadPhoto = new UploadPhoto();
        uploadPhoto.setInstitute_id(instID);
        uploadPhoto.setSection_id(secId);
        uploadPhoto.setTimeStamp(currentDateTime);
        uploadPhoto.setPhoto_name(typeOfImage);
        uploadPhoto.setPhoto_path(valueOfImage);
        uploadPhotos.add(uploadPhoto);
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
                String OLDmyBase64Image = encodeToBase64(bm, Bitmap.CompressFormat.JPEG,
                        100);

                if (PIC_TYPE.equals(AppConstants.STOREROOM)) {
                    flag_storeroom = 1;
                    binding.ivStoreRoom.setPadding(0, 0, 0, 0);
                    binding.ivStoreRoom.setBackgroundColor(getResources().getColor(R.color.white));
                    file_storeroom = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_storeroom).into(binding.ivStoreRoom);

                } else if (PIC_TYPE.equals(AppConstants.VARANDAH)) {
                    flag_varandah = 1;
                    binding.ivVarandah.setPadding(0, 0, 0, 0);
                    binding.ivVarandah.setBackgroundColor(getResources().getColor(R.color.white));
                    file_varandah = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_varandah).into(binding.ivVarandah);
                } else if (PIC_TYPE.equals(AppConstants.PLAYGROUND)) {
                    flag_playGround = 1;
                    binding.ivPlaygound.setPadding(0, 0, 0, 0);
                    binding.ivPlaygound.setBackgroundColor(getResources().getColor(R.color.white));
                    file_playGround = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_playGround).into(binding.ivPlaygound);
                } else if (PIC_TYPE.equals(AppConstants.DININGHALL)) {
                    flag_diningHall = 1;
                    binding.ivDiningHall.setPadding(0, 0, 0, 0);
                    binding.ivDiningHall.setBackgroundColor(getResources().getColor(R.color.white));
                    file_diningHall = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_diningHall).into(binding.ivDiningHall);
                } else if (PIC_TYPE.equals(AppConstants.DORMITORY)) {
                    flag_dormitory = 1;
                    binding.ivDormitory.setPadding(0, 0, 0, 0);
                    binding.ivDormitory.setBackgroundColor(getResources().getColor(R.color.white));
                    file_dormitory = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_dormitory).into(binding.ivDormitory);
                } else if (PIC_TYPE.equals(AppConstants.MAINBUILDING)) {
                    flag_mainBuilding = 1;
                    binding.ivMainBuilding.setPadding(0, 0, 0, 0);
                    binding.ivMainBuilding.setBackgroundColor(getResources().getColor(R.color.white));
                    file_mainBulding = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_mainBulding).into(binding.ivMainBuilding);
                } else if (PIC_TYPE.equals(AppConstants.TOILET)) {
                    flag_toilet = 1;
                    binding.ivToilet.setPadding(0, 0, 0, 0);
                    binding.ivToilet.setBackgroundColor(getResources().getColor(R.color.white));
                    file_toilet = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_toilet).into(binding.ivToilet);
                } else if (PIC_TYPE.equals(AppConstants.KITCHEN)) {
                    flag_kitchen = 1;
                    binding.ivKitchen.setPadding(0, 0, 0, 0);
                    binding.ivKitchen.setBackgroundColor(getResources().getColor(R.color.white));
                    file_kitchen = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_kitchen).into(binding.ivKitchen);
                } else if (PIC_TYPE.equals(AppConstants.CLASSROOM)) {
                    flag_classroom = 1;
                    binding.ivClassroom.setPadding(0, 0, 0, 0);
                    binding.ivClassroom.setBackgroundColor(getResources().getColor(R.color.white));
                    file_classroom = new File(FilePath);
                    Glide.with(UploadedPhotoActivity.this).load(file_classroom).into(binding.ivClassroom);
                }

                editor.commit();

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
                    UploadedPhotoActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
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
            PIC_NAME = officerId + "~" + instId + "~" + Utils.getCurrentDateTime() + "~" + PIC_TYPE + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
        } else {
            return null;
        }

        return mediaFile;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        callBack();
    }

    public void callBack() {
        Utils.customHomeAlert(UploadedPhotoActivity.this, getString(R.string.app_name), getString(R.string.go_back));
    }

    @Override
    public void submitData() {
        long x = viewModel.insertPhotos(uploadPhotos);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Photos");
                liveData.observe(UploadedPhotoActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instID);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(UploadedPhotoActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }

        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGpsSwitchStateReceiver);
    }

    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent != null && intent.getAction() != null &&
                        intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver
                (mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
            }
        };
        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {

                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re), instMainViewModel);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
    }
}
