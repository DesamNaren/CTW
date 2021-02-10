package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityViewMedicalBinding;
import com.cgg.twdinspection.inspection.adapter.GViewMedicalDetailsAdapter;
import com.cgg.twdinspection.inspection.source.medical_and_health.MedicalDetailsBean;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.MedicalDetailsViewModel;

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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    InstMainViewModel instMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, (R.layout.activity_view_medical));
        binding.appBarLayout.backBtn.setVisibility(View.GONE);
        binding.appBarLayout.ivHome.setVisibility(View.GONE);
        binding.appBarLayout.headerTitle.setText(getString(R.string.medical_health));
        sharedPreferences = TWDApplication.get(this).getPreferences();
        editor = sharedPreferences.edit();
        MedicalDetailsViewModel viewModel = new MedicalDetailsViewModel(getApplication());
        binding.setViewModel(viewModel);
        listDataChild = new HashMap<>();
        feverMedicalDetailsBeans = new ArrayList<>();
        coldMedicalDetailsBeans = new ArrayList<>();
        hMedicalDetailsBeans = new ArrayList<>();
        dMedicalDetailsBeans = new ArrayList<>();
        mMedicalDetailsBeans = new ArrayList<>();
        oMedicalDetailsBeans = new ArrayList<>();
        instMainViewModel = new InstMainViewModel(getApplication());

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

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

}
