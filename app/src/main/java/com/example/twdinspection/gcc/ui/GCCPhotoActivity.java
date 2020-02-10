package com.example.twdinspection.gcc.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.twdinspection.BuildConfig;
import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityGccPhotoCaptureBinding;
import com.example.twdinspection.gcc.source.inspections.InspectionSubmitResponse;
import com.example.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.example.twdinspection.gcc.source.suppliers.depot.DRDepots;
import com.example.twdinspection.gcc.source.suppliers.dr_godown.DrGodowns;
import com.example.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.example.twdinspection.gcc.source.suppliers.punit.PUnits;
import com.example.twdinspection.inspection.ui.LocBaseActivity;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class GCCPhotoActivity extends LocBaseActivity {

    ActivityGccPhotoCaptureBinding binding;
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    Bitmap bm;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    int flag_entrance = 0, flag_ceiling = 0, flag_floor = 0, flag_stock_arrang1 = 0, flag_stock_arrang2 = 0, flag_machinary = 0, flag_repair =0;
    File file_entrance, file_ceiling, file_floor, file_stock_arrang1, file_stock_arrang2, file_machinary;
    String FilePath, repairPath;
    private String officerID,divId,suppId;
    public static final String IMAGE_DIRECTORY_NAME = "GCC_IMAGES";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gcc_photo_capture);
        binding.header.headerTitle.setText(getString(R.string.upload_photos));

        try {
            sharedPreferences= TWDApplication.get(this).getPreferences();
        }catch (Exception e){
            e.printStackTrace();
        }
        String inspectionData = sharedPreferences.getString(AppConstants.InspectionDetails, "");
        Gson gson=new Gson();
        InspectionSubmitResponse inspectionSubmitResponse=gson.fromJson(inspectionData, InspectionSubmitResponse.class);
        String stockDetails = sharedPreferences.getString(AppConstants.stockData, "");
        StockDetailsResponse stockDetailsResponse=gson.fromJson(stockDetails,StockDetailsResponse.class);
        repairPath=sharedPreferences.getString(AppConstants.repairsPath,"");
        officerID=sharedPreferences.getString(AppConstants.OFFICER_ID,"");

        if(inspectionSubmitResponse.getProcessingUnit()!=null){
            binding.machineryCV.setVisibility(View.VISIBLE);
            String pUnitData = sharedPreferences.getString(AppConstants.P_UNIT_DATA, "");
            PUnits pUnits=gson.fromJson(pUnitData, PUnits.class);
            divId=pUnits.getDivisionId();
            suppId=pUnits.getGodownId();
        }
        if(inspectionSubmitResponse.getDrDepot()!=null){
            String depotData = sharedPreferences.getString(AppConstants.DR_DEPOT_DATA, "");
            DRDepots drDepot=gson.fromJson(depotData, DRDepots.class);
            divId=drDepot.getDivisionId();
            suppId=drDepot.getGodownId();
        }
        if(inspectionSubmitResponse.getDrGodown()!=null){
            String godownData = sharedPreferences.getString(AppConstants.DR_GODOWN_DATA, "");
            DrGodowns drGodowns=gson.fromJson(godownData, DrGodowns.class);
            divId=drGodowns.getDivisionId();
            suppId=drGodowns.getGodownId();
        }
        if(inspectionSubmitResponse.getMfpGodowns()!=null){
            String mfpGodown = sharedPreferences.getString(AppConstants.MFP_DEPOT_DATA, "");
            MFPGoDowns mfpGoDowns=gson.fromJson(mfpGodown, MFPGoDowns.class);
            divId=mfpGoDowns.getDivisionId();
            suppId=mfpGoDowns.getGodownId();
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
        binding.ivFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.FLOOR;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivCeiling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.CEILING;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivStockArrng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.STOCK_ARRANG1;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivStockArrng2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.STOCK_ARRANG2;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivMachinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.MACHINARY;
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
                String OLDmyBase64Image = encodeToBase64(bm, Bitmap.CompressFormat.JPEG,
                        100);

                if (PIC_TYPE.equals(AppConstants.ENTRANCE)) {
                    flag_entrance = 1;
                    binding.ivEntrance.setPadding(0, 0, 0, 0);
                    binding.ivEntrance.setBackgroundColor(getResources().getColor(R.color.white));
                    file_entrance = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_entrance).into(binding.ivEntrance);

                } else if (PIC_TYPE.equals(AppConstants.FLOOR)) {
                    flag_floor = 1;
                    binding.ivFloor.setPadding(0, 0, 0, 0);
                    binding.ivFloor.setBackgroundColor(getResources().getColor(R.color.white));
                    file_floor = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_floor).into(binding.ivFloor);
                } else if (PIC_TYPE.equals(AppConstants.CEILING)) {
                    flag_ceiling = 1;
                    binding.ivCeiling.setPadding(0, 0, 0, 0);
                    binding.ivCeiling.setBackgroundColor(getResources().getColor(R.color.white));
                    file_ceiling = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_ceiling).into(binding.ivCeiling);
                } else if (PIC_TYPE.equals(AppConstants.STOCK_ARRANG1)) {
                    flag_stock_arrang1 = 1;
                    binding.ivStockArrng1.setPadding(0, 0, 0, 0);
                    binding.ivStockArrng1.setBackgroundColor(getResources().getColor(R.color.white));
                    file_stock_arrang1 = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_stock_arrang1).into(binding.ivStockArrng1);
                } else if (PIC_TYPE.equals(AppConstants.STOCK_ARRANG2)) {
                    flag_stock_arrang2 = 1;
                    binding.ivStockArrng2.setPadding(0, 0, 0, 0);
                    binding.ivStockArrng2.setBackgroundColor(getResources().getColor(R.color.white));
                    file_stock_arrang2 = new File(FilePath);
                    Glide.with(GCCPhotoActivity.this).load(file_stock_arrang2).into(binding.ivStockArrng2);
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
                    GCCPhotoActivity.this,
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
            PIC_NAME = officerID + "~" + divId + "~" +  suppId + "~" + Utils.getCurrentDateTime() + "~" + PIC_TYPE + ".png";
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

}
