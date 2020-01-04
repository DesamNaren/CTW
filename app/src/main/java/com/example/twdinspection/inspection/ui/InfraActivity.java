package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityInfrastructureBinding;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.viewmodel.InfraCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.InfraViewModel;

public class InfraActivity extends AppCompatActivity {
    ActivityInfrastructureBinding binding;
    InfraViewModel infraViewModel;
    InfraStructureEntity infrastuctureEntity;
    String drinkingWaterFacility = "", bigSchoolNameBoard = "", roPlant = "", sourceOfDrinkingWater, inverter_available, lighting_facility, electricity_wiring, enough_fans, dining_hall;
    String separate_kitchen_room_available, is_it_in_good_condition, transformer_available, powerConnectionType, individual_connection, road_required, compWall_required, gate_required;
    String pathway_required, sump_required, sewage_allowed, drainage_functioning, heater_available, heater_workingStatus, painting;

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
                if (selctedItem == R.id.ro_plant_yes)
                    roPlant = "YES";
                else
                    roPlant = "NO";
            }
        });
        binding.rgRoPlant.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgRoPlant.getCheckedRadioButtonId();
                if (selctedItem == R.id.ro_plant_yes)
                    roPlant = "YES";
                else
                    roPlant = "NO";
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
                if (selctedItem == R.id.is_inverter_available_yes)
                    inverter_available = "YES";
                else
                    inverter_available = "NO";
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
                if (selctedItem == R.id.electricity_wiring_yes)
                    electricity_wiring = "YES";
                else
                    electricity_wiring = "NO";
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

        binding.rgPainting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPainting.getCheckedRadioButtonId();
                if (selctedItem == R.id.painting_yes)
                    painting = "YES";
                else
                    painting = "NO";
            }
        });


        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ceilingFansWorking = binding.etCeilingFansWorking.getText().toString().trim();
                String ceilingFansNonWorking = binding.etCeilingFansNonWorking.getText().toString().trim();
                String mountedFansWorking = binding.etWallMountedFansWorking.getText().toString().trim();
                String mountedFansNonWorking = binding.etWallMountedFansNonWorking.getText().toString().trim();
                String repair_required = binding.etRepairRequired.getText().toString().trim();
                String how_many_buildings = binding.etHowManyBuildings.getText().toString().trim();
                String painting = binding.etPainting.getText().toString().trim();

                infrastuctureEntity = new InfraStructureEntity();
                infrastuctureEntity.setDrinking_water_facility(drinkingWaterFacility);
                infrastuctureEntity.setBigSchoolNameBoard(bigSchoolNameBoard);
                infrastuctureEntity.setRo_plant_woking(roPlant);
                infrastuctureEntity.setDrinking_water_source(sourceOfDrinkingWater);
                infrastuctureEntity.setInverter_available(inverter_available);
                infrastuctureEntity.setLighting_facility(lighting_facility);
                infrastuctureEntity.setElectricity_wiring(electricity_wiring);

                
                startActivity(new Intent(InfraActivity.this, AcademicActivity.class));
            }
        });
    }
}
