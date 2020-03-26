package com.example.twdinspection.engineering_works.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
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
import com.example.twdinspection.engineering_works.source.SectorsEntity;
import com.example.twdinspection.engineering_works.source.SectorsResponse;
import com.example.twdinspection.engineering_works.viewmodels.EngDashboardCustomViewModel;
import com.example.twdinspection.engineering_works.viewmodels.EngDashboardViewModel;
import com.example.twdinspection.engineering_works.viewmodels.InspDetailsCustomViewModel;
import com.example.twdinspection.engineering_works.viewmodels.InspDetailsViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class InspectionDetailsActivity extends AppCompatActivity implements ErrorHandlerInterface {

    ActivityInspDetailsBinding binding;
    ArrayAdapter spinnerAdapter;
    ArrayList<String> buildings, roads, drinkingWater, minorIrrigation, repairBuildings;
    String inspTime, OfficerName, officerDesg, place;
    SharedPreferences sharedPreferences;
    InspDetailsViewModel viewModel;
    int sectorsCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insp_details);
        binding.header.headerTitle.setText("Work Details");
        binding.header.ivHome.setVisibility(View.GONE);
        viewModel = ViewModelProviders.of(this,
                new InspDetailsCustomViewModel(this, getApplication())).get(InspDetailsViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            OfficerName = sharedPreferences.getString(AppConstants.OFFICER_NAME, "");
            officerDesg = sharedPreferences.getString(AppConstants.OFFICER_DES, "");
            inspTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
        } catch (Exception e) {
            e.printStackTrace();
        }


        viewModel.getSectors().observe(InspectionDetailsActivity.this, new Observer<List<SectorsEntity>>() {
            @Override
            public void onChanged(List<SectorsEntity> sectorsEntities) {
                if (sectorsEntities != null) {
                    ArrayList<String> sectorsList = new ArrayList<>();
                    sectorsList.add("Select");
                    for (int i = 0; i < sectorsEntities.size(); i++) {
                        sectorsList.add(sectorsEntities.get(i).getSectorName());
                    }
                    ArrayAdapter spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, sectorsList);
                    binding.spSector.setAdapter(spinnerAdapter);
                } else {
                    viewModel.getSectorResponse().observe(InspectionDetailsActivity.this, new Observer<SectorsResponse>() {
                        @Override
                        public void onChanged(SectorsResponse sectorsResponse) {
                            if (sectorsResponse != null && sectorsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {
                                sectorsCnt = viewModel.insertSectorsInfo(sectorsResponse.getSectorsEntitys());
                                if (sectorsCnt >= 0)
                                    callSnackBar(getString(R.string.something));

                            } else {
                                callSnackBar(sectorsResponse.getStatusMessage());
                            }

                        }
                    });
                }
            }
        });

        spinnerAdapter = null;
        buildings = new ArrayList<>();
        buildings.add("Select");
        buildings.add("Foundation Level");
        buildings.add("Basement Level");
        buildings.add("Lintel Level");
        buildings.add("Slab Level");
        buildings.add("Finishings Level");

        roads = new ArrayList<>();
        roads.add("Select");
        roads.add("Earthwork");
        roads.add("Gravel work");
        roads.add("Metal work");
        roads.add("BT work");
        roads.add("Culvert works");

        drinkingWater = new ArrayList<>();
        drinkingWater.add("Select");
        drinkingWater.add("Borewell source");
        drinkingWater.add("Main line work");
        drinkingWater.add("Distribution line work");
        drinkingWater.add("Storage tank work");
        drinkingWater.add("Motors work");

        minorIrrigation = new ArrayList<>();
        minorIrrigation.add("Select");
        minorIrrigation.add("Checkdam work");
        minorIrrigation.add("Channel work");
        minorIrrigation.add("Earthen bund work");
        minorIrrigation.add("Guide bunds etc");
        minorIrrigation.add("CMCD works");

        repairBuildings = new ArrayList<>();
        repairBuildings.add("Select");
        repairBuildings.add("Roof leakages");
        repairBuildings.add("Wall repairs");
        repairBuildings.add("Flooring repairs");
        repairBuildings.add("WC Bath Doors repairs");
        repairBuildings.add("Electrical repairs");

        binding.spSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Buildings")) {
                    spinnerAdapter = null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, buildings);
                } else if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Roads")) {
                    spinnerAdapter = null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, roads);
                } else if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Drinking Water")) {
                    spinnerAdapter = null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, drinkingWater);
                } else if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Minor Irrigation")) {
                    spinnerAdapter = null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, minorIrrigation);
                } else if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Repairs to Buildings")) {
                    spinnerAdapter = null;
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, repairBuildings);
                } else if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Others")) {
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

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void handleError(Throwable e, Context context) {

    }
}
