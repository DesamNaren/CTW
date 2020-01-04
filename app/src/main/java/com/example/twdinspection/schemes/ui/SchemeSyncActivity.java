package com.example.twdinspection.schemes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivitySchemeSyncBinding;
import com.example.twdinspection.inspection.ui.BaseActivity;
import com.example.twdinspection.schemes.interfaces.SchemeDMVInterface;
import com.example.twdinspection.schemes.room.repository.SchemeSyncRepository;
import com.example.twdinspection.schemes.source.DMV.SchemeDMVResponse;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;
import com.example.twdinspection.schemes.source.finyear.FinancialYearResponse;
import com.example.twdinspection.schemes.viewmodel.SchemeSyncViewModel;

import java.util.List;

public class SchemeSyncActivity extends BaseActivity implements SchemeDMVInterface {
    private SchemeSyncRepository schemeSyncRepository;
    private SchemeDMVResponse schemeDMVResponse;
    private FinancialYearResponse financialYearResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySchemeSyncBinding binding = putContentView(R.layout.activity_scheme_sync, getResources().getString(R.string.general_info));

        SchemeSyncViewModel viewModel = new SchemeSyncViewModel(getApplication());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        schemeSyncRepository = new SchemeSyncRepository(getApplication());

        binding.syncMandals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewModel.getSchemeReposnse().observe(SchemeSyncActivity.this, new Observer<SchemeDMVResponse>() {
                   @Override
                   public void onChanged(SchemeDMVResponse schemeDMVResponse) {
                       SchemeSyncActivity.this.schemeDMVResponse=schemeDMVResponse;
                       if (schemeDMVResponse.getDistricts() != null && schemeDMVResponse.getDistricts().size() > 0) {
                           schemeSyncRepository.insertSchemeDistricts(SchemeSyncActivity.this, schemeDMVResponse.getDistricts());
                       }
                   }
               });
            }
        });

        binding.syncYears.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               viewModel.getFinYearResponse().observe(SchemeSyncActivity.this, new Observer<FinancialYearResponse>() {
                   @Override
                   public void onChanged(FinancialYearResponse financialYearResponse) {
                       SchemeSyncActivity.this.financialYearResponse=financialYearResponse;
                       if (financialYearResponse.getFinYears() != null && financialYearResponse.getFinYears().size() > 0) {
                           schemeSyncRepository.insertFinYears(SchemeSyncActivity.this, financialYearResponse.getFinYears());
                       }
                   }
               });
            }
        });
    }

    @Override
    public void distCount(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("D_CNT", "distCount: "+cnt);
                schemeSyncRepository.insertSchemeMandals(SchemeSyncActivity.this, schemeDMVResponse.getMandals());
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
                schemeSyncRepository.insertSchemeVillages(SchemeSyncActivity.this, schemeDMVResponse.getVillages());
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
                startActivity(new Intent(SchemeSyncActivity.this, SchemesDMVActivity.class));
                // Success Alert;
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finYear(int cnt) {
        try {
            if (cnt > 0) {
                Log.i("F_CNT", "finCount: "+cnt);
                startActivity(new Intent(SchemeSyncActivity.this, SchemesDMVActivity.class));
                // Success Alert;
            } else {
                // onDataNotAvailable();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
