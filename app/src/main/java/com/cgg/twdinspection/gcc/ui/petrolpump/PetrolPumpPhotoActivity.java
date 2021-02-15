package com.cgg.twdinspection.gcc.ui.petrolpump;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityPetrolPumpPhotoCaptureBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.PetrolStockDetailsResponse;
import com.cgg.twdinspection.gcc.source.stock.StockSubmitRequest;
import com.cgg.twdinspection.gcc.source.stock.SubmitReqCommodities;
import com.cgg.twdinspection.gcc.source.submit.GCCPhotoSubmitResponse;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitRequest;
import com.cgg.twdinspection.gcc.source.submit.GCCSubmitResponse;
import com.cgg.twdinspection.gcc.source.suppliers.petrol_pump.PetrolSupplierInfo;
import com.cgg.twdinspection.gcc.ui.gcc.GCCDashboardActivity;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoCustomViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCPhotoViewModel;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class PetrolPumpPhotoActivity extends LocBaseActivity implements GCCSubmitInterface, ErrorHandlerInterface, GCCOfflineInterface {

    ActivityPetrolPumpPhotoCaptureBinding binding;
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    Bitmap bm;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    int flag_entrance = 0, flag_office2 = 0, flag_office1 = 0, flag_safety_equip1 = 0, flag_safety_equip2 = 0,
            flag_machinary1 = 0, flag_machinary2 = 0, flag_repair = 0, flag_pUnits = 0;
    File file_repair, file_entrance, file_office2, file_office1, file_safety_equip1, file_safety_equip2, file_machinary1, file_machinary2;
    String FilePath, repairPath;
    private String officerID, divId, divName, socId, socName, inchName, suppType, suppId, godId, godName;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    public static String IMAGE_DIRECTORY_NAME_MODE;
    private CustomProgressDialog customProgressDialog;
    SharedPreferences sharedPreferences;
    GCCPhotoViewModel viewModel;
    InspectionSubmitResponse inspectionSubmitResponse;
    PetrolStockDetailsResponse
            stockDetailsResponse;
    StockSubmitRequest stockSubmitRequest;
    private String randomNum;
    File mediaStorageDir;
    private boolean flag;
    private GCCOfflineRepository gccOfflineRepository;
    private GCCSubmitRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_petrol_pump_photo_capture);
        if (getIntent() != null) {
            binding.header.headerTitle.setText(getIntent().getStringExtra(AppConstants.TITLE));
        } else {
            binding.header.headerTitle.setText(getString(R.string.upload_photos));
        }
        binding.header.ivHome.setVisibility(View.GONE);
        binding.btnLayout.btnNext.setText(getString(R.string.submit));
        customProgressDialog = new CustomProgressDialog(this);

        GCCOfflineViewModel gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        viewModel = ViewModelProviders.of(this,
                new GCCPhotoCustomViewModel(this)).get(GCCPhotoViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            repairPath = sharedPreferences.getString(AppConstants.repairsPath, "");
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            randomNum = sharedPreferences.getString(AppConstants.randomNum, "");
            if (!TextUtils.isEmpty(repairPath)) {
                file_repair = new File(repairPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String inspectionData = sharedPreferences.getString(AppConstants.InspectionDetails, "");
        Gson gson = new Gson();
        inspectionSubmitResponse = gson.fromJson(inspectionData, InspectionSubmitResponse.class);
        String stockDetails = sharedPreferences.getString(AppConstants.finalStockData, "");
        stockDetailsResponse = gson.fromJson(stockDetails, PetrolStockDetailsResponse.class);
        stockSubmitRequest = new StockSubmitRequest();

        if (inspectionSubmitResponse.getPetrolPump() != null) {
            String petrolPumpData = sharedPreferences.getString(AppConstants.PETROL_PUMP_DATA, "");
            PetrolSupplierInfo petrolSupplierInfo = gson.fromJson(petrolPumpData, PetrolSupplierInfo.class);
            divId = petrolSupplierInfo.getDivisionId();
            divName = petrolSupplierInfo.getDivisionName();
            socId = petrolSupplierInfo.getSocietyId();
            socName = petrolSupplierInfo.getSocietyName();
            inchName = petrolSupplierInfo.getIncharge();
            suppType = getString(R.string.petrol_req);
            suppId = petrolSupplierInfo.getGodownId();
            godName = petrolSupplierInfo.getGodownName();
        }
        binding.ivEntrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.ENTRANCE;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivOffice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.OFFICE1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivOffice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.OFFICE2;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivSafetyEqui1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.SAFETY_EQUIPMENT1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivSafetyEqui2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.SAFETY_EQUIPMENT2;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivMachinary1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.MACHINARY1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivMachinary2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.MACHINARY2;
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
                if (validate()) {
                    customSaveAlert(PetrolPumpPhotoActivity.this, getString(R.string.app_name));
                }
            }
        });

        LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(
                divId, socId, suppId);

        drGodownLiveData.observe(PetrolPumpPhotoActivity.this, new Observer<GccOfflineEntity>() {
            @Override
            public void onChanged(GccOfflineEntity gccOfflineEntity) {
                if (gccOfflineEntity != null) {
                    flag = true;
                    binding.btnLayout.btnNext.setText(getString(R.string.save));
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.offline_mode));
                    IMAGE_DIRECTORY_NAME_MODE = AppConstants.OFFLINE;
                } else {
                    flag = false;
                    binding.btnLayout.btnNext.setText(getString(R.string.submit));
                    binding.header.ivMode.setBackground(getResources().getDrawable(R.drawable.online_mode));
                    IMAGE_DIRECTORY_NAME_MODE = AppConstants.ONLINE;
                }
            }
        });
    }


    public void customSaveAlert(Activity activity, String title) {
        try {
            String msg = "";
            if (flag) {
                msg = getString(R.string.do_you_want_save);
            } else {
                msg = getString(R.string.do_you_want);
            }
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

    private void callDataSubmit() {
        request = new GCCSubmitRequest();
        request.setInspectionFindings(inspectionSubmitResponse);
        request.setOfficerId(officerID);
        request.setDivisionId(divId);
        request.setDivisionName(divName);
        request.setSocietyId(socId);
        request.setSocietyName(socName);
        request.setInchargeName(inchName);
        request.setSupplierType(suppType);
        request.setInspectionTime(Utils.getCurrentDateTime());
        request.setGodownId(suppId);
        request.setGodown_name(godName);
        request.setDeviceId(Utils.getDeviceID(this));
        request.setVersionNo(Utils.getVersionName(this));
        request.setPhoto_key_id(randomNum);
        setStockDetailsSubmitRequest();
        request.setStockDetails(stockSubmitRequest);
        if (flag) {
            callPhotoSubmit();
        } else {
            if (Utils.checkInternetConnection(PetrolPumpPhotoActivity.this)) {
                customProgressDialog.show();
                customProgressDialog.addText("Please wait...Uploading Data");
                viewModel.submitGCCDetails(request);
            } else {
                Utils.customWarningAlert(PetrolPumpPhotoActivity.this, getResources().getString(R.string.app_name), "Please check internet");
            }
        }
    }

    private void setStockDetailsSubmitRequest() {

        List<SubmitReqCommodities> petrolCommoditiesList = new ArrayList<>();


        if (stockDetailsResponse != null && stockDetailsResponse.getCommonCommodities() != null
                && stockDetailsResponse.getCommonCommodities().size() > 0) {
            for (int i = 0; i < stockDetailsResponse.getCommonCommodities().size(); i++) {
                SubmitReqCommodities petrolCommodities = new SubmitReqCommodities();
                petrolCommodities.setComType(stockDetailsResponse.getCommonCommodities().get(i).getCommName());
                petrolCommodities.setComCode(stockDetailsResponse.getCommonCommodities().get(i).getCommCode());
                petrolCommodities.setUnits(stockDetailsResponse.getCommonCommodities().get(i).getUnits());
                petrolCommodities.setSystemQty(stockDetailsResponse.getCommonCommodities().get(i).getQty());
                petrolCommodities.setSystemRate(stockDetailsResponse.getCommonCommodities().get(i).getRate());
                petrolCommodities.setSystemValue(stockDetailsResponse.getCommonCommodities().get(i).getQty() * stockDetailsResponse.getCommonCommodities().get(i).getRate());
                petrolCommodities.setPhysicalRate(stockDetailsResponse.getCommonCommodities().get(i).getRate());
                if (!TextUtils.isEmpty(stockDetailsResponse.getCommonCommodities().get(i).getPhyQuant())) {
                    petrolCommodities.setPhysiacalQty(Double.parseDouble(stockDetailsResponse.getCommonCommodities().get(i).getPhyQuant()));
                    petrolCommodities.setPhysicalValue(Double.parseDouble(stockDetailsResponse.getCommonCommodities().get(i).getPhyQuant()) * stockDetailsResponse.getCommonCommodities().get(i).getRate());
                }

                petrolCommoditiesList.add(petrolCommodities);
            }
        }


        stockSubmitRequest.setPetrolPumps(petrolCommoditiesList);
        String sysVal = sharedPreferences.getString(AppConstants.TOTAL_SYSVAL, "");
        String phyVal = sharedPreferences.getString(AppConstants.TOTAL_PHYVAL, "");
        stockSubmitRequest.setTotalSystemValue(Double.parseDouble(sysVal));
        stockSubmitRequest.setTotalPhysicalValue(Double.parseDouble(phyVal));
    }

    private void callPhotoSubmit() {

        if (flag) {
            List<File> photoList = new ArrayList<>();
            photoList.add(file_entrance);
            photoList.add(file_office1);
            photoList.add(file_office2);
            photoList.add(file_safety_equip1);
            photoList.add(file_safety_equip2);
            photoList.add(file_machinary1);
            photoList.add(file_machinary2);
            if (file_repair != null)
                photoList.add(file_repair);


            String data = new Gson().toJson(request);
            String photos = new Gson().toJson(photoList);
            GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
            gccOfflineEntity.setDivisionId(divId);
            gccOfflineEntity.setDivisionName(divName);
            gccOfflineEntity.setSocietyId(socId);
            gccOfflineEntity.setSocietyName(socName);
            gccOfflineEntity.setDrgownId(suppId);
            gccOfflineEntity.setDrgownName(godName);
            gccOfflineEntity.setTime(Utils.getCurrentDateTimeDisplay());

            gccOfflineEntity.setData(data);
            gccOfflineEntity.setPhotos(photos);
            gccOfflineEntity.setType(AppConstants.OFFLINE_PETROL);
            gccOfflineEntity.setFlag(true);
            gccOfflineRepository.insertGCCRecord(PetrolPumpPhotoActivity.this, gccOfflineEntity);

        } else {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_entrance);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file_entrance.getName(), requestFile);
            RequestBody requestFile1 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_office1);
            MultipartBody.Part body1 =
                    MultipartBody.Part.createFormData("image", file_office1.getName(), requestFile1);
            RequestBody requestFile2 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_office2);
            MultipartBody.Part body2 =
                    MultipartBody.Part.createFormData("image", file_office2.getName(), requestFile2);
            RequestBody requestFile3 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_safety_equip1);
            MultipartBody.Part body3 =
                    MultipartBody.Part.createFormData("image", file_safety_equip1.getName(), requestFile3);
            RequestBody requestFile4 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_safety_equip2);
            MultipartBody.Part body4 =
                    MultipartBody.Part.createFormData("image", file_safety_equip2.getName(), requestFile4);

            RequestBody requestFile5 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_machinary1);
            MultipartBody.Part body5 = MultipartBody.Part.createFormData("image", file_machinary1.getName(), requestFile5);
            RequestBody requestFile6 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file_machinary2);
            MultipartBody.Part body6 = MultipartBody.Part.createFormData("image", file_machinary2.getName(), requestFile6);
            MultipartBody.Part body7 = null;
            if (file_repair != null) {
                RequestBody requestFile7 =
                        RequestBody.create(MediaType.parse("multipart/form-data"), file_repair);
                body7 = MultipartBody.Part.createFormData("image", file_repair.getName(), requestFile7);
            }


            List<MultipartBody.Part> partList = new ArrayList<>();
            partList.add(body);
            partList.add(body1);
            partList.add(body2);
            partList.add(body3);
            partList.add(body4);
            partList.add(body5);
            partList.add(body6);

            if (body7 != null) {
                partList.add(body7);
            }

            customProgressDialog.show();
            customProgressDialog.addText("Please wait...Uploading Photos");

            viewModel.UploadImageServiceCall(partList);
        }
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (flag_entrance == 0) {
            returnFlag = false;
            showSnackBar("Please capture entrance image");
        } else if (flag_machinary1 == 0) {
            returnFlag = false;
            showSnackBar("Please capture machinary image");
        } else if (flag_machinary2 == 0) {
            returnFlag = false;
            showSnackBar("Please capture machinary image");
        } else if (flag_safety_equip1 == 0) {
            returnFlag = false;
            showSnackBar("Please capture safety equipment image");
        } else if (flag_safety_equip2 == 0) {
            returnFlag = false;
            showSnackBar("Please capture safety equipment image");
        } else if (flag_office1 == 0) {
            returnFlag = false;
            showSnackBar("Please capture office image");
        } else if (flag_office2 == 0) {
            returnFlag = false;
            showSnackBar("Please capture office image");
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
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

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
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

//      check the rotation of the image and display it properly
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

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        FilePath = getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME
                + "/" + IMAGE_DIRECTORY_NAME_MODE + "/" + AppConstants.OFFLINE_PETROL + "_" + suppId;

        String Image_name = PIC_NAME;
        FilePath = FilePath + "/" + Image_name;

//        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return FilePath;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
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

                FilePath = getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME
                        + "/" + IMAGE_DIRECTORY_NAME_MODE + "/" + AppConstants.OFFLINE_PETROL + "_" + suppId;

                String Image_name = PIC_TYPE + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);

                if (PIC_TYPE.equals(AppConstants.ENTRANCE)) {
                    flag_entrance = 1;
                    binding.ivEntrance.setPadding(0, 0, 0, 0);
                    binding.ivEntrance.setBackgroundColor(getResources().getColor(R.color.white));
                    file_entrance = new File(FilePath);
                    Glide.with(PetrolPumpPhotoActivity.this).load(file_entrance).into(binding.ivEntrance);

                } else if (PIC_TYPE.equals(AppConstants.OFFICE1)) {
                    flag_office1 = 1;
                    binding.ivOffice1.setPadding(0, 0, 0, 0);
                    binding.ivOffice1.setBackgroundColor(getResources().getColor(R.color.white));
                    file_office1 = new File(FilePath);
                    Glide.with(PetrolPumpPhotoActivity.this).load(file_office1).into(binding.ivOffice1);
                } else if (PIC_TYPE.equals(AppConstants.OFFICE2)) {
                    flag_office2 = 1;
                    binding.ivOffice2.setPadding(0, 0, 0, 0);
                    binding.ivOffice2.setBackgroundColor(getResources().getColor(R.color.white));
                    file_office2 = new File(FilePath);
                    Glide.with(PetrolPumpPhotoActivity.this).load(file_office2).into(binding.ivOffice2);
                } else if (PIC_TYPE.equals(AppConstants.SAFETY_EQUIPMENT1)) {
                    flag_safety_equip1 = 1;
                    binding.ivSafetyEqui1.setPadding(0, 0, 0, 0);
                    binding.ivSafetyEqui1.setBackgroundColor(getResources().getColor(R.color.white));
                    file_safety_equip1 = new File(FilePath);
                    Glide.with(PetrolPumpPhotoActivity.this).load(file_safety_equip1).into(binding.ivSafetyEqui1);
                } else if (PIC_TYPE.equals(AppConstants.SAFETY_EQUIPMENT2)) {
                    flag_safety_equip2 = 1;
                    binding.ivSafetyEqui2.setPadding(0, 0, 0, 0);
                    binding.ivSafetyEqui2.setBackgroundColor(getResources().getColor(R.color.white));
                    file_safety_equip2 = new File(FilePath);
                    Glide.with(PetrolPumpPhotoActivity.this).load(file_safety_equip2).into(binding.ivSafetyEqui2);
                } else if (PIC_TYPE.equals(AppConstants.MACHINARY1)) {
                    flag_machinary1 = 1;
                    binding.ivMachinary1.setPadding(0, 0, 0, 0);
                    binding.ivMachinary1.setBackgroundColor(getResources().getColor(R.color.white));
                    file_machinary1 = new File(FilePath);
                    Glide.with(PetrolPumpPhotoActivity.this).load(file_machinary1).into(binding.ivMachinary1);
                } else if (PIC_TYPE.equals(AppConstants.MACHINARY2)) {
                    flag_machinary2 = 1;
                    binding.ivMachinary2.setPadding(0, 0, 0, 0);
                    binding.ivMachinary2.setBackgroundColor(getResources().getColor(R.color.white));
                    file_machinary2 = new File(FilePath);
                    Glide.with(PetrolPumpPhotoActivity.this).load(file_machinary2).into(binding.ivMachinary2);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.user_cancelled_cap),Toast.LENGTH_SHORT)
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
                    PetrolPumpPhotoActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider", //(use your app signature + ".provider" )
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME
                + "/" + IMAGE_DIRECTORY_NAME_MODE + "/" + AppConstants.OFFLINE_PETROL + "_" + suppId);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + divId + "~" + suppId + "~" + Utils.getCurrentDateTimeFormat() + "~" +
                    Utils.getDeviceID(PetrolPumpPhotoActivity.this) + "~" + Utils.getVersionName(PetrolPumpPhotoActivity.this) + "~" + randomNum + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        showSnackBar(errMsg);
    }

    @Override
    public void getData(GCCSubmitResponse gccSubmitResponse) {
        customProgressDialog.hide();
        if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_STRING_CODE)) {
            callPhotoSubmit();
        } else if (gccSubmitResponse != null && gccSubmitResponse.getStatusCode() != null && gccSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_STRING_CODE)) {
            Snackbar.make(binding.root, gccSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
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
        if (mediaStorageDir.isDirectory()) {
            String[] children = mediaStorageDir.list();
            for (String child : children) new File(mediaStorageDir, child).delete();
            mediaStorageDir.delete();
        }
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    @Override
    public void gccRecCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {


                Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, GCCDashboardActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCount(int cnt) {
    }

    @Override
    public void deletedrGoDownCountSubmitted(int cnt, String msg) {

    }
}
