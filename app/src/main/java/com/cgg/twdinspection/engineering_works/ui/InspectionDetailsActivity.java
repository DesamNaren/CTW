package com.cgg.twdinspection.engineering_works.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.ActivityInspDetailsBinding;
import com.cgg.twdinspection.engineering_works.source.GrantScheme;
import com.cgg.twdinspection.engineering_works.source.GrantSchemesResponse;
import com.cgg.twdinspection.engineering_works.source.SectorsEntity;
import com.cgg.twdinspection.engineering_works.source.SectorsResponse;
import com.cgg.twdinspection.engineering_works.source.StagesResponse;
import com.cgg.twdinspection.engineering_works.source.SubmitEngWorksRequest;
import com.cgg.twdinspection.engineering_works.source.WorkDetail;
import com.cgg.twdinspection.engineering_works.viewmodels.InspDetailsCustomViewModel;
import com.cgg.twdinspection.engineering_works.viewmodels.InspDetailsViewModel;
import com.cgg.twdinspection.inspection.ui.LocBaseActivity;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class InspectionDetailsActivity extends LocBaseActivity implements ErrorHandlerInterface {

    ActivityInspDetailsBinding binding;
    ArrayAdapter spinnerAdapter;
    String inspTime, officerName, officerDesg, place,officerId;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InspDetailsViewModel viewModel;
    int sectorsCnt, schemesCnt;
    List<SectorsEntity> sectorsEntities = new ArrayList<>();
    Integer selSectorId = -1, selSchemeId = -1, selWorkProgStageId = -1,physProgRating=-1;
    String selSectorName, selSchemeName, selStageName, selWorkInProgStageName;
    private String overallAppearance, worksmenSkill, qualCare, qualMat, surfaceFinishing, observation, satLevel;
    StagesResponse stagesResponse;
    ArrayList<String> majorStages,tempMajorStages;
    ArrayAdapter majorStagesAdapter,selectAdapter;
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
            editor=sharedPreferences.edit();
            officerName = sharedPreferences.getString(AppConstants.OFFICER_NAME, "");
            officerDesg = sharedPreferences.getString(AppConstants.OFFICER_DES, "");
            inspTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList selectList=new ArrayList();
        selectList.add("Select");
        selectAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, selectList);



        Gson gson = new Gson();
        WorkDetail workDetail = gson.fromJson(sharedPreferences.getString(AppConstants.ENGWORKSMASTER, ""), WorkDetail.class);
        if (workDetail != null) {
            binding.setWorkDetails(workDetail);
        }
        viewModel.getSectors().observe(InspectionDetailsActivity.this, new Observer<List<SectorsEntity>>() {
            @Override
            public void onChanged(List<SectorsEntity> sectorsEntities) {
                InspectionDetailsActivity.this.sectorsEntities = sectorsEntities;
                if (sectorsEntities != null && sectorsEntities.size() > 0) {
                    ArrayList<String> sectorsList = new ArrayList<>();
                    sectorsList.add("Select");
                    for (int i = 0; i < sectorsEntities.size(); i++) {
                        sectorsList.add(sectorsEntities.get(i).getSectorName());
                    }
                    ArrayAdapter spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, sectorsList);
                    binding.spSector.setAdapter(spinnerAdapter);
                } else {
                   callSnackBar("No data found");
                }
            }
        });

        viewModel.getGrantSchemes().observe(InspectionDetailsActivity.this, new Observer<List<GrantScheme>>() {
            @Override
            public void onChanged(List<GrantScheme> grantSchemes) {
                if (grantSchemes != null && grantSchemes.size() > 0) {
                    ArrayList<String> schemesList = new ArrayList<>();
                    schemesList.add("Select");
                    for (int i = 0; i < grantSchemes.size(); i++) {
                        schemesList.add(grantSchemes.get(i).getSchemeName());
                    }
                    ArrayAdapter spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, schemesList);
                    binding.spScheme.setAdapter(spinnerAdapter);
                } else {
                    callSnackBar("No data found");
                }
            }
        });

        majorStages=new ArrayList<>();
        majorStages.add("Not Started");
        majorStages.add("Tender stage");
        majorStages.add("Grounded");
        majorStages.add("In progress");
        majorStages.add("Work Stopped");
        majorStages.add("Completed");
        tempMajorStages=new ArrayList<>();
        tempMajorStages.add("Select");
        tempMajorStages.addAll(majorStages);
        majorStagesAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, tempMajorStages);
        binding.spStage.setAdapter(majorStagesAdapter);

        binding.spStage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int selPos=0;
                if (binding.spStage.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    selStageName = "";
                } else {
                    selStageName = binding.spStage.getSelectedItem().toString();
                    tempMajorStages.clear();
                    for(int z=0;z<majorStages.size();z++){
                        if(majorStages.get(z).equalsIgnoreCase(selStageName)){
                            selPos=z;
                            break;
                        }
                    }
                    for(int z=0;z<majorStages.size();z++){
                        if(z>=selPos){
                            tempMajorStages.add(majorStages.get(z));
                        }
                    }
                    binding.spStage.setAdapter(majorStagesAdapter);
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
                if (binding.spSector.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    selSectorId = -1;
                    selSectorName = "";
                    selWorkProgStageId = -1;
                    physProgRating = -1;
                    selWorkInProgStageName = "";
                    binding.spStageInProgress.setAdapter(selectAdapter);
                    binding.tvSectorOthers.setVisibility(View.GONE);
                    binding.etSectorOthers.setText(null);
                    binding.llStageWork.setVisibility(View.VISIBLE);
                    binding.tvStageOthers.setVisibility(View.GONE);
                    binding.etStageOthers.setText(null);
                } else {
                    viewModel.getSectorId(binding.spSector.getSelectedItem().toString()).observe(InspectionDetailsActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            if (integer != null) {
                                selSectorId = integer;
                                selSectorName = binding.spSector.getSelectedItem().toString();
                                if (selSectorName.equalsIgnoreCase("Others")) {
                                    binding.tvSectorOthers.setVisibility(View.VISIBLE);
                                    binding.llStageWork.setVisibility(View.GONE);
                                    binding.tvStageOthers.setVisibility(View.VISIBLE);
                                    selWorkProgStageId = -1;
                                    physProgRating = -1;
                                    selWorkInProgStageName = "";
                                } else {
                                    binding.tvSectorOthers.setVisibility(View.GONE);
                                    binding.llStageWork.setVisibility(View.VISIBLE);
                                    binding.tvStageOthers.setVisibility(View.GONE);
                                    binding.etSectorOthers.setText(null);
                                    binding.etStageOthers.setText(null);
                                    viewModel.getStagesResponse(selSectorId).observe(InspectionDetailsActivity.this, new Observer<StagesResponse>() {
                                        @Override
                                        public void onChanged(StagesResponse stagesResponse) {
                                            InspectionDetailsActivity.this.stagesResponse = stagesResponse;
                                            if (stagesResponse != null && stagesResponse.getStages().size() > 0) {
                                                stagesList.clear();
                                                stagesList.add("Select");
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

                                }
                            } else {
                                callSnackBar(getString(R.string.something));
                            }
                        }
                    });
                    spinnerAdapter = new ArrayAdapter(InspectionDetailsActivity.this, android.R.layout.simple_spinner_dropdown_item, stagesList);
                    binding.spStageInProgress.setAdapter(spinnerAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (binding.spScheme.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    selSchemeId = -1;
                    selSchemeName = "";
                } else {
                    viewModel.getgrantSchemeId(binding.spScheme.getSelectedItem().toString()).observe(InspectionDetailsActivity.this, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
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
                if (binding.spStageInProgress.getSelectedItem()!=null && binding.spStageInProgress.getSelectedItem().toString().equalsIgnoreCase("Select")) {
                    selWorkProgStageId = -1;
                    physProgRating = -1;
                    selWorkInProgStageName = "";
                } else if(binding.spStageInProgress.getSelectedItem()!=null){
                    selWorkInProgStageName = binding.spStageInProgress.getSelectedItem().toString();
                    if (stagesResponse != null && stagesResponse.getStages().size() > 0) {
                        for (int z = 0; z < stagesResponse.getStages().size(); z++) {
                            if (stagesResponse.getStages().get(z).getStageName().equalsIgnoreCase(selWorkInProgStageName)) {
                                selWorkProgStageId = stagesResponse.getStages().get(z).getStageId();
                                physProgRating = stagesResponse.getStages().get(z).getStageProgressRating();
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

                Location dLocation = new Location("dLoc");
                observation = binding.etObs.getText().toString().trim();
//                if (validate())
                SubmitEngWorksRequest submitEngWorksRequest=new SubmitEngWorksRequest();
                submitEngWorksRequest.setOfficerId(officerId);
                submitEngWorksRequest.setOfficerName(officerName);
                submitEngWorksRequest.setDesignation(officerDesg);
//                submitEngWorksRequest.setPlaceOfWork(officerDesg);
                submitEngWorksRequest.setInspectionTime(inspTime);
                submitEngWorksRequest.setLatitude(String.valueOf(mCurrentLocation.getLatitude()));
                submitEngWorksRequest.setLongitude(String.valueOf(mCurrentLocation.getLongitude()));
                submitEngWorksRequest.setAreaOfOperation(workDetail.getAreaOfOperation());
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
                submitEngWorksRequest.setWorkName(workDetail.getWorkName());
                submitEngWorksRequest.setEstimateCost(Double.valueOf(workDetail.getEstimateCost()));
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
                submitEngWorksRequest.setActExpIncurred(workDetail.getActualExpenditureIncurred());
                submitEngWorksRequest.setFinProgressAchieved(workDetail.getPercentageOfFinancialProgressAchieved());
                submitEngWorksRequest.setOverallExp(overallAppearance);
                submitEngWorksRequest.setWorkmenSkill(worksmenSkill);
                submitEngWorksRequest.setQualCare(qualCare);
                submitEngWorksRequest.setQualMat(qualMat);
                submitEngWorksRequest.setSurfaceFinish(surfaceFinishing);
                submitEngWorksRequest.setObservation(observation);
                submitEngWorksRequest.setSatisfactionLevel(satLevel);
                String request=gson.toJson(submitEngWorksRequest);
                editor.putString(AppConstants.EngSubmitRequest,request);
                editor.commit();
                    startActivity(new Intent(InspectionDetailsActivity.this, UploadEngPhotosActivity.class));
            }
        });
    }

    private boolean validate() {
        if (selSectorId == -1) {
            callSnackBar("Please select sector");
            return false;
        } else if (selSchemeId == -1) {
            callSnackBar("Please select scheme");
            return false;
        } else if (TextUtils.isEmpty(selStageName)) {
            callSnackBar("Please select stage of work");
            return false;
        } else if (selWorkProgStageId == -1) {
            callSnackBar("Please select stage of works for in progress works");
            return false;
        } else if (TextUtils.isEmpty(overallAppearance)) {
            callSnackBar("Please check overall apearance");
            return false;
        } else if (TextUtils.isEmpty(worksmenSkill)) {
            callSnackBar("Please check workmen's skill");
            return false;
        } else if (TextUtils.isEmpty(qualCare)) {
            callSnackBar("Please check quality care");
            return false;
        } else if (TextUtils.isEmpty(qualMat)) {
            callSnackBar("Please check quality of materials");
            return false;
        } else if (TextUtils.isEmpty(surfaceFinishing)) {
            callSnackBar("Please check surface finishes");
            return false;
        } else if (TextUtils.isEmpty(observation)) {
            callSnackBar("Please enter observations");
            return false;
        } else if (TextUtils.isEmpty(satLevel)) {
            callSnackBar("Please check satisfaction level");
            return false;
        }
        return true;
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
