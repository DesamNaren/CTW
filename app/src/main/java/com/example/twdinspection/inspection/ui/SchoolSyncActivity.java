package com.example.twdinspection.inspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.example.twdinspection.databinding.ActivitySchoolSyncBinding;
import com.example.twdinspection.inspection.Room.repository.SchoolSyncRepository;
import com.example.twdinspection.inspection.interfaces.SchoolDMVInterface;
import com.example.twdinspection.inspection.source.dmv.SchoolDMVResponse;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.example.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.example.twdinspection.schemes.room.repository.SchemeSyncRepository;
import com.example.twdinspection.schemes.source.DMV.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.source.remarks.InspectionRemarkResponse;
import com.example.twdinspection.schemes.source.schemes.SchemeEntity;
import com.example.twdinspection.schemes.source.schemes.SchemeResponse;
import com.example.twdinspection.schemes.ui.SchemesDMVActivity;
import com.example.twdinspection.schemes.viewmodel.SchemeSyncViewModel;
import com.example.twdinspection.schemes.viewmodel.SchoolSyncViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SchoolSyncActivity extends AppCompatActivity implements SchoolDMVInterface, ErrorHandlerInterface {
    private SchoolSyncRepository schoolSyncRepository;
    private SchoolDMVResponse schoolDMVResponse;
    ActivitySchoolSyncBinding binding;
    SharedPreferences sharedPreferences;
    private String officerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(SchoolSyncActivity.this,R.layout.activity_school_sync);

        SchoolSyncViewModel viewModel = new SchoolSyncViewModel(SchoolSyncActivity.this,getApplication(),binding);
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        schoolSyncRepository = new SchoolSyncRepository(getApplication());
        binding.header.headerTitle.setText(getResources().getString(R.string.sync_school_activity));

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.something), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.btnDmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveData<SchoolDMVResponse> schoolDMVResponseLiveData= viewModel.getSchoolDMVReposnse(officerId);
                schoolDMVResponseLiveData.observe(SchoolSyncActivity.this, new Observer<SchoolDMVResponse>() {
                    @Override
                    public void onChanged(SchoolDMVResponse schoolDMVResponse) {
                        schoolDMVResponseLiveData.removeObservers(SchoolSyncActivity.this);
                        SchoolSyncActivity.this.schoolDMVResponse=schoolDMVResponse;
                        if (schoolDMVResponse.getDistricts() != null && schoolDMVResponse.getDistricts().size() > 0) {
                            schoolSyncRepository.insertSchoolDistricts(SchoolSyncActivity.this, schoolDMVResponse.getDistricts());
                        }
                    }
                });
            }
        });
    }

    void callSnackBar(String msg){
        Snackbar snackbar = Snackbar.make(binding.root, msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SchoolSyncActivity.this,DMVSelectionActivity.class));
    }

    @Override
    public void handleError(Throwable e, Context context) {
        String errMsg = ErrorHandler.handleError(e, context);
        callSnackBar(errMsg);
    }

    @Override
    public void distCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("D_CNT", "distCount: "+cnt);
                schoolSyncRepository.insertSchoolMandals(SchoolSyncActivity.this, schoolDMVResponse.getMandals());
            } else {
               // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void manCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("M_CNT", "manCount: "+cnt);
                schoolSyncRepository.insertSchoolVillages(SchoolSyncActivity.this, schoolDMVResponse.getVillages());
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void vilCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("V_CNT", "vilCount: "+cnt);
                binding.progress.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(SchoolSyncActivity.this,getResources().getString(R.string.app_name),
                        "District master synced successfully");
                // Success Alert;
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
