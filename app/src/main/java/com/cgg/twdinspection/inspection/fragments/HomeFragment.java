package com.cgg.twdinspection.inspection.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityDashboardBinding;
import com.cgg.twdinspection.databinding.FragmentHomeBinding;
import com.cgg.twdinspection.engineering_works.ui.EngineeringDashboardActivity;
import com.cgg.twdinspection.gcc.ui.gcc.GCCDashboardActivity;
import com.cgg.twdinspection.inspection.reports.ui.ReportActivity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstMenuInfoEntity;
import com.cgg.twdinspection.inspection.source.inst_menu_info.InstSelectionInfo;
import com.cgg.twdinspection.inspection.ui.DMVSelectionActivity;
import com.cgg.twdinspection.inspection.ui.DashboardMenuActivity;
import com.cgg.twdinspection.inspection.ui.InstMenuMainActivity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstSelectionViewModel;
import com.cgg.twdinspection.schemes.ui.SchemesDMVActivity;

import java.util.List;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    InstMainViewModel instMainViewModel;
    InstSelectionViewModel instSelectionViewModel;
    private String instId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();
        binding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        try {
            sharedPreferences = TWDApplication.get(context).getPreferences();
            instId = sharedPreferences.getString(AppConstants.INST_ID, "");
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
        instSelectionViewModel = new InstSelectionViewModel(getActivity().getApplication());
        instMainViewModel = new InstMainViewModel(getActivity().getApplication());

        binding.btnInstInsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instSelectionViewModel.getSelectedInst().observe(getActivity(), new Observer<InstSelectionInfo>() {
                    @Override
                    public void onChanged(InstSelectionInfo instSelectionInfo) {
                        if (instSelectionInfo != null) {
                            instId = instSelectionInfo.getInst_id();
                            if (!TextUtils.isEmpty(instId)) {
                                instMainViewModel.getAllSections().observe(getActivity(), new Observer<List<InstMenuInfoEntity>>() {
                                    @Override
                                    public void onChanged(List<InstMenuInfoEntity> instMenuInfoEntities) {
                                        if (instMenuInfoEntities != null && instMenuInfoEntities.size() > 0) {

                                            boolean flag = false;
                                            for (int i = 0; i < instMenuInfoEntities.size(); i++) {
                                                if (instMenuInfoEntities.get(i).getFlag_completed() == 1) {
                                                    flag = true;
                                                    break;
                                                }
                                            }
                                            if (flag) {
                                                startActivity(new Intent(context, InstMenuMainActivity.class));
                                                getActivity().finish();
                                            } else {
                                                startActivity(new Intent(context, DMVSelectionActivity.class));
                                            }
                                        } else {
                                            startActivity(new Intent(context, DMVSelectionActivity.class));
                                        }
                                    }

                                });
                            } else {
                                startActivity(new Intent(context, DMVSelectionActivity.class));
                            }
                        } else {
                            startActivity(new Intent(context, DMVSelectionActivity.class));
                        }
                    }
                });

            }
        });

        binding.btnSchemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SchemesDMVActivity.class));
            }
        });

        binding.btnEngWorks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, EngineeringDashboardActivity.class));
            }
        });

        binding.btnGcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, GCCDashboardActivity.class));
            }
        });

        binding.btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ReportActivity.class));
            }
        });
       
        return binding.getRoot();
    }
}