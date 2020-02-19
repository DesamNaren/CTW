package com.example.twdinspection.inspection.ui.reports;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.ErrorHandler;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.CustomProgressDialog;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.InstMainActivityBinding;
import com.example.twdinspection.databinding.ReportsInstMenuActivityBinding;
import com.example.twdinspection.inspection.adapter.MenuSectionsAdapter;
import com.example.twdinspection.inspection.adapter.ReportsMenuSectionsAdapter;
import com.example.twdinspection.inspection.interfaces.InstSubmitInterface;
import com.example.twdinspection.inspection.source.AcademicOverview.AcademicEntity;
import com.example.twdinspection.inspection.source.EntitlementsDistribution.EntitlementsEntity;
import com.example.twdinspection.inspection.source.GeneralComments.GeneralCommentsEntity;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.source.InfrastructureAndMaintenance.InfraStructureEntity;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.CoCurricularEntity;
import com.example.twdinspection.inspection.source.dietIssues.DietIssuesEntity;
import com.example.twdinspection.inspection.source.instMenuInfo.InstMenuInfoEntity;
import com.example.twdinspection.inspection.source.medical_and_health.MedicalInfoEntity;
import com.example.twdinspection.inspection.source.staffAttendance.StaffAttendanceEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;
import com.example.twdinspection.inspection.source.submit.InstSubmitRequest;
import com.example.twdinspection.inspection.source.submit.InstSubmitResponse;
import com.example.twdinspection.inspection.ui.DMVSelectionActivity;
import com.example.twdinspection.inspection.ui.LocBaseActivity;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.schemes.interfaces.ErrorHandlerInterface;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InstReportsMenuActivity extends LocBaseActivity {
    ReportsInstMenuActivityBinding binding;
    SharedPreferences sharedPreferences;
    String instId, officer_id, dist_id, mand_id, vill_id;
    boolean submitFlag = false, generalInfoFlag = false, studAttendFlag = false, staffAttendFlag = false, medicalFlag = false, dietFlag = false, infraFlag = false, academicFlag = false, cocurricularFlag = false, entitlementsFlag = false, regFlag = false, generalCommentsFlag = false;
    InstMainViewModel instMainViewModel;
    private String desLat, desLng;
    private CustomProgressDialog customProgressDialog;
    private String cacheDate, currentDate;
    List<InstMenuInfoEntity> arrayListLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customProgressDialog = new CustomProgressDialog(InstReportsMenuActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.reports_inst_menu_activity);


        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officer_id = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        dist_id = String.valueOf(sharedPreferences.getInt(AppConstants.DIST_ID, 0));
        mand_id = String.valueOf(sharedPreferences.getInt(AppConstants.MAN_ID, 0));
        vill_id = String.valueOf(sharedPreferences.getInt(AppConstants.VILL_ID, 0));
        desLat = sharedPreferences.getString(AppConstants.LAT, "");
        desLng = sharedPreferences.getString(AppConstants.LNG, "");

        String[] stringArray = getResources().getStringArray(R.array.inst_sections);
        ArrayList<String> sections = new ArrayList<>(Arrays.asList(stringArray));


//            menuInfoEntities = new ArrayList<>();
//                        for (int x = 0; x < sections.size(); x++) {
//                            menuInfoEntities.add(new InstMenuInfoEntity(instId, x + 1, 0, sections.get(x), null, sectionsNames.get(x)));
//                        }
        if (sections.size() > 0) {
//                            instMainViewModel.insertMenuSections(menuInfoEntities);
//                setAdapter(sectionsNames);
            ReportsMenuSectionsAdapter adapter = new ReportsMenuSectionsAdapter(InstReportsMenuActivity.this, sections);
            binding.rvMenu.setLayoutManager(new LinearLayoutManager(InstReportsMenuActivity.this));
            binding.rvMenu.setAdapter(adapter);

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

