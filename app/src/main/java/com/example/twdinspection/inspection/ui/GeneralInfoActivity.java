package com.example.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityGeneralInfoBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.example.twdinspection.inspection.viewmodel.GeneralInfoViewModel;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

public class GeneralInfoActivity extends BaseActivity implements SaveListener {
    private static final String TAG = GeneralInfoActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityGeneralInfoBinding binding;
    GeneralInfoViewModel generalInfoViewModel;
    InstMainViewModel instMainViewModel;
    GeneralInfoEntity generalInfoEntity;
    String headQuarters;
    String presentTime, leavetype, capturetype, movementRegisterEntry, staffQuarters, stayingFacilitiesType, captureDistance;
    private String officerID, instID, insTime;
    private int localFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_general_info, getResources().getString(R.string.general_info));

        generalInfoViewModel = new GeneralInfoViewModel(getApplication());
        instMainViewModel = new InstMainViewModel(getApplication());
        binding.setViewModel(generalInfoViewModel);

        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            officerID = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));

            insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");
            binding.includeBasicLayout.inspectionTime.setText(insTime);

            binding.manNameTv.setText(sharedPreferences.getString(AppConstants.MAN_NAME, ""));
            binding.instNameTv.setText(sharedPreferences.getString(AppConstants.INST_NAME, ""));
            instID = sharedPreferences.getString(AppConstants.INST_ID, "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
        if(localFlag==1){
            //get local record & set to data binding
            LiveData<GeneralInfoEntity> generalInfoEntityLiveData = instMainViewModel.getGeneralInfoData();
            generalInfoEntityLiveData.observe(GeneralInfoActivity.this, new Observer<GeneralInfoEntity>() {
                @Override
                public void onChanged(GeneralInfoEntity generalInfoEntity) {
                    generalInfoEntityLiveData.removeObservers(GeneralInfoActivity.this);
                    if (generalInfoEntity != null) {
                        binding.setInspDataGeneral(generalInfoEntity);
                        binding.executePendingBindings();
                    }
                }
            });
        }

        binding.rgHeadQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHeadQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_head_quarters)
                    headQuarters = AppConstants.Yes;
                else
                    headQuarters = AppConstants.No;
            }
        });

        binding.rgPresentTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPresentTime.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_present_time) {
                    presentTime = AppConstants.No;
                    binding.llLeavetype.setVisibility(View.VISIBLE);
                    binding.viewLeaveType.setVisibility(View.VISIBLE);

                    binding.rgLeavetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int selctedItem = binding.rgLeavetype.getCheckedRadioButtonId();
                            if (selctedItem == R.id.rb_absent) {
                                leavetype = "Unauthorized Absent";

                                binding.llOdCaptureType.setVisibility(View.GONE);
                                binding.rgCapturetype.clearCheck();
                                binding.rgMovementRegisterEntry.clearCheck();
                            } else if (selctedItem == R.id.rb_leaves) {
                                leavetype = "Leaves";

                                binding.llOdCaptureType.setVisibility(View.GONE);
                                binding.rgCapturetype.clearCheck();
                                binding.rgMovementRegisterEntry.clearCheck();
                            } else if (selctedItem == R.id.rb_od) {
                                leavetype = "OD";
                                binding.llOdCaptureType.setVisibility(View.VISIBLE);
                                binding.rgCapturetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                        int selctedItem = binding.rgCapturetype.getCheckedRadioButtonId();
                                        if (selctedItem == R.id.rb_out_of_station) {
                                            capturetype = "Out of station";
                                            binding.llMovementRegisterEntry.setVisibility(View.VISIBLE);
                                            binding.rgMovementRegisterEntry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                                    int selctedItem = binding.rgMovementRegisterEntry.getCheckedRadioButtonId();
                                                    if (selctedItem == R.id.rb_yes_movement_register_entry)
                                                        movementRegisterEntry = AppConstants.Yes;
                                                    else if (selctedItem == R.id.rb_no_movement_register_entry)
                                                        movementRegisterEntry = AppConstants.No;
                                                    else
                                                        movementRegisterEntry = null;
                                                }
                                            });
                                        } else if (selctedItem == R.id.rb_within_campus) {
                                            capturetype = "Within campus";
                                            binding.llMovementRegisterEntry.setVisibility(View.GONE);
                                            binding.rgMovementRegisterEntry.clearCheck();

                                        } else if (selctedItem == R.id.rb_within_mandal) {
                                            capturetype = "Within Mandal";
                                            binding.llMovementRegisterEntry.setVisibility(View.GONE);
                                            binding.rgMovementRegisterEntry.clearCheck();

                                        } else if (selctedItem == R.id.rb_within_district) {
                                            capturetype = "Within District";
                                            binding.llMovementRegisterEntry.setVisibility(View.GONE);
                                            binding.rgMovementRegisterEntry.clearCheck();

                                        } else if (selctedItem == R.id.rb_itda_level) {
                                            capturetype = "ITDA level";
                                            binding.llMovementRegisterEntry.setVisibility(View.GONE);
                                            binding.rgMovementRegisterEntry.clearCheck();

                                        } else if (selctedItem == R.id.rb_state_level) {
                                            capturetype = "State Level";
                                            binding.llMovementRegisterEntry.setVisibility(View.GONE);
                                            binding.rgMovementRegisterEntry.clearCheck();

                                        } else {
                                            capturetype = null;
                                        }
                                    }
                                });
                            } else {
                                leavetype = null;
                            }
                        }
                    });
                } else if (selctedItem == R.id.rb_yes_present_time) {
                    presentTime = AppConstants.Yes;

                    binding.llLeavetype.setVisibility(View.GONE);
                    binding.viewLeaveType.setVisibility(View.GONE);

                    binding.rgLeavetype.clearCheck();
                    binding.rgCapturetype.clearCheck();
                    binding.rgMovementRegisterEntry.clearCheck();
                } else {
                    presentTime = null;
                }

            }
        });

        binding.rgStaffQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_staff_quarters) {
                    staffQuarters = AppConstants.No;
                    binding.llCapture.setVisibility(View.VISIBLE);
                    binding.llCaptureDistance.setVisibility(View.VISIBLE);

                    binding.llCaptureStayingFacilitiesType.setVisibility(View.GONE);
                    binding.rgCaptureStayingFacilitiesType.clearCheck();

                    binding.rgCaptureDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int selctedItem = binding.rgCaptureDistance.getCheckedRadioButtonId();
                            if (selctedItem == R.id.rb_below_30)
                                captureDistance = "BELOW 30";
                            else if (selctedItem == R.id.rb_31_50)
                                captureDistance = "31-50";
                            else if (selctedItem == R.id.rb_above_50)
                                captureDistance = "ABOVE 50";
                            else
                                captureDistance = null;

                        }
                    });
                } else if (selctedItem == R.id.rb_yes_staff_quarters) {
                    staffQuarters = AppConstants.Yes;
                    binding.llCapture.setVisibility(View.VISIBLE);
                    binding.llCaptureDistance.setVisibility(View.GONE);
                    binding.rgCaptureDistance.clearCheck();

                    binding.llCaptureStayingFacilitiesType.setVisibility(View.VISIBLE);

                    binding.rgCaptureStayingFacilitiesType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int selctedItem = binding.rgCaptureStayingFacilitiesType.getCheckedRadioButtonId();
                            if (selctedItem == R.id.rb_in_staff_quarters)
                                stayingFacilitiesType = "in Staff Quarters";
                            else if (selctedItem == R.id.rb_in_school_building)
                                stayingFacilitiesType = "in School Building";
                            else if (selctedItem == R.id.rb_pvt_building)
                                stayingFacilitiesType = "Pvt. Building";
                            else
                                stayingFacilitiesType = null;
                        }
                    });
                } else {
                    staffQuarters = null;
                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validateData()) {
                    generalInfoEntity = new GeneralInfoEntity();
                    generalInfoEntity.setOfficer_id(officerID);
                    generalInfoEntity.setInstitute_id(instID);
                    generalInfoEntity.setMandalName(binding.manNameTv.getText().toString());
                    generalInfoEntity.setInstName(binding.instNameTv.getText().toString());
                    generalInfoEntity.setInspection_time(Utils.getCurrentDateTime());
                    generalInfoEntity.setHM_HWO_presence(presentTime);
                    generalInfoEntity.setHaving_headquarters(headQuarters);
                    generalInfoEntity.setLeaveType(leavetype);
                    generalInfoEntity.setCapturetype(capturetype);
                    generalInfoEntity.setMovementRegisterEntry(movementRegisterEntry);
                    generalInfoEntity.setCaptureDistance(captureDistance);
                    generalInfoEntity.setStaffQuarters(staffQuarters);
                    generalInfoEntity.setStayingFacilitiesType(stayingFacilitiesType);

                    Utils.customSaveAlert(GeneralInfoActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                }
            }
        });


    }

    private boolean validateData() {
        if (TextUtils.isEmpty(presentTime)) {
            showSnackBar(getResources().getString(R.string.hm_hwo));
            return false;
        }
        if (presentTime.equals(AppConstants.No) && TextUtils.isEmpty(leavetype)) {
            showSnackBar(getResources().getString(R.string.sel_leave_type));
            return false;
        }
        if (!TextUtils.isEmpty(leavetype) && leavetype.equalsIgnoreCase("OD") && TextUtils.isEmpty(capturetype)) {
            showSnackBar(getResources().getString(R.string.sel_od_type));
            return false;
        }
        if (!TextUtils.isEmpty(leavetype) && leavetype.equalsIgnoreCase("OD") && !TextUtils.isEmpty(capturetype)
                && capturetype.equalsIgnoreCase("Out of station") && TextUtils.isEmpty(movementRegisterEntry)) {
            showSnackBar(getResources().getString(R.string.sel_mov_reg));
            return false;
        }
        if (TextUtils.isEmpty(headQuarters)) {
            showSnackBar(getResources().getString(R.string.sel_head_qua));
            return false;
        }
        if (TextUtils.isEmpty(staffQuarters)) {
            showSnackBar(getResources().getString(R.string.sel_staff_qua));
            return false;
        }

        if (!TextUtils.isEmpty(staffQuarters) && staffQuarters.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(stayingFacilitiesType)) {
            showSnackBar(getResources().getString(R.string.sel_stay_facility));
            return false;
        }
        if (!TextUtils.isEmpty(staffQuarters) && staffQuarters.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(captureDistance)) {
            showSnackBar(getResources().getString(R.string.sel_distance));
            return false;
        }
        return true;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void submitData() {
        long x = generalInfoViewModel.insertGeneralInfo(generalInfoEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("GI");
                liveData.observe(GeneralInfoActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        if (id != null) {
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id, instID);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {

                Utils.customSectionSaveAlert(GeneralInfoActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
//                startActivity(new Intent(GeneralInfoActivity.this, StudentsAttendActivity.class));
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
}
