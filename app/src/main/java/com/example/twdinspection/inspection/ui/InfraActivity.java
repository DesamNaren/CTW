package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityInfrastructureBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.viewmodel.InfraCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.InfraViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

public class InfraActivity extends LocBaseActivity implements SaveListener {
    ActivityInfrastructureBinding binding;
    InfraViewModel infraViewModel;
    SharedPreferences sharedPreferences;
    InfraStructureEntity infrastuctureEntity;
    String drinkingWaterFacility, bigSchoolNameBoard, roPlant, sourceOfDrinkingWater, sourceOfRunningWater, inverter_available, inverterWorkingStatus, lighting_facility, electricity_wiring, enough_fans, dining_hall, dining_hall_used;
    String separate_kitchen_room_available, construct_kitchen_room, is_it_in_good_condition, transformer_available, powerConnectionType, individual_connection, road_required, compWall_required, gate_required;
    String pathway_required, sump_required, sewage_allowed, drainage_functioning, heater_available, heater_workingStatus, repairs_to_door, painting, electricity_wiring_repairs_req;
    String roplant_reason, ceilingFansWorking, ceilingFansNonWorking, mountedFansWorking, mountedFansNonWorking, repair_required, how_many_buildings, totalToilets, totalBathrooms, functioningBathrooms, functioningToilets;
    String repairsReqToilets, repairsReqBathrooms, color;
    InstMainViewModel instMainViewModel;
    private String officerID, instID, insTime;
    private boolean add_class = false, add_din = false, add_dom = false;
    private String add_cls_cnt, add_din_cnt, add_dom_cnt;

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final String IMAGE_DIRECTORY_NAME = "SCHOOL_INSP_IMAGES";
    String PIC_NAME, PIC_TYPE;
    public Uri fileUri;
    String FilePath;
    File file_tds;
    int flag_tds = 0;
    SharedPreferences.Editor editor;
    private String cacheDate, currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_infrastructure);
        binding.header.headerTitle.setText(getResources().getString(R.string.infra_mai));

        infraViewModel = ViewModelProviders.of(InfraActivity.this,
                new InfraCustomViewModel(binding, this, getApplication())).get(InfraViewModel.class);
        binding.setViewModel(infraViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();

            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        binding.rgDrinkingWaterFacility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrinkingWaterFacility.getCheckedRadioButtonId();
                if (selctedItem == R.id.drinking_water_facility_yes)
                    drinkingWaterFacility = "GOOD";
                else if (selctedItem == R.id.drinking_water_facility_no)
                    drinkingWaterFacility = "BAD";
                else
                    drinkingWaterFacility = null;
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
        binding.rgRoPlant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRoPlant.getCheckedRadioButtonId();
                if (selctedItem == R.id.ro_plant_yes) {
                    roPlant = "YES";
                    binding.llReason.setVisibility(View.GONE);
                    binding.llTdsMeterReading.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.ro_plant_no) {
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

        binding.ivTds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callPermissions()) {

                    PIC_TYPE = AppConstants.TDS;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    if (fileUri != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }
            }
        });


        binding.rgSourceOfDrinkingWater.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSourceOfDrinkingWater.getCheckedRadioButtonId();
                if (selctedItem == R.id.drinking_water_source_open_well)
                    sourceOfDrinkingWater = getResources().getString(R.string.open_well);
                else if (selctedItem == R.id.drinking_water_source_bore_well)
                    sourceOfDrinkingWater = getResources().getString(R.string.bore_well);
                else if (selctedItem == R.id.drinking_water_source_municipal)
                    sourceOfDrinkingWater = getResources().getString(R.string.municipal);
                else if (selctedItem == R.id.drinking_water_source_hand_pump)
                    sourceOfDrinkingWater = getResources().getString(R.string.hand_pump);
                else
                    sourceOfDrinkingWater = null;
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
                } else {
                    inverter_available = null;
                    binding.llInverterWorkingStatus.setVisibility(View.GONE);
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

        binding.rgLightingFacility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLightingFacility.getCheckedRadioButtonId();
                if (selctedItem == R.id.lighting_facility_yes)
                    lighting_facility = "GOOD";
                else if (selctedItem == R.id.lighting_facility_no)
                    lighting_facility = "BAD";
                else
                    lighting_facility = null;
            }
        });
        binding.rgElectricityWiring.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgElectricityWiring.getCheckedRadioButtonId();
                if (selctedItem == R.id.electricity_wiring_yes) {
                    electricity_wiring = "YES";
                    binding.llElectricityWiringRepairsReq.setVisibility(View.GONE);
                } else if (selctedItem == R.id.electricity_wiring_no) {
                    electricity_wiring = "NO";
                    binding.llElectricityWiringRepairsReq.setVisibility(View.VISIBLE);
                } else {
                    electricity_wiring = null;
                    binding.llElectricityWiringRepairsReq.setVisibility(View.GONE);
                }
            }
        });
        binding.rgElectricityWiringRepairsReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgElectricityWiringRepairsReq.getCheckedRadioButtonId();
                if (selctedItem == R.id.electricity_wiring_repairs_req_yes)
                    electricity_wiring_repairs_req = "YES";
                else if (selctedItem == R.id.electricity_wiring_repairs_req_no)
                    electricity_wiring_repairs_req = "NO";
                else
                    electricity_wiring_repairs_req = null;
            }
        });
        binding.rgEnoughFans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEnoughFans.getCheckedRadioButtonId();
                if (selctedItem == R.id.enough_fans_yes)
                    enough_fans = "YES";
                else if (selctedItem == R.id.enough_fans_no)
                    enough_fans = "NO";
                else
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
                } else if (selctedItem == R.id.dining_hall_no) {
                    dining_hall = "NO";
                    binding.llDininghallUsed.setVisibility(View.GONE);
                } else {
                    dining_hall = null;
                    binding.llDininghallUsed.setVisibility(View.GONE);
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

        binding.rgSeparateKitchenRoomAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSeparateKitchenRoomAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.separate_kitchen_room_available_yes) {
                    separate_kitchen_room_available = "YES";
                    binding.llConstructKitchenroom.setVisibility(View.GONE);
                    binding.llGoodCondition.setVisibility(View.VISIBLE);
                } else if (selctedItem == R.id.separate_kitchen_room_available_no) {
                    separate_kitchen_room_available = "NO";
                    binding.llConstructKitchenroom.setVisibility(View.VISIBLE);
                    binding.llGoodCondition.setVisibility(View.GONE);
                } else {
                    separate_kitchen_room_available = null;
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
                } else if (selctedItem == R.id.is_it_in_good_condition_no) {
                    is_it_in_good_condition = "NO";
                    binding.llKitchenRepairRequired.setVisibility(View.VISIBLE);
                } else {
                    is_it_in_good_condition = null;
                    binding.llKitchenRepairRequired.setVisibility(View.GONE);
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
                if (selctedItem == R.id.compWall_required_yes)
                    compWall_required = "YES";
                else if (selctedItem == R.id.compWall_required_no)
                    compWall_required = "NO";
                else
                    compWall_required = null;
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
                if (selctedItem == R.id.sewage_allowed_yes)
                    sewage_allowed = "YES";
                else if (selctedItem == R.id.sewage_allowed_no)
                    sewage_allowed = "NO";
                else
                    sewage_allowed = null;
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
                } else {
                    heater_available = null;
                    binding.llSolarWorkingStatus.setVisibility(View.GONE);
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
                    binding.llColor.setVisibility(View.GONE);
                } else if (selctedItem == R.id.painting_no) {
                    painting = "BAD";
                    binding.llColor.setVisibility(View.VISIBLE);
                } else {
                    painting = null;
                    binding.llColor.setVisibility(View.GONE);
                }
            }
        });


        binding.addClassCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    add_class = true;
                    binding.addClassLayout.setVisibility(View.VISIBLE);
                } else {
                    add_class = false;
                    binding.addClassCnt.setText("");
                    binding.addClassLayout.setVisibility(View.GONE);
                }
            }
        });

        binding.addDinCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    add_din = true;
                    binding.addDinLayout.setVisibility(View.VISIBLE);
                } else {
                    add_din = false;
                    binding.addDinCnt.setText("");
                    binding.addDinLayout.setVisibility(View.GONE);
                }
            }
        });

        binding.addDomCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    add_dom = true;
                    binding.addDomLayout.setVisibility(View.VISIBLE);
                } else {
                    add_dom = false;
                    binding.addDomCnt.setText("");
                    binding.addDomLayout.setVisibility(View.GONE);
                }
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                roplant_reason = binding.etReason.getText().toString().trim();
                ceilingFansWorking = binding.etCeilingFansWorking.getText().toString().trim();
                ceilingFansNonWorking = binding.etCeilingFansNonWorking.getText().toString().trim();
                mountedFansWorking = binding.etWallMountedFansWorking.getText().toString().trim();
                mountedFansNonWorking = binding.etWallMountedFansNonWorking.getText().toString().trim();
                repair_required = binding.etRepairRequired.getText().toString().trim();
                how_many_buildings = binding.etHowManyBuildings.getText().toString().trim();
                totalToilets = binding.etTotalToilets.getText().toString().trim();
                totalBathrooms = binding.etTotalBathrooms.getText().toString().trim();
                functioningToilets = binding.etFunctioningToilets.getText().toString().trim();
                functioningBathrooms = binding.etFuntioningBathrooms.getText().toString().trim();
                repairsReqToilets = binding.etRequiredToilets.getText().toString().trim();
                repairsReqBathrooms = binding.etRequiredBathrooms.getText().toString().trim();
                color = binding.etPainting.getText().toString().trim();
                add_cls_cnt = binding.addClassCnt.getText().toString();
                add_din_cnt = binding.addDinCnt.getText().toString();
                add_dom_cnt = binding.addDomCnt.getText().toString();

                if (validateData()) {

                    if (roPlant.equalsIgnoreCase("yes")) {
                        editor.putString(AppConstants.TDS, String.valueOf(file_tds));
                        editor.commit();
                    }

                    infrastuctureEntity = new InfraStructureEntity();
                    infrastuctureEntity.setOfficer_id(officerID);
                    infrastuctureEntity.setInspection_time(Utils.getCurrentDateTime());
                    infrastuctureEntity.setInstitute_id(instID);
                    infrastuctureEntity.setDrinking_water_facility(drinkingWaterFacility);
                    infrastuctureEntity.setBigSchoolNameBoard(bigSchoolNameBoard);
                    infrastuctureEntity.setRo_plant_woking(roPlant);
                    infrastuctureEntity.setRo_plant_reason(roplant_reason);
                    infrastuctureEntity.setDrinking_water_source(sourceOfDrinkingWater);
                    infrastuctureEntity.setInverter_available(inverter_available);
                    infrastuctureEntity.setInverterWorkingStatus(inverterWorkingStatus);
                    infrastuctureEntity.setLighting_facility(lighting_facility);
                    infrastuctureEntity.setElectricity_wiring(electricity_wiring);
                    infrastuctureEntity.setElectricity_wiring_repairs_req(electricity_wiring_repairs_req);
                    infrastuctureEntity.setEnough_fans(enough_fans);
                    infrastuctureEntity.setCeilingfans_working(ceilingFansWorking);
                    infrastuctureEntity.setCeilingfans_nonworking(ceilingFansNonWorking);
                    infrastuctureEntity.setMountedfans_working(mountedFansWorking);
                    infrastuctureEntity.setMountedfans_nonworking(mountedFansNonWorking);
                    infrastuctureEntity.setDininghall_available(dining_hall);
                    infrastuctureEntity.setDininghall_used(dining_hall_used);
                    infrastuctureEntity.setSeparate_kitchen_room_available(separate_kitchen_room_available);
                    infrastuctureEntity.setConstruct_kitchen_room(construct_kitchen_room);
                    infrastuctureEntity.setIs_it_in_good_condition(is_it_in_good_condition);
                    infrastuctureEntity.setRepair_required(repair_required);
                    infrastuctureEntity.setHow_many_buildings(how_many_buildings);
                    infrastuctureEntity.setTransformer_available(transformer_available);
                    infrastuctureEntity.setPowerConnection_type(powerConnectionType);
                    infrastuctureEntity.setIndividual_connection(individual_connection);
                    infrastuctureEntity.setRunningWater_source(sourceOfRunningWater);
                    infrastuctureEntity.setRoad_required(road_required);
                    infrastuctureEntity.setCompWall_required(compWall_required);
                    infrastuctureEntity.setGate_required(gate_required);
                    infrastuctureEntity.setPathway_required(pathway_required);
                    infrastuctureEntity.setSump_required(sump_required);
                    infrastuctureEntity.setSewage_allowed(sewage_allowed);
                    infrastuctureEntity.setDrainage_functioning(drainage_functioning);
                    infrastuctureEntity.setHeater_available(heater_available);
                    infrastuctureEntity.setHeater_workingStatus(heater_workingStatus);
                    infrastuctureEntity.setTotal_toilets(totalToilets);
                    infrastuctureEntity.setTotal_bathrooms(totalBathrooms);
                    infrastuctureEntity.setFunctioning_toilets(functioningToilets);
                    infrastuctureEntity.setFunctioning_bathrooms(functioningBathrooms);
                    infrastuctureEntity.setRepairs_req_toilets(repairsReqToilets);
                    infrastuctureEntity.setRepairs_req_bathrooms(repairsReqBathrooms);
                    infrastuctureEntity.setDoor_window_repairs(repairs_to_door);
                    infrastuctureEntity.setPainting(painting);
                    infrastuctureEntity.setColor(color);
                    Utils.customSaveAlert(InfraActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

//                    startActivity(new Intent(InfraActivity.this, AcademicActivity.class));
                }
            }
        });
    }

    private boolean validateData() {
        if (TextUtils.isEmpty(drinkingWaterFacility)) {
            showSnackBar(getResources().getString(R.string.select_water));
            return false;
        }
        if (TextUtils.isEmpty(bigSchoolNameBoard)) {
            showSnackBar(getResources().getString(R.string.select_school));
            return false;
        }
        if (TextUtils.isEmpty(roPlant)) {
            showSnackBar(getResources().getString(R.string.select_roPlant));
            return false;
        }
        if (roPlant.equals(AppConstants.Yes) && flag_tds == 0) {
            showSnackBar("Please capture TDS image");
            return false;
        }
        if (roPlant.equals(AppConstants.No) && TextUtils.isEmpty(roplant_reason)) {
            showSnackBar(getResources().getString(R.string.select_roPlant_reason));
            binding.etReason.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(sourceOfDrinkingWater)) {
            showSnackBar(getResources().getString(R.string.select_src_drinking_water));
            return false;
        }
        if (TextUtils.isEmpty(inverter_available)) {
            showSnackBar(getResources().getString(R.string.select_inverter));
            return false;
        }
        if (inverter_available.equals(AppConstants.Yes) && TextUtils.isEmpty(inverterWorkingStatus)) {
            showSnackBar(getResources().getString(R.string.select_inverter_working));
            return false;
        }
        if (TextUtils.isEmpty(lighting_facility)) {
            showSnackBar(getResources().getString(R.string.select_lighting_facility));
            return false;
        }
        if (TextUtils.isEmpty(electricity_wiring)) {
            showSnackBar(getResources().getString(R.string.select_electricity));
            return false;
        }
        if (electricity_wiring.equals(AppConstants.No) && TextUtils.isEmpty(electricity_wiring_repairs_req)) {
            showSnackBar(getResources().getString(R.string.select_electricity_repairs));
            return false;
        }
        if (TextUtils.isEmpty(enough_fans)) {
            showSnackBar(getResources().getString(R.string.select_fans));
            return false;
        }
        if (TextUtils.isEmpty(ceilingFansWorking)) {
            showSnackBar(getResources().getString(R.string.select_ceilingFansWorking));
            binding.etCeilingFansWorking.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ceilingFansNonWorking)) {
            showSnackBar(getResources().getString(R.string.select_ceilingFansNonWorking));
            binding.etCeilingFansNonWorking.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mountedFansWorking)) {
            showSnackBar(getResources().getString(R.string.select_mountedFansWorking));
            binding.etWallMountedFansWorking.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(mountedFansNonWorking)) {
            showSnackBar(getResources().getString(R.string.select_mountedFansNonWorking));
            binding.etWallMountedFansNonWorking.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(dining_hall)) {
            showSnackBar(getResources().getString(R.string.select_dininghall_available));
            return false;
        }
        if (dining_hall.equals(AppConstants.Yes) && TextUtils.isEmpty(dining_hall_used)) {
            showSnackBar(getResources().getString(R.string.select_dininghall_used));
            return false;
        }
        if (TextUtils.isEmpty(separate_kitchen_room_available)) {
            showSnackBar(getResources().getString(R.string.select_kitchen_available));
            return false;
        }
        if (separate_kitchen_room_available.equals(AppConstants.No) && TextUtils.isEmpty(construct_kitchen_room)) {
            showSnackBar(getResources().getString(R.string.select_kitchen_construct));
            return false;
        }
        if (separate_kitchen_room_available.equals(AppConstants.Yes) && TextUtils.isEmpty(is_it_in_good_condition)) {
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
            return false;
        }
        if (TextUtils.isEmpty(powerConnectionType)) {
            showSnackBar(getResources().getString(R.string.select_powerconnection));
            return false;
        }
        if (TextUtils.isEmpty(individual_connection)) {
            showSnackBar(getResources().getString(R.string.select_individual_connection));
            return false;
        }
        if (TextUtils.isEmpty(sourceOfRunningWater)) {
            showSnackBar(getResources().getString(R.string.select_src_runnig_water));
            return false;
        }
        if (TextUtils.isEmpty(road_required)) {
            showSnackBar(getResources().getString(R.string.select_road_req));
            return false;
        }
        if (TextUtils.isEmpty(compWall_required)) {
            showSnackBar(getResources().getString(R.string.select_compoundwall_req));
            return false;
        }
        if (TextUtils.isEmpty(gate_required)) {
            showSnackBar(getResources().getString(R.string.select_gate));
            return false;
        }
        if (TextUtils.isEmpty(pathway_required)) {
            showSnackBar(getResources().getString(R.string.select_pathway));
            return false;
        }
        if (TextUtils.isEmpty(sump_required)) {
            showSnackBar(getResources().getString(R.string.select_sump_req));
            return false;
        }
        if (TextUtils.isEmpty(sewage_allowed)) {
            showSnackBar(getResources().getString(R.string.select_sewerage));
            return false;
        }
        if (TextUtils.isEmpty(drainage_functioning)) {
            showSnackBar(getResources().getString(R.string.select_drainage));
            return false;
        }
        if (TextUtils.isEmpty(heater_available)) {
            showSnackBar(getResources().getString(R.string.select_solarwater));
            return false;
        }
        if (heater_available.equals(AppConstants.Yes) && TextUtils.isEmpty(heater_workingStatus)) {
            showSnackBar(getResources().getString(R.string.select_solarwater_working));
            return false;
        }

        if (heater_available.equals(AppConstants.Yes) && TextUtils.isEmpty(heater_workingStatus)) {
            showSnackBar(getResources().getString(R.string.select_solarwater_working));
            return false;
        }

        if (add_class && TextUtils.isEmpty(add_cls_cnt)) {
            showSnackBar(getResources().getString(R.string.add_enter_number));
            binding.addClassLayout.requestFocus();
            return false;
        }

        if (add_dom && TextUtils.isEmpty(add_dom_cnt)) {
            showSnackBar(getResources().getString(R.string.add_enter_number));
            binding.addDomLayout.requestFocus();
            return false;
        }

        if (add_din && TextUtils.isEmpty(add_din_cnt)) {
            showSnackBar(getResources().getString(R.string.add_enter_number));
            binding.addDinLayout.requestFocus();
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
        if (TextUtils.isEmpty(functioningBathrooms)) {
            showSnackBar(getResources().getString(R.string.select_functioning_bathrooms));
            binding.etFuntioningBathrooms.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repairsReqToilets)) {
            showSnackBar(getResources().getString(R.string.select_repaired_toilets));
            binding.etRequiredToilets.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repairsReqBathrooms)) {
            showSnackBar(getResources().getString(R.string.select_repaired_bathrooms));
            binding.etRequiredBathrooms.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(repairs_to_door)) {
            showSnackBar(getResources().getString(R.string.select_door));
            return false;
        }
        if (TextUtils.isEmpty(painting)) {
            showSnackBar(getResources().getString(R.string.select_painting));
            return false;
        }
        if (painting.equals("BAD") && TextUtils.isEmpty(color)) {
            showSnackBar(getResources().getString(R.string.select_color));
            binding.etPainting.requestFocus();
            return false;
        }

        return true;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void submitData() {
        long x = infraViewModel.insertInfraStructureInfo(infrastuctureEntity);
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
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                FilePath = getExternalFilesDir(null)
                        + "/" + IMAGE_DIRECTORY_NAME;

                String Image_name = PIC_NAME;
                FilePath = FilePath + "/" + Image_name;

                flag_tds = 1;
                binding.ivTds.setPadding(0, 0, 0, 0);
                binding.ivTds.setBackgroundColor(getResources().getColor(R.color.white));
                file_tds = new File(FilePath);
                Glide.with(InfraActivity.this).load(file_tds).into(binding.ivTds);

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
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {
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
}
