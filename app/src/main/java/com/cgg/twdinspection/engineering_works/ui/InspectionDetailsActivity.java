package com.cgg.twdinspection.engineering_works.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityInspDetailsBinding;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.StagesResponse;
import com.cgg.twdinspection.engineering_works.source.SubmitEngWorksRequest;
import com.cgg.twdinspection.engineering_works.source.SubmittedStageResponse;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;
import com.cgg.twdinspection.engineering_works.viewmodels.InspDetailsCustomViewModel;
import com.cgg.twdinspection.engineering_works.viewmodels.InspDetailsViewModel;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class InspectionDetailsActivity extends LocBaseActivity implements ErrorHandlerInterface {

    ActivityInspDetailsBinding binding;
    ArrayAdapter spinnerAdapter;
    String inspTime, officerName, officerDesg, place, officerId, sectorOthers;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InspDetailsViewModel viewModel;
    List<SectorsEntity> sectorsEntities = new ArrayList<>();
    Integer selSectorId = -1, selSchemeId = -1, selWorkProgStageId = -1;
    Double actExpIncurred = -1.0, physProgRating = -1.0;
    String selSectorName, selSchemeName, selStageName, selWorkInProgStageName;
    private String overallAppearance, worksmenSkill, qualCare, qualMat, surfaceFinishing, observation, satLevel;
    StagesResponse stagesResponse;
    ArrayList<String> majorStages, tempMajorStages;
    ArrayAdapter majorStagesAdapter, selectAdapter;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insp_details);
        binding.header.headerTitle.setText("WORK DETAILS");
        binding.header.ivHome.setVisibility(View.GONE);
        viewModel = ViewModelProviders.of(this,
                new InspDetailsCustomViewModel(this, getApplication())).get(InspDetailsViewModel.class);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        customProgressDialog = new CustomProgressDialog(this);

        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerName = sharedPreferences.getString(AppConstants.OFFICER_NAME, "");
            officerDesg = sharedPreferences.getString(AppConstants.OFFICER_DES, "");
            inspTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            place = sharedPreferences.getString(AppConstants.OFF_PLACE_OF_WORK, "");
            binding.tvAreaOperation.setText(place);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList selectList = new ArrayList<>();
        selectList.add(getString(R.string.select));
        selectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selectList);


        Gson gson = new Gson();
        WorkDetail workDetail = gson.fromJson(sharedPreferences.getString(AppConstants.ENGWORKSMASTER, ""), WorkDetail.class);
        if (workDetail != null) {
            binding.setWorkDetails(workDetail);
            editor.commit();
        }
        LiveData<List<SectorsEntity>> liveData = viewModel.getSectors();
        liveData.observe(InspectionDetailsActivity.this, new Observer<List<SectorsEntity>>() {
            @Override
            public void onChanged(List<SectorsEntity> sectorsEntities) {
                liveData.removeObservers(InspectionDetailsActivity.this);
                InspectionDetailsActivity.this.sectorsEntities = sectorsEntities;
                if (sectorsEntities != null && sectorsEntities.size() > 0) {
                    ArrayList<String> sectorsList = new ArrayList<>();
                    sectorsList.add(getString(R.string.select));
                    for (int i = 0; i < sectorsEntities.size(); i++) {
                        sectorsList.add(sectorsEntities.get(i).getSectorName());
                    }
                    ArrayAdapter spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, sectorsList);
                    binding.spSector.setAdapter(spinnerAdapter);
                } else {
                    Utils.customEngSyncAlert(InspectionDetailsActivity.this, getString(R.string.app_name), "No sectors found...\n Do you want to sync Sector master data to proceed further?");
                }
            }
        });
        majorStages = new ArrayList<>();
        majorStages.add("Not Started");
        majorStages.add("Tender stage");
        majorStages.add("Grounded");
        majorStages.add("In progress");
        majorStages.add("Work Stopped");
        majorStages.add("Completed");
        tempMajorStages = new ArrayList<>();
        tempMajorStages.add("Select");
        tempMajorStages.addAll(majorStages);
        majorStagesAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, tempMajorStages);
        binding.spStage.setAdapter(majorStagesAdapter);
        if (Utils.checkInternetConnection(InspectionDetailsActivity.this)) {
            customProgressDialog.show();
            LiveData<SubmittedStageResponse> submittedStageResponseLiveData = viewModel.getSubmittedStageResponse(workDetail.getWorkId());
            submittedStageResponseLiveData.observe(InspectionDetailsActivity.this, new Observer<SubmittedStageResponse>() {
                @Override
                public void onChanged(SubmittedStageResponse stageResponse) {
                    submittedStageResponseLiveData.removeObservers(InspectionDetailsActivity.this);
                    customProgressDialog.hide();
                    int selPos = 0;
                    if (stageResponse != null && !TextUtils.isEmpty(stageResponse.getStageOfWork())) {
                        String subStageName = stageResponse.getStageOfWork();
                        tempMajorStages.clear();
                        tempMajorStages.add(getString(R.string.select));
                        for (int z = 0; z < majorStages.size(); z++) {
                            if (majorStages.get(z).equalsIgnoreCase(subStageName)) {
                                selPos = z;
                                break;
                            }
                        }
                        for (int z = 0; z < majorStages.size(); z++) {
                            if (z >= selPos) {
                                tempMajorStages.add(majorStages.get(z));
                            }
                        }
                        binding.spStage.setAdapter(majorStagesAdapter);
                    } else if (stageResponse != null && TextUtils.isEmpty(stageResponse.getStageOfWork())) {
                        binding.spStage.setAdapter(majorStagesAdapter);
                    } else {
                        callSnackBar(getString(R.string.something));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(InspectionDetailsActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }

        LiveData<List<GrantScheme>> grantListLiveData = viewModel.getGrantSchemes();
        grantListLiveData.observe(InspectionDetailsActivity.this, new Observer<List<GrantScheme>>() {
            @Override
            public void onChanged(List<GrantScheme> grantSchemes) {
                grantListLiveData.removeObservers(InspectionDetailsActivity.this);
                if (grantSchemes != null && grantSchemes.size() > 0) {
                    ArrayList<String> schemesList = new ArrayList<>();
                    schemesList.add(getString(R.string.select));
                    for (int i = 0; i < grantSchemes.size(); i++) {
                        schemesList.add(grantSchemes.get(i).getSchemeName());
                    }
                    ArrayAdapter spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, schemesList);
                    binding.spScheme.setAdapter(spinnerAdapter);
                } else {
                    Utils.customEngSyncAlert(InspectionDetailsActivity.this, getString(R.string.app_name), "No schemes found...\n Do you want to sync Scheme master data to proceed further?");
                }
            }
        });


        binding.spStage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int selPos = 0;
                if (binding.spStage.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.select))) {
                    selStageName = "";
                } else {
                    selStageName = binding.spStage.getSelectedItem().toString();
//                    tempMajorStages.clear();
//                    for (int z = 0; z < majorStages.size(); z++) {
//                        if (majorStages.get(z).equalsIgnoreCase(selStageName)) {
//                            selPos = z;
//                            break;
//                        }
//                    }
//                    for (int z = 0; z < majorStages.size(); z++) {
//                        if (z >= selPos) {
//                            tempMajorStages.add(majorStages.get(z));
//                        }
//                    }
//                    binding.spStage.setAdapter(majorStagesAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAdapter = null;
        binding.spSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerAdapter = null;
//                binding.tvSectorOthers.setVisibility(View.GONE);
//                binding.tvStageOthers.setVisibility(View.GONE);
//                binding.llStageWork.setVisibility(View.VISIBLE);
                ArrayList<String> stagesList = new ArrayList<>();
                binding.etFinProgressOthers.setText("");
                if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.select))) {
                    binding.llObs.setVisibility(View.GONE);
                    selSectorId = -1;
                    selSectorName = "";
                    selWorkProgStageId = -1;
                    physProgRating = -1.0;
                    actExpIncurred = -1.0;
                    selWorkInProgStageName = "";
                    binding.spStageInProgress.setAdapter(selectAdapter);
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.etSectorOthers.setText(null);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.etStageOthers.setText(null);
                    binding.tvPercentFinProg.setText("");
                    binding.tvActExp.setText("");
                } else {
                    LiveData<Integer> liveData1 = viewModel.getSectorId(binding.spSector.getSelectedItem().toString());
                    liveData1.observe(InspectionDetailsActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            liveData1.removeObservers(InspectionDetailsActivity.this);
                            if (integer != null) {
                                selSectorId = integer;
                                selSectorName = binding.spSector.getSelectedItem().toString();
                                if (selSectorName.equalsIgnoreCase("Others")) {
                                    binding.llFinProgRat.setVisibility(View.GONE);
                                    binding.tvFinProgressOthers.setVisibility(View.VISIBLE);
                                    binding.llObs.setVisibility(View.VISIBLE);
                                    binding.tvObs.setHint("Observations for specified sector");
                                    binding.tvSectorOthers.setVisibility(View.VISIBLE);
                                    binding.llStageWork.setVisibility(View.GONE);
                                    binding.tvStageOthers.setVisibility(View.VISIBLE);
                                    selWorkProgStageId = -1;
                                    physProgRating = -1.0;
                                    actExpIncurred = -1.0;
                                    selWorkInProgStageName = "";
                                    binding.tvPercentFinProg.setText("");
                                    binding.tvActExp.setText("");
                                } else {
                                    binding.llFinProgRat.setVisibility(View.VISIBLE);
                                    binding.tvFinProgressOthers.setVisibility(View.GONE);
                                    binding.llObs.setVisibility(View.VISIBLE);
                                    binding.tvObs.setHint("Observations for " + binding.spSector.getSelectedItem().toString());
                                    binding.tvSectorOthers.setVisibility(View.GONE);
                                    binding.llStageWork.setVisibility(View.VISIBLE);
                                    binding.tvStageOthers.setVisibility(View.GONE);
                                    binding.etSectorOthers.setText(null);
                                    binding.etStageOthers.setText(null);
                                    if (Utils.checkInternetConnection(InspectionDetailsActivity.this)) {
                                        customProgressDialog.show();

                                        LiveData<StagesResponse> liveData2 = viewModel.getStagesResponse(selSectorId);
                                        liveData2.observe(InspectionDetailsActivity.this, new Observer<StagesResponse>() {
                                            @Override
                                            public void onChanged(StagesResponse stagesResponse) {
//                                                liveData2.removeObservers(InspectionDetailsActivity.this);
                                                InspectionDetailsActivity.this.stagesResponse = stagesResponse;
                                                if (stagesResponse != null && stagesResponse.getStages().size() > 0) {
                                                    customProgressDialog.hide();
                                                    stagesList.clear();
                                                    stagesList.add(getString(R.string.select));
                                                    for (int y = 0; y < stagesResponse.getStages().size(); y++) {
                                                        stagesList.add(stagesResponse.getStages().get(y).getStageName());
                                                    }
                                                    ArrayAdapter spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, stagesList);
                                                    binding.spStageInProgress.setAdapter(spinnerAdapter);
                                                } else {
                                                    callSnackBar(getString(R.string.something));
                                                }
                                            }
                                        });
                                    } else {
                                        Utils.customErrorAlert(InspectionDetailsActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
                                    }
                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                    spinnerAdapter = new ArrayAdapter<>(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, stagesList);
                    binding.spStageInProgress.setAdapter(spinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.etFinProgressOthers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {
                    try {
                        physProgRating = Double.valueOf(charSequence.toString());
                        if (physProgRating < 100.00 && workDetail != null) {
                            actExpIncurred = (physProgRating / 100) * Double.valueOf(workDetail.getEstimateCost()).intValue();
                            binding.tvActExp.setText(String.format("%.2f", actExpIncurred.doubleValue()));
                        } else {
                            binding.etFinProgressOthers.requestFocus();
                            binding.etFinProgressOthers.setError(getResources().getString(R.string.enter_valid_fin_progress_percentage));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        binding.etFinProgressOthers.setText("");
                        binding.tvActExp.setText("");
                    }
                } else {
                    binding.tvActExp.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.spScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.spScheme.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    selSchemeId = -1;
                    selSchemeName = "";
                } else {
                    LiveData<Integer> integerLiveData = viewModel.getgrantSchemeId(binding.spScheme.getSelectedItem().toString());
                    integerLiveData.observe(InspectionDetailsActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            integerLiveData.removeObservers(InspectionDetailsActivity.this);
                            if (integer != null) {

                                selSchemeId = integer;
                                selSchemeName = binding.spScheme.getSelectedItem().toString();
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spStageInProgress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.spStageInProgress.getSelectedItem() != null && binding.spStageInProgress.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.select))) {
                    selWorkProgStageId = -1;
                    physProgRating = -1.0;
                    actExpIncurred = -1.0;
                    selWorkInProgStageName = "";
                    binding.tvPercentFinProg.setText("");
                    binding.tvActExp.setText("");
                } else if (binding.spStageInProgress.getSelectedItem() != null) {
                    selWorkInProgStageName = binding.spStageInProgress.getSelectedItem().toString();
                    if (stagesResponse != null && stagesResponse.getStages().size() > 0) {
                        for (int z = 0; z < stagesResponse.getStages().size(); z++) {
                            if (stagesResponse.getStages().get(z).getStageName().equalsIgnoreCase(selWorkInProgStageName)) {
                                selWorkProgStageId = stagesResponse.getStages().get(z).getStageId();
                                physProgRating = stagesResponse.getStages().get(z).getStageProgressRating().doubleValue();
                                try {
                                    actExpIncurred = (Double.valueOf(physProgRating) / 100) * Double.valueOf(workDetail.getEstimateCost()).intValue();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                binding.tvPercentFinProg.setText(physProgRating.toString());
                                binding.tvActExp.setText(String.format("%.2f", actExpIncurred.doubleValue()));

                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.rgOverallAppear.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_overall_appear_sat) {
                    overallAppearance = AppConstants.SATISFACTORY;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_overall_appear_poor) {
                    overallAppearance = AppConstants.POOR;
                }
            }
        });
        binding.rgWorkmenSkill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_workmen_skill_sat) {
                    worksmenSkill = AppConstants.SATISFACTORY;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_workmen_skill_poor) {
                    worksmenSkill = AppConstants.POOR;
                }
            }
        });

        binding.rgQualCare.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_qual_care_sat) {
                    qualCare = AppConstants.SATISFACTORY;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_qual_care_poor) {
                    qualCare = AppConstants.POOR;
                }
            }
        });

        binding.rgQualMaterials.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_qual_materials_sat) {
                    qualMat = AppConstants.SATISFACTORY;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_qual_materials_poor) {
                    qualMat = AppConstants.POOR;
                }
            }
        });
        binding.rgSurfaceFinish.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_surface_finish_sat) {
                    surfaceFinishing = AppConstants.SATISFACTORY;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_surface_finish_poor) {
                    surfaceFinishing = AppConstants.POOR;
                }
            }
        });
        binding.rgSatisLevel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_satis_level_sat) {
                    satLevel = AppConstants.SATISFACTORY;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_satis_level_poor) {
                    satLevel = AppConstants.POOR;
                }
            }
        });
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                observation = binding.etObs.getText().toString().trim();
                sectorOthers = binding.etSectorOthers.getText().toString().trim();
                if (binding.llStageWork.getVisibility() == View.GONE) {
                    selWorkProgStageId = -1;
                    selWorkInProgStageName = binding.etStageOthers.getText().toString().trim();
                }
                if (validate()) {
                    SubmitEngWorksRequest submitEngWorksRequest = new SubmitEngWorksRequest();
                    submitEngWorksRequest.setOfficerId(officerId);
                    submitEngWorksRequest.setOfficerName(officerName);
                    submitEngWorksRequest.setDesignation(officerDesg);
                    submitEngWorksRequest.setPlaceOfWork(place);
                    submitEngWorksRequest.setInspectionTime(inspTime);
                    submitEngWorksRequest.setAreaOfOperation(place);
                    if (mCurrentLocation != null && mCurrentLocation.getLatitude() > 0 && mCurrentLocation.getLongitude() > 0) {
                        submitEngWorksRequest.setLatitude(String.valueOf(mCurrentLocation.getLatitude()));
                        submitEngWorksRequest.setLongitude(String.valueOf(mCurrentLocation.getLongitude()));
                    }
                    if (workDetail != null) {
                        submitEngWorksRequest.setDistName(workDetail.getDistName());
                        submitEngWorksRequest.setDistId(workDetail.getDistId());
                        submitEngWorksRequest.setMandName(workDetail.getMandName());
                        submitEngWorksRequest.setMandId(workDetail.getMandId());
                        submitEngWorksRequest.setGpName(workDetail.getGpName());
                        submitEngWorksRequest.setGpId(workDetail.getGpId());
                        submitEngWorksRequest.setVillName(workDetail.getVillName());
                        submitEngWorksRequest.setVillId(workDetail.getVillId());
                        submitEngWorksRequest.setAssemblyConstName(workDetail.getAssemblyConstName());
                        submitEngWorksRequest.setAssemblyContId(workDetail.getAssemblyContId());
                        submitEngWorksRequest.setSectorId(selSectorId.toString());
                        submitEngWorksRequest.setSectorName(selSectorName);
                        submitEngWorksRequest.setSectorOtherValue(sectorOthers);
                        submitEngWorksRequest.setWorkName(workDetail.getWorkName());
                        submitEngWorksRequest.setWorkId(workDetail.getWorkId().toString());
                        submitEngWorksRequest.setEstimateCost(workDetail.getEstimateCost());
                        submitEngWorksRequest.setSchemeId(selSchemeId.toString());
                        submitEngWorksRequest.setExectingAgency(workDetail.getExectingAgency());
                        submitEngWorksRequest.setSanctionDate(workDetail.getSanctionDate());
                        submitEngWorksRequest.setTechSanctionDate(workDetail.getTechSanctionDate());
                        submitEngWorksRequest.setCommenceDate(workDetail.getCommenceDate());
                        submitEngWorksRequest.setTargetDate(workDetail.getTargetDate());
                        submitEngWorksRequest.setExtensionTime(workDetail.getExtensionTime());
                        submitEngWorksRequest.setStaffDeptName(workDetail.getStaffDept());
                        submitEngWorksRequest.setStaffMandalName(workDetail.getStaffMandalName());
                        submitEngWorksRequest.setStaffMandalId(workDetail.getStaffMandalId());
                        submitEngWorksRequest.setStaffEe(workDetail.getStaffEe());
                        submitEngWorksRequest.setStaffDyee(workDetail.getStaffDyee());
                        submitEngWorksRequest.setStaffAee(workDetail.getStaffAee());
                        submitEngWorksRequest.setStageOfWork(selStageName);
                        submitEngWorksRequest.setWorkInProgId(selWorkProgStageId.toString());
                        submitEngWorksRequest.setWorkInProgName(selWorkInProgStageName);
                        submitEngWorksRequest.setPhysProgressRating(physProgRating.toString());
                        submitEngWorksRequest.setActExpIncurred(String.format("%.2f", actExpIncurred.doubleValue()));
                        submitEngWorksRequest.setFinProgressAchieved(physProgRating.toString());
                        submitEngWorksRequest.setOverallExp(overallAppearance);
                        submitEngWorksRequest.setWorkmenSkill(worksmenSkill);
                        submitEngWorksRequest.setQualCare(qualCare);
                        submitEngWorksRequest.setQualMat(qualMat);
                        submitEngWorksRequest.setSurfaceFinish(surfaceFinishing);
                        submitEngWorksRequest.setObservation(observation);
                        submitEngWorksRequest.setSatisfactionLevel(satLevel);
                        submitEngWorksRequest.setVersionNo(Utils.getVersionName(InspectionDetailsActivity.this));
                        submitEngWorksRequest.setDeviceId(Utils.getDeviceID(InspectionDetailsActivity.this));
                        submitEngWorksRequest.setSchemeName(selSchemeName);
                        String request = gson.toJson(submitEngWorksRequest);
                        editor.putString(AppConstants.EngSubmitRequest, request);
                        editor.commit();
                        startActivity(new Intent(InspectionDetailsActivity.this, UploadEngPhotosActivity.class));
                    } else {
                        Toast.makeText(InspectionDetailsActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mGpsSwitchStateReceiver);
    }


    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                    // Make an action or refresh an already managed state.
                    callPermissions();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private boolean validate() {
        if (selSectorId == -1) {
            callSnackBar(getString(R.string.please_select_sector));
            return false;
        } else if (binding.tvSectorOthers.getVisibility() == View.VISIBLE && TextUtils.isEmpty(binding.etSectorOthers.getText())) {
            callSnackBar(getString(R.string.please_specify_sect_name));
            return false;
        } else if (selSchemeId == -1) {
            callSnackBar(getString(R.string.please_select_scheme));
            return false;
        } else if (TextUtils.isEmpty(selStageName)) {
            callSnackBar(getString(R.string.please_select_stage_of_work));
            return false;
        } else if (binding.llStageWork.getVisibility() == View.VISIBLE && selWorkProgStageId == -1) {
            callSnackBar(getString(R.string.select_stage_of_work_in_progress_work));
            return false;
        } else if (binding.llStageWork.getVisibility() == View.GONE && TextUtils.isEmpty(binding.etStageOthers.getText())) {
            callSnackBar(getString(R.string.enter_stage_of_work_in_progress_work));
            return false;
        }else if (binding.llFinProgRat.getVisibility() == View.GONE && TextUtils.isEmpty(binding.etFinProgressOthers.getText())) {
            callSnackBar(getString(R.string.enter_fin_prog_acheived));
            return false;
        }else if (physProgRating>100.00) {
            callSnackBar(getString(R.string.enter_valid_fin_progress_percentage));
            return false;
        } else if (TextUtils.isEmpty(overallAppearance)) {
            callSnackBar(getString(R.string.check_overall_experience));
            return false;
        } else if (TextUtils.isEmpty(worksmenSkill)) {
            callSnackBar(getString(R.string.check_workmen_skill));
            return false;
        } else if (TextUtils.isEmpty(qualCare)) {
            callSnackBar(getString(R.string.check_qual_care));
            return false;
        } else if (TextUtils.isEmpty(qualMat)) {
            callSnackBar(getString(R.string.check_qual_materials));
            return false;
        } else if (TextUtils.isEmpty(surfaceFinishing)) {
            callSnackBar(getString(R.string.check_surface_finishings));
            return false;
        } else if (TextUtils.isEmpty(observation)) {
            callSnackBar(getString(R.string.enter_observations));
            return false;
        } else if (TextUtils.isEmpty(satLevel)) {
            callSnackBar(getString(R.string.check_sat_level));
            return false;
        }
        return true;
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Log.i("MSG", "handleError: " + errMsg);
        callSnackBar(errMsg);
    }
}

