package com.cgg.twdinspection.inspection.ui;

import android.Manifest;
import android.app.Activity;
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
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.cgg.twdinspection.BuildConfig;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityDietIssuesBinding;
import com.cgg.twdinspection.inspection.adapter.DietIssuesAdapter;
import com.cgg.twdinspection.inspection.interfaces.DietInterface;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.diet_issues.DietIssuesEntity;
import com.cgg.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.cgg.twdinspection.inspection.source.inst_master.MasterDietInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.cgg.twdinspection.inspection.viewmodel.DietIssuesCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.DietIsuuesViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
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
    private String officerID, instID, insTime, randomNo;
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
    SearchView mSearchView;
    Menu mMenu = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diet_issues);

        viewModel = new UploadPhotoViewModel(DietIssuesActivity.this);

        try {
            if (getSupportActionBar() != null) {
                TextView tv = new TextView(getApplicationContext());
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                        RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
                tv.setLayoutParams(lp);
                tv.setText(getResources().getString(R.string.diet_issues));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                getSupportActionBar().setCustomView(tv);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            randomNo = sharedPreferences.getString(AppConstants.RANDOM_NO, "");

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
                if (selctedItem == R.id.rb_yes_menu_chart_painted) {
                    file_menu = null;
                    flag_menu = 0;
                    menu_chart_painted = AppConstants.Yes;
                    binding.llMenuChart.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.rb_no_menu_chart_painted) {
                    file_menu = null;
                    flag_menu = 0;
                    binding.ivMenu.setPadding(0, 0, 0, 0);
                    binding.ivMenu.setBackgroundColor(getResources().getColor(R.color.white));
                    Glide.with(DietIssuesActivity.this).load(R.drawable.ic_menu_camera).into(binding.ivMenu);
                    binding.ivMenu.setBackground(getResources().getDrawable(R.drawable.ic_menu_camera));
                    menu_chart_painted = AppConstants.No;
                    binding.llMenuChart.setVisibility(View.GONE);
                } else {
                    menu_chart_painted = null;
                    binding.llMenuChart.setVisibility(View.GONE);
                }
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
                if (selctedItem == R.id.rb_food_provisions_yes) {
                    food_provisions = AppConstants.Yes;
                    binding.llMatchingWithSamples.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.rb_food_provisions_no) {
                    matching_with_samples = "";
                    binding.rgMatchingWithSamples.clearCheck();
                    food_provisions = AppConstants.No;
                    binding.llMatchingWithSamples.setVisibility(View.GONE);
                } else {
                    binding.rgMatchingWithSamples.clearCheck();
                    binding.llMatchingWithSamples.setVisibility(View.GONE);
                    food_provisions = null;
                }
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
                    binding.llDiscussWithComm.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.rb_committee_exist_no) {
                    binding.rgDiscussedWithCommittee.clearCheck();
                    discussed_with_committee = "";
                    committee_exist = AppConstants.No;
                    binding.llDiscussWithComm.setVisibility(View.GONE);
                    discussed_with_committee = null;
                } else {
                    binding.rgDiscussedWithCommittee.clearCheck();
                    discussed_with_committee = "";
                    committee_exist = null;
                    binding.llDiscussWithComm.setVisibility(View.GONE);
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
                    List<DietListEntity> selectedList = new ArrayList<>();
                    for (int i = 0; i < dietInfoEntityListMain.size(); i++) {
                        if (dietInfoEntityListMain.get(i).isFlag_selected()) {
                            selectedList.add(dietInfoEntityListMain.get(i));
                        }
                    }
                    dietIssuesEntity.setDietListEntities(selectedList);
                    dietIsuuesViewModel.insertDietInfo(dietInfoEntityListMain);
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
                                    if (uploadPhoto != null && !TextUtils.isEmpty(uploadPhoto.getPhoto_path())) {
                                        file_menu = new File(uploadPhoto.getPhoto_path());
                                        Glide.with(DietIssuesActivity.this).load(file_menu).into(binding.ivMenu);
                                        flag_menu = 1;
                                    } else {
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
                                    if (uploadPhoto != null && !TextUtils.isEmpty(uploadPhoto.getPhoto_path())) {
                                        file_officer = new File(uploadPhoto.getPhoto_path());
                                        Glide.with(DietIssuesActivity.this).load(file_officer).into(binding.ivInspOfficer);
                                        flag_officer = 1;
                                    } else {
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

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view,view);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        mMenu = menu;
        MenuItem item = mMenu.findItem(R.id.mi_filter);
        item.setVisible(false);
        MenuItem mSearch = mMenu.findItem(R.id.action_search);
        mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint_diet) + "</font>"));
        mSearchView.setInputType(InputType.TYPE_CLASS_TEXT);
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);
        mSearchView.setGravity(Gravity.CENTER);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mi_filter:
                if (!mSearchView.isIconified()) {
                    mSearchView.onActionViewCollapsed();
                }

                break;

            case android.R.id.home:
                finish();
                break;
        }
        return true;
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

        if (TextUtils.isEmpty(menu_chart_painted)) {
            showSnackBar(getResources().getString(R.string.sel_menu_chart_painted));
            ScrollToView(binding.rgMenuChartPainted);
            return false;
        }
        if (menu_chart_painted.equalsIgnoreCase(AppConstants.Yes) && flag_menu == 0) {
            showSnackBar(getString(R.string.cap_menu_image));
            return false;
        }
        if (TextUtils.isEmpty(menu_served)) {
            showSnackBar(getResources().getString(R.string.sel_menu_served));
            ScrollToView(binding.rgPrescribedMenuServed);
            return false;
        }

        if (TextUtils.isEmpty(food_provisions)) {
            showSnackBar(getResources().getString(R.string.sel_food_provisions));
            ScrollToView(binding.rgFoodProvisions);
            return false;
        }
        if (food_provisions.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(matching_with_samples)) {
            showSnackBar(getResources().getString(R.string.sel_matching_with_samples));
            ScrollToView(binding.rgMatchingWithSamples);
            return false;
        }
        if (TextUtils.isEmpty(committee_exist)) {
            showSnackBar(getResources().getString(R.string.sel_committee_exist));
            ScrollToView(binding.rgCommitteeExist);
            return false;
        }

        if (committee_exist.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(discussed_with_committee)) {
            showSnackBar(getResources().getString(R.string.sel_discussed_with_committee));
            ScrollToView(binding.rgDiscussedWithCommittee);
            return false;
        }

        if (TextUtils.isEmpty(maintaining_register)) {
            showSnackBar(getResources().getString(R.string.sel_maintaining_register));
            ScrollToView(binding.rgMaintainingRegister);
            return false;
        }
        if (TextUtils.isEmpty(menu_chart_served)) {
            showSnackBar(getResources().getString(R.string.select_menu_chart_served));
            ScrollToView(binding.rgMenuChartServed);
            return false;
        }
        if (flag_officer == 0) {
            showSnackBar(getString(R.string.insp_officer_image));
            return false;
        }

        return true;
    }

    UploadPhotoViewModel viewModel;
    private List<UploadPhoto> uploadPhotos = new ArrayList<>();

    @Override
    public void submitData() {
        if (menu_chart_painted.equalsIgnoreCase(AppConstants.Yes)) {
            addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.MENU, String.valueOf(file_menu));
        } else {
            file_menu = null;
            addPhoto(instID, "12", Utils.getCurrentDateTime(), AppConstants.MENU, String.valueOf(file_menu));
        }
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
            String deviceId = Utils.getDeviceID(DietIssuesActivity.this);
            String versionName = Utils.getVersionName(DietIssuesActivity.this);
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + instID + "~" + Utils.getCurrentDateTimeFormat() + "~" + deviceId + "~" + versionName + "~" + randomNo + ".png";

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
                        R.string.user_cancelled, Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        R.string.image_capture_failed, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (dietInfoEntityListMain != null && dietInfoEntityListMain.size() > 0 && !(localFlag == 1)) {
            customExitAlert(DietIssuesActivity.this, getString(R.string.app_name), getString(R.string.data_lost));
        } else {
            super.callBack();
        }
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


    @Override
    public void validate() {

    }


    private void customExitAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_exit);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button exit = dialog.findViewById(R.id.btDialogExit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        dietIsuuesViewModel.deleteDietListInfo();
                        finish();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btDialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
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
