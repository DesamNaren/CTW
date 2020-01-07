package com.example.twdinspection.schemes.ui;

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

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.example.twdinspection.inspection.ui.LocBaseActivity;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.submit.SchemePhotoSubmitResponse;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.example.twdinspection.schemes.viewmodel.BenDetailsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class BenDetailsActivity extends LocBaseActivity {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    ActivityBenDetailsActivtyBinding benDetailsBinding;
    private BenDetailsViewModel viewModel;
    private BeneficiaryDetail beneficiaryDetail;
    private String fieldSelVal = "";
    private String selectedRemId = "";
    SharedPreferences sharedPreferences;
    public Uri fileUri;
    public static final String IMAGE_DIRECTORY_NAME = "SCHEME_IMAGES";
    String FilePath;
    Bitmap bm;
    String PIC_NAME;
    ;
    int imgflag = 0;
    private String officerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callPermissions();

        benDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_ben_details_activty);
        benDetailsBinding.header.headerTitle.setText(getString(R.string.ben_details));

        try {
            beneficiaryDetail = getIntent().getParcelableExtra(AppConstants.BEN_DETAIL);
            if (beneficiaryDetail != null) {
                viewModel = new BenDetailsViewModel(getApplication(), beneficiaryDetail);
                benDetailsBinding.setViewModel(viewModel);
                benDetailsBinding.executePendingBindings();
            } else {
                //something went wrong ..
            }
        } catch (Exception e) {
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

        benDetailsBinding.ivCam.setOnClickListener(new View.OnClickListener() {
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
                //Submit Call

                if (TextUtils.isEmpty(fieldSelVal)) {
                    Snackbar.make(benDetailsBinding.cl, "Please select online status value", Snackbar.LENGTH_SHORT).show();
                } else if (fieldSelVal.equals(AppConstants.No) && TextUtils.isEmpty(selectedRemId)) {
                    Snackbar.make(benDetailsBinding.cl, "Please select remark type", Snackbar.LENGTH_SHORT).show();
                } else if (imgflag == 0) {
                    Toast.makeText(BenDetailsActivity.this, "Please capture image", Toast.LENGTH_SHORT).show();
                } else {
                    submitCall(beneficiaryDetail);
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

        viewModel.getSchemeSubmitResponse(schemeSubmitRequest).observe(this, new Observer<SchemeSubmitResponse>() {
            @Override
            public void onChanged(SchemeSubmitResponse schemeSubmitResponse) {
                if (schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
                    FilePath = getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME + "/" + PIC_NAME;

                    File file = new File(FilePath);
                    RequestBody requestFile =
                            RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("image", file.getName(), requestFile);
//                    progressDialog = new ProgressDialog(UpdateLocationActivity.this);
//                    progressDialog.show();
//                    progressDialog.setMessage("Loading...");
//                    progressDialog.setCancelable(false);


//                    if (Utils.checkInternetConnection(UpdateLocationActivity.this)) {
                    callUploadPhoto(body);
//                    } else {
//                        progressDialog.dismiss();
//                        Utils.showAlert(UpdateLocationActivity.this, getResources().getString(R.string.app_name),
//                                "No internet connection", false);
//                    }
                } else {
                    Snackbar.make(benDetailsBinding.cl, schemeSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });


    }


    void callUploadPhoto(final MultipartBody.Part body) {
        viewModel.getSchemePhotoSubmitResponse(body).observe(this, new Observer<SchemePhotoSubmitResponse>() {
            @Override
            public void onChanged(SchemePhotoSubmitResponse schemeSubmitResponse) {
                if (schemeSubmitResponse.getStatusCode() != null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)) {
                    Snackbar.make(benDetailsBinding.cl, schemeSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(benDetailsBinding.cl, schemeSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    BenDetailsActivity.this,
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
            PIC_NAME = officerId + "~" + beneficiaryDetail.getSchemeId() + "~" + beneficiaryDetail.getBenID() + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void updateLocationUI() {
        super.updateLocationUI();
    }
//
//    @Override
//    public boolean callPermissions() {
//        return super.callPermissions();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


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

                imgflag = 1;

                benDetailsBinding.ivCam.setImageBitmap(bm);

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

        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }
}
