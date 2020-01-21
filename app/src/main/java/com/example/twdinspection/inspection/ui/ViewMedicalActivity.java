package com.example.twdinspection.inspection.ui;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityViewMedicalBinding;
import com.example.twdinspection.inspection.adapter.GViewMedicalDetailsAdapter;
import com.example.twdinspection.inspection.source.MedicalDetailsBean;
import com.example.twdinspection.inspection.viewmodel.MedicalDetailsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ViewMedicalActivity extends BaseActivity {

    private ActivityViewMedicalBinding binding;
    private GViewMedicalDetailsAdapter GViewMedicalDetailsAdapter;
    HashMap<String, List<MedicalDetailsBean>> listDataChild;
    private List<MedicalDetailsBean> feverMedicalDetailsBeans;
    private List<MedicalDetailsBean> coldMedicalDetailsBeans;
    private List<MedicalDetailsBean> hMedicalDetailsBeans;
    private List<MedicalDetailsBean> dMedicalDetailsBeans;
    private List<MedicalDetailsBean> mMedicalDetailsBeans;
    private List<MedicalDetailsBean> oMedicalDetailsBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_view_medical, getResources().getString(R.string.call_health));
        MedicalDetailsViewModel viewModel = new MedicalDetailsViewModel(getApplication());
        binding.setViewModel(viewModel);
        listDataChild = new HashMap<>();
        feverMedicalDetailsBeans = new ArrayList<>();
        coldMedicalDetailsBeans = new ArrayList<>();
        hMedicalDetailsBeans = new ArrayList<>();
        dMedicalDetailsBeans = new ArrayList<>();
        mMedicalDetailsBeans = new ArrayList<>();
        oMedicalDetailsBeans = new ArrayList<>();

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

}
