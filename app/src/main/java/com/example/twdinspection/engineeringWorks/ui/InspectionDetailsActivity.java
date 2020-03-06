package com.example.twdinspection.engineeringWorks.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityInspDetailsBinding;
import com.example.twdinspection.engineeringWorks.viewmodels.EngDashboardViewModel;

import java.util.ArrayList;

public class InspectionDetailsActivity extends AppCompatActivity {

    ActivityInspDetailsBinding binding;
    ArrayAdapter spinnerAdapter;
    ArrayList<String> buildings,roads,drinkingWater,minorIrrigation,repairBuildings;
    String inspTime,OfficerName,officerDesg,place;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insp_details);
        binding.header.headerTitle.setText("Work Details");
        binding.header.ivHome.setVisibility(View.GONE);

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            OfficerName=sharedPreferences.getString(AppConstants.OFFICER_NAME,"");
            officerDesg=sharedPreferences.getString(AppConstants.OFFICER_DES,"");
            inspTime=sharedPreferences.getString(AppConstants.INSP_TIME,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        spinnerAdapter=null;
        buildings=new ArrayList<>();
        buildings.add("Select");
        buildings.add("Foundation Level");
        buildings.add("Basement Level");
        buildings.add("Lintel Level");
        buildings.add("Slab Level");
        buildings.add("Finishings Level");

        roads=new ArrayList<>();
        roads.add("Select");
        roads.add("Earthwork");
        roads.add("Gravel work");
        roads.add("Metal work");
        roads.add("BT work");
        roads.add("Culvert works");

        drinkingWater=new ArrayList<>();
        drinkingWater.add("Select");
        drinkingWater.add("Borewell source");
        drinkingWater.add("Main line work");
        drinkingWater.add("Distribution line work");
        drinkingWater.add("Storage tank work");
        drinkingWater.add("Motors work");

        minorIrrigation=new ArrayList<>();
        minorIrrigation.add("Select");
        minorIrrigation.add("Checkdam work");
        minorIrrigation.add("Channel work");
        minorIrrigation.add("Earthen bund work");
        minorIrrigation.add("Guide bunds etc");
        minorIrrigation.add("CMCD works");

        repairBuildings=new ArrayList<>();
        repairBuildings.add("Select");
        repairBuildings.add("Roof leakages");
        repairBuildings.add("Wall repairs");
        repairBuildings.add("Flooring repairs");
        repairBuildings.add("WC Bath Doors repairs");
        repairBuildings.add("Electrical repairs");

        binding.spSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Buildings")) {
                    spinnerAdapter=null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, buildings);
                }else if(binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Roads")){
                    spinnerAdapter=null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, roads);
                }else if(binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Drinking Water")){
                    spinnerAdapter=null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, drinkingWater);
                }else if(binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Minor Irrigation")){
                    spinnerAdapter=null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, minorIrrigation);
                }else if(binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Repairs to Buildings")){
                    spinnerAdapter=null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, repairBuildings);
                }else if(binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Others")){
                    binding.tvSectorOthers.setVisibility(View.VISIBLE);
                    binding.tvStageOthers.setVisibility(View.VISIBLE);
                    binding.llStageWork.setVisibility(View.GONE);
                }
                binding.spStageInProgress.setAdapter(spinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InspectionDetailsActivity.this, UploadEngPhotosActivity.class));
            }
        });
    }
}
