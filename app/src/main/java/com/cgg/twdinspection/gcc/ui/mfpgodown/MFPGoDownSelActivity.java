package com.cgg.twdinspection.gcc.ui.mfpgodown;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityMfpGodownSelBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.mfp.MFPGoDowns;
import com.cgg.twdinspection.gcc.viewmodel.DivisionSelectionViewModel;
import com.cgg.twdinspection.gcc.viewmodel.GCCOfflineViewModel;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.viewmodel.StockViewModel;
import com.cgg.twdinspection.offline.GCCOfflineDataActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MFPGoDownSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GCCOfflineInterface {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityMfpGodownSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedMfpID;
    private List<String> mfpGoDowns;
    private MFPGoDowns selectedMfpGoDowns;
    ArrayAdapter selectAdapter;
    private boolean mfp_flag, emp_flag;
    private GCCOfflineRepository gccOfflineRepository;
    private GCCOfflineViewModel gccOfflineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mfp_godown_sel);
        context = MFPGoDownSelActivity.this;
        mfpGoDowns = new ArrayList<>();
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_mfp_godown));
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MFPGoDownSelActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });
        viewModel = new DivisionSelectionViewModel(getApplication());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            String curTime = Utils.getCurrentDateTimeDisplay();
            editor.putString(AppConstants.INSP_TIME, curTime);
            editor.commit();
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList selectList = new ArrayList();
        selectList.add("Select");
        selectAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, selectList);

        LiveData<List<String>> divisionLiveData = viewModel.getAllDivisions();
        divisionLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> divisions) {
                divisionLiveData.removeObservers(MFPGoDownSelActivity.this);
                customProgressDialog.dismiss();
                if (divisions != null && divisions.size() > 0) {
                    ArrayList<String> divisionNames = new ArrayList<>();
                    divisionNames.add("-Select-");
                    divisionNames.addAll(divisions);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, divisionNames
                    );
                    binding.spDivision.setAdapter(adapter);
                    LiveData<List<MFPGoDowns>> drGodownLiveData = viewModel.getAllMFPGoDowns();
                    drGodownLiveData.observe(MFPGoDownSelActivity.this, new Observer<List<MFPGoDowns>>() {
                        @Override
                        public void onChanged(List<MFPGoDowns> drGodowns) {
                            drGodownLiveData.removeObservers(MFPGoDownSelActivity.this);
                            customProgressDialog.dismiss();
                            if (drGodowns == null || drGodowns.size() <= 0) {
                                Utils.customGCCSyncAlert(MFPGoDownSelActivity.this, getString(R.string.app_name), "No MFP Godowns found...\n Do you want sync MFP Godown master data to proceed further?");
                            }
                        }
                    });
                } else {
                    Utils.customGCCSyncAlert(MFPGoDownSelActivity.this, getString(R.string.app_name), "No divisions found...\n Do you want to sync division master data to proceed further?");
                }
            }
        });


        binding.spDivision.setOnItemSelectedListener(this);
        binding.spMfp.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {

                    editor.putString(AppConstants.StockDetailsResponse, "");
                    editor.commit();

                    LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, "", selectedMfpID);
                    drGodownLiveData.observe(MFPGoDownSelActivity.this, new Observer<GccOfflineEntity>() {
                        @Override
                        public void onChanged(GccOfflineEntity drGodowns) {
                            drGodownLiveData.removeObservers(MFPGoDownSelActivity.this);

                            if (drGodowns != null) {

                                Type listType = new TypeToken<ArrayList<CommonCommodity>>() {
                                }.getType();
                                List<CommonCommodity> mfpCommodities = new Gson().fromJson(drGodowns.getMfpCommodities(), listType);
                                List<CommonCommodity> emptiesCommodities = new Gson().fromJson(drGodowns.getEmpties(), listType);


                                if (mfpCommodities != null && mfpCommodities.size() > 0) {
                                    mfp_flag = true;
                                }
                                if (emptiesCommodities != null && emptiesCommodities.size() > 0) {
                                    emp_flag = true;
                                }

                                if (mfp_flag || emp_flag) {
                                    Gson gson = new Gson();
                                    String mfpData = gson.toJson(selectedMfpGoDowns);
                                    editor.putString(AppConstants.MFP_DEPOT_DATA, mfpData);

                                    StockDetailsResponse stockDetailsResponse = new StockDetailsResponse();
                                    stockDetailsResponse.setMfp_commodities(mfpCommodities);
                                    stockDetailsResponse.setEmpties(emptiesCommodities);

                                    editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                    editor.commit();

                                    startActivity(new Intent(MFPGoDownSelActivity.this, MFPGodownActivity.class));
                                } else {
                                    callSnackBar(getString(R.string.no_comm));
                                }


                            } else {
                                customOnlineAlert("Do you want to proceed in Online mode?");
                            }
                        }
                    });
                }
            }
        });

        binding.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callService(false);
            }
        });

        binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GccOfflineEntity entity = new GccOfflineEntity();
                entity.setDivisionId(selectedDivId);
                entity.setSocietyId("");
                entity.setDrgownId(selectedMfpID);

                gccOfflineRepository.deleteGCCRecord(MFPGoDownSelActivity.this, entity);
            }
        });


        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MFPGoDownSelActivity.this, GCCOfflineDataActivity.class)
                        .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_MFP));
                finish();
            }
        });

    }

    void callService(boolean flag) {
        if (validateFields()) {
            Gson gson = new Gson();
            StockViewModel viewModel = new StockViewModel(getApplication(), MFPGoDownSelActivity.this);
            if (Utils.checkInternetConnection(MFPGoDownSelActivity.this)) {
                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(selectedMfpID);
                officesResponseLiveData.observe(MFPGoDownSelActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(MFPGoDownSelActivity.this);

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {

                                if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                                    mfp_flag = true;
                                }

                                if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                                    emp_flag = true;
                                }


                                if (mfp_flag || emp_flag) {
                                    if (flag) {
                                        Gson gson = new Gson();
                                        String mfpData = gson.toJson(selectedMfpGoDowns);
                                        editor.putString(AppConstants.MFP_DEPOT_DATA, mfpData);
                                        editor.putString(AppConstants.StockDetailsResponse, new Gson().toJson(stockDetailsResponse));
                                        editor.commit();

                                        startActivity(new Intent(context, MFPGodownActivity.class));
                                    } else {
                                        GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
                                        gccOfflineEntity.setDivisionId(selectedDivId);
                                        gccOfflineEntity.setDivisionName(binding.spDivision.getSelectedItem().toString());
                                        gccOfflineEntity.setSocietyId("");
                                        gccOfflineEntity.setSocietyName("");
                                        gccOfflineEntity.setDrgownId(selectedMfpID);
                                        gccOfflineEntity.setDrgownName(binding.spMfp.getSelectedItem().toString());
                                        gccOfflineEntity.setMfpCommodities(gson.toJson(stockDetailsResponse.getMfp_commodities()));
                                        gccOfflineEntity.setEmpties(gson.toJson(stockDetailsResponse.getEmpties()));
                                        gccOfflineEntity.setType(AppConstants.OFFLINE_MFP);

                                        gccOfflineRepository.insertGCCRecord(MFPGoDownSelActivity.this, gccOfflineEntity);
                                    }
                                } else {
                                    callSnackBar(getString(R.string.no_comm));
                                }


                            } else if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.FAILURE_STRING_CODE)) {
                                callSnackBar(getString(R.string.no_comm));
                            } else {
                                callSnackBar(getString(R.string.something));
                            }

                        } else {
                            callSnackBar(getString(R.string.something));
                        }

                    }

                });
            } else {
                Utils.customWarningAlert(MFPGoDownSelActivity.this, getResources().getString(R.string.app_name), "Please check internet");
            }
        }
    }

    void callSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(binding.cl, msg, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    private boolean validateFields() {
        if (TextUtils.isEmpty(selectedDivId)) {
            showSnackBar("Please select division");
            return false;
        } else if (TextUtils.isEmpty(selectedMfpID)) {
            showSnackBar("Please select MFP Godown");
            return false;
        }
        return true;
    }


    private void showSnackBar(String str) {
        Snackbar snackbar = Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        if (adapterView.getId() == R.id.sp_division) {
            binding.llDownload.setVisibility(View.GONE);
            binding.llView.setVisibility(View.GONE);
            selectedMfpGoDowns = null;
            selectedDivId = "";
            selectedMfpID = "";
            binding.spMfp.setAdapter(selectAdapter);
            mfpGoDowns = new ArrayList<>();
            if (position != 0) {
                LiveData<String> liveData = viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
                liveData.observe(MFPGoDownSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(MFPGoDownSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                            LiveData<List<MFPGoDowns>> listLiveData = viewModel.getMFPGoDowns(selectedDivId);
                            listLiveData.observe(MFPGoDownSelActivity.this, new Observer<List<MFPGoDowns>>() {
                                @Override
                                public void onChanged(List<MFPGoDowns> mfpGoDownsList) {
                                    listLiveData.removeObservers(MFPGoDownSelActivity.this);

                                    if (mfpGoDownsList != null && mfpGoDownsList.size() > 0) {
                                        mfpGoDowns.add("--Select--");
                                        for (int i = 0; i < mfpGoDownsList.size(); i++) {
                                            mfpGoDowns.add(mfpGoDownsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, mfpGoDowns);
                                        binding.spMfp.setAdapter(adapter);
                                    } else {
                                        binding.spMfp.setAdapter(selectAdapter);
                                        binding.spMfp.setAdapter(selectAdapter);
                                        showSnackBar("No MFP Godowns found");
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedMfpGoDowns = null;
                selectedDivId = "";
                selectedMfpID = "";
                mfpGoDowns = new ArrayList<>();
                binding.spMfp.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_mfp) {
            if (position != 0) {
                binding.llDownload.setVisibility(View.VISIBLE);
                binding.llView.setVisibility(View.GONE);
                selectedMfpGoDowns = null;
                selectedMfpID = "";
                LiveData<MFPGoDowns> liveData = viewModel.getMFPGoDownID(selectedDivId, binding.spMfp.getSelectedItem().toString());
                liveData.observe(MFPGoDownSelActivity.this, new Observer<MFPGoDowns>() {
                    @Override
                    public void onChanged(MFPGoDowns mfps) {
                        liveData.removeObservers(MFPGoDownSelActivity.this);
                        if (mfps != null) {
                            selectedMfpID = mfps.getGodownId();
                            selectedMfpGoDowns = mfps;

                            LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, "", selectedMfpID);
                            drGodownLiveData.observe(MFPGoDownSelActivity.this, new Observer<GccOfflineEntity>() {
                                @Override
                                public void onChanged(GccOfflineEntity drGodowns) {
                                    drGodownLiveData.removeObservers(MFPGoDownSelActivity.this);

                                    if (drGodowns == null) {
                                        binding.btnDownload.setText(getString(R.string.download));
                                        binding.btnRemove.setVisibility(View.GONE);
                                        binding.btnProceed.setVisibility(View.VISIBLE);
                                    } else {
                                        if (drGodowns.isFlag()) {
                                            binding.llView.setVisibility(View.VISIBLE);
                                            binding.llDownload.setVisibility(View.GONE);
                                            binding.btnProceed.setVisibility(View.GONE);
                                        } else {
                                            binding.llDownload.setVisibility(View.VISIBLE);
                                            binding.llView.setVisibility(View.GONE);
                                            binding.btnDownload.setText(R.string.re_download);
                                            binding.btnRemove.setVisibility(View.VISIBLE);
                                            binding.btnProceed.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }
                            });

                        } else {
                            showSnackBar(getString(R.string.something));
                        }
                    }
                });
            } else {
                binding.llDownload.setVisibility(View.GONE);
                binding.llView.setVisibility(View.GONE);
                selectedMfpGoDowns = null;
                selectedMfpID = "";
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void gccRecCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                binding.btnDownload.setText("Re-Download");
                binding.btnRemove.setVisibility(View.VISIBLE);
                Utils.customSyncSuccessAlert(MFPGoDownSelActivity.this, getResources().getString(R.string.app_name),
                        "Data downloaded successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCount(int cnt) {

        try {
            if (cnt > 0) {
                binding.btnDownload.setText("Download");
                binding.btnRemove.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(MFPGoDownSelActivity.this, getResources().getString(R.string.app_name),
                        "Data deleted successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCountSubmitted(int cnt, String msg) {

    }

    private void customOnlineAlert(String msg) {
        try {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_confirmation);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(getString(R.string.app_name));
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }


                        callService(true);


                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
}
