package com.example.twdinspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.application.TWDApplication;
import com.example.twdinspection.databinding.ProfileLayoutBinding;
import com.example.twdinspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.utils.AppConstants;
import com.example.twdinspection.viewmodel.BasicDetailsViewModel;

import java.util.ArrayList;
import java.util.List;

public class BasicDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    BasicDetailsViewModel viewModel;
    ProfileLayoutBinding profileLayoutBinding;
    private Context context;
    int selectedDistId;
    ArrayList<String> villageNames;
    ArrayList<String> instNames;
    ArrayList<String> mandalNames;
    SharedPreferences sharedPreferences;
    List<InstitutesEntity> institutesEntityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BasicDetailsActivity.this;
        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
        profileLayoutBinding = DataBindingUtil.setContentView(this, R.layout.profile_layout);
        viewModel = new BasicDetailsViewModel(getApplication());
        profileLayoutBinding.setViewModel(viewModel);
        profileLayoutBinding.executePendingBindings();

        mandalNames = new ArrayList<>();
        villageNames = new ArrayList<>();
        instNames = new ArrayList<>();
        institutesEntityList = new ArrayList<>();
        viewModel.getAllDistricts().observe(this, districts -> {
            if (districts != null && districts.size() > 0) {
                ArrayList<String> distNames = new ArrayList<>();
                distNames.add("-Select-");
                for (int i = 0; i < districts.size(); i++) {
                    distNames.add(districts.get(i).getDist_name());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, distNames
                );
                profileLayoutBinding.spDist.setAdapter(adapter);
            }
        });

        profileLayoutBinding.spDist.setOnItemSelectedListener(this);
        profileLayoutBinding.spMandal.setOnItemSelectedListener(this);
        profileLayoutBinding.spVillage.setOnItemSelectedListener(this);
        profileLayoutBinding.spInstitution.setOnItemSelectedListener(this);
        profileLayoutBinding.btnProceed.setOnClickListener(
                view -> startActivity(new Intent(BasicDetailsActivity.this, InfoActivity.class)));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.sp_dist){
            mandalNames.clear();
            mandalNames.add("-Select-");
            if(i!=0){
                viewModel.getDistId(profileLayoutBinding.spDist.getSelectedItem().toString()).observe(BasicDetailsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer!=null){
                            selectedDistId=integer;
                            viewModel.getAllMandals(integer).observe(BasicDetailsActivity.this, mandals -> {
                                if (mandals != null && mandals.size() > 0) {
                                    for (int i = 0; i < mandals.size(); i++) {
                                        mandalNames.add(mandals.get(i).getMandal_name());
                                    }
                                }
                            });
                        }
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, mandalNames
                );
                profileLayoutBinding.spMandal.setAdapter(adapter);
            }else{
                profileLayoutBinding.spMandal.setAdapter(null);
                profileLayoutBinding.spVillage.setAdapter(null);
            }
        }else if(adapterView.getId()==R.id.sp_Mandal) {
            villageNames.clear();
            villageNames.add("-Select-");
            if (i != 0) {
                viewModel.getMandalId(profileLayoutBinding.spMandal.getSelectedItem().toString(), selectedDistId).observe(BasicDetailsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            viewModel.getAllVillages(integer, selectedDistId).observe(BasicDetailsActivity.this, villages -> {
                                if (villages != null && villages.size() > 0) {

                                    for (int i = 0; i < villages.size(); i++) {
                                        villageNames.add(villages.get(i).getVillage_name());
                                    }

                                }
                            });
                        }
                    }
                });
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_dropdown_item, villageNames);
                profileLayoutBinding.spVillage.setAdapter(adapter);
            }else{
                profileLayoutBinding.spVillage.setAdapter(null);
            }
        }
        else if(adapterView.getId()==R.id.sp_village) {
            instNames.clear();
            instNames.add("-Select-");
            if (i != 0) {
                viewModel.getInstitutes().observe(BasicDetailsActivity.this, new Observer<List<InstitutesEntity>>() {
                    @Override
                    public void onChanged(List<InstitutesEntity> institutesEntities) {
                        institutesEntityList.addAll(institutesEntities);
                        if (institutesEntityList != null && institutesEntityList.size()>0) {
                            for(int i=0;i<institutesEntityList.size();i++){
                                instNames.add(institutesEntityList.get(i).getInst_Name());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_dropdown_item, instNames);
                            profileLayoutBinding.spInstitution.setAdapter(adapter);
                        }
                    }
                });
            }else{
                profileLayoutBinding.spInstitution.setAdapter(null);
            }
        }else if(adapterView.getId()==R.id.sp_institution) {
            for(int z=0;z<institutesEntityList.size();z++){
                if(institutesEntityList.get(z).getInst_Name().equals(profileLayoutBinding.spInstitution.getSelectedItem())){
                    sharedPreferences.edit().putString(AppConstants.InstId,institutesEntityList.get(z).getInst_Id())
                            .commit();
                }
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
