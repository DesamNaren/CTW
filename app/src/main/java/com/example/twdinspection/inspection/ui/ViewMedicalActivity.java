package com.example.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityViewMedicalBinding;
import com.example.twdinspection.inspection.adapter.GViewMedicalDetailsAdapter;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.MedicalDetailsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewMedicalActivity extends AppCompatActivity {

    private ActivityViewMedicalBinding binding;
    private GViewMedicalDetailsAdapter GViewMedicalDetailsAdapter;
    HashMap<String, List<MedicalDetailsBean>> listDataChild;
    private List<MedicalDetailsBean> feverMedicalDetailsBeans;
    private List<MedicalDetailsBean> coldMedicalDetailsBeans;
    private List<MedicalDetailsBean> hMedicalDetailsBeans;
    private List<MedicalDetailsBean> dMedicalDetailsBeans;
    private List<MedicalDetailsBean> mMedicalDetailsBeans;
    private List<MedicalDetailsBean> oMedicalDetailsBeans;
    private String cacheDate, currentDate;
    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, (R.layout.activity_view_medical));
        binding.appBarLayout.backBtn.setVisibility(View.GONE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.medical_health));
        sharedPreferences = TWDApplication.get(this).getPreferences();
        MedicalDetailsViewModel viewModel = new MedicalDetailsViewModel(getApplication());
        binding.setViewModel(viewModel);
        listDataChild = new HashMap<>();
        feverMedicalDetailsBeans = new ArrayList<>();
        coldMedicalDetailsBeans = new ArrayList<>();
        hMedicalDetailsBeans = new ArrayList<>();
        dMedicalDetailsBeans = new ArrayList<>();
        mMedicalDetailsBeans = new ArrayList<>();
        oMedicalDetailsBeans = new ArrayList<>();
        instMainViewModel=new InstMainViewModel(getApplication());

        viewModel.getMedicalDetails().observe(ViewMedicalActivity.this, new Observer<List<MedicalDetailsBean>>() {
            @Override
            public void onChanged(List<MedicalDetailsBean> medicalDetailsBeans) {
                if (medicalDetailsBeans != null && medicalDetailsBeans.size() > 0) {
                    binding.noDataTv.setVisibility(View.GONE);
                    binding.medicalRv.setVisibility(View.VISIBLE);


                    for (int x = 0; x < medicalDetailsBeans.size(); x++) {
                        if (medicalDetailsBeans.get(x).getType().equals("1")) {
                            feverMedicalDetailsBeans.add(medicalDetailsBeans.get(x));
                            listDataChild.put(medicalDetailsBeans.get(x).getType(), feverMedicalDetailsBeans);
                        }

                        if (medicalDetailsBeans.get(x).getType().equals("2")) {
                            coldMedicalDetailsBeans.add(medicalDetailsBeans.get(x));
                            listDataChild.put(medicalDetailsBeans.get(x).getType(), coldMedicalDetailsBeans);
                        }

                        if (medicalDetailsBeans.get(x).getType().equals("3")) {
                            hMedicalDetailsBeans.add(medicalDetailsBeans.get(x));
                            listDataChild.put(medicalDetailsBeans.get(x).getType(), hMedicalDetailsBeans);
                        }


                        if (medicalDetailsBeans.get(x).getType().equals("4")) {
                            dMedicalDetailsBeans.add(medicalDetailsBeans.get(x));
                            listDataChild.put(medicalDetailsBeans.get(x).getType(), dMedicalDetailsBeans);
                        }


                        if (medicalDetailsBeans.get(x).getType().equals("5")) {
                            mMedicalDetailsBeans.add(medicalDetailsBeans.get(x));
                            listDataChild.put(medicalDetailsBeans.get(x).getType(), mMedicalDetailsBeans);
                        }


                        if (medicalDetailsBeans.get(x).getType().equals("6")) {
                            oMedicalDetailsBeans.add(medicalDetailsBeans.get(x));
                            listDataChild.put(medicalDetailsBeans.get(x).getType(), oMedicalDetailsBeans);
                        }
                    }


                    GViewMedicalDetailsAdapter = new GViewMedicalDetailsAdapter(ViewMedicalActivity.this, listDataChild);
                    binding.medicalRv.setLayoutManager(new LinearLayoutManager(ViewMedicalActivity.this));
                    binding.medicalRv.setAdapter(GViewMedicalDetailsAdapter);
                } else {
                    binding.noDataTv.setVisibility(View.VISIBLE);
                    binding.medicalRv.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            boolean isAutomatic = Utils.isTimeAutomatic(this);
            if (!isAutomatic) {
                Utils.customTimeAlert(this,
                        getResources().getString(R.string.app_name),
                        getString(R.string.date_time));
                return;
            }

            currentDate = Utils.getCurrentDate();
            cacheDate = sharedPreferences.getString(AppConstants.CACHE_DATE, "");

            if (!TextUtils.isEmpty(cacheDate)) {
                if (!cacheDate.equalsIgnoreCase(currentDate)) {
                    instMainViewModel.deleteAllInspectionData();
                    Utils.ShowDeviceSessionAlert(this,
                            getResources().getString(R.string.app_name),
                            getString(R.string.ses_expire_re));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cacheDate = currentDate;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AppConstants.CACHE_DATE, cacheDate);
        editor.commit();
    }
}
