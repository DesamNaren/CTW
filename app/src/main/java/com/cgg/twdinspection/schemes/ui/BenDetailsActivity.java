package com.cgg.twdinspection.schemes.ui;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
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
    String PIC_NAME, PIC_NAME2;
    int imgflag1 = 0;
    int imgflag2 = 0;
    private String officerId;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callPermissions();
        customProgressDialog = new CustomProgressDialog(BenDetailsActivity.this);

        benDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_ben_details_activty);
        benDetailsBinding.header.headerTitle.setText(getString(R.string.ben_details));
        benDetailsBinding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        benDetailsBinding.header.ivHome.setVisibility(View.GONE);

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
                                && ((beneficiaryDetail.getStatusValue().equalsIgnoreCase("Grounded but UC not uploaded"))) ||
                                ((beneficiaryDetail.getStatusValue().equalsIgnoreCase("Unit grounded but defunct")))))
                        && imgflag1 == 0 && imgflag2 == 0) {
                    Toast.makeText(BenDetailsActivity.this, "Please capture images", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utils.checkInternetConnection(BenDetailsActivity.this)) {
                        customProgressDialog.show();
                        submitCall(beneficiaryDetail);
                    } else {
                        Utils.customWarningAlert(BenDetailsActivity.this, getResources().getString(R.string.app_name), "Please check internet");
                    }
                }
            }
        });
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

        viewModel.submitSchemeDetails(schemeSubmitRequest);

    }

    void callUploadPhoto(final MultipartBody.Part body, final MultipartBody.Part body2) {
        customProgressDialog.show();
        viewModel.UploadImageServiceCall(body, body2);
    }

    private void CallSuccessAlert(String msg) {
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
            PIC_NAME = officerId + "~" + beneficiaryDetail.getSchemeId() + "~" + beneficiaryDetail.getBenID() + "~" + Utils.getCurrentDateTime() + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
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
            PIC_NAME2 = officerId + "~" + beneficiaryDetail.getSchemeId() + "~" + beneficiaryDetail.getBenID() + "~" + Utils.getCurrentDateTime() + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME2);
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void updateLocationUI() {
        super.updateLocationUI();
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

                String Image_name = PIC_NAME2;
                FilePath = FilePath + "/" + Image_name;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                bm = BitmapFactory.decodeFile(FilePath, options);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                String OLDmyBase64Image = encodeToBase64(bm, Bitmap.CompressFormat.JPEG,
                        100);

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

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
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
        &&imgflag1 == 1 && imgflag2 == 1) {

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
            callUploadPhoto(body, body2);

        }else  if (schemeSubmitResponse != null && schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)
                &&imgflag1 == 0 && imgflag2 == 0){
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
