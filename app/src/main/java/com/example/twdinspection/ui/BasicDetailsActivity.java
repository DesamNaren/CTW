package com.example.twdinspection.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.application.TWDApplication;
import com.example.twdinspection.databinding.ProfileLayoutBinding;
import com.example.twdinspection.source.GeneralInformation.InstitutesEntity;
import com.example.twdinspection.utils.AppConstants;
import com.example.twdinspection.utils.Utils;
import com.example.twdinspection.viewmodel.BasicDetailsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class BasicDetailsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    BasicDetailsViewModel viewModel;
    ProfileLayoutBinding profileLayoutBinding;
    private Context context;
    int selectedDistId, selectedManId, selectedVilId;
    String selectedInstId;
    ArrayList<String> villageNames;
    ArrayList<String> instNames;
    ArrayList<String> mandalNames;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<InstitutesEntity> institutesEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = BasicDetailsActivity.this;

        profileLayoutBinding = putContentView(R.layout.profile_layout, getResources().getString(R.string.general_info));

        viewModel = new BasicDetailsViewModel(getApplication());
        profileLayoutBinding.setViewModel(viewModel);
        profileLayoutBinding.executePendingBindings();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            profileLayoutBinding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            profileLayoutBinding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTime();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            profileLayoutBinding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        profileLayoutBinding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields())
                    startActivity(new Intent(BasicDetailsActivity.this, InfoActivity.class));
            }
        });
    }

    private boolean validateFields() {


        if (selectedDistId == 0) {
            showSnackBar("Please select district");
            return false;
        } else if (selectedManId == 0) {
            showSnackBar("Please select mandal");
            return false;
        } else if (selectedVilId == 0) {
            showSnackBar("Please select village");
            return false;
        }else if (TextUtils.isEmpty(selectedInstId)) {
            showSnackBar("Please select institute");
            return false;
        }
        return true;
    }

    private void showSnackBar(String str) {
        Snackbar snackbar = Snackbar.make(profileLayoutBinding.cl, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.sp_dist) {
            mandalNames.clear();
            mandalNames.add("-Select-");
            if (i != 0) {
                viewModel.getDistId(profileLayoutBinding.spDist.getSelectedItem().toString()).observe(BasicDetailsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            selectedDistId = integer;
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
            } else {
                selectedDistId =0; selectedManId=0; selectedVilId=0;
                profileLayoutBinding.spMandal.setAdapter(null);
                profileLayoutBinding.spVillage.setAdapter(null);
                profileLayoutBinding.spInstitution.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_Mandal) {
            villageNames.clear();
            villageNames.add("-Select-");
            if (i != 0) {

                viewModel.getMandalId(profileLayoutBinding.spMandal.getSelectedItem().toString(), selectedDistId).observe(BasicDetailsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            selectedManId = integer;
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
            } else {
                selectedManId=0; selectedVilId=0;
                profileLayoutBinding.spVillage.setAdapter(null);
                profileLayoutBinding.spInstitution.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_village) {
            instNames.clear();
            instNames.add("-Select-");
            if (i != 0) {

                viewModel.getVillageId(profileLayoutBinding.spVillage.getSelectedItem().toString(), selectedManId, selectedDistId).observe(BasicDetailsActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer != null) {
                            selectedVilId = integer;
                        }
                    }
                });
                viewModel.getInstitutes().observe(BasicDetailsActivity.this, new Observer<List<InstitutesEntity>>() {
                    @Override
                    public void onChanged(List<InstitutesEntity> institutesEntities) {
                        institutesEntityList.addAll(institutesEntities);
                        if (institutesEntityList != null && institutesEntityList.size() > 0) {
                            for (int i = 0; i < institutesEntityList.size(); i++) {
                                instNames.add(institutesEntityList.get(i).getInst_Name());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_dropdown_item, instNames);
                            profileLayoutBinding.spInstitution.setAdapter(adapter);
                        }
                    }
                });
            } else {
                selectedVilId=0; selectedInstId="";
                profileLayoutBinding.spInstitution.setAdapter(null);
            }
        } else if (adapterView.getId() == R.id.sp_institution) {
            if (i != 0) {
                viewModel.getInstId(profileLayoutBinding.spInstitution.getSelectedItem().toString()).observe(BasicDetailsActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String inst_id) {
                        if (inst_id != null) {
                            selectedInstId = inst_id;
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
