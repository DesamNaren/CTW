package com.cgg.twdinspection.inspection.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.CustomProgressDialog;
import com.cgg.twdinspection.common.utils.ErrorHandler;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityStaffAttBinding;
import com.cgg.twdinspection.inspection.adapter.StaffAdapter;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.interfaces.SchoolInstInterface;
import com.cgg.twdinspection.inspection.room.repository.SchoolSyncRepository;
import com.cgg.twdinspection.inspection.source.inst_master.InstMasterResponse;
import com.cgg.twdinspection.inspection.source.inst_master.MasterInstituteInfo;
import com.cgg.twdinspection.inspection.source.inst_master.MasterStaffInfo;
import com.cgg.twdinspection.inspection.source.staff_attendance.StaffAttendanceEntity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StaffAttendCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.StaffViewModel;
import com.cgg.twdinspection.inspection.viewmodel.SyncViewModel;
import com.cgg.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StaffAttendActivity extends BaseActivity implements SaveListener, SchoolInstInterface, ErrorHandlerInterface {

    ActivityStaffAttBinding staffAttBinding;
    List<StaffAttendanceEntity> staffAttendanceEntitiesmain;
    StaffAdapter staffAdapter;
    StaffViewModel staffViewModel;
    InstMainViewModel instMainViewModel;
    SharedPreferences sharedPreferences;
    String instName, instId, officerId;
    MasterInstituteInfo masterInstituteInfos;
    private CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        staffAttBinding = putContentView(R.layout.activity_staff_att, getResources().getString(R.string.sta_att));
        customProgressDialog = new CustomProgressDialog(StaffAttendActivity.this);
        staffAttBinding.btnLayout.btnPrevious.setVisibility(View.GONE);
        staffViewModel = ViewModelProviders.of(
                this, new StaffAttendCustomViewModel(staffAttBinding, this)).get(StaffViewModel.class);
        staffAttBinding.setViewmodel(staffViewModel);
        staffAttendanceEntitiesmain = new ArrayList<>();
        instMainViewModel = new InstMainViewModel(getApplication());
        sharedPreferences = TWDApplication.get(this).getPreferences();
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        instName = sharedPreferences.getString(AppConstants.INST_NAME, "");


        binding.syncIv.setVisibility(View.VISIBLE);
        binding.syncIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customSyncAlert(StaffAttendActivity.this, getString(R.string.app_name),
                        getString(R.string.data_lost_staff));
            }
        });


        staffAttBinding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate())
                    Utils.customSaveAlert(StaffAttendActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));

            }
        });

        LiveData<List<StaffAttendanceEntity>> listLiveData = staffViewModel.getStaffInfo(instId);
        listLiveData.observe(StaffAttendActivity.this, new Observer<List<StaffAttendanceEntity>>() {
            @Override
            public void onChanged(List<StaffAttendanceEntity> staffAttendanceEntities) {
                if (staffAttendanceEntities != null && staffAttendanceEntities.size() > 0) {
                    staffAttendanceEntitiesmain = staffAttendanceEntities;
                    setAdapter();
                } else {
                    getMasterStaffInfo();
                }
            }
        });

        localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
    }


    private void customSyncAlert(Activity activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_exit);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button exit = dialog.findViewById(R.id.btDialogExit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        callService();
                    }
                });

                Button cancel = dialog.findViewById(R.id.btDialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    private void callService() {
        if (Utils.checkInternetConnection(StaffAttendActivity.this)) {

            customProgressDialog.show();
            SyncViewModel viewModel = new SyncViewModel(StaffAttendActivity.this, getApplication());
            SchoolSyncRepository schoolSyncRepository = new SchoolSyncRepository(getApplication());
            LiveData<InstMasterResponse> instMasterResponseLiveData = viewModel.getStudentsInstMasterResponse(instId);
            instMasterResponseLiveData.observe(StaffAttendActivity.this, new Observer<InstMasterResponse>() {
                @Override
                public void onChanged(InstMasterResponse instMasterResponse) {
                    if (instMasterResponse != null) {
                        if (instMasterResponse.getInstituteInfo() != null && instMasterResponse.getInstituteInfo().size() > 0) {
                            String str = new Gson().toJson(instMasterResponse.getInstituteInfo().get(0).getStaffInfo());
                            schoolSyncRepository.updateStaffMasterInstitutes(StaffAttendActivity.this, str, instId);
                        } else {
                            customProgressDialog.hide();
                            Utils.customErrorAlert(StaffAttendActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_insts));
                        }
                    } else {
                        customProgressDialog.hide();
                        Utils.customErrorAlert(StaffAttendActivity.this, getResources().getString(R.string.app_name), getString(R.string.server_not));
                    }
                }
            });
        } else {
            Utils.customErrorAlert(StaffAttendActivity.this, getResources().getString(R.string.app_name), getString(R.string.plz_check_int));
        }
    }

    private void setStaffData(MasterInstituteInfo masterInstituteInfos) {
        if (masterInstituteInfos != null) {
            staffAttendanceEntitiesmain = new ArrayList<>();
            StaffAttendActivity.this.masterInstituteInfos = masterInstituteInfos;

            List<MasterStaffInfo> masterStaffInfos = masterInstituteInfos.getStaffInfo();
            if (masterStaffInfos != null && masterStaffInfos.size() > 0) {
                for (int i = 0; i < masterStaffInfos.size(); i++) {
                    StaffAttendanceEntity staffAttendanceEntity = new StaffAttendanceEntity(officerId,
                            String.valueOf(masterStaffInfos.get(i).getHostelId()),
                            instName, String.valueOf(masterStaffInfos.get(i).getEmpId()),
                            masterStaffInfos.get(i).getEmpName(), masterStaffInfos.get(i).getDesignation());
                    staffAttendanceEntitiesmain.add(staffAttendanceEntity);
                }
            }

            staffViewModel.insertStaffInfo(staffAttendanceEntitiesmain, instId);

            if (!(staffAttendanceEntitiesmain != null && staffAttendanceEntitiesmain.size() > 0)) {
                staffAttBinding.emptyTv.setVisibility(View.VISIBLE);
                staffAttBinding.staffRv.setVisibility(View.GONE);
            } else {
                setAdapter();
            }

        } else {
            staffAttBinding.emptyTv.setVisibility(View.VISIBLE);
            staffAttBinding.staffRv.setVisibility(View.GONE);
        }
    }

    private void setAdapter() {
        staffAdapter = new StaffAdapter(StaffAttendActivity.this, staffAttendanceEntitiesmain);
        staffAttBinding.staffRv.setAdapter(staffAdapter);
        staffAttBinding.staffRv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        staffAttBinding.staffRv.setLayoutManager(layoutManager);
    }

    private int localFlag;

    private boolean validate() {
        boolean returnFlag = true;
        for (int i = 0; i < staffAttendanceEntitiesmain.size(); i++) {
            if (localFlag != 1 && !(staffAttendanceEntitiesmain.get(i).isAbsentFlag() || staffAttendanceEntitiesmain.get(i).isPresentFlag()
                    || staffAttendanceEntitiesmain.get(i).isOndepFlag() || staffAttendanceEntitiesmain.get(i).isLeavesFlag())) {
                returnFlag = false;
                showSnackBar(getString(R.string.mark_att_emp));
                break;
            } else if (TextUtils.isEmpty(staffAttendanceEntitiesmain.get(i).getYday_duty_allotted())) {
                returnFlag = false;
                showSnackBar(getString(R.string.yes_duty_alloted));
                break;
            } else if (TextUtils.isEmpty(staffAttendanceEntitiesmain.get(i).getLast_week_turn_duties_attended())) {
                returnFlag = false;
                showSnackBar(getString(R.string.last_week_turn_att));
                break;
            } else if (TextUtils.isEmpty(staffAttendanceEntitiesmain.get(i).getAcad_panel_grade()) || staffAttendanceEntitiesmain.get(i).getAcad_panel_grade_pos() == 0) {
                returnFlag = false;
                showSnackBar(getString(R.string.last_yr_acad_panel));
                break;
            }

        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(staffAttBinding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void submitData() {
        for (int i = 0; i < staffAttendanceEntitiesmain.size(); i++) {
            staffAttendanceEntitiesmain.get(i).setInspection_time(Utils.getCurrentDateTime());
        }
        long x = staffViewModel.updateStaffInfo(staffAttendanceEntitiesmain);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("StfAtt");
                liveData.observe(StaffAttendActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instId);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(StaffAttendActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    @Override
    public void onBackPressed() {
        super.callBack();
    }

    @Override
    public void instCount(int cnt) {
        customProgressDialog.hide();
        try {
            if (cnt > 0) {
                getMasterStaffInfo();

                Utils.customStudentSyncSuccessAlert(StaffAttendActivity.this, getResources().getString(R.string.app_name),
                        getString(R.string.cls_mas_syn));
            } else {
                Utils.customErrorAlert(StaffAttendActivity.this, getResources().getString(R.string.app_name), getString(R.string.no_insts));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clstCount(int cnt) {

    }

    @Override
    public void handleError(Throwable e, Context context) {
        customProgressDialog.hide();
        String errMsg = ErrorHandler.handleError(e, context);
        Utils.customErrorAlert(StaffAttendActivity.this, getResources().getString(R.string.app_name), errMsg);
    }

    private void getMasterStaffInfo() {
        LiveData<MasterInstituteInfo> masterInstituteInfoLiveData = staffViewModel.getMasterStaffInfo(
                instId);
        masterInstituteInfoLiveData.observe(StaffAttendActivity.this, new Observer<MasterInstituteInfo>() {
            @Override
            public void onChanged(MasterInstituteInfo masterInstituteInfos) {
                setStaffData(masterInstituteInfos);
            }
        });
    }

}
