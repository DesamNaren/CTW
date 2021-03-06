package com.cgg.twdinspection.inspection.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityInfrastructureBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.infra_maintenance.InfraStructureEntity;
import com.cgg.twdinspection.inspection.source.upload_photo.UploadPhoto;
import com.cgg.twdinspection.inspection.viewmodel.InfraCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InfraViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.inspection.viewmodel.UploadPhotoViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class InfraActivity extends BaseActivity implements SaveListener {
    ActivityInfrastructureBinding binding;
    InfraViewModel infraViewModel;
    SharedPreferences sharedPreferences;
    InfraStructureEntity infraStructureEntity;
    private String sourceOfDrinkingWater ;
    String drinkingWaterFacility;
    String runningWaterFacility;
    String bigSchoolNameBoard;
    String roPlant;
    String sourceOfRunningWater;
    String inverter_available;
    String inverterWorkingStatus;
    String electricity_wiring;
    String enough_fans;
    String dining_hall;
    String dining_hall_used;
    String dining_hall_avail_construction;
    String dining_hall_add_req;
    String separate_kitchen_room_available;
    String construct_kitchen_room;
    String is_it_in_good_condition;
    String transformer_available;
    String powerConnectionType;
    String individual_connection;
    String road_required;
    String compWall_required;
    String construction_part_type;
    String compWall_cnt;
    String cc_cameras;
    String steam_cooking;
    String bunker_beds;
    String bunker_beds_cnt;
    String gate_required;
    String pathway_required;
    String sump_required;
    String sewage_allowed;
    String sewage_raise_req;
    String drainage_functioning;
    String heater_available;
    String heater_workingStatus;
    String repairs_to_door;
    String painting;
    String roplant_reason;
    String ceilingFansWorking;
    String ceilingFansNonWorking;
    String ceilingFansReq;
    String mountedFansWorking;
    String mountedFansNonWorking;
    String mountedFansReq;
    String lightsWorking;
    String lightsNonWorking;
    String lightsReq;
    String repair_required;
    String how_many_buildings;
    String totalToilets;
    String totalBathrooms;
    String functioningBathrooms;
    String functioningToilets;
    String repairsReqToilets;
    String repairsReqBathrooms;
    String add_req;

    InstMainViewModel instMainViewModel;
    private String officerID;
    private String instID;
    private String randomNo;
    private String add_cls_cnt, add_din_cnt, add_dom_cnt, add_toilets_cnt, add_bathrooms_cnt;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    String FilePath;
    File file_ro_plant;
    int flag_ro_plant = 0;
    SharedPreferences.Editor editor;
    private Set<String> drinkingWaterSources;

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_infrastructure);
        drinkingWaterSources= new HashSet<>();
        TextView[] ids = new TextView[]{binding.slno1, binding.slno2, binding.slno3, binding.slno4, binding.slno5,
                binding.slno6, binding.slno7, binding.slno8, binding.slno9, binding.slno10, binding.slno11, binding.slno12,
                binding.slno13, binding.slno14, binding.slno15, binding.slno16, binding.slno17,
                binding.slno19, binding.slno20, binding.slno21, binding.slno22, binding.slno23, binding.slno24,
                binding.slno25, binding.slno26};
        BaseActivity.setIds(ids, 28);
        binding.slno261.setText(binding.slno26.getText().toString() + "1.");

        binding.header.headerTitle.setText(getResources().getString(R.string.title_infra));
        binding.header.ivHome.setVisibility(View.GONE);
        viewModel = new UploadPhotoViewModel(InfraActivity.this);
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        infraViewModel = ViewModelProviders.of(InfraActivity.this,
                new InfraCustomViewModel(binding, this, getApplication())).get(InfraViewModel.class);
        binding.setViewModel(infraViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();

            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
            String insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        InstSelectionViewModel selectionViewModel = new InstSelectionViewModel(getApplication());
        LiveData<String> liveData = selectionViewModel.getRandomId(instID);
        liveData.observe(InfraActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String value) {
                randomNo = value;
            }
        });

        binding.rgBigSchoolNameBoard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBigSchoolNameBoard.getCheckedRadioButtonId();
                if (selctedItem == R.id.big_school_name_board_yes)
                    bigSchoolNameBoard = "YES";
                else if (selctedItem == R.id.big_school_name_board_no)
                    bigSchoolNameBoard = "NO";
                else
                    bigSchoolNameBoard = null;
            }
        });

        binding.rgDrinkingWaterFacility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrinkingWaterFacility.getCheckedRadioButtonId();
                if (selctedItem == R.id.drinking_water_facility_yes) {
                    drinkingWaterFacility = "YES";
                    binding.llDrinkingWater.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.drinking_water_facility_no) {
                    drinkingWaterFacility = "NO";
                    binding.llDrinkingWater.setVisibility(View.GONE);
                } else
                    drinkingWaterFacility = null;
            }
        });

        binding.drinkingWaterRoPlant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    drinkingWaterSources.add(getResources().getString(R.string.ro_plant));
                }else {
                    drinkingWaterSources.remove(getResources().getString(R.string.ro_plant));
                }

            }
        });
        binding.drinkingWaterSourceOpenWell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    drinkingWaterSources.add(getResources().getString(R.string.open_well));
                }else {
                    drinkingWaterSources.remove(getResources().getString(R.string.open_well));
                }
            }
        });
        binding.drinkingWaterSourceBoreWell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    drinkingWaterSources.add(getResources().getString(R.string.bore_well));
                }else {
                    drinkingWaterSources.remove(getResources().getString(R.string.bore_well));
                }
            }
        });
        binding.drinkingWaterSourceMunicipal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    drinkingWaterSources.add(getResources().getString(R.string.municipal));
                }else {
                    drinkingWaterSources.remove(getResources().getString(R.string.municipal));
                }
            }
        });
        binding.drinkingWaterSourceHandPump.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    drinkingWaterSources.add(getResources().getString(R.string.hand_pump));
                }else {
                    drinkingWaterSources.remove(getResources().getString(R.string.hand_pump));
                }
            }
        });

        binding.rgRoPlant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int selctedItem = binding.rgRoPlant.getCheckedRadioButtonId();
                if (selctedItem == R.id.ro_plant_yes) {
                    flag_ro_plant = 0;
                    file_ro_plant = null;
                    roPlant = "YES";
                    binding.llReason.setVisibility(View.GONE);
                    binding.llTdsMeterReading.setVisibility(View.VISIBLE);
                    binding.etReason.setText("");

                    binding.ivTds.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));

                } else if (selctedItem == R.id.ro_plant_no) {
                    file_ro_plant = null;
                    roPlant = "NO";
                    binding.llReason.setVisibility(View.VISIBLE);
                    binding.llTdsMeterReading.setVisibility(View.GONE);
                } else {
                    roPlant = null;
                    binding.llReason.setVisibility(View.GONE);
                    binding.llTdsMeterReading.setVisibility(View.GONE);
                }
            }
        });

        binding.rgRunningWaterFacility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRunningWaterFacility.getCheckedRadioButtonId();
                if (selctedItem == R.id.running_water_facility_yes) {
                    runningWaterFacility = "YES";
                    binding.llRunningwater.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.running_water_facility_no) {
                    runningWaterFacility = "NO";
                    binding.llRunningwater.setVisibility(View.GONE);
                    binding.rgRunningWaterSource.clearCheck();
                } else
                    runningWaterFacility = null;
            }
        });

        binding.rgRunningWaterSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRunningWaterSource.getCheckedRadioButtonId();
                if (selctedItem == R.id.runningWater_source_open_well)
                    sourceOfRunningWater = getResources().getString(R.string.open_well);
                else if (selctedItem == R.id.runningWater_source_bore_well)
                    sourceOfRunningWater = getResources().getString(R.string.bore_well);
                else if (selctedItem == R.id.runningWater_source_municipal)
                    sourceOfRunningWater = getResources().getString(R.string.municipal);
                else if (selctedItem == R.id.runningWater_source_hand_pump)
                    sourceOfRunningWater = getResources().getString(R.string.hand_pump);
                else
                    sourceOfRunningWater = null;
            }
        });

        binding.ivTds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        callSettings();
                    } else {
                        PIC_TYPE = AppConstants.RO_PLANT;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                        if (fileUri != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                        }
                    }
                } else {
                    PIC_TYPE = AppConstants.RO_PLANT;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });


        binding.rgIsInverterAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgIsInverterAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.is_inverter_available_yes) {
                    inverter_available = "YES";
                    binding.llInverterWorkingStatus.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.is_inverter_available_no) {
                    inverter_available = "NO";
                    binding.llInverterWorkingStatus.setVisibility(View.GONE);
                    binding.rgInverterWorkingStatus.clearCheck();
                } else {
                    inverter_available = null;
                    binding.llInverterWorkingStatus.setVisibility(View.GONE);
                    binding.rgInverterWorkingStatus.clearCheck();
                }
            }
        });

        binding.rgInverterWorkingStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgInverterWorkingStatus.getCheckedRadioButtonId();
                if (selctedItem == R.id.inverter_working_status_yes)
                    inverterWorkingStatus = "GOOD";
                else if (selctedItem == R.id.inverter_working_status_no)
                    inverterWorkingStatus = "BAD";
                else
                    inverterWorkingStatus = null;
            }
        });

        binding.rgElectricityWiring.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgElectricityWiring.getCheckedRadioButtonId();
                if (selctedItem == R.id.electricity_wiring_yes) {
                    electricity_wiring = "YES";
                } else if (selctedItem == R.id.electricity_wiring_no) {
                    electricity_wiring = "NO";
                } else {
                    electricity_wiring = null;
                }
            }
        });


        binding.rgEnoughFans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEnoughFans.getCheckedRadioButtonId();
                if (selctedItem == R.id.enough_fans_yes) {

                    enough_fans = "YES";
                    binding.llCeilingFans.setVisibility(View.GONE);
                    binding.llWallMountedFans.setVisibility(View.GONE);
                    binding.llTubeLights.setVisibility(View.GONE);

                    binding.etCeilingFansWorking.setText("");
                    binding.etCeilingFansNonWorking.setText("");
                    binding.etCeilingFansRequired.setText("");

                    binding.etWallMountedFansWorking.setText("");
                    binding.etWallMountedFansNonWorking.setText("");
                    binding.etWallMountedFansRequired.setText("");

                } else if (selctedItem == R.id.enough_fans_no) {
                    enough_fans = "NO";

                    binding.llCeilingFans.setVisibility(View.VISIBLE);
                    binding.llWallMountedFans.setVisibility(View.VISIBLE);
                    binding.llTubeLights.setVisibility(View.VISIBLE);
                } else
                    enough_fans = null;
            }
        });

        binding.rgDiningHall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDiningHall.getCheckedRadioButtonId();
                if (selctedItem == R.id.dining_hall_yes) {
                    dining_hall = "YES";
                    binding.llDininghallUsed.setVisibility(View.VISIBLE);
                    binding.llDininghallAddReq.setVisibility(View.VISIBLE);
                    binding.llDininghallAvailConstruction.setVisibility(View.GONE);
                } else if (selctedItem == R.id.dining_hall_no) {
                    dining_hall = "NO";
                    binding.rgDininghallUsed.clearCheck();
                    binding.llDininghallUsed.setVisibility(View.GONE);
                    binding.llDininghallAddReq.setVisibility(View.GONE);
                    binding.llDininghallAvailConstruction.setVisibility(View.VISIBLE);
                } else {
                    dining_hall = null;
                    binding.rgDininghallUsed.clearCheck();
                    binding.llDininghallUsed.setVisibility(View.GONE);
                    binding.llDininghallAddReq.setVisibility(View.GONE);
                    binding.llDininghallAvailConstruction.setVisibility(View.GONE);
                }
            }
        });

        binding.rgDininghallUsed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDininghallUsed.getCheckedRadioButtonId();
                if (selctedItem == R.id.dininghall_used_yes)
                    dining_hall_used = "YES";
                else if (selctedItem == R.id.dininghall_used_no)
                    dining_hall_used = "NO";
                else
                    dining_hall_used = null;
            }
        });

        binding.rgDininghallAddReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDininghallAddReq.getCheckedRadioButtonId();
                if (selctedItem == R.id.dininghall_add_req_yes) {
                    dining_hall_add_req = "YES";
                    binding.llAddDinCnt.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.dininghall_add_req_no) {
                    dining_hall_add_req = "NO";
                    binding.llAddDinCnt.setVisibility(View.GONE);
                } else {
                    dining_hall_add_req = null;
                    binding.llAddDinCnt.setVisibility(View.GONE);
                }
            }
        });

        binding.rgDininghallAvailConstruction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDininghallAvailConstruction.getCheckedRadioButtonId();
                if (selctedItem == R.id.dininghall_avail_construction_yes)
                    dining_hall_avail_construction = "YES";
                else if (selctedItem == R.id.dininghall_avail_construction_no)
                    dining_hall_avail_construction = "NO";
                else
                    dining_hall_avail_construction = null;
            }
        });

        binding.rgSeparateKitchenRoomAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSeparateKitchenRoomAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.separate_kitchen_room_available_yes) {
                    separate_kitchen_room_available = "YES";
                    binding.rgConstructKitchenRoom.clearCheck();
                    binding.llConstructKitchenroom.setVisibility(View.GONE);
                    binding.llGoodCondition.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.separate_kitchen_room_available_no) {
                    separate_kitchen_room_available = "NO";
                    binding.rgIsItInGoodCondition.clearCheck();
                    binding.llConstructKitchenroom.setVisibility(View.VISIBLE);
                    binding.llGoodCondition.setVisibility(View.GONE);
                } else {
                    separate_kitchen_room_available = null;
                    binding.rgConstructKitchenRoom.clearCheck();
                    binding.rgIsItInGoodCondition.clearCheck();

                    binding.llConstructKitchenroom.setVisibility(View.GONE);
                    binding.llGoodCondition.setVisibility(View.GONE);
                }

            }
        });

        binding.rgConstructKitchenRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgConstructKitchenRoom.getCheckedRadioButtonId();
                if (selctedItem == R.id.construct_kitchen_room_yes)
                    construct_kitchen_room = "YES";
                else if (selctedItem == R.id.construct_kitchen_room_no)
                    construct_kitchen_room = "NO";
                else
                    construct_kitchen_room = null;

            }
        });


        binding.rgIsItInGoodCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int selctedItem = binding.rgIsItInGoodCondition.getCheckedRadioButtonId();
                if (selctedItem == R.id.is_it_in_good_condition_yes) {
                    is_it_in_good_condition = "YES";
                    binding.llKitchenRepairRequired.setVisibility(View.GONE);
                    binding.etRepairRequired.setText("");
                } else if (selctedItem == R.id.is_it_in_good_condition_no) {
                    is_it_in_good_condition = "NO";
                    binding.llKitchenRepairRequired.setVisibility(View.VISIBLE);
                } else {
                    is_it_in_good_condition = null;
                    binding.llKitchenRepairRequired.setVisibility(View.GONE);
                    binding.etRepairRequired.setText("");
                }
            }
        });

        binding.rgTransformerAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTransformerAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.transformer_available_yes)
                    transformer_available = "YES";
                else if (selctedItem == R.id.transformer_available_no)
                    transformer_available = "NO";
                else
                    transformer_available = null;
            }
        });


        binding.rgPowerConnectionType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPowerConnectionType.getCheckedRadioButtonId();
                if (selctedItem == R.id.single_phase)
                    powerConnectionType = "SINGLE PHASE";
                else if (selctedItem == R.id.three_phase)
                    powerConnectionType = "3 PHASE";
                else
                    powerConnectionType = null;
            }
        });

        binding.rgIndividualConnection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgIndividualConnection.getCheckedRadioButtonId();
                if (selctedItem == R.id.individual_connection_yes)
                    individual_connection = "YES";
                else if (selctedItem == R.id.individual_connection_no)
                    individual_connection = "NO";
                else
                    individual_connection = null;
            }
        });

        binding.rgRoadRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRoadRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.road_required_yes)
                    road_required = "YES";
                else if (selctedItem == R.id.road_required_no)
                    road_required = "NO";
                else
                    road_required = null;
            }
        });

        binding.rgCompWallRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCompWallRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.compWall_required_yes) {
                    compWall_required = "YES";
                    binding.llCompoundwall.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.compWall_required_no) {
                    compWall_required = "NO";
                    binding.llCompoundwall.setVisibility(View.GONE);
                } else {
                    compWall_required = null;
                    binding.llCompoundwall.setVisibility(View.GONE);
                }
            }
        });

        binding.rgConstructionPartType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgConstructionPartType.getCheckedRadioButtonId();
                if (selctedItem == R.id.construction_part_type_yes) {
                    construction_part_type = getResources().getString(R.string.new_construction);
                } else if (selctedItem == R.id.construction_part_type_no) {
                    construction_part_type = getResources().getString(R.string.part_construction);
                } else {
                    construction_part_type = null;
                }
            }
        });

        binding.rgCcCameras.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCcCameras.getCheckedRadioButtonId();
                if (selctedItem == R.id.cc_cameras_yes)
                    cc_cameras = "YES";
                else if (selctedItem == R.id.cc_cameras_no)
                    cc_cameras = "NO";
                else
                    cc_cameras = null;
            }
        });

        binding.rgSteamCooking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSteamCooking.getCheckedRadioButtonId();
                if (selctedItem == R.id.steam_cooking_yes)
                    steam_cooking = "YES";
                else if (selctedItem == R.id.steam_cooking_no)
                    steam_cooking = "NO";
                else
                    steam_cooking = null;
            }
        });

        binding.rgBunkerBeds.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBunkerBeds.getCheckedRadioButtonId();
                if (selctedItem == R.id.bunker_beds_yes) {
                    bunker_beds = "YES";
                    binding.llBunkerBedsCnt.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.bunker_beds_no) {
                    bunker_beds = "NO";
                    binding.llBunkerBedsCnt.setVisibility(View.GONE);
                } else {
                    bunker_beds = null;
                    binding.llBunkerBedsCnt.setVisibility(View.GONE);
                }
            }
        });

        binding.rgGateRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgGateRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.gate_required_yes)
                    gate_required = "YES";
                else if (selctedItem == R.id.gate_required_no)
                    gate_required = "NO";
                else
                    gate_required = null;
            }
        });

        binding.rgPathwayRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPathwayRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.pathway_required_yes)
                    pathway_required = "YES";
                else if (selctedItem == R.id.pathway_required_no)
                    pathway_required = "NO";
                else
                    pathway_required = null;
            }
        });

        binding.rgSumpRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSumpRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.sump_required_yes)
                    sump_required = "YES";
                else if (selctedItem == R.id.sump_required_no)
                    sump_required = "NO";
                else
                    sump_required = null;
            }
        });

        binding.rgSewageAllowed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSewageAllowed.getCheckedRadioButtonId();
                if (selctedItem == R.id.sewage_allowed_yes) {
                    sewage_allowed = "YES";
                    binding.llSewageRaiseReq.setVisibility(View.GONE);
                    binding.rgSewageRaiseReq.clearCheck();
                } else if (selctedItem == R.id.sewage_allowed_no) {
                    sewage_allowed = "NO";
                    binding.llSewageRaiseReq.setVisibility(View.VISIBLE);
                } else
                    sewage_allowed = null;
            }
        });
        binding.rgSewageRaiseReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSewageRaiseReq.getCheckedRadioButtonId();
                if (selctedItem == R.id.sewage_raise_req_yes) {
                    sewage_raise_req = "YES";
                } else if (selctedItem == R.id.sewage_raise_req_no) {
                    sewage_raise_req = "NO";
                } else
                    sewage_raise_req = null;
            }
        });

        binding.rgDrainageFunctioning.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrainageFunctioning.getCheckedRadioButtonId();
                if (selctedItem == R.id.drainage_functioning_yes)
                    drainage_functioning = "YES";
                else if (selctedItem == R.id.drainage_functioning_no)
                    drainage_functioning = "NO";
                else
                    drainage_functioning = null;
            }
        });

        binding.rgHeaterAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHeaterAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.heater_available_yes) {
                    heater_available = "YES";
                    binding.llSolarWorkingStatus.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.heater_available_no) {
                    heater_available = "NO";
                    binding.llSolarWorkingStatus.setVisibility(View.GONE);
                    binding.rgHeaterWorkingStatus.clearCheck();
                } else {
                    heater_available = null;
                    binding.llSolarWorkingStatus.setVisibility(View.GONE);
                    binding.rgHeaterWorkingStatus.clearCheck();
                }
            }
        });

        binding.rgHeaterWorkingStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHeaterWorkingStatus.getCheckedRadioButtonId();
                if (selctedItem == R.id.heater_workingStatus_yes)
                    heater_workingStatus = "YES";
                else if (selctedItem == R.id.heater_workingStatus_no)
                    heater_workingStatus = "NO";
                else
                    heater_workingStatus = null;
            }
        });

        binding.rgRepairsToDoor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRepairsToDoor.getCheckedRadioButtonId();
                if (selctedItem == R.id.repairs_to_door_yes)
                    repairs_to_door = "YES";
                else if (selctedItem == R.id.repairs_to_door_no)
                    repairs_to_door = "NO";
                else
                    repairs_to_door = null;
            }
        });

        binding.rgPainting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPainting.getCheckedRadioButtonId();
                if (selctedItem == R.id.painting_yes) {
                    painting = "GOOD";
                } else if (selctedItem == R.id.painting_no) {
                    painting = "BAD";
                } else {
                    painting = null;
                }
            }
        });

        binding.rgAddReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAddReq.getCheckedRadioButtonId();
                if (selctedItem == R.id.add_req_yes) {
                    add_req = "YES";
                    binding.llAddReq.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.add_req_no) {
                    add_req = "NO";
                    binding.llAddReq.setVisibility(View.GONE);
                    binding.addClassCnt.setText("");
                    binding.addDomCnt.setText("");
                    binding.addToiletsCnt.setText("");
                    binding.addBathroomsCnt.setText("");
                } else {
                    add_req = null;
                }
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                roplant_reason = binding.etReason.getText().toString().trim();
                ceilingFansWorking = binding.etCeilingFansWorking.getText().toString().trim();
                ceilingFansNonWorking = binding.etCeilingFansNonWorking.getText().toString().trim();
                ceilingFansReq = binding.etCeilingFansRequired.getText().toString().trim();
                mountedFansWorking = binding.etWallMountedFansWorking.getText().toString().trim();
                mountedFansNonWorking = binding.etWallMountedFansNonWorking.getText().toString().trim();
                mountedFansReq = binding.etWallMountedFansRequired.getText().toString().trim();

                lightsWorking = binding.etLightWorking.getText().toString().trim();
                lightsNonWorking = binding.etLightNonWorking.getText().toString().trim();
                lightsReq = binding.etLightRequired.getText().toString().trim();

                repair_required = binding.etRepairRequired.getText().toString().trim();
                how_many_buildings = binding.etHowManyBuildings.getText().toString().trim();
                totalToilets = binding.etTotalToilets.getText().toString().trim();
                totalBathrooms = binding.etTotalBathrooms.getText().toString().trim();
                functioningToilets = binding.etFunctioningToilets.getText().toString().trim();
                functioningBathrooms = binding.etFuntioningBathrooms.getText().toString().trim();
                repairsReqToilets = binding.etRequiredToilets.getText().toString().trim();
                repairsReqBathrooms = binding.etRequiredBathrooms.getText().toString().trim();
                add_cls_cnt = binding.addClassCnt.getText().toString().trim();
                add_din_cnt = binding.addDinCnt.getText().toString().trim();
                add_dom_cnt = binding.addDomCnt.getText().toString().trim();
                add_toilets_cnt = binding.addToiletsCnt.getText().toString().trim();
                add_bathrooms_cnt = binding.addBathroomsCnt.getText().toString().trim();
                compWall_cnt = binding.etCompoundwallCnt.getText().toString().trim();
                bunker_beds_cnt = binding.etBunkerBedsCnt.getText().toString().trim();
                if(drinkingWaterSources.size()>0) {
                    sourceOfDrinkingWater = new Gson().toJson(drinkingWaterSources);
                }else {
                    sourceOfDrinkingWater=null;
                }

                if (validateData()) {
                    infraStructureEntity = new InfraStructureEntity();
                    infraStructureEntity.setOfficer_id(officerID);
                    infraStructureEntity.setInspection_time(Utils.getCurrentDateTime());
                    infraStructureEntity.setInstitute_id(instID);
                    infraStructureEntity.setDrinking_water_facility(drinkingWaterFacility);
                    infraStructureEntity.setBigSchoolNameBoard(bigSchoolNameBoard);
                    infraStructureEntity.setRo_plant_woking(roPlant);
                    infraStructureEntity.setRo_plant_reason(roplant_reason);
                    infraStructureEntity.setDrinking_water_source(sourceOfDrinkingWater);
                    infraStructureEntity.setRunning_water_facility(runningWaterFacility);
                    infraStructureEntity.setRunningWater_source(sourceOfRunningWater);
                    infraStructureEntity.setInverter_available(inverter_available);
                    infraStructureEntity.setInverterWorkingStatus(inverterWorkingStatus);
                    infraStructureEntity.setElectricity_wiring(electricity_wiring);
                    infraStructureEntity.setEnough_fans(enough_fans);
                    infraStructureEntity.setCeilingfans_working(ceilingFansWorking);
                    infraStructureEntity.setCeilingfans_nonworking(ceilingFansNonWorking);
                    infraStructureEntity.setCeilingfans_required(ceilingFansReq);
                    infraStructureEntity.setMountedfans_working(mountedFansWorking);
                    infraStructureEntity.setMountedfans_nonworking(mountedFansNonWorking);
                    infraStructureEntity.setMountedfans_required(mountedFansReq);

                    infraStructureEntity.setLights_working(lightsWorking);
                    infraStructureEntity.setLights_nonworking(lightsNonWorking);
                    infraStructureEntity.setLights_required(lightsReq);


                    infraStructureEntity.setDininghall_available(dining_hall);
                    infraStructureEntity.setDininghall_used(dining_hall_used);
                    infraStructureEntity.setDininghall_add_req(dining_hall_add_req);
                    infraStructureEntity.setDininghall_avail_construction(dining_hall_avail_construction);
                    infraStructureEntity.setSeparate_kitchen_room_available(separate_kitchen_room_available);
                    infraStructureEntity.setConstruct_kitchen_room(construct_kitchen_room);
                    infraStructureEntity.setIs_it_in_good_condition(is_it_in_good_condition);
                    infraStructureEntity.setKitchen_repair_required(repair_required);
                    infraStructureEntity.setHow_many_buildings(how_many_buildings);
                    infraStructureEntity.setTransformer_available(transformer_available);
                    infraStructureEntity.setPowerConnection_type(powerConnectionType);
                    infraStructureEntity.setIndividual_connection(individual_connection);
                    infraStructureEntity.setRunningWater_source(sourceOfRunningWater);
                    infraStructureEntity.setRoad_required(road_required);
                    infraStructureEntity.setCompWall_required(compWall_required);
                    infraStructureEntity.setConstruction_part_type(construction_part_type);
                    infraStructureEntity.setCompWall_cnt(compWall_cnt);
                    infraStructureEntity.setGate_required(gate_required);
                    infraStructureEntity.setPathway_required(pathway_required);
                    infraStructureEntity.setSump_required(sump_required);
                    infraStructureEntity.setSewage_allowed(sewage_allowed);
                    infraStructureEntity.setDrainage_functioning(drainage_functioning);
                    infraStructureEntity.setHeater_available(heater_available);
                    infraStructureEntity.setHeater_workingStatus(heater_workingStatus);
                    infraStructureEntity.setTotal_toilets(totalToilets);
                    infraStructureEntity.setTotal_bathrooms(totalBathrooms);
                    infraStructureEntity.setFunctioning_toilets(functioningToilets);
                    infraStructureEntity.setFunctioning_bathrooms(functioningBathrooms);
                    infraStructureEntity.setRepairs_req_toilets(repairsReqToilets);
                    infraStructureEntity.setRepairs_req_bathrooms(repairsReqBathrooms);
                    infraStructureEntity.setDoor_window_repairs(repairs_to_door);
                    infraStructureEntity.setPainting(painting);
                    infraStructureEntity.setCc_cameras(cc_cameras);
                    infraStructureEntity.setSteam_cooking(steam_cooking);
                    infraStructureEntity.setBunker_beds(bunker_beds);
                    infraStructureEntity.setBunker_beds_cnt(bunker_beds_cnt);
                    infraStructureEntity.setAdd_req(add_req);
                    infraStructureEntity.setAdd_class_required_cnt(add_cls_cnt);
                    infraStructureEntity.setAdd_dining_required_cnt(add_din_cnt);
                    infraStructureEntity.setAdd_dormitory_required_cnt(add_dom_cnt);
                    infraStructureEntity.setAdd_toilets_required_cnt(add_toilets_cnt);
                    infraStructureEntity.setAdd_bathrooms_required_cnt(add_bathrooms_cnt);
                    infraStructureEntity.setSewage_raise_request(sewage_raise_req);

                    Utils.customSaveAlert(InfraActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                }
            }
        });

        try {
            int localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<InfraStructureEntity> dietInfoData = instMainViewModel.getInfrastructureInfoData(instID);
                dietInfoData.observe(InfraActivity.this, new Observer<InfraStructureEntity>() {
                    @Override
                    public void onChanged(InfraStructureEntity infraStructureEntity) {
                        dietInfoData.removeObservers(InfraActivity.this);
                        if (infraStructureEntity != null) {
                            binding.setInspData(infraStructureEntity);
                            binding.executePendingBindings();

                            Type type = new TypeToken<Set<String>>() {}.getType();
                            drinkingWaterSources = new Gson().fromJson(infraStructureEntity.getDrinking_water_source(), type);

                            if(drinkingWaterSources!=null && drinkingWaterSources.size()>0){
                                for(int z=0;z<drinkingWaterSources.size();z++){
                                    if(drinkingWaterSources.contains(getString(R.string.ro_plant))){
                                        binding.drinkingWaterRoPlant.setChecked(true);
                                    }

                                    if(drinkingWaterSources.contains(getString(R.string.open_well))){
                                        binding.drinkingWaterSourceOpenWell.setChecked(true);
                                    }

                                    if(drinkingWaterSources.contains(getString(R.string.bore_well))){
                                        binding.drinkingWaterSourceBoreWell.setChecked(true);
                                    }

                                    if(drinkingWaterSources.contains(getString(R.string.municipal))){
                                        binding.drinkingWaterSourceMunicipal.setChecked(true);
                                    }

                                    if(drinkingWaterSources.contains(getString(R.string.hand_pump))){
                                        binding.drinkingWaterSourceHandPump.setChecked(true);
                                    }
                                }

                            }

                            LiveData<UploadPhoto> uploadPhotoLiveData = viewModel.getPhotoData(AppConstants.RO_PLANT, instID);
                            uploadPhotoLiveData.observe(InfraActivity.this, new Observer<UploadPhoto>() {
                                @Override
                                public void onChanged(UploadPhoto uploadPhoto) {
                                    uploadPhotoLiveData.removeObservers(InfraActivity.this);
                                    if (uploadPhoto != null && !TextUtils.isEmpty(uploadPhoto.getPhoto_path())) {
                                        file_ro_plant = new File(uploadPhoto.getPhoto_path());
                                        Glide.with(InfraActivity.this).load(file_ro_plant).into(binding.ivTds);
                                        flag_ro_plant = 1;
                                    } else {
                                        Glide.with(InfraActivity.this).load(R.drawable.ic_menu_camera).into(binding.ivTds);
                                        flag_ro_plant = 0;
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                flag_ro_plant = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(InfraActivity.this,
                    Manifest.permission.CAMERA)) {
                customPerAlert();
            } else {
                callSettings();
            }
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
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
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

    private void callSettings() {
        Snackbar snackbar = Snackbar.make(binding.cl, getString(R.string.all_cam_per_setting), Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("Settings", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
                Utils.openSettings(InfraActivity.this);
            }
        });

        snackbar.show();
    }

    private boolean validateData() {

        if (TextUtils.isEmpty(drinkingWaterFacility)) {
            ScrollToView(binding.rgDrinkingWaterFacility);
            showSnackBar(getResources().getString(R.string.select_water));
            return false;
        }

        if (drinkingWaterFacility.equals(AppConstants.Yes) && TextUtils.isEmpty(sourceOfDrinkingWater)) {
            ScrollToView(binding.llDrinkingWater);
            showSnackBar(getResources().getString(R.string.select_src_drinking_water));
            return false;
        }

        if (TextUtils.isEmpty(roPlant)) {
            ScrollToView(binding.rgRoPlant);
            showSnackBar(getResources().getString(R.string.select_roPlant));
            return false;
        }
        if (roPlant.equals(AppConstants.Yes) && flag_ro_plant == 0) {
            ScrollToView(binding.ivTds);
            showSnackBar(getString(R.string.capture_tds_image));
            return false;
        }
        if (roPlant.equals(AppConstants.No) && TextUtils.isEmpty(roplant_reason)) {
            ScrollToView(binding.llReason);
            showSnackBar(getResources().getString(R.string.select_roPlant_reason));
            binding.etReason.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(runningWaterFacility)) {
            ScrollToView(binding.rgRunningWaterFacility);
            showSnackBar(getResources().getString(R.string.select_running_water));
            return false;
        }

        if (runningWaterFacility.equals(AppConstants.Yes) && TextUtils.isEmpty(sourceOfRunningWater)) {
            ScrollToView(binding.rgRunningWaterSource);
            showSnackBar(getResources().getString(R.string.select_src_running_water));
            return false;
        }

        if (TextUtils.isEmpty(inverter_available)) {
            ScrollToView(binding.rgIsInverterAvailable);
            showSnackBar(getResources().getString(R.string.select_inverter));
            return false;
        }
        if (inverter_available.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(inverterWorkingStatus)) {
            ScrollToView(binding.rgInverterWorkingStatus);
            showSnackBar(getResources().getString(R.string.select_inverter_working));
            return false;
        }

        if (TextUtils.isEmpty(electricity_wiring)) {
            ScrollToView(binding.rgElectricityWiring);
            showSnackBar(getResources().getString(R.string.select_electricity));
            return false;
        }
        if (TextUtils.isEmpty(enough_fans)) {
            ScrollToView(binding.rgEnoughFans);
            showSnackBar(getResources().getString(R.string.select_fans));
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(ceilingFansWorking)) {
            showSnackBar(getResources().getString(R.string.select_ceilingFansWorking));
            binding.etCeilingFansWorking.requestFocus();
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(ceilingFansNonWorking)) {
            showSnackBar(getResources().getString(R.string.select_ceilingFansNonWorking));
            binding.etCeilingFansNonWorking.requestFocus();
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(ceilingFansReq)) {
            showSnackBar(getResources().getString(R.string.select_ceilingFansReq));
            binding.etCeilingFansRequired.requestFocus();
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(mountedFansWorking)) {
            showSnackBar(getResources().getString(R.string.select_mountedFansWorking));
            binding.etWallMountedFansWorking.requestFocus();
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(mountedFansNonWorking)) {
            showSnackBar(getResources().getString(R.string.select_mountedFansNonWorking));
            binding.etWallMountedFansNonWorking.requestFocus();
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(mountedFansReq)) {
            showSnackBar(getResources().getString(R.string.select_mountedFansReq));
            binding.etWallMountedFansRequired.requestFocus();
            return false;
        }

        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(lightsWorking)) {
            showSnackBar(getResources().getString(R.string.select_tube_lights_working));
            binding.etLightWorking.requestFocus();
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(lightsNonWorking)) {
            showSnackBar(getResources().getString(R.string.select_tube_lights_non_working));
            binding.etLightNonWorking.requestFocus();
            return false;
        }
        if (enough_fans.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(lightsReq)) {
            showSnackBar(getResources().getString(R.string.select_tube_lights_req));
            binding.etLightRequired.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(dining_hall)) {
            ScrollToView(binding.rgDiningHall);
            showSnackBar(getResources().getString(R.string.select_dininghall_available));
            return false;
        }
        if (dining_hall.equals(AppConstants.Yes) && TextUtils.isEmpty(dining_hall_used)) {
            ScrollToView(binding.rgDininghallUsed);
            showSnackBar(getResources().getString(R.string.select_dininghall_used));
            return false;
        }
        if (dining_hall.equals(AppConstants.Yes) && TextUtils.isEmpty(dining_hall_add_req)) {
            ScrollToView(binding.rgDininghallAddReq);
            showSnackBar(getResources().getString(R.string.select_dininghall_add_req));
            return false;
        }
        if (dining_hall.equals(AppConstants.Yes) && dining_hall_add_req.equals(AppConstants.Yes) && TextUtils.isEmpty(add_din_cnt)) {
            binding.addDinLayout.requestFocus();
            showSnackBar(getResources().getString(R.string.sel_din_count));
            return false;
        }
        if (dining_hall.equals(AppConstants.No) && TextUtils.isEmpty(dining_hall_avail_construction)) {
            ScrollToView(binding.rgDininghallAvailConstruction);
            showSnackBar(getResources().getString(R.string.select_dininghall_avail_construction));
            return false;
        }
        if (TextUtils.isEmpty(separate_kitchen_room_available)) {
            ScrollToView(binding.rgSeparateKitchenRoomAvailable);
            showSnackBar(getResources().getString(R.string.select_kitchen_available));
            return false;
        }
        if (separate_kitchen_room_available.equals(AppConstants.No) && TextUtils.isEmpty(construct_kitchen_room)) {
            ScrollToView(binding.rgConstructKitchenRoom);
            showSnackBar(getResources().getString(R.string.select_kitchen_construct));
            return false;
        }
        if (separate_kitchen_room_available.equals(AppConstants.Yes) && TextUtils.isEmpty(is_it_in_good_condition)) {
            ScrollToView(binding.rgIsItInGoodCondition);
            showSnackBar(getResources().getString(R.string.select_kitchen_good_condition));
            return false;
        }
        if (separate_kitchen_room_available.equals(AppConstants.Yes) && is_it_in_good_condition.equals(AppConstants.No) && TextUtils.isEmpty(repair_required)) {
            showSnackBar(getResources().getString(R.string.select_kitchen_repairs));
            binding.etRepairRequired.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(how_many_buildings)) {
            showSnackBar(getResources().getString(R.string.select_buildings));
            binding.etHowManyBuildings.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(transformer_available)) {
            showSnackBar(getResources().getString(R.string.select_transformer));
            ScrollToView(binding.rgTransformerAvailable);
            return false;
        }
        if (TextUtils.isEmpty(powerConnectionType)) {
            showSnackBar(getResources().getString(R.string.select_powerconnection));
            ScrollToView(binding.rgPowerConnectionType);
            return false;
        }
        if (TextUtils.isEmpty(individual_connection)) {
            showSnackBar(getResources().getString(R.string.select_individual_connection));
            ScrollToView(binding.rgIndividualConnection);
            return false;
        }
        if (TextUtils.isEmpty(road_required)) {
            showSnackBar(getResources().getString(R.string.select_road_req));
            ScrollToView(binding.rgRoadRequired);
            return false;
        }
        if (TextUtils.isEmpty(compWall_required)) {
            showSnackBar(getResources().getString(R.string.select_compoundwall_req));
            ScrollToView(binding.rgCompWallRequired);
            return false;
        }
        if (compWall_required.equals(AppConstants.Yes) && TextUtils.isEmpty(construction_part_type)) {
            showSnackBar(getResources().getString(R.string.sel_construction_part_type));
            ScrollToView(binding.rgConstructionPartType);
            return false;
        }
        if (compWall_required.equals(AppConstants.Yes) && TextUtils.isEmpty(compWall_cnt)) {
            showSnackBar(getResources().getString(R.string.sel_compwall_count));
            binding.etCompoundwallCnt.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(cc_cameras)) {
            showSnackBar(getResources().getString(R.string.select_cc_cameras));
            ScrollToView(binding.rgCcCameras);
            return false;
        }
        if (TextUtils.isEmpty(steam_cooking)) {
            showSnackBar(getResources().getString(R.string.select_steam_cooking));
            ScrollToView(binding.rgSteamCooking);
            return false;
        }
        if (TextUtils.isEmpty(bunker_beds)) {
            showSnackBar(getResources().getString(R.string.select_bunker_beds));
            ScrollToView(binding.rgBunkerBeds);
            return false;
        }

        if (bunker_beds.equals(AppConstants.Yes) && TextUtils.isEmpty(bunker_beds_cnt)) {
            showSnackBar(getResources().getString(R.string.select_bunker_beds));
            ScrollToView(binding.rgBunkerBeds);
            return false;
        }

        if (TextUtils.isEmpty(gate_required)) {
            ScrollToView(binding.rgGateRequired);
            showSnackBar(getResources().getString(R.string.select_gate));
            return false;
        }
//        if (TextUtils.isEmpty(pathway_required)) {
//            ScrollToView(binding.rgPathwayRequired);
//            showSnackBar(getResources().getString(R.string.select_pathway));
//            return false;
//        }
        if (TextUtils.isEmpty(sump_required)) {
            ScrollToView(binding.rgSumpRequired);
            showSnackBar(getResources().getString(R.string.select_sump_req));
            return false;
        }
        if (TextUtils.isEmpty(sewage_allowed)) {
            ScrollToView(binding.rgSewageAllowed);
            showSnackBar(getResources().getString(R.string.select_sewerage));
            return false;
        }
        if (sewage_allowed.equals(AppConstants.No) && TextUtils.isEmpty(sewage_raise_req)) {
            ScrollToView(binding.rgSewageRaiseReq);
            showSnackBar(getResources().getString(R.string.select_raise_req));
            return false;
        }
        if (TextUtils.isEmpty(drainage_functioning)) {
            ScrollToView(binding.rgDrainageFunctioning);
            showSnackBar(getResources().getString(R.string.select_drainage));
            return false;
        }
        if (TextUtils.isEmpty(heater_available)) {
            ScrollToView(binding.rgHeaterAvailable);
            showSnackBar(getResources().getString(R.string.select_solarwater));
            return false;
        }
        if (heater_available.equals(AppConstants.Yes) && TextUtils.isEmpty(heater_workingStatus)) {
            ScrollToView(binding.rgHeaterWorkingStatus);
            showSnackBar(getResources().getString(R.string.select_solarwater_working));
            return false;
        }

        if (TextUtils.isEmpty(add_req)) {
            ScrollToView(binding.rgAddReq);
            showSnackBar(getResources().getString(R.string.sel_add_req));
            return false;
        }
        if (add_req.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(add_cls_cnt)) {
            showSnackBar(getResources().getString(R.string.cls_count));
            binding.addClassLayout.requestFocus();
            return false;
        }

        if (add_req.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(add_dom_cnt)) {
            showSnackBar(getResources().getString(R.string.dor_count));
            binding.addDomLayout.requestFocus();
            return false;
        }
        if (add_req.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(add_toilets_cnt)) {
            showSnackBar(getResources().getString(R.string.toilets_count));
            binding.addToiletsLayout.requestFocus();
            return false;
        }
        if (add_req.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(add_bathrooms_cnt)) {
            showSnackBar(getResources().getString(R.string.bathrooms_count));
            binding.addBathroomsLayout.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repairs_to_door)) {
            showSnackBar(getResources().getString(R.string.select_door));
            ScrollToView(binding.rgRepairsToDoor);
            return false;
        }
        if (TextUtils.isEmpty(painting)) {
            ScrollToView(binding.rgPainting);
            showSnackBar(getResources().getString(R.string.select_painting));
            return false;
        }
        if (TextUtils.isEmpty(totalToilets)) {
            showSnackBar(getResources().getString(R.string.select_toilets));
            binding.etTotalToilets.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(totalBathrooms)) {
            showSnackBar(getResources().getString(R.string.select_bathrooms));
            binding.etTotalBathrooms.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(functioningToilets)) {
            showSnackBar(getResources().getString(R.string.select_functioning_toilets));
            binding.etFunctioningToilets.requestFocus();
            return false;
        }
        if (Long.parseLong(totalToilets) < Long.parseLong(functioningToilets)) {
            showSnackBar(getResources().getString(R.string.select_valid_functioning_toilets));
            binding.etFunctioningToilets.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(functioningBathrooms)) {
            showSnackBar(getResources().getString(R.string.select_functioning_bathrooms));
            binding.etFuntioningBathrooms.requestFocus();
            return false;
        }
        if (Long.parseLong(totalBathrooms) < Long.parseLong(functioningBathrooms)) {
            showSnackBar(getResources().getString(R.string.select_valid_functioning_bathrooms));
            binding.etFuntioningBathrooms.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repairsReqToilets)) {
            showSnackBar(getResources().getString(R.string.select_repaired_toilets));
            binding.etRequiredToilets.requestFocus();
            return false;
        }
        if (Long.parseLong(totalToilets) < Long.parseLong(repairsReqToilets)) {
            showSnackBar(getResources().getString(R.string.select_valid_repaired_toilets));
            binding.etRequiredToilets.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repairsReqBathrooms)) {
            showSnackBar(getResources().getString(R.string.select_repaired_bathrooms));
            binding.etRequiredBathrooms.requestFocus();
            return false;
        }
        if (Long.parseLong(totalBathrooms) < Long.parseLong(repairsReqBathrooms)) {
            showSnackBar(getResources().getString(R.string.select_valid_repaired_bathrooms));
            binding.etRequiredBathrooms.requestFocus();
            return false;
        }
        return true;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    private void addPhoto(String instID, String currentDateTime, String valueOfImage) {
        uploadPhotos = new ArrayList<>();
        UploadPhoto uploadPhoto = new UploadPhoto();
        uploadPhoto.setInstitute_id(instID);
        uploadPhoto.setSection_id("12");
        uploadPhoto.setTimeStamp(currentDateTime);
        uploadPhoto.setPhoto_name(AppConstants.RO_PLANT);
        uploadPhoto.setPhoto_path(valueOfImage);
        uploadPhotos.add(uploadPhoto);
    }

    UploadPhotoViewModel viewModel;
    private List<UploadPhoto> uploadPhotos;

    @Override
    public void submitData() {
        if (roPlant.equalsIgnoreCase("yes")) {
            addPhoto(instID, Utils.getCurrentDateTime(), String.valueOf(file_ro_plant));
        } else {
            file_ro_plant = null;
            addPhoto(instID, Utils.getCurrentDateTime(), String.valueOf(file_ro_plant));
        }
        long y = viewModel.insertPhotos(uploadPhotos);
        if (y >= 0) {
            insertSectionData();
        }
    }

    private void insertSectionData() {
        long x = infraViewModel.insertInfraStructureInfo(infraStructureEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Infra");
                liveData.observe(InfraActivity.this, new Observer<Integer>() {
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
                InstSelectionViewModel instSelectionViewModel = new InstSelectionViewModel(getApplication());
                instSelectionViewModel.updateTimeInfo(Utils.getOfflineTime(), instID);
                Utils.customSectionSaveAlert(InfraActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        File imageFile = getOutputMediaFile(type);
        Uri imageUri = null;
        if (imageFile != null) {
            imageUri = FileProvider.getUriForFile(
                    InfraActivity.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    imageFile);
        }
        return imageUri;
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(getExternalFilesDir(null) + "/" + IMAGE_DIRECTORY_NAME
                + "/" + instID);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("TAG", "Oops! Failed create " + "Android File Upload"
                        + " directory");
                return null;
            }
        }
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            String deviceId = Utils.getDeviceID(InfraActivity.this);
            String versionName = Utils.getVersionName(InfraActivity.this);
            PIC_NAME = PIC_TYPE + "~" + officerID + "~" + instID + "~" + Utils.getCurrentDateTimeFormat() + "~" + deviceId + "~" + versionName + "~" + randomNo + ".png";

            mediaFile = new File(mediaStorageDir.getPath() + File.separator + PIC_TYPE + ".png");
        } else {
            return null;
        }

        return mediaFile;
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

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        FilePath = getExternalFilesDir(null)
                + "/" + IMAGE_DIRECTORY_NAME + "/" + instID;

        String Image_name = PIC_NAME;
        FilePath = FilePath + "/" + Image_name;
        return FilePath;
    }

    private String getRealPathFromURI(String contentURI) {
        Cursor cursor = null;
        int index = 0;
        try {
            Uri contentUri = Uri.parse(contentURI);
            cursor = getContentResolver().query(contentUri, null, null, null, null);
            if (cursor == null) {
                return contentUri.getPath();
            } else {
                cursor.moveToFirst();
                index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(index);
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
                        + "/" + IMAGE_DIRECTORY_NAME + "/" + instID;

                String Image_name = PIC_TYPE + ".png";
                FilePath = FilePath + "/" + Image_name;

                FilePath = compressImage(FilePath);

                flag_ro_plant = 1;
                binding.ivTds.setPadding(0, 0, 0, 0);
                binding.ivTds.setBackgroundColor(getResources().getColor(R.color.white));
                file_ro_plant = new File(FilePath);
                Glide.with(InfraActivity.this).load(file_ro_plant).into(binding.ivTds);

            } else if (resultCode == RESULT_CANCELED) {
                binding.ivTds.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
                Toast.makeText(getApplicationContext(),
                        getString(R.string.user_cancelled), Toast.LENGTH_SHORT)
                        .show();
            } else {
                binding.ivTds.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
                Toast.makeText(getApplicationContext(),
                        getString(R.string.image_capture_failed), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        callBack();
    }

    public void callBack() {
        Utils.customHomeAlert(InfraActivity.this, getString(R.string.app_name), getString(R.string.go_back));
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
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
