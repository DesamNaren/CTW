package com.example.twdinspection.schemes.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.inspection.ui.DashboardActivity;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitRequest;
import com.example.twdinspection.schemes.source.submit.SchemeSubmitResponse;
import com.example.twdinspection.schemes.viewmodel.BenDetailsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class BenDetailsActivity extends BaseActivity {

    ActivityBenDetailsActivtyBinding benDetailsBinding;
    private BenDetailsViewModel viewModel;
    private List<InspectionRemarksEntity> inspectionRemarksEntities;
    private BeneficiaryDetail beneficiaryDetail;
    private String fieldSelVal="";
    private String selectedRemId="";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inspectionRemarksEntities = new ArrayList<>();

        benDetailsBinding = putContentView(R.layout.activity_ben_details_activty, getString(R.string.ben_details));
        try {
             beneficiaryDetail = getIntent().getParcelableExtra(AppConstants.BEN_DETAIL);
            if (beneficiaryDetail != null) {
                viewModel = new BenDetailsViewModel(getApplication(), beneficiaryDetail);
                benDetailsBinding.setViewModel(viewModel);
                benDetailsBinding.executePendingBindings();
            } else {
                //something went wrong ..
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            String officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            beneficiaryDetail.setOfficerId(officerId);
            String insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            beneficiaryDetail.setInspectionTime(insTime);
            String distId = sharedPreferences.getString(AppConstants.SCHEME_DIST_ID, "");
            beneficiaryDetail.setDistId(distId);
            String distName = sharedPreferences.getString(AppConstants.SCHEME_DIST_NAME, "");
            beneficiaryDetail.setDistrictName(distName);
            String manId = sharedPreferences.getString(AppConstants.SCHEME_MAN_ID, "");
            beneficiaryDetail.setMandalId(manId);
            String manName = sharedPreferences.getString(AppConstants.SCHEME_MAN_NAME, "");
            beneficiaryDetail.setMandalName(manName);
            String vilId = sharedPreferences.getString(AppConstants.SCHEME_VIL_ID, "");
            beneficiaryDetail.setVillageId(vilId);
            String vilName = sharedPreferences.getString(AppConstants.SCHEME_VIL_NAME, "");
            beneficiaryDetail.setVillageName(vilName);
            String finId = sharedPreferences.getString(AppConstants.SCHEME_FIN_ID, "");
            beneficiaryDetail.setFinYearId(finId);
            String finValue = sharedPreferences.getString(AppConstants.SCHEME_FIN_YEAR, "");
            beneficiaryDetail.setFinYear(finValue);
        } catch (Exception e) {
            e.printStackTrace();
        }


        viewModel.getRemarks().observe(this, new Observer<List<InspectionRemarksEntity>>() {
            @Override
            public void onChanged(List<InspectionRemarksEntity> inspectionRemarksEntities) {
                if (inspectionRemarksEntities != null && inspectionRemarksEntities.size() > 0) {
                    BenDetailsActivity.this.inspectionRemarksEntities=inspectionRemarksEntities;
                    ArrayList<String> remarks = new ArrayList<>();
                    remarks.add("-Select-");
                    for (int i = 0; i < inspectionRemarksEntities.size(); i++) {
                        remarks.add(inspectionRemarksEntities.get(i).getRemark_type());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(BenDetailsActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, remarks
                    );
                    benDetailsBinding.remarksSP.setAdapter(adapter);
                }
            }
        });




        benDetailsBinding.remarksSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.getRemarkId(benDetailsBinding.remarksSP.getSelectedItem().toString()).observe(
                        BenDetailsActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String value) {
                        if (value != null) {
                            selectedRemId = value;
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        benDetailsBinding.rgEntitlementsProvidedToStudents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_no_entitlements_provided_to_students){
                    fieldSelVal=AppConstants.No;
                    benDetailsBinding.remarksSP.setVisibility(View.VISIBLE);
                }else {
                    fieldSelVal=AppConstants.Yes;
                    benDetailsBinding.remarksSP.setVisibility(View.GONE);
                    benDetailsBinding.remarksSP.setSelection(0);
                    selectedRemId="";
                }
            }
        });

        benDetailsBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(benDetailsBinding.rbYesEntitlementsProvidedToStudents.isChecked()){
                    //Submit Call
                    submitCall(beneficiaryDetail);
                }else  if(benDetailsBinding.rbNoEntitlementsProvidedToStudents.isChecked()){
                    if(inspectionRemarksEntities.size()>0) {
                        if (benDetailsBinding.remarksSP.getSelectedItemPosition() > 0) {
                            //Submit Call
                            submitCall(beneficiaryDetail);
                        } else {
                            Snackbar.make(benDetailsBinding.cl, "Please select remark type", Snackbar.LENGTH_SHORT).show();
                        }
                    }else {
                        Snackbar.make(benDetailsBinding.cl, "Something went wrong", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void submitCall(BeneficiaryDetail beneficiaryDetail) {
        SchemeSubmitRequest schemeSubmitRequest = new SchemeSubmitRequest();
        schemeSubmitRequest.setOfficerId(beneficiaryDetail.getOfficerId());
        schemeSubmitRequest.setInspectionTime(beneficiaryDetail.getInspectionTime());
        schemeSubmitRequest.setDistId(beneficiaryDetail.getDistId());
        schemeSubmitRequest.setDistrictName(beneficiaryDetail.getDistrictName());
        schemeSubmitRequest.setMandalId(beneficiaryDetail.getMandalId());
        schemeSubmitRequest.setMandalName(beneficiaryDetail.getMandalName());
        schemeSubmitRequest.setVillageId(beneficiaryDetail.getVillageId());
        schemeSubmitRequest.setVillageName(beneficiaryDetail.getVillageName());
        schemeSubmitRequest.setFinYearId(beneficiaryDetail.getFinYearId());
        schemeSubmitRequest.setFinYear(beneficiaryDetail.getFinYear());
        schemeSubmitRequest.setBenId(beneficiaryDetail.getBenID());
        schemeSubmitRequest.setBenName(beneficiaryDetail.getBenName());
        schemeSubmitRequest.setActivity(beneficiaryDetail.getActivity());
        schemeSubmitRequest.setSchemeId(beneficiaryDetail.getSchemeId());
        schemeSubmitRequest.setSchemeType(beneficiaryDetail.getSchemeType());
        schemeSubmitRequest.setUnitCost(beneficiaryDetail.getUnitCost());
        schemeSubmitRequest.setSubsidy(beneficiaryDetail.getSubsidy());
        schemeSubmitRequest.setBankLoan(beneficiaryDetail.getBankLoan());
        schemeSubmitRequest.setContribution(beneficiaryDetail.getContribution());
        schemeSubmitRequest.setStatusId(beneficiaryDetail.getStatus());
        schemeSubmitRequest.setStatusValue(beneficiaryDetail.getStatusValue());
        schemeSubmitRequest.setStatusFieldMatch(fieldSelVal);
        schemeSubmitRequest.setRemarksId(String.valueOf(selectedRemId));
        schemeSubmitRequest.setRemarksType(benDetailsBinding.remarksSP.getSelectedItem().toString());

        viewModel.getSchemeSubmitResponse(schemeSubmitRequest).observe(this, new Observer<SchemeSubmitResponse>() {
            @Override
            public void onChanged(SchemeSubmitResponse schemeSubmitResponse) {
                if(schemeSubmitResponse.getStatusCode()!=null && schemeSubmitResponse.getStatusCode().equals(AppConstants.SUCCESS_CODE)){
                  //call to photo upload
                    Snackbar.make(benDetailsBinding.cl, schemeSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                }else {
                    Snackbar.make(benDetailsBinding.cl, schemeSubmitResponse.getStatusMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
