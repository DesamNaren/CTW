package com.cgg.twdinspection.inspection.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityUploadedPhotoBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    String officerId;
    Bitmap bm;
    String FilePath;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    File file_storeroom, file_varandah, file_playGround, file_diningHall, file_dormitory, file_mainBulding, file_toilet, file_kitchen, file_classroom, file_tds, file_menu, file_officer;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String instId;
    private String randomNo;
    private InstSelectionViewModel selectionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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
            instId = sharedPreferences.getString(AppConstants.INST_ID, "");
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectionViewModel = new InstSelectionViewModel(getApplication());
        LiveData<String> liveData = selectionViewModel.getRandomId(instId);
        liveData.observe(UploadedPhotoActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                randomNo = value;
            }
        });

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

                if (flag_storeroom != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.STOREROOM, String.valueOf(file_storeroom));
                }
                if (flag_varandah != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.VARANDAH, String.valueOf(file_varandah));
                }
                if (flag_playGround != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.PLAYGROUND, String.valueOf(file_playGround));
                }
                if (flag_diningHall != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.DININGHALL, String.valueOf(file_diningHall));
                }
                if (flag_dormitory != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.DORMITORY, String.valueOf(file_dormitory));
                }
                if (flag_mainBuilding != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.MAINBUILDING, String.valueOf(file_mainBulding));
                }
                if (flag_toilet != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.TOILET, String.valueOf(file_toilet));
                }
                if (flag_kitchen != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.KITCHEN, String.valueOf(file_kitchen));
                }
                if (flag_classroom != 0) {
                    addPhoto(instId, Utils.getCurrentDateTime(), AppConstants.CLASSROOM, String.valueOf(file_classroom));
                }
                if (uploadPhotos.size() > 0) {
                    Utils.customSaveAlert(UploadedPhotoActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                } else {
                    showSnackBar(getString(R.string.cap_at_least));
                }
            }
        });


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

    private void addPhoto(String instID, String currentDateTime, String typeOfImage, String valueOfImage) {
        UploadPhoto uploadPhoto = new UploadPhoto();
        uploadPhoto.setInstitute_id(instID);
        uploadPhoto.setSection_id("12");
        uploadPhoto.setTimeStamp(currentDateTime);
        uploadPhoto.setPhoto_name(typeOfImage);
        uploadPhoto.setPhoto_path(valueOfImage);
        uploadPhotos.add(uploadPhoto);
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        FilePath = getExternalFilesDir(null)
                + "/" + IMAGE_DIRECTORY_NAME;
        String Image_name = PIC_NAME;
        FilePath = FilePath + "/" + Image_name;
        return FilePath;
    }

    private String getRealPathFromURI(String contentURI) {
        int index = 0;
        Cursor cursor = null;
        try {
            Uri contentUri = Uri.parse(contentURI);
            cursor = getContentResolver().query(contentUri, null, null, null, null);
            if (cursor == null) {
                return contentUri.getPath();
            } else {
                cursor.moveToFirst();
                index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor.getString(index);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_TYPE + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);

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
                        getString(R.string.user_cancelled_cap), Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.sorry_failed_to_cap), Toast.LENGTH_SHORT)
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
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            String deviceId = Utils.getDeviceID(UploadedPhotoActivity.this);
            String versionName = Utils.getVersionName(UploadedPhotoActivity.this);
            PIC_NAME = PIC_TYPE + "~" + officerId + "~" + instId + "~" + Utils.getCurrentDateTimeFormat() + "~" + deviceId + "~" + versionName + "~" + randomNo + ".png";

            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
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
        if (x >= 0 && uploadPhotos.size() == 9) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Photos");
                liveData.observe(UploadedPhotoActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instId);
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
            if (x >= 0) {
                Utils.customSectionSaveAlert(UploadedPhotoActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGpsSwitchStateReceiver);
    }

    private final BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
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
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
