package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityInfrastructureBinding;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.viewmodel.InfraCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.InfraViewModel;

public class InfraActivity extends AppCompatActivity {
    ActivityInfrastructureBinding binding;
    InfraViewModel infraViewModel;
    InfraStructureEntity infrastuctureEntity;
    String drinkingWaterFacility = "", bigSchoolNameBoard = "", roPlant = "", sourceOfDrinkingWater, inverter_available, inverterWorkingStatus, lighting_facility, electricity_wiring, enough_fans, dining_hall;
    String separate_kitchen_room_available, construct_kitchen_room, is_it_in_good_condition, transformer_available, powerConnectionType, individual_connection, road_required, compWall_required, gate_required;
    String pathway_required, sump_required, sewage_allowed, drainage_functioning, heater_available, heater_workingStatus, repairs_to_door, painting,electricity_wiring_repairs_req;

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
                else
                    drinkingWaterFacility = "BAD";
            }
        });
        binding.rgBigSchoolNameBoard.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBigSchoolNameBoard.getCheckedRadioButtonId();
                if (selctedItem == R.id.big_school_name_board_yes)
                    bigSchoolNameBoard = "YES";
                else
                    bigSchoolNameBoard = "NO";
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
                } else {
                    roPlant = "NO";
                    binding.llTdsMeterReading.setVisibility(View.GONE);
                    binding.llReason.setVisibility(View.VISIBLE);
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
                else
                    sourceOfDrinkingWater = getResources().getString(R.string.hand_pump);
            }
        });
        binding.rgIsInverterAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgIsInverterAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.is_inverter_available_yes) {
                    inverter_available = "YES";
                    binding.llInverterWorkingStatus.setVisibility(View.VISIBLE);
                } else {
                    inverter_available = "NO";
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
                else
                    inverterWorkingStatus = "BAD";
            }
        });

        binding.rgLightingFacility.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLightingFacility.getCheckedRadioButtonId();
                if (selctedItem == R.id.lighting_facility_yes)
                    lighting_facility = "GOOD";
                else
                    lighting_facility = "BAD";
            }
        });
        binding.rgElectricityWiring.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgElectricityWiring.getCheckedRadioButtonId();
                if (selctedItem == R.id.electricity_wiring_yes) {
                    electricity_wiring = "YES";
                    binding.llElectricityWiringRepairsReq.setVisibility(View.GONE);
                }
                else {
                    electricity_wiring = "NO";
                    binding.llElectricityWiringRepairsReq.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rgElectricityWiringRepairsReq.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgElectricityWiringRepairsReq.getCheckedRadioButtonId();
                if (selctedItem == R.id.electricity_wiring_repairs_req_yes)
                    electricity_wiring_repairs_req = "YES";
                else
                    electricity_wiring_repairs_req = "NO";
            }
        });
        binding.rgEnoughFans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgEnoughFans.getCheckedRadioButtonId();
                if (selctedItem == R.id.enough_fans_yes)
                    enough_fans = "YES";
                else
                    enough_fans = "NO";
            }
        });
        binding.rgDiningHall.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDiningHall.getCheckedRadioButtonId();
                if (selctedItem == R.id.dining_hall_yes)
                    dining_hall = "YES";
                else
                    dining_hall = "NO";
            }
        });

        binding.rgSeparateKitchenRoomAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSeparateKitchenRoomAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.separate_kitchen_room_available_yes)
                    separate_kitchen_room_available = "YES";
                else
                    separate_kitchen_room_available = "NO";
            }
        });

        binding.rgConstructKitchenRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgConstructKitchenRoom.getCheckedRadioButtonId();
                if (selctedItem == R.id.construct_kitchen_room_yes)
                    construct_kitchen_room = "YES";
                else
                    construct_kitchen_room = "NO";
            }
        });


        binding.rgIsItInGoodCondition.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgIsItInGoodCondition.getCheckedRadioButtonId();
                if (selctedItem == R.id.is_it_in_good_condition_yes)
                    is_it_in_good_condition = "YES";
                else
                    is_it_in_good_condition = "NO";
            }
        });

        binding.rgTransformerAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTransformerAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.transformer_available_yes)
                    transformer_available = "YES";
                else
                    transformer_available = "NO";
            }
        });


        binding.rgPowerConnectionType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPowerConnectionType.getCheckedRadioButtonId();
                if (selctedItem == R.id.single_phase)
                    powerConnectionType = "SINGLE PHASE";
                else
                    powerConnectionType = "3 PHASE";
            }
        });

        binding.rgIndividualConnection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgIndividualConnection.getCheckedRadioButtonId();
                if (selctedItem == R.id.individual_connection_yes)
                    individual_connection = "YES";
                else
                    individual_connection = "NO";
            }
        });

        binding.rgRunningWaterSource.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRunningWaterSource.getCheckedRadioButtonId();
                if (selctedItem == R.id.runningWater_source_open_well)
                    sourceOfDrinkingWater = getResources().getString(R.string.open_well);
                else if (selctedItem == R.id.runningWater_source_bore_well)
                    sourceOfDrinkingWater = getResources().getString(R.string.bore_well);
                else if (selctedItem == R.id.runningWater_source_municipal)
                    sourceOfDrinkingWater = getResources().getString(R.string.municipal);
                else
                    sourceOfDrinkingWater = getResources().getString(R.string.hand_pump);
            }
        });

        binding.rgRoadRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRoadRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.road_required_yes)
                    road_required = "YES";
                else
                    road_required = "NO";
            }
        });

        binding.rgCompWallRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCompWallRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.compWall_required_yes)
                    compWall_required = "YES";
                else
                    compWall_required = "NO";
            }
        });

        binding.rgGateRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgGateRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.gate_required_yes)
                    gate_required = "YES";
                else
                    gate_required = "NO";
            }
        });

        binding.rgPathwayRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPathwayRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.pathway_required_yes)
                    pathway_required = "YES";
                else
                    pathway_required = "NO";
            }
        });

        binding.rgSumpRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSumpRequired.getCheckedRadioButtonId();
                if (selctedItem == R.id.sump_required_yes)
                    sump_required = "YES";
                else
                    sump_required = "NO";
            }
        });

        binding.rgSewageAllowed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgSewageAllowed.getCheckedRadioButtonId();
                if (selctedItem == R.id.sewage_allowed_yes)
                    sewage_allowed = "YES";
                else
                    sewage_allowed = "NO";
            }
        });

        binding.rgDrainageFunctioning.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDrainageFunctioning.getCheckedRadioButtonId();
                if (selctedItem == R.id.drainage_functioning_yes)
                    drainage_functioning = "YES";
                else
                    drainage_functioning = "NO";
            }
        });

        binding.rgHeaterAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHeaterAvailable.getCheckedRadioButtonId();
                if (selctedItem == R.id.heater_available_yes)
                    heater_available = "YES";
                else
                    heater_available = "NO";
            }
        });

        binding.rgHeaterWorkingStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHeaterWorkingStatus.getCheckedRadioButtonId();
                if (selctedItem == R.id.heater_workingStatus_yes)
                    heater_workingStatus = "YES";
                else
                    heater_workingStatus = "NO";
            }
        });

        binding.rgRepairsToDoor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRepairsToDoor.getCheckedRadioButtonId();
                if (selctedItem == R.id.repairs_to_door_yes)
                    repairs_to_door = "YES";
                else
                    repairs_to_door = "NO";
            }
        });

        binding.rgPainting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPainting.getCheckedRadioButtonId();
                if (selctedItem == R.id.painting_yes)
                    painting = "GOOD";
                else
                    painting = "BAD";
            }
        });


        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String roplant_reason = binding.etReason.getText().toString().trim();
                String ceilingFansWorking = binding.etCeilingFansWorking.getText().toString().trim();
                String ceilingFansNonWorking = binding.etCeilingFansNonWorking.getText().toString().trim();
                String mountedFansWorking = binding.etWallMountedFansWorking.getText().toString().trim();
                String mountedFansNonWorking = binding.etWallMountedFansNonWorking.getText().toString().trim();
                String repair_required = binding.etRepairRequired.getText().toString().trim();
                String how_many_buildings = binding.etHowManyBuildings.getText().toString().trim();
                String totalToilets = binding.etTotalToilets.getText().toString().trim();
                String totalBathrooms = binding.etTotalBathrooms.getText().toString().trim();
                String functioningToilets = binding.etFunctioningToilets.getText().toString().trim();
                String functioningBathrooms = binding.etFuntioningBathrooms.getText().toString().trim();
                String repairsReqToilets = binding.etRequiredToilets.getText().toString().trim();
                String repairsReqBathrooms = binding.etRequiredBathrooms.getText().toString().trim();
                String color = binding.etPainting.getText().toString().trim();

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
                infrastuctureEntity.setSeparate_kitchen_room_available(separate_kitchen_room_available);
                infrastuctureEntity.setConstruct_kitchen_room(construct_kitchen_room);
                infrastuctureEntity.setIs_it_in_good_condition(is_it_in_good_condition);
                infrastuctureEntity.setRepair_required(repair_required);
                infrastuctureEntity.setHow_many_buildings(how_many_buildings);
                infrastuctureEntity.setTransformer_available(transformer_available);
                infrastuctureEntity.setPowerConnection_type(powerConnectionType);
                infrastuctureEntity.setIndividual_connection(individual_connection);
                infrastuctureEntity.setRunningWater_source(sourceOfDrinkingWater);
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
        });
    }
}
