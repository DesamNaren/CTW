package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityInfrastructureBinding;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.viewmodel.InfraCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.InfraViewModel;
import com.google.android.material.snackbar.Snackbar;

public class InfraActivity extends AppCompatActivity {
    ActivityInfrastructureBinding binding;
    InfraViewModel infraViewModel;
    InfraStructureEntity infrastuctureEntity;
    String drinkingWaterFacility, bigSchoolNameBoard, roPlant, sourceOfDrinkingWater, sourceOfRunningWater, inverter_available, inverterWorkingStatus, lighting_facility, electricity_wiring, enough_fans, dining_hall, dining_hall_used;
    String separate_kitchen_room_available, construct_kitchen_room, is_it_in_good_condition, transformer_available, powerConnectionType, individual_connection, road_required, compWall_required, gate_required;
    String pathway_required, sump_required, sewage_allowed, drainage_functioning, heater_available, heater_workingStatus, repairs_to_door, painting, electricity_wiring_repairs_req;
    String roplant_reason, ceilingFansWorking, ceilingFansNonWorking, mountedFansWorking, mountedFansNonWorking, repair_required, how_many_buildings, totalToilets, totalBathrooms, functioningBathrooms, functioningToilets;
    String repairsReqToilets, repairsReqBathrooms, color;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_infrastructure);

        infraViewModel = ViewModelProviders.of(InfraActivity.this,
                new InfraCustomViewModel(binding, this, getApplication())).get(InfraViewModel.class);
        binding.setViewModel(infraViewModel);

        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Infrastructure & Maintenance");

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
                    binding.llTdsMeterReading.setVisibility(View.VISIBLE);
                    binding.llReason.setVisibility(View.GONE);
                } else if (selctedItem == R.id.ro_plant_no) {
                    roPlant = "NO";
                    binding.llTdsMeterReading.setVisibility(View.GONE);
                    binding.llReason.setVisibility(View.VISIBLE);
                } else {
                    roPlant = null;
                    binding.llTdsMeterReading.setVisibility(View.GONE);
                    binding.llReason.setVisibility(View.GONE);
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
                if (selctedItem == R.id.dining_hall_yes)
                    dining_hall = "YES";
                else if (selctedItem == R.id.dining_hall_no)
                    dining_hall = "NO";
                else
                    dining_hall = null;
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
                if (selctedItem == R.id.separate_kitchen_room_available_yes)
                    separate_kitchen_room_available = "YES";
                else if (selctedItem == R.id.separate_kitchen_room_available_no)
                    separate_kitchen_room_available = "NO";
                else
                    separate_kitchen_room_available = null;

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
                if (selctedItem == R.id.is_it_in_good_condition_yes)
                    is_it_in_good_condition = "YES";
                else if (selctedItem == R.id.is_it_in_good_condition_no)
                    is_it_in_good_condition = "NO";
                else
                    is_it_in_good_condition = null;
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
                if (selctedItem == R.id.heater_available_yes)
                    heater_available = "YES";
                else if (selctedItem == R.id.heater_available_no)
                    heater_available = "NO";
                else
                    heater_available = null;
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
                if (selctedItem == R.id.painting_yes)
                    painting = "GOOD";
                else if (selctedItem == R.id.painting_no)
                    painting = "BAD";
                else
                    painting = null;
            }
        });


        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateData()) {
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

                    infrastuctureEntity = new InfraStructureEntity();
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

                    long x = infraViewModel.insertInfraStructureInfo(infrastuctureEntity);
//                Toast.makeText(InfraActivity.this, "Inserted " + x, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(InfraActivity.this, AcademicActivity.class));
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
        if (roPlant.equals(AppConstants.No) && TextUtils.isEmpty(roplant_reason)) {
            showSnackBar(getResources().getString(R.string.select_roPlant_reason));
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
            return false;
        }
        if (TextUtils.isEmpty(ceilingFansNonWorking)) {
            showSnackBar(getResources().getString(R.string.select_ceilingFansNonWorking));
            return false;
        }
        if (TextUtils.isEmpty(mountedFansWorking)) {
            showSnackBar(getResources().getString(R.string.select_mountedFansWorking));
            return false;
        }
        if (TextUtils.isEmpty(mountedFansNonWorking)) {
            showSnackBar(getResources().getString(R.string.select_mountedFansNonWorking));
            return false;
        }
       /* if (!TextUtils.isEmpty(leavetype) && leavetype.equalsIgnoreCase("OD") && TextUtils.isEmpty(capturetype)) {
            showSnackBar(getResources().getString(R.string.sel_od_type));
            return false;
        }
        if (!TextUtils.isEmpty(leavetype) && leavetype.equalsIgnoreCase("OD") && !TextUtils.isEmpty(capturetype)
                && capturetype.equalsIgnoreCase("Out of station") && TextUtils.isEmpty(movementRegisterEntry)) {
            showSnackBar(getResources().getString(R.string.sel_mov_reg));
            return false;
        }
        if (TextUtils.isEmpty(headQuarters)) {
            showSnackBar(getResources().getString(R.string.sel_head_qua));
            return false;
        }
        if (TextUtils.isEmpty(staffQuarters)) {
            showSnackBar(getResources().getString(R.string.sel_staff_qua));
            return false;
        }

        if (!TextUtils.isEmpty(staffQuarters) && staffQuarters.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(stayingFacilitiesType)) {
            showSnackBar(getResources().getString(R.string.sel_stay_facility));
            return false;
        }
        if (!TextUtils.isEmpty(staffQuarters) && staffQuarters.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(captureDistance)) {
            showSnackBar(getResources().getString(R.string.sel_distance));
            return false;
        }*/
        return true;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

}
