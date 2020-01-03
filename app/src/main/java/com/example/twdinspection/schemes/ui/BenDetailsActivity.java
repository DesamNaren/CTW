package com.example.twdinspection.schemes.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;

import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.databinding.ActivityBenDetailsActivtyBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.schemes.source.InspectionRemarksEntity;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.viewmodel.BenDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class BenDetailsActivity extends BaseActivity {

    ActivityBenDetailsActivtyBinding benDetailsBinding;
    private BenDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        benDetailsBinding = putContentView(R.layout.activity_ben_details_activty, getString(R.string.ben_report));
        try {
            BeneficiaryDetail beneficiaryDetail = getIntent().getParcelableExtra(AppConstants.BEN_DETAIL);
            if (beneficiaryDetail != null) {
                viewModel = new BenDetailsViewModel(getApplication(), beneficiaryDetail);
                benDetailsBinding.setViewModel(viewModel);
                benDetailsBinding.executePendingBindings();
            } else {
                //something went wrong
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        viewModel.getRemarks().observe(this, new Observer<List<InspectionRemarksEntity>>() {
            @Override
            public void onChanged(List<InspectionRemarksEntity> inspectionRemarksEntities) {
                if (inspectionRemarksEntities != null && inspectionRemarksEntities.size() > 0) {
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

        benDetailsBinding.rgEntitlementsProvidedToStudents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rb_no_entitlements_provided_to_students){
                    benDetailsBinding.remarksSP.setVisibility(View.VISIBLE);
                }else {
                    benDetailsBinding.remarksSP.setVisibility(View.GONE);
                }
            }
        });
    }
}
