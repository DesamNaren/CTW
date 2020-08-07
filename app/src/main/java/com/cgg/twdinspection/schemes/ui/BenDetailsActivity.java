package com.cgg.twdinspection.schemes.ui;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.cgg.twdinspection.schemes.interfaces.SchemeSubmitInterface;
import com.cgg.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.cgg.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.cgg.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.cgg.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.cgg.twdinspection.schemes.viewmodel.BenCustomDetailViewModel;
import com.cgg.twdinspection.schemes.viewmodel.BenDetailsViewModel;
import com.google.android.material.snackbar.Snackbar;

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

public class BenDetailsActivity extends LocBaseActivity implements ErrorHandlerInterface, SchemeSubmitInterface {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE2 = 200;
    ActivityBenDetailsActivtyBinding benDetailsBinding;
    private BenDetailsViewModel viewModel;
    private BeneficiaryDetail beneficiaryDetail;
    private String fieldSelVal = "";
    private String selectedRemId = "";
    SharedPreferences sharedPreferences;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "SCHEME_IMAGES";
    String FilePath, FilePath2;
    Bitmap bm;
    String PIC_NAME, PIC_NAME2, PIC_TYPE;
    int imgflag1 = 0;
    int imgflag2 = 0;
    private String officerId;
    private CustomProgressDialog customProgressDialog;
    String randomNo, deviceId, versionNo;
    File mediaStorageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callPermissions();
        customProgressDialog = new CustomProgressDialog(this);

        benDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_ben_details_activty);
        benDetailsBinding.header.headerTitle.setText(getString(R.string.ben_details));
        benDetailsBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        benDetailsBinding.header.ivHome.setVisibility(View.GONE);
        randomNo = Utils.getRandomNumberString();
        deviceId = Utils.getDeviceID(BenDetailsActivity.this);
        versionNo = Utils.getVersionName(BenDetailsActivity.this);

        try {
            beneficiaryDetail = getIntent().getParcelableExtra(AppConstants.BEN_DETAIL);
            if (beneficiaryDetail != null) {
                viewModel = ViewModelProviders.of(this,
                        new BenCustomDetailViewModel(beneficiaryDetail, benDetailsBinding, this))
                        .get(BenDetailsViewModel.class);

                benDetailsBinding.setViewModel(viewModel);
                benDetailsBinding.executePendingBindings();
            } else {
                Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            beneficiaryDetail.setOfficerId(officerId);
            String insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            beneficiaryDetail.setInspectionTime(insTime);
            String distId = sharedPreferences.getString(AppConstants.SCHEME_DIST_ID, "");
            beneficiaryDetail.setDistId(distId);
            String distName = sharedPreferences.getString(AppConstants.SCHEME_DIST_NAME, "");
            beneficiaryDetail.setDistrictName(distName);
            String manId = sharedPreferences.getString(AppConstants.SCHEME_MAN_ID, "");
            beneficiaryDetail.setMandalId(manId);
            String manName = sharedPreferences.getString(AppConstants.SCHEME_MAN_NAME, "");
            beneficiaryDetail.setMandalName(manName);
            String vilId = sharedPreferences.getString(AppConstants.SCHEME_VIL_ID, "");
            beneficiaryDetail.setVillageId(vilId);
            String vilName = sharedPreferences.getString(AppConstants.SCHEME_VIL_NAME, "");
            beneficiaryDetail.setVillageName(vilName);
            String finId = sharedPreferences.getString(AppConstants.SCHEME_FIN_ID, "");
            beneficiaryDetail.setFinYearId(finId);
            String finValue = sharedPreferences.getString(AppConstants.SCHEME_FIN_YEAR, "");
            beneficiaryDetail.setFinYear(finValue);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        viewModel.getRemarks().observe(this, new Observer<List<InspectionRemarksEntity>>() {
            @Override
            public void onChanged(List<InspectionRemarksEntity> inspectionRemarksEntities) {
                if (inspectionRemarksEntities != null && inspectionRemarksEntities.size() > 0) {

                    ArrayList<String> remarks = new ArrayList<>();
                    remarks.add("-Select-");
                    for (int i = 0; i < inspectionRemarksEntities.size(); i++) {
                        remarks.add(inspectionRemarksEntities.get(i).getRemark_type());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(BenDetailsActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, remarks
                    );
                    benDetailsBinding.remarksSP.setAdapter(adapter);
                } else {
                    callSnackBar(getResources().getString(R.string.no_remarks));
                }
            }
        });


        benDetailsBinding.remarksSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    viewModel.getRemarkId(benDetailsBinding.remarksSP.getSelectedItem().toString()).observe(
                            BenDetailsActivity.this, new Observer<String>() {
                                @Override
                                public void onChanged(String value) {
                                    if (value != null) {
                                        selectedRemId = value;
                                    }
                                }
                            });
                } else {
                    selectedRemId = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        benDetailsBinding.ivCam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PIC_TYPE = AppConstants.PIC1;
                if (callPermissions()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });
        benDetailsBinding.ivCam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {
                    PIC_TYPE = AppConstants.PIC2;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri2(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE2);
                    }
                }
            }
        });


        benDetailsBinding.rgEntitlementsProvidedToStudents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_no_entitlements_provided_to_students) {
                    fieldSelVal = AppConstants.No;
                    benDetailsBinding.remarksSP.setVisibility(View.VISIBLE);
                } else {
                    fieldSelVal = AppConstants.Yes;
                    benDetailsBinding.remarksSP.setVisibility(View.GONE);
                    benDetailsBinding.remarksSP.setSelection(0);
                    selectedRemId = "";
                }
            }
        });

        benDetailsBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(fieldSelVal)) {
                    Snackbar.make(benDetailsBinding.cl, "Please select online status value", Snackbar.LENGTH_SHORT).show();
                } else if (fieldSelVal.equals(AppConstants.No) && TextUtils.isEmpty(selectedRemId)) {
                    Snackbar.make(benDetailsBinding.cl, "Please select remark type", Snackbar.LENGTH_SHORT).show();
                } else if (((benDetailsBinding.rgEntitlementsProvidedToStudents.getCheckedRadioButtonId() == R.id.rb_yes_entitlements_provided_to_students
                        && beneficiaryDetail.getStatusValue().equalsIgnoreCase("Grounded")) ||
                        ((benDetailsBinding.rgEntitlementsProvidedToStudents.getCheckedRadioButtonId() == R.id.rb_no_entitlements_provided_to_students)
                                && (((beneficiaryDetail.getStatusValue().equalsIgnoreCase("Grounded but UC not uploaded"))) ||
                                ((beneficiaryDetail.getStatusValue().equalsIgnoreCase("Unit grounded but defunct"))))))
                        && imgflag1 == 0 && imgflag2 == 0) {
                    Toast.makeText(BenDetailsActivity.this, "Please capture images", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.checkInternetConnection(BenDetailsActivity.this)) {
                        customSaveAlert(BenDetailsActivity.this, getString(R.string.app_name), getString(R.string.do_you_want));
                    } else {
                        Utils.customErrorAlert(BenDetailsActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
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


                        submitCall(beneficiaryDetail);
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    private void submitCall(BeneficiaryDetail beneficiaryDetail) {
        SchemeSubmitRequest schemeSubmitRequest = new SchemeSubmitRequest();
        schemeSubmitRequest.setOfficerId(beneficiaryDetail.getOfficerId());
        schemeSubmitRequest.setInspectionTime(beneficiaryDetail.getInspectionTime());
        schemeSubmitRequest.setDistId(beneficiaryDetail.getDistId());
        schemeSubmitRequest.setDistrictName(beneficiaryDetail.getDistrictName());
        schemeSubmitRequest.setMandalId(beneficiaryDetail.getMandalId());
        schemeSubmitRequest.setMandalName(beneficiaryDetail.getMandalName());
        schemeSubmitRequest.setVillageId(beneficiaryDetail.getVillageId());
        schemeSubmitRequest.setVillageName(beneficiaryDetail.getVillageName());
        schemeSubmitRequest.setFinYearId(beneficiaryDetail.getFinYearId());
        schemeSubmitRequest.setFinYear(beneficiaryDetail.getFinYear());
        schemeSubmitRequest.setBenId(beneficiaryDetail.getBenID());
        schemeSubmitRequest.setBenName(beneficiaryDetail.getBenName());
        schemeSubmitRequest.setActivity(beneficiaryDetail.getActivity());
        schemeSubmitRequest.setSchemeId(beneficiaryDetail.getSchemeId());
        schemeSubmitRequest.setSchemeType(beneficiaryDetail.getSchemeType());
        schemeSubmitRequest.setUnitCost(beneficiaryDetail.getUnitCost());
        schemeSubmitRequest.setSubsidy(beneficiaryDetail.getSubsidy());
        schemeSubmitRequest.setBankLoan(beneficiaryDetail.getBankLoan());
        schemeSubmitRequest.setContribution(beneficiaryDetail.getContribution());
        schemeSubmitRequest.setStatusId(beneficiaryDetail.getStatus());
        schemeSubmitRequest.setStatusValue(beneficiaryDetail.getStatusValue());
        schemeSubmitRequest.setStatusFieldMatch(fieldSelVal);
        schemeSubmitRequest.setRemarksId(String.valueOf(selectedRemId));
        schemeSubmitRequest.setRemarksType(benDetailsBinding.remarksSP.getSelectedItem().toString());
        schemeSubmitRequest.setDeviceId(Utils.getDeviceID(this));
        schemeSubmitRequest.setVersionNo(Utils.getVersionName(this));
        schemeSubmitRequest.setPhoto_key_id(randomNo);

        customProgressDialog.show();
        customProgressDialog.addText("Please wait...Uploading Data");

        viewModel.submitSchemeDetails(schemeSubmitRequest);


    }

    void callUploadPhoto(List<MultipartBody.Part> partList) {
        customProgressDialog.show();
        customProgressDialog.addText("Please wait...Uploadig Photos");

        viewModel.UploadImageServiceCall(partList);
    }

    private void CallSuccessAlert(String msg) {
        if (mediaStorageDir.isDirectory()) {
            String[] children = mediaStorageDir.list();
            for (int i = 0; i < children.length; i++)
                new File(mediaStorageDir, children[i]).delete();
            mediaStorageDir.delete();
        }
        Utils.customSuccessAlert(this, getResources().getString(R.string.app_name), msg);
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    BenDetailsActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    imageFile);
        }
        return imageUri;
    }

    public Uri getOutputMediaFileUri2(int type) {
        File imageFile = getOutputMediaFile2(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    BenDetailsActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            PIC_NAME = officerId + "~" + beneficiaryDetail.getSchemeId() + "~" + beneficiaryDetail.getBenID() + "~" + Utils.getCurrentDateTimeFormat() + "~" + deviceId + "~" + versionNo + "~" + randomNo + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }

    private File getOutputMediaFile2(int type) {
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
            PIC_NAME2 = officerId + "~" + beneficiaryDetail.getSchemeId() + "~" + beneficiaryDetail.getBenID() + "~" + Utils.getCurrentDateTimeFormat() + "~" + deviceId + "~" + versionNo + "~" + randomNo + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void updateLocationUI() {
        super.updateLocationUI();
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
        FilePath = getExternalFilesDir(null)
                + "/" + IMAGE_DIRECTORY_NAME;
        String Image_name = null;
        if (PIC_TYPE.equalsIgnoreCase(AppConstants.PIC1))
            Image_name = PIC_NAME;
        if (PIC_TYPE.equalsIgnoreCase(AppConstants.PIC2))
            Image_name = PIC_NAME2;

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
                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_TYPE + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                imgflag1 = 1;
                benDetailsBinding.ivCam1.setImageBitmap(bm);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE2) {
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
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                imgflag2 = 1;
                benDetailsBinding.ivCam2.setImageBitmap(bm);

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


    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(benDetailsBinding.root, msg, Snackbar.LENGTH_INDEFINITE);
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
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }

    @Override
    public void getData(SchemeSubmitResponse schemeSubmitResponse) {
        customProgressDialog.hide();
        if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)
                && (imgflag1 == 1 || imgflag2 == 1)) {

//            callSnackBar("Data inserted sucessfully.Uploading photos...");

            String inspection_id = schemeSubmitResponse.getInspection_id();
            FilePath = getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME + "/" + PIC_NAME;
            File file1 = new File(FilePath);

            FilePath2 = getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME + "/" + PIC_NAME2;
            File file2 = new File(FilePath2);
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file1.getName(), requestFile);
            RequestBody requestFile1 =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file2);
            MultipartBody.Part body2 =
                    MultipartBody.Part.createFormData("image", file2.getName(), requestFile1);
            List<MultipartBody.Part> partList = new ArrayList<>();
            if (PIC_NAME != null)
                partList.add(body);
            if (PIC_NAME2 != null)
                partList.add(body2);
            callUploadPhoto(partList);

        } else if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)
                && imgflag1 == 0 && imgflag2 == 0) {
            CallSuccessAlert(schemeSubmitResponse.getStatusMessage());
        } else if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            benDetailsBinding.progress.setVisibility(View.GONE);
            Snackbar.make(benDetailsBinding.cl, schemeSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            benDetailsBinding.progress.setVisibility(View.GONE);
            Snackbar.make(benDetailsBinding.cl, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getPhotoData(SchemePhotoSubmitResponse schemePhotoSubmitResponse) {
        customProgressDialog.hide();
        if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
            CallSuccessAlert(schemePhotoSubmitResponse.getStatusMessage());
        } else if (schemePhotoSubmitResponse != null && schemePhotoSubmitResponse.getStatusCode() != null && schemePhotoSubmitResponse.getStatusCode().equals(AppConstants.FAILURE_CODE)) {
            Snackbar.make(benDetailsBinding.cl, schemePhotoSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(benDetailsBinding.cl, getString(R.string.something), Snackbar.LENGTH_SHORT).show();
        }
    }
}
