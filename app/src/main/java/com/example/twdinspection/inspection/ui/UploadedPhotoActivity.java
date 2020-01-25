package com.example.twdinspection.inspection.ui;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityUploadedPhotoBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.UploadPhotoCustomlViewModel;
import com.example.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.example.twdinspection.schemes.ui.BenDetailsActivity;
import com.example.twdinspection.schemes.viewmodel.BenCustomDetailViewModel;
import com.example.twdinspection.schemes.viewmodel.BenDetailsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class UploadedPhotoActivity extends LocBaseActivity implements SchemeSubmitInterface, SaveListener, ErrorHandlerInterface {

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
    File file_storeroom, file_varandah, file_playGround, file_diningHall, file_dormitory, file_mainBulding, file_toilet, file_kitchen, file_classroom;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    private String officerID, instID, insTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_uploaded_photo);
        binding.header.headerTitle.setText(getString(R.string.ben_details));
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        instMainViewModel = new InstMainViewModel(getApplication());

        viewModel = ViewModelProviders.of(this,
                new UploadPhotoCustomlViewModel(binding, this)).get(UploadPhotoViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
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
                Toast.makeText(UploadedPhotoActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                if (flag_storeroom == 0) {
                    showSnackBar("Please capture storeroom image");
                } else if (flag_varandah == 0) {
                    showSnackBar("Please capture varandah image");
                } else if (flag_playGround == 0) {
                    showSnackBar("Please capture playground image");
                } else if (flag_diningHall == 0) {
                    showSnackBar("Please capture dining hall image");
                } else if (flag_dormitory == 0) {
                    showSnackBar("Please capture dormitory image");
                } else if (flag_mainBuilding == 0) {
                    showSnackBar("Please capture main building image");
                } else if (flag_toilet == 0) {
                    showSnackBar("Please capture toilet image");
                } else if (flag_kitchen == 0) {
                    showSnackBar("Please capture kitchen image");
                } else if (flag_classroom == 0) {
                    showSnackBar("Please capture classroom image");
                } else {
                    if (Utils.checkInternetConnection(UploadedPhotoActivity.this)) {
                        callPhotoSubmit();
                    } else {
                        Utils.customWarningAlert(UploadedPhotoActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                    }
                }
            }
        });

        sharedPreferences = TWDApplication.get(this).getPreferences();
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");

        binding.ivStoreRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {

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
                if (callPermissions()) {

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
                if (callPermissions()) {

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
                if (callPermissions()) {

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
                if (callPermissions()) {

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
                if (callPermissions()) {

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
                if (callPermissions()) {

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
                if (callPermissions()) {

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
                if (callPermissions()) {

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

    private void callPhotoSubmit() {

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_classroom);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file_classroom.getName(), requestFile);
        RequestBody requestFile1 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_diningHall);
        MultipartBody.Part body1 =
                MultipartBody.Part.createFormData("image", file_diningHall.getName(), requestFile1);
        RequestBody requestFile2 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_kitchen);
        MultipartBody.Part body2 =
                MultipartBody.Part.createFormData("image", file_kitchen.getName(), requestFile2);
        RequestBody requestFile3 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_mainBulding);
        MultipartBody.Part body3 =
                MultipartBody.Part.createFormData("image", file_mainBulding.getName(), requestFile3);
        RequestBody requestFile4 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_playGround);
        MultipartBody.Part body4 =
                MultipartBody.Part.createFormData("image", file_playGround.getName(), requestFile4);
        RequestBody requestFile5 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_toilet);
        MultipartBody.Part body5 =
                MultipartBody.Part.createFormData("image", file_toilet.getName(), requestFile5);
        RequestBody requestFile6 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_varandah);
        MultipartBody.Part body6 =
                MultipartBody.Part.createFormData("image", file_varandah.getName(), requestFile6);
        RequestBody requestFile7 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_dormitory);
        MultipartBody.Part body7 =
                MultipartBody.Part.createFormData("image", file_dormitory.getName(), requestFile7);
        RequestBody requestFile8 =
                RequestBody.create(MediaType.parse("multipart/form-data"), file_storeroom);
        MultipartBody.Part body8 =
                MultipartBody.Part.createFormData("image", file_storeroom.getName(), requestFile8);
        viewModel.UploadImageServiceCall(body, body1, body2, body3, body4, body5, body6, body7, body8);
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
                    binding.ivStoreRoom.setImageBitmap(bm);
                    binding.ivStoreRoom.setPadding(0, 0, 0, 0);
                    binding.ivStoreRoom.setBackgroundColor(getResources().getColor(R.color.white));
                    file_storeroom = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.VARANDAH)) {
                    flag_varandah = 1;
                    binding.ivVarandah.setImageBitmap(bm);
                    binding.ivVarandah.setPadding(0, 0, 0, 0);
                    binding.ivVarandah.setBackgroundColor(getResources().getColor(R.color.white));
                    file_varandah = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.PLAYGROUND)) {
                    flag_playGround = 1;
                    binding.ivPlaygound.setImageBitmap(bm);
                    binding.ivPlaygound.setPadding(0, 0, 0, 0);
                    binding.ivPlaygound.setBackgroundColor(getResources().getColor(R.color.white));
                    file_playGround = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.DININGHALL)) {
                    flag_diningHall = 1;
                    binding.ivDiningHall.setImageBitmap(bm);
                    binding.ivDiningHall.setPadding(0, 0, 0, 0);
                    binding.ivDiningHall.setBackgroundColor(getResources().getColor(R.color.white));
                    file_diningHall = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.DORMITORY)) {
                    flag_dormitory = 1;
                    binding.ivDormitory.setImageBitmap(bm);
                    binding.ivDormitory.setPadding(0, 0, 0, 0);
                    binding.ivDormitory.setBackgroundColor(getResources().getColor(R.color.white));
                    file_dormitory = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.MAINBUILDING)) {
                    flag_mainBuilding = 1;
                    binding.ivMainBuilding.setImageBitmap(bm);
                    binding.ivMainBuilding.setPadding(0, 0, 0, 0);
                    binding.ivMainBuilding.setBackgroundColor(getResources().getColor(R.color.white));
                    file_mainBulding = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.TOILET)) {
                    flag_toilet = 1;
                    binding.ivToilet.setImageBitmap(bm);
                    binding.ivToilet.setPadding(0, 0, 0, 0);
                    binding.ivToilet.setBackgroundColor(getResources().getColor(R.color.white));
                    file_toilet = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.KITCHEN)) {
                    flag_kitchen = 1;
                    binding.ivKitchen.setImageBitmap(bm);
                    binding.ivKitchen.setPadding(0, 0, 0, 0);
                    binding.ivKitchen.setBackgroundColor(getResources().getColor(R.color.white));
                    file_kitchen = new File(FilePath);
                } else if (PIC_TYPE.equals(AppConstants.CLASSROOM)) {
                    flag_classroom = 1;
                    binding.ivClassroom.setImageBitmap(bm);
                    binding.ivClassroom.setPadding(0, 0, 0, 0);
                    binding.ivClassroom.setBackgroundColor(getResources().getColor(R.color.white));
                    file_classroom = new File(FilePath);
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
                    UploadedPhotoActivity.this,
                    "com.example.twdinspection.provider", //(use your app signature + ".provider" )
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
    public void getData(SchemeSubmitResponse schemeSubmitResponse) {

    }

    @Override
    public void getPhotoData(SchemePhotoSubmitResponse schemePhotoSubmitResponse) {
        if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            CallSuccessAlert(schemePhotoSubmitResponse.getStatusMessage());
        } else if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            Snackbar.make(binding.root, schemePhotoSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(binding.root, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void CallSuccessAlert(String msg) {
//        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
        submitData();
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        showSnackBar(errMsg);
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
            Utils.customSectionSaveAlert(UploadedPhotoActivity.this,getString(R.string.data_saved),getString(R.string.app_name));
        } else {
            showSnackBar(getString(R.string.failed));
        }

    }
}
