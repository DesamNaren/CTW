package com.example.twdinspection.inspection.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityDietIssuesBinding;
import com.example.twdinspection.inspection.adapter.DietIssuesAdapter;
import com.example.twdinspection.inspection.interfaces.DietInterface;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.reports.source.DietIssues;
import com.example.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.example.twdinspection.inspection.source.inst_master.MasterDietInfo;
import com.example.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.example.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.example.twdinspection.inspection.viewmodel.DietIssuesCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.DietIsuuesViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class DietIssuesActivity extends BaseActivity implements SaveListener, DietInterface {

    ActivityDietIssuesBinding binding;
    DietIsuuesViewModel dietIsuuesViewModel;
    InstMainViewModel instMainViewModel;
    DietIssuesEntity dietIssuesEntity;
    SharedPreferences sharedPreferences;
    private String officerID, instID, insTime;
    List<DietListEntity> dietInfoEntityListMain;
    DietIssuesAdapter adapter;
    MasterInstituteInfo masterInstituteInfos;
    String menu_chart_served, menu_chart_painted, menu_served, food_provisions, matching_with_samples, committee_exist, discussed_with_committee, maintaining_register;
    private String cacheDate, currentDate;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    String FilePath;
    File file_menu, file_officer;
    int flag_menu = 0, flag_officer = 0;
    SharedPreferences.Editor editor;
    private int localFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diet_issues);
        binding.header.headerTitle.setText(getResources().getString(R.string.diet_issues));
        binding.header.ivHome.setVisibility(View.GONE);
        viewModel = new UploadPhotoViewModel(DietIssuesActivity.this);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dietIsuuesViewModel = ViewModelProviders.of(DietIssuesActivity.this,
                new DietIssuesCustomViewModel(binding, this, getApplication())).get(DietIsuuesViewModel.class);
        binding.setViewModel(dietIsuuesViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        dietInfoEntityListMain = new ArrayList<>();
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();

            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        dietIsuuesViewModel.getDietInfo(TWDApplication.get(DietIssuesActivity.this).getPreferences().getString(AppConstants.INST_ID, "")).observe(DietIssuesActivity.this, new Observer<List<DietListEntity>>() {
            @Override
            public void onChanged(List<DietListEntity> dietIssuesEntities) {
                if (dietIssuesEntities != null && dietIssuesEntities.size() > 0) {
                    dietInfoEntityListMain = dietIssuesEntities;
                    adapter = new DietIssuesAdapter(DietIssuesActivity.this, dietInfoEntityListMain);
                    binding.recyclerView.setLayoutManager(new GridLayoutManager(DietIssuesActivity.this, 2));
                    binding.recyclerView.setAdapter(adapter);
                } else {
                    LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = dietIsuuesViewModel.getMasterDietInfo(TWDApplication.get(DietIssuesActivity.this).getPreferences().getString(AppConstants.INST_ID, ""));
                    masterInstituteInfoLiveData.observe(DietIssuesActivity.this, new Observer<MasterInstituteInfo>() {
                        @Override
                        public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                            masterInstituteInfoLiveData.removeObservers(DietIssuesActivity.this);
                            DietIssuesActivity.this.masterInstituteInfos = masterInstituteInfos;
                            List<MasterDietInfo> masterDietInfos = masterInstituteInfos.getDietInfo();
                            if (masterDietInfos != null && masterDietInfos.size() > 0) {
                                for (int i = 0; i < masterDietInfos.size(); i++) {
                                    DietListEntity studAttendInfoEntity = new DietListEntity(masterDietInfos.get(i).getItemName(), masterDietInfos.get(i).getGroundBal(), masterDietInfos.get(i).getBookBal(), instID, officerID);
                                    dietInfoEntityListMain.add(studAttendInfoEntity);
                                }
                            }

                            if (dietInfoEntityListMain != null && dietInfoEntityListMain.size() > 0) {
                                dietIsuuesViewModel.insertDietInfo(dietInfoEntityListMain);
                            } else {
                                binding.emptyView.setVisibility(View.VISIBLE);
                                binding.recyclerView.setVisibility(View.GONE);
                            }
                        }
                    });

                }

            }
        });

        binding.rgMenuChartServed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMenuChartServed.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_menu_chart_served)
                    menu_chart_served = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_menu_chart_served)
                    menu_chart_served = AppConstants.No;
                else
                    menu_chart_served = null;
            }
        });


        binding.ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.MENU;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.MENU;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });
        binding.ivInspOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.OFFICER;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.OFFICER;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, MY_CAMERA_REQUEST_CODE);
                    }
                }
            }
        });

        binding.rgMenuChartPainted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMenuChartPainted.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_menu_chart_painted)
                    menu_chart_painted = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_menu_chart_painted)
                    menu_chart_painted = AppConstants.No;
                else
                    menu_chart_painted = null;
            }
        });

        binding.rgPrescribedMenuServed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPrescribedMenuServed.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_menu_served_yes)
                    menu_served = AppConstants.Yes;
                else if (selctedItem == R.id.rb_menu_served_no)
                    menu_served = AppConstants.No;
                else
                    menu_served = null;
            }
        });

        binding.rgFoodProvisions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgFoodProvisions.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_food_provisions_yes)
                    food_provisions = AppConstants.Yes;
                else if (selctedItem == R.id.rb_food_provisions_no)
                    food_provisions = AppConstants.No;
                else
                    food_provisions = null;
            }
        });

        binding.rgMatchingWithSamples.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMatchingWithSamples.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_matching_with_samples_yes)
                    matching_with_samples = AppConstants.Yes;
                else if (selctedItem == R.id.rb_matching_with_samples_no)
                    matching_with_samples = AppConstants.No;
                else
                    matching_with_samples = null;
            }
        });

        binding.rgCommitteeExist.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCommitteeExist.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_committee_exist_yes) {
                    committee_exist = AppConstants.Yes;
                    binding.llCommittee.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.rb_committee_exist_no) {
                    committee_exist = AppConstants.No;
                    binding.llCommittee.setVisibility(View.GONE);
                    discussed_with_committee = null;
                    maintaining_register = null;
                } else {
                    committee_exist = null;
                    binding.llCommittee.setVisibility(View.GONE);
                }
            }
        });


        binding.rgDiscussedWithCommittee.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDiscussedWithCommittee.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_discussed_with_committee_yes)
                    discussed_with_committee = AppConstants.Yes;
                else if (selctedItem == R.id.rb_discussed_with_committee_no)
                    discussed_with_committee = AppConstants.No;
                else
                    discussed_with_committee = null;
            }
        });

        binding.rgMaintainingRegister.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMaintainingRegister.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_maintaining_register_yes)
                    maintaining_register = AppConstants.Yes;
                else if (selctedItem == R.id.rb_maintaining_register_no)
                    maintaining_register = AppConstants.No;
                else
                    maintaining_register = null;
            }
        });


        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateData()) {

                    dietIssuesEntity = new DietIssuesEntity();

                    dietIssuesEntity.setInstitute_id(instID);
                    dietIssuesEntity.setInspection_time(Utils.getCurrentDateTime());
                    dietIssuesEntity.setOfficer_id(officerID);
                    dietIssuesEntity.setMenu_chart_served(menu_chart_served);
                    dietIssuesEntity.setMenu_chart_painted(menu_chart_painted);
                    dietIssuesEntity.setMenu_served(menu_served);
                    dietIssuesEntity.setFood_provisions(food_provisions);
                    dietIssuesEntity.setMatching_with_samples(matching_with_samples);
                    dietIssuesEntity.setCommittee_exist(committee_exist);
                    dietIssuesEntity.setDiscussed_with_committee(discussed_with_committee);
                    dietIssuesEntity.setMaintaining_register(maintaining_register);

                    Utils.customSaveAlert(DietIssuesActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

                }
            }
        });


        try {
            localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<DietIssuesEntity> dietInfoData = instMainViewModel.getDietInfoData();
                dietInfoData.observe(DietIssuesActivity.this, new Observer<DietIssuesEntity>() {
                    @Override
                    public void onChanged(DietIssuesEntity dietIssuesEntity) {
                        dietInfoData.removeObservers(DietIssuesActivity.this);
                        if (dietIssuesEntity != null) {
                            binding.setInspData(dietIssuesEntity);
                            binding.executePendingBindings();

                            LiveData<UploadPhoto> uploadPhotoLiveData = viewModel.getPhotoData(AppConstants.MENU);
                            uploadPhotoLiveData.observe(DietIssuesActivity.this, new Observer<UploadPhoto>() {
                                @Override
                                public void onChanged(UploadPhoto uploadPhoto) {
                                    uploadPhotoLiveData.removeObservers(DietIssuesActivity.this);
                                    if(uploadPhoto!=null && !TextUtils.isEmpty(uploadPhoto.getPhoto_path())){
                                        file_menu = new File(uploadPhoto.getPhoto_path());
                                        Glide.with(DietIssuesActivity.this).load(file_menu).into(binding.ivMenu);
                                        flag_menu = 1;
                                    }else {
                                        Glide.with(DietIssuesActivity.this).load(R.drawable.ic_menu_camera).into(binding.ivMenu);
                                        flag_menu = 0;
                                    }
                                }
                            });

                            LiveData<UploadPhoto> uploadPhotoLiveData1 = viewModel.getPhotoData(AppConstants.OFFICER);
                            uploadPhotoLiveData1.observe(DietIssuesActivity.this, new Observer<UploadPhoto>() {
                                @Override
                                public void onChanged(UploadPhoto uploadPhoto) {
                                    uploadPhotoLiveData1.removeObservers(DietIssuesActivity.this);
                                    if(uploadPhoto!=null && !TextUtils.isEmpty(uploadPhoto.getPhoto_path())){
                                        file_officer = new File(uploadPhoto.getPhoto_path());
                                        Glide.with(DietIssuesActivity.this).load(file_officer).into(binding.ivInspOfficer);
                                        flag_officer = 1;
                                    }else {
                                        Glide.with(DietIssuesActivity.this).load(R.drawable.ic_menu_camera).into(binding.ivInspOfficer);
                                        flag_officer = 0;
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                flag_menu = 0;
                flag_officer = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(DietIssuesActivity.this,
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
                Utils.openSettings(DietIssuesActivity.this);
            }
        });

        snackbar.show();
    }

    private boolean validateData() {

        if (TextUtils.isEmpty(menu_chart_served)) {
            showSnackBar(getResources().getString(R.string.select_menu_chart_served));
            return false;
        }

        if (TextUtils.isEmpty(menu_chart_painted)) {
            showSnackBar(getResources().getString(R.string.sel_menu_chart_painted));
            return false;
        }
        if (flag_menu == 0) {
            showSnackBar("Please capture Menu image");
            return false;
        }
        if (TextUtils.isEmpty(menu_served)) {
            showSnackBar(getResources().getString(R.string.sel_menu_served));
            return false;
        }

        if (TextUtils.isEmpty(food_provisions)) {
            showSnackBar(getResources().getString(R.string.sel_food_provisions));
            return false;
        }

        if (TextUtils.isEmpty(matching_with_samples)) {
            showSnackBar(getResources().getString(R.string.sel_matching_with_samples));
            return false;
        }

        if (TextUtils.isEmpty(committee_exist)) {
            showSnackBar(getResources().getString(R.string.sel_committee_exist));
            return false;
        }

        if (committee_exist.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(discussed_with_committee)) {
            showSnackBar(getResources().getString(R.string.sel_discussed_with_committee));
            return false;
        }

        if (committee_exist.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(maintaining_register)) {
            showSnackBar(getResources().getString(R.string.sel_maintaining_register));
            return false;
        }
        if (flag_officer == 0) {
            showSnackBar("Please capture Inspector Officer image");
            return false;
        }

        return true;
    }

    UploadPhotoViewModel viewModel;
    private List<UploadPhoto> uploadPhotos = new ArrayList<>();

    @Override
    public void submitData() {
        addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.MENU, String.valueOf(file_menu));
        addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.OFFICER, String.valueOf(file_officer));

        long y = viewModel.insertPhotos(uploadPhotos);
        if (y >= 0) {
            long x = dietIsuuesViewModel.updateDietIssuesInfo(dietIssuesEntity);
            if (x >= 0) {
                final long[] z = {0};
                try {
                    LiveData<Integer> liveData = instMainViewModel.getSectionId("Diet");
                    ;
                    liveData.observe(DietIssuesActivity.this, new Observer<Integer>() {
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
                    Utils.customSectionSaveAlert(DietIssuesActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
                } else {
                    showSnackBar(getString(R.string.failed));
                }
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    DietIssuesActivity.this,
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
            PIC_NAME = officerID + "~" + instID + "~" + Utils.getCurrentDateTime() + "~" + PIC_TYPE + ".png";
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + PIC_NAME);
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_NAME;
                FilePath = FilePath + "/" + Image_name;
                if (PIC_TYPE.equals(AppConstants.MENU)) {
                    flag_menu = 1;
                    binding.ivMenu.setPadding(0, 0, 0, 0);
                    binding.ivMenu.setBackgroundColor(getResources().getColor(R.color.white));
                    file_menu = new File(FilePath);
                    Glide.with(DietIssuesActivity.this).load(file_menu).into(binding.ivMenu);
                } else if (PIC_TYPE.equals(AppConstants.OFFICER)) {
                    flag_officer = 1;
                    binding.ivInspOfficer.setPadding(0, 0, 0, 0);
                    binding.ivInspOfficer.setBackgroundColor(getResources().getColor(R.color.white));
                    file_officer = new File(FilePath);
                    Glide.with(DietIssuesActivity.this).load(file_officer).into(binding.ivInspOfficer);
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

    @Override
    public void onBackPressed() {
        callBack();
    }


    @Override
    protected void onResume() {
        super.onResume();

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
                    editor.clear();
                    editor.commit();
                    instMainViewModel.deleteAllInspectionData();
                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re));
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

    public void callBack() {
        Utils.customHomeAlert(DietIssuesActivity.this, getString(R.string.app_name), getString(R.string.go_back));
    }

    @Override
    public void validate() {

    }

    public void customPerAlert() {
        try {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                CustomFontTextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(getString(R.string.plz_grant));
                Button yes = dialog.findViewById(R.id.btDialogYes);
                Button no = dialog.findViewById(R.id.btDialogNo);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                        }
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
                if (!dialog.isShowing()) {
                    dialog.show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
