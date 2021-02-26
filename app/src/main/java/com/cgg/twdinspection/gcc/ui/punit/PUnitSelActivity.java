package com.cgg.twdinspection.gcc.ui.punit;

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
import com.cgg.twdinspection.databinding.ActivityPUnitSelBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCOfflineInterface;
import com.cgg.twdinspection.gcc.room.repository.GCCOfflineRepository;
import com.cgg.twdinspection.gcc.source.divisions.DivisionsInfo;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.cgg.twdinspection.gcc.source.stock.StockDetailsResponse;
import com.cgg.twdinspection.gcc.source.suppliers.punit.PUnits;
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

public class PUnitSelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GCCOfflineInterface {
    private SharedPreferences.Editor editor;
    CustomProgressDialog customProgressDialog;
    ActivityPUnitSelBinding binding;
    private Context context;
    private DivisionSelectionViewModel viewModel;
    private String selectedDivId, selectedSocietyId, selectedPUnitID;
    private List<DivisionsInfo> divisionsInfos;
    private List<String> societies;
    private List<String> pUnits;
    private PUnits selectedPUnits;
    private ArrayAdapter<String> selectAdapter;
    private boolean dailyreq_flag, emp_flag, ess_flag, p_unit_flag, mfp_flag;
    private GCCOfflineRepository gccOfflineRepository;
    private GCCOfflineViewModel gccOfflineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_p_unit_sel);
        context = PUnitSelActivity.this;
        divisionsInfos = new ArrayList<>();
        societies = new ArrayList<>();
        pUnits = new ArrayList<>();
        customProgressDialog = new CustomProgressDialog(context);
        binding.header.headerTitle.setText(getResources().getString(R.string.gcc_p_unit));
        gccOfflineRepository = new GCCOfflineRepository(getApplication());
        gccOfflineViewModel = new GCCOfflineViewModel(getApplication());
        binding.header.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.header.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PUnitSelActivity.this, DashboardMenuActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();

            }
        });

        viewModel = new DivisionSelectionViewModel(getApplication());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        try {
            SharedPreferences sharedPreferences = TWDApplication.get(this).getPreferences();
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
        ArrayList<String> selectList = new ArrayList<>();
        selectList.add("Select");
        selectAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, selectList);


        LiveData<List<String>> divisionLiveData = viewModel.getAllDivisions();
        divisionLiveData.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> divisions) {
                divisionLiveData.removeObservers(PUnitSelActivity.this);
                customProgressDialog.dismiss();
                if (divisions != null && divisions.size() > 0) {
                    ArrayList<String> divisionNames = new ArrayList<>();
                    divisionNames.add("-Select-");
                    divisionNames.addAll(divisions);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                            android.R.layout.simple_spinner_dropdown_item, divisionNames
                    );
                    binding.spDivision.setAdapter(adapter);
                    LiveData<List<PUnits>> drGodownLiveData = viewModel.getAllPUnits();
                    drGodownLiveData.observe(PUnitSelActivity.this, new Observer<List<PUnits>>() {
                        @Override
                        public void onChanged(List<PUnits> drGodowns) {
                            drGodownLiveData.removeObservers(PUnitSelActivity.this);
                            customProgressDialog.dismiss();
                            if (drGodowns == null || drGodowns.size() <= 0) {
                                Utils.customGCCSyncAlert(PUnitSelActivity.this, getString(R.string.app_name), getString(R.string.no_punit_sync));
                            }
                        }
                    });
                } else {
                    Utils.customGCCSyncAlert(PUnitSelActivity.this, getString(R.string.app_name), getString(R.string.no_div_sync));
                }
            }
        });


        binding.spDivision.setOnItemSelectedListener(this);
        binding.spSociety.setOnItemSelectedListener(this);
        binding.spPUnit.setOnItemSelectedListener(this);
        binding.btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {

                    editor.putString(AppConstants.StockDetailsResponse, "");
                    editor.commit();

                    LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectedPUnitID);
                    drGodownLiveData.observe(PUnitSelActivity.this, new Observer<GccOfflineEntity>() {
                        @Override
                        public void onChanged(GccOfflineEntity drGodowns) {
                            drGodownLiveData.removeObservers(PUnitSelActivity.this);

                            if (drGodowns != null) {

                                Type listType = new TypeToken<ArrayList<CommonCommodity>>() {
                                }.getType();
                                List<CommonCommodity> essestinalCommodities = new Gson().fromJson(drGodowns.getEssentials(), listType);
                                List<CommonCommodity> dailyReqCommodities = new Gson().fromJson(drGodowns.getDailyReq(), listType);
                                List<CommonCommodity> emptiesCommodities = new Gson().fromJson(drGodowns.getEmpties(), listType);
                                List<CommonCommodity> mfpCommodities = new Gson().fromJson(drGodowns.getMfpCommodities(), listType);
                                List<CommonCommodity> pUnitCommodities = new Gson().fromJson(drGodowns.getProcessingUniit(), listType);


                                if (essestinalCommodities != null && essestinalCommodities.size() > 0) {
                                    ess_flag = true;
                                }
                                if (dailyReqCommodities != null && dailyReqCommodities.size() > 0) {
                                    dailyreq_flag = true;
                                }
                                if (emptiesCommodities != null && emptiesCommodities.size() > 0) {
                                    emp_flag = true;
                                }
                                if (mfpCommodities != null && mfpCommodities.size() > 0) {
                                    mfp_flag = true;
                                }
                                if (pUnitCommodities != null && pUnitCommodities.size() > 0) {
                                    p_unit_flag = true;
                                }


                                if (ess_flag || dailyreq_flag || emp_flag || mfp_flag || p_unit_flag) {
                                    Gson gson = new Gson();
                                    String pUnitData = gson.toJson(selectedPUnits);
                                    editor.putString(AppConstants.P_UNIT_DATA, pUnitData);

                                    StockDetailsResponse stockDetailsResponse = new StockDetailsResponse();
                                    stockDetailsResponse.setEssential_commodities(essestinalCommodities);
                                    stockDetailsResponse.setDialy_requirements(dailyReqCommodities);
                                    stockDetailsResponse.setEmpties(emptiesCommodities);
                                    stockDetailsResponse.setMfp_commodities(mfpCommodities);
                                    stockDetailsResponse.setProcessing_units(pUnitCommodities);

                                    editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                    editor.commit();

                                    startActivity(new Intent(PUnitSelActivity.this, PUnitActivity.class));
                                } else {
                                    callSnackBar(getString(R.string.no_comm));
                                }


                            } else {
                                customOnlineAlert(getString(R.string.do_you_want_online_mode));
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
                entity.setSocietyId(selectedSocietyId);
                entity.setDrgownId(selectedPUnitID);

                gccOfflineRepository.deleteGCCRecord(PUnitSelActivity.this, entity);
            }
        });
        binding.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PUnitSelActivity.this, GCCOfflineDataActivity.class)
                        .putExtra(AppConstants.FROM_CLASS, AppConstants.OFFLINE_P_UNIT));
                finish();
            }
        });
    }


    void callService(boolean flag) {
        if (validateFields()) {
            Gson gson = new Gson();
            StockViewModel viewModel = new StockViewModel(getApplication(), PUnitSelActivity.this);
            if (Utils.checkInternetConnection(PUnitSelActivity.this)) {
                customProgressDialog.show();
                LiveData<StockDetailsResponse> officesResponseLiveData = viewModel.getStockData(selectedPUnitID);
                officesResponseLiveData.observe(PUnitSelActivity.this, new Observer<StockDetailsResponse>() {
                    @Override
                    public void onChanged(StockDetailsResponse stockDetailsResponse) {

                        customProgressDialog.hide();
                        officesResponseLiveData.removeObservers(PUnitSelActivity.this);

                        if (stockDetailsResponse != null && stockDetailsResponse.getStatusCode() != null) {
                            if (stockDetailsResponse.getStatusCode().equalsIgnoreCase(AppConstants.SUCCESS_STRING_CODE)) {

                                if (stockDetailsResponse.getEssential_commodities() != null && stockDetailsResponse.getEssential_commodities().size() > 0) {
                                    ess_flag = true;
                                }

                                if (stockDetailsResponse.getDialy_requirements() != null && stockDetailsResponse.getDialy_requirements().size() > 0) {
                                    dailyreq_flag = true;
                                }

                                if (stockDetailsResponse.getEmpties() != null && stockDetailsResponse.getEmpties().size() > 0) {
                                    emp_flag = true;
                                }

                                if (stockDetailsResponse.getMfp_commodities() != null && stockDetailsResponse.getMfp_commodities().size() > 0) {
                                    mfp_flag = true;
                                }

                                if (stockDetailsResponse.getProcessing_units() != null && stockDetailsResponse.getProcessing_units().size() > 0) {
                                    p_unit_flag = true;
                                }

                                if (ess_flag || dailyreq_flag || emp_flag || mfp_flag || p_unit_flag) {
                                    if (flag) {
                                        Gson gson = new Gson();
                                        String pUnitData = gson.toJson(selectedPUnits);
                                        editor.putString(AppConstants.P_UNIT_DATA, pUnitData);
                                        editor.putString(AppConstants.StockDetailsResponse, gson.toJson(stockDetailsResponse));
                                        editor.commit();

                                        startActivity(new Intent(context, PUnitActivity.class));
                                    } else {
                                        GccOfflineEntity gccOfflineEntity = new GccOfflineEntity();
                                        gccOfflineEntity.setDivisionId(selectedDivId);
                                        gccOfflineEntity.setDivisionName(binding.spDivision.getSelectedItem().toString());
                                        gccOfflineEntity.setSocietyId(selectedSocietyId);
                                        gccOfflineEntity.setSocietyName(binding.spSociety.getSelectedItem().toString());
                                        gccOfflineEntity.setDrgownId(selectedPUnitID);
                                        gccOfflineEntity.setDrgownName(binding.spPUnit.getSelectedItem().toString());
                                        gccOfflineEntity.setEssentials(gson.toJson(stockDetailsResponse.getEssential_commodities()));
                                        gccOfflineEntity.setDailyReq(gson.toJson(stockDetailsResponse.getDialy_requirements()));
                                        gccOfflineEntity.setEmpties(gson.toJson(stockDetailsResponse.getEmpties()));
                                        gccOfflineEntity.setType(AppConstants.OFFLINE_P_UNIT);

                                        gccOfflineRepository.insertGCCRecord(PUnitSelActivity.this, gccOfflineEntity);
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
                Utils.customWarningAlert(PUnitSelActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
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
        }

        else if (TextUtils.isEmpty(selectedPUnitID)) {
            showSnackBar("Please select processing unit");
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
            selectedPUnits = null;
            selectedSocietyId = "";
            selectedDivId = "";
            selectedPUnitID = "";
            divisionsInfos = new ArrayList<>();
            pUnits = new ArrayList<>();
            binding.spPUnit.setAdapter(selectAdapter);
            societies = new ArrayList<>();
            societies.add("--Select--");
            if (position != 0) {
                LiveData<String> liveData = viewModel.getDivisionId(binding.spDivision.getSelectedItem().toString());
                liveData.observe(PUnitSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(PUnitSelActivity.this);
                        if (str != null) {
                            selectedDivId = str;
                            LiveData<List<DivisionsInfo>> listLiveData = viewModel.getSocieties(selectedDivId);
                            listLiveData.observe(PUnitSelActivity.this, new Observer<List<DivisionsInfo>>() {
                                @Override
                                public void onChanged(List<DivisionsInfo> divisionsInfoList) {
                                    listLiveData.removeObservers(PUnitSelActivity.this);
                                    divisionsInfos.addAll(divisionsInfoList);
                                    if (divisionsInfos != null && divisionsInfos.size() > 0) {
                                        for (int i = 0; i < divisionsInfos.size(); i++) {
                                            if (!TextUtils.isEmpty(divisionsInfos.get(i).getSocietyName()))
                                                societies.add(divisionsInfos.get(i).getSocietyName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, societies);
                                        binding.spSociety.setAdapter(adapter);
                                    } else {
                                        binding.spSociety.setAdapter(selectAdapter);
                                        showSnackBar(getString(R.string.no_soc_found));
                                    }
                                }
                            });

                            LiveData<List<PUnits>> godownslistLiveData = viewModel.getPUnits(selectedDivId);
                            godownslistLiveData.observe(PUnitSelActivity.this, new Observer<List<PUnits>>() {
                                @Override
                                public void onChanged(List<PUnits> godownsList) {
                                    godownslistLiveData.removeObservers(PUnitSelActivity.this);
                                    if (godownsList != null && godownsList.size() > 0) {
                                        pUnits.add("-Select-");
                                        for (int i = 0; i < godownsList.size(); i++) {
                                            pUnits.add(godownsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, pUnits);
                                        binding.spPUnit.setAdapter(adapter);
                                    } else {
                                        binding.spPUnit.setAdapter(selectAdapter);
                                        showSnackBar(getString(R.string.no_p_unit_found));
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedPUnits = null;
                selectedDivId = "";
                selectedSocietyId = "";
                binding.spSociety.setAdapter(selectAdapter);
                selectedPUnitID = "";
                binding.spPUnit.setAdapter(selectAdapter);
            }
        } else if (adapterView.getId() == R.id.sp_society) {
            binding.llDownload.setVisibility(View.GONE);
            binding.llView.setVisibility(View.GONE);
            if (position != 0) {
                selectedPUnits = null;
                selectedSocietyId = "";
                selectedPUnitID = "";

                pUnits = new ArrayList<>();
                LiveData<String> liveData = viewModel.getSocietyId(selectedDivId, binding.spSociety.getSelectedItem().toString());
                liveData.observe(PUnitSelActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String str) {
                        liveData.removeObservers(PUnitSelActivity.this);
                        if (str != null) {
                            selectedSocietyId = str;
                            LiveData<List<PUnits>> listLiveData = viewModel.getPUnits(selectedDivId, selectedSocietyId);
                            listLiveData.observe(PUnitSelActivity.this, new Observer<List<PUnits>>() {
                                @Override
                                public void onChanged(List<PUnits> godownsList) {
                                    binding.spPUnit.setAdapter(selectAdapter);
                                    listLiveData.removeObservers(PUnitSelActivity.this);
                                    if (godownsList != null && godownsList.size() > 0) {
                                        pUnits.add("-Select-");
                                        for (int i = 0; i < godownsList.size(); i++) {
                                            pUnits.add(godownsList.get(i).getGodownName());
                                        }
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_spinner_dropdown_item, pUnits);
                                        binding.spPUnit.setAdapter(adapter);
                                    } else {
                                        binding.spPUnit.setAdapter(selectAdapter);
                                        showSnackBar(getString(R.string.no_p_unit_found));
                                    }
                                }
                            });
                        }
                    }
                });
            } else {
                selectedPUnits = null;
//                selectedSocietyId = "";
                selectedPUnitID = "";
            }
        } else if (adapterView.getId() == R.id.sp_p_unit) {
            if (position != 0) {
                binding.llDownload.setVisibility(View.VISIBLE);
                binding.llView.setVisibility(View.GONE);

                selectedPUnits = null;
                selectedPUnitID = "";
                LiveData<PUnits> liveData = viewModel.getPUnitID(selectedDivId, selectedSocietyId, binding.spPUnit.getSelectedItem().toString());
                liveData.observe(PUnitSelActivity.this, new Observer<PUnits>() {
                    @Override
                    public void onChanged(PUnits pUnits) {
                        liveData.removeObservers(PUnitSelActivity.this);
                        if (pUnits != null) {
                            selectedPUnitID = pUnits.getGodownId();
                            selectedPUnits = pUnits;

                            LiveData<GccOfflineEntity> drGodownLiveData = gccOfflineViewModel.
                                    getDRGoDownsOffline(selectedDivId, selectedSocietyId, selectedPUnitID);
                            drGodownLiveData.observe(PUnitSelActivity.this, new Observer<GccOfflineEntity>() {
                                @Override
                                public void onChanged(GccOfflineEntity drGodowns) {
                                    drGodownLiveData.removeObservers(PUnitSelActivity.this);

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
                selectedPUnits = null;
                selectedPUnitID = "";
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
                binding.btnDownload.setText(getString(R.string.re_download));
                binding.btnRemove.setVisibility(View.VISIBLE);
                Utils.customSyncSuccessAlert(PUnitSelActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.data_downloaded_success));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedrGoDownCount(int cnt) {

        try {
            if (cnt > 0) {
                binding.btnDownload.setText(getString(R.string.download));
                binding.btnRemove.setVisibility(View.GONE);
                Utils.customSyncSuccessAlert(PUnitSelActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.data_del_success));
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
