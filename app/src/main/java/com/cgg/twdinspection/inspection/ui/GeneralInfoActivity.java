package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityGeneralInfoBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.general_information.GeneralInfoEntity;
import com.cgg.twdinspection.inspection.viewmodel.GeneralInfoViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.snackbar.Snackbar;

public class GeneralInfoActivity extends BaseActivity implements SaveListener {
    private static final String TAG = GeneralInfoActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityGeneralInfoBinding binding;
    GeneralInfoViewModel generalInfoViewModel;
    InstMainViewModel instMainViewModel;
    GeneralInfoEntity generalInfoEntity;
    String staffQuarters, hwoheadQuarters, hwostayingFacilitiesType, hwocaptureDistance;
    String hmheadQuarters, hmstayingFacilitiesType, hmcaptureDistance;
    String hwopresentTime, hworeasontype, hwocaptureodtype, hwocaptureLeavetype, hwomovementRegisterEntry;
    String hmPresentTime, hmreasontype, hmCaptureodtype, hmCaptureLeavetype, hmMovementRegisterEntry;
    private String officerID, instID, insTime;
    private int localFlag = -1;


    private void ScrollToView(View view) {
        binding.scrl.smoothScrollTo(0, view.getTop());
    }

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

        try {
            localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                final LiveData<GeneralInfoEntity> generalInfoEntityLiveData = instMainViewModel.getGeneralInfoData();
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
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.rgHmPresentTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmPresentTime.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_hm_present_time) {
                    hmPresentTime = AppConstants.No;

                    binding.llHmLeavetype.setVisibility(View.VISIBLE);


                } else if (selctedItem == R.id.rb_yes_hm_present_time) {
                    hmPresentTime = AppConstants.Yes;

                    binding.rgHmLeavetype.clearCheck();
                    binding.rgHmCapturetype.clearCheck();
                    binding.rgHmLeavecapturetype.clearCheck();
                    binding.rgHmMovementRegisterEntry.clearCheck();

                    binding.llHmLeavetype.setVisibility(View.GONE);
                    binding.llHmOdCaptureType.setVisibility(View.GONE);
                    binding.llHmLeaveCaptureType.setVisibility(View.GONE);
                    binding.llHmMovementRegisterEntry.setVisibility(View.GONE);
                } else {
                    hmPresentTime = null;
                }

            }
        });

        binding.rgHmLeavetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmLeavetype.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_hm_absent) {
                    hmreasontype = "Unauthorized Absent";

                    binding.rgHmCapturetype.clearCheck();
                    binding.rgHmMovementRegisterEntry.clearCheck();
                    binding.rgHmLeavecapturetype.clearCheck();

                    binding.llHmLeaveCaptureType.setVisibility(View.GONE);
                    binding.llHmMovementRegisterEntry.setVisibility(View.GONE);
                    binding.llHmOdCaptureType.setVisibility(View.GONE);

                } else if (selctedItem == R.id.rb_hm_leaves) {
                    hmreasontype = "Leaves";

                    binding.rgHmCapturetype.clearCheck();
                    binding.rgHmMovementRegisterEntry.clearCheck();

                    binding.llHmLeaveCaptureType.setVisibility(View.VISIBLE);
                    binding.llHmMovementRegisterEntry.setVisibility(View.GONE);
                    binding.llHmOdCaptureType.setVisibility(View.GONE);

                } else if (selctedItem == R.id.rb_hm_od) {
                    hmreasontype = "OD";
                    binding.rgHmLeavecapturetype.clearCheck();

                    binding.llHmLeaveCaptureType.setVisibility(View.GONE);
                    binding.llHmMovementRegisterEntry.setVisibility(View.VISIBLE);
                    binding.llHmOdCaptureType.setVisibility(View.VISIBLE);

                } else {
                    hmreasontype = null;
                }

            }
        });

        binding.rgHmLeavecapturetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmLeavecapturetype.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_hm_cl) {
                    hmCaptureLeavetype = "CL";

                } else if (selctedItem == R.id.rb_hm_ccl) {
                    hmCaptureLeavetype = "Medical";

                } else if (selctedItem == R.id.rb_hm_scl) {
                    hmCaptureLeavetype = "Maternity";

                } else if (selctedItem == R.id.rb_hm_medical) {
                    hmCaptureLeavetype = "Medical";

                } else if (selctedItem == R.id.rb_hm_maternity) {
                    hmCaptureLeavetype = "Maternity";

                } else if (selctedItem == R.id.rb_hm_paternity) {
                    hmCaptureLeavetype = "Paternity";

                } else {
                    hmCaptureLeavetype = null;
                }
            }
        });

        binding.rgHmCapturetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmCapturetype.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_hm_out_of_station) {
                    hmCaptureodtype = "Out of station";

                } else if (selctedItem == R.id.rb_hm_within_campus) {
                    hmCaptureodtype = "Within campus";

                } else if (selctedItem == R.id.rb_hm_within_mandal) {
                    hmCaptureodtype = "Within Mandal";


                } else if (selctedItem == R.id.rb_hm_within_district) {
                    hmCaptureodtype = "Within District";

                } else if (selctedItem == R.id.rb_hm_itda_level) {
                    hmCaptureodtype = "ITDA level";

                } else if (selctedItem == R.id.rb_hm_state_level) {
                    hmCaptureodtype = "State Level";

                } else {
                    hmCaptureodtype = null;
                }
            }
        });

        binding.rgHmMovementRegisterEntry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmMovementRegisterEntry.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_hm_movement_register_entry)
                    hmMovementRegisterEntry = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_hm_movement_register_entry)
                    hmMovementRegisterEntry = AppConstants.No;
                else
                    hmMovementRegisterEntry = null;
            }
        });


        binding.rgPresentTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPresentTime.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_present_time) {
                    hwopresentTime = AppConstants.No;

                    binding.llLeavetype.setVisibility(View.VISIBLE);

                } else if (selctedItem == R.id.rb_yes_present_time) {
                    hwopresentTime = AppConstants.Yes;

                    binding.rgLeavetype.clearCheck();
                    binding.rgCapturetype.clearCheck();
                    binding.rgLeavecapturetype.clearCheck();
                    binding.rgMovementRegisterEntry.clearCheck();

                    binding.llLeavetype.setVisibility(View.GONE);
                    binding.llOdCaptureType.setVisibility(View.GONE);
                    binding.llLeaveCaptureType.setVisibility(View.GONE);
                    binding.llMovementRegisterEntry.setVisibility(View.GONE);

                } else {
                    hwopresentTime = null;
                }

            }
        });

        binding.rgLeavetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLeavetype.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_absent) {
                    hworeasontype = "Unauthorized Absent";
                    binding.rgCapturetype.clearCheck();
                    binding.rgMovementRegisterEntry.clearCheck();
                    binding.rgLeavecapturetype.clearCheck();

                    binding.llLeaveCaptureType.setVisibility(View.GONE);
                    binding.llMovementRegisterEntry.setVisibility(View.GONE);
                    binding.llOdCaptureType.setVisibility(View.GONE);

                } else if (selctedItem == R.id.rb_leaves) {
                    hworeasontype = "Leaves";
                    binding.rgCapturetype.clearCheck();
                    binding.rgMovementRegisterEntry.clearCheck();

                    binding.llLeaveCaptureType.setVisibility(View.VISIBLE);
                    binding.llMovementRegisterEntry.setVisibility(View.GONE);
                    binding.llOdCaptureType.setVisibility(View.GONE);

                } else if (selctedItem == R.id.rb_od) {
                    hworeasontype = "OD";
                    binding.rgLeavecapturetype.clearCheck();

                    binding.llLeaveCaptureType.setVisibility(View.GONE);
                    binding.llMovementRegisterEntry.setVisibility(View.VISIBLE);
                    binding.llOdCaptureType.setVisibility(View.VISIBLE);

                } else {
                    hworeasontype = null;
                }


            }
        });

        binding.rgLeavecapturetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgLeavecapturetype.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_cl) {
                    hwocaptureLeavetype = "CL";

                } else if (selctedItem == R.id.rb_medical) {
                    hwocaptureLeavetype = "Medical";

                } else if (selctedItem == R.id.rb_maternity) {
                    hwocaptureLeavetype = "Maternity";

                } else if (selctedItem == R.id.rb_paternity) {
                    hwocaptureLeavetype = "Paternity";

                } else {
                    hwocaptureLeavetype = null;
                }
            }
        });

        binding.rgCapturetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCapturetype.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_out_of_station) {
                    hwocaptureodtype = "Out of station";

                } else if (selctedItem == R.id.rb_within_campus) {
                    hwocaptureodtype = "Within campus";

                } else if (selctedItem == R.id.rb_within_mandal) {
                    hwocaptureodtype = "Within Mandal";

                } else if (selctedItem == R.id.rb_within_district) {
                    hwocaptureodtype = "Within District";

                } else if (selctedItem == R.id.rb_itda_level) {
                    hwocaptureodtype = "ITDA level";

                } else if (selctedItem == R.id.rb_state_level) {
                    hwocaptureodtype = "State Level";

                } else {
                    hwocaptureodtype = null;
                }
            }
        });

        binding.rgMovementRegisterEntry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgMovementRegisterEntry.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_movement_register_entry)
                    hwomovementRegisterEntry = AppConstants.Yes;
                else if (selctedItem == R.id.rb_no_movement_register_entry)
                    hwomovementRegisterEntry = AppConstants.No;
                else
                    hwomovementRegisterEntry = null;
            }
        });


        binding.rgHeadQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHeadQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_head_quarters) {
                    hwoheadQuarters = AppConstants.Yes;

                    binding.rgCaptureDistance.clearCheck();

                    binding.llCaptureDistance.setVisibility(View.GONE);
                    binding.llCaptureStayingFacilitiesType.setVisibility(View.VISIBLE);


                } else if (selctedItem == R.id.rb_no_head_quarters) {
                    hwoheadQuarters = AppConstants.No;

                    binding.rgCaptureStayingFacilitiesType.clearCheck();

                    binding.llCaptureDistance.setVisibility(View.VISIBLE);
                    binding.llCaptureStayingFacilitiesType.setVisibility(View.GONE);

                } else {
                    hwoheadQuarters = null;
                }
            }
        });

        binding.rgCaptureDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCaptureDistance.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_below_30)
                    hwocaptureDistance = "BELOW 30";
                else if (selctedItem == R.id.rb_31_50)
                    hwocaptureDistance = "31-50";
                else if (selctedItem == R.id.rb_above_50)
                    hwocaptureDistance = "ABOVE 50";
                else
                    hwocaptureDistance = null;

            }
        });

        binding.rgCaptureStayingFacilitiesType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCaptureStayingFacilitiesType.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_in_staff_quarters)
                    hwostayingFacilitiesType = "in Staff Quarters";
                else if (selctedItem == R.id.rb_in_school_building)
                    hwostayingFacilitiesType = "in School Building";
                else if (selctedItem == R.id.rb_pvt_building)
                    hwostayingFacilitiesType = "Pvt. Building";
                else
                    hwostayingFacilitiesType = null;
            }
        });


        binding.rgHmHeadQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmHeadQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_hm_head_quarters) {
                    hmheadQuarters = AppConstants.Yes;

                    binding.rgHmCaptureDistance.clearCheck();

                    binding.llHmCaptureDistance.setVisibility(View.GONE);
                    binding.llHmCaptureStayingFacilitiesType.setVisibility(View.VISIBLE);

                } else if (selctedItem == R.id.rb_no_hm_head_quarters) {
                    hmheadQuarters = AppConstants.No;

                    binding.rgHmCaptureStayingFacilitiesType.clearCheck();

                    binding.llHmCaptureDistance.setVisibility(View.VISIBLE);
                    binding.llHmCaptureStayingFacilitiesType.setVisibility(View.GONE);

                } else {
                    hmheadQuarters = null;
                }
            }
        });

        binding.rgHmCaptureDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmCaptureDistance.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_hm_below_30)
                    hmcaptureDistance = "BELOW 30";
                else if (selctedItem == R.id.rb_hm_31_50)
                    hmcaptureDistance = "31-50";
                else if (selctedItem == R.id.rb_hm_above_50)
                    hmcaptureDistance = "ABOVE 50";
                else
                    hmcaptureDistance = null;

            }
        });

        binding.rgHmCaptureStayingFacilitiesType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHmCaptureStayingFacilitiesType.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_hm_in_staff_quarters)
                    hmstayingFacilitiesType = "in Staff Quarters";
                else if (selctedItem == R.id.rb_hm_in_school_building)
                    hmstayingFacilitiesType = "in School Building";
                else if (selctedItem == R.id.rb_hm_pvt_building)
                    hmstayingFacilitiesType = "Pvt. Building";
                else
                    hmstayingFacilitiesType = null;
            }
        });


        binding.rgStaffQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_staff_quarters) {
                    staffQuarters = AppConstants.No;

                } else if (selctedItem == R.id.rb_yes_staff_quarters) {
                    staffQuarters = AppConstants.Yes;
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

                    generalInfoEntity.setHmPresence(hmPresentTime);
                    generalInfoEntity.setHmReasonType(hmreasontype);
                    generalInfoEntity.setHmCaptureodtype(hmCaptureodtype);
                    generalInfoEntity.setHmcaptureleavetype(hmCaptureLeavetype);
                    generalInfoEntity.setHmMovementRegisterEntry(hmMovementRegisterEntry);

                    generalInfoEntity.setHwoPresence(hwopresentTime);
                    generalInfoEntity.setHwoReasonType(hworeasontype);
                    generalInfoEntity.setHwocaptureodtype(hwocaptureodtype);
                    generalInfoEntity.setHwocaptureleavetype(hwocaptureLeavetype);
                    generalInfoEntity.setHwomovementRegisterEntry(hwomovementRegisterEntry);

                    generalInfoEntity.setHmheadquarters(hmheadQuarters);
                    generalInfoEntity.setHmcaptureDistance(hmcaptureDistance);
                    generalInfoEntity.setHmstayingFacilitiesType(hmstayingFacilitiesType);

                    generalInfoEntity.setHwoheadquarters(hwoheadQuarters);
                    generalInfoEntity.setHwocaptureDistance(hwocaptureDistance);
                    generalInfoEntity.setHwostayingFacilitiesType(hwostayingFacilitiesType);
                    generalInfoEntity.setStaffQuarters(staffQuarters);

                    Utils.customSaveAlert(GeneralInfoActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                }
            }
        });
    }

    private boolean validateData() {

        if (TextUtils.isEmpty(hmPresentTime)) {
            ScrollToView(binding.rgHmPresentTime);
            showSnackBar(getResources().getString(R.string.hm_presence));
            return false;
        }
        if (hmPresentTime.equals(AppConstants.No) && TextUtils.isEmpty(hmreasontype)) {
            ScrollToView(binding.rgHmCapturetype);
            showSnackBar(getResources().getString(R.string.sel_leave_type));
            return false;
        }
        if (!TextUtils.isEmpty(hmreasontype) && hmreasontype.equalsIgnoreCase("Leaves") && TextUtils.isEmpty(hmCaptureLeavetype)) {
            ScrollToView(binding.rgHmLeavecapturetype);
            showSnackBar(getResources().getString(R.string.sel_cap_leave_type));
            return false;
        }
        if (!TextUtils.isEmpty(hmreasontype) && hmreasontype.equalsIgnoreCase("OD") && TextUtils.isEmpty(hmCaptureodtype)) {
            showSnackBar(getResources().getString(R.string.sel_od_type));
            return false;
        }
        if (!TextUtils.isEmpty(hmreasontype) && hmreasontype.equalsIgnoreCase("OD") && TextUtils.isEmpty(hmMovementRegisterEntry)) {
            showSnackBar(getResources().getString(R.string.sel_hm_mov_reg));
            return false;
        }


        if (TextUtils.isEmpty(hwopresentTime)) {
            showSnackBar(getResources().getString(R.string.hwo_presence));
            return false;
        }
        if (hwopresentTime.equals(AppConstants.No) && TextUtils.isEmpty(hworeasontype)) {
            showSnackBar(getResources().getString(R.string.sel_leave_type));
            return false;
        }
        if (!TextUtils.isEmpty(hworeasontype) && hworeasontype.equalsIgnoreCase("Leaves") && TextUtils.isEmpty(hwocaptureLeavetype)) {
            showSnackBar(getResources().getString(R.string.sel_cap_leave_type));
            return false;
        }
        if (!TextUtils.isEmpty(hworeasontype) && hworeasontype.equalsIgnoreCase("OD") && TextUtils.isEmpty(hwocaptureodtype)) {
            showSnackBar(getResources().getString(R.string.sel_od_type));
            return false;
        }
        if (!TextUtils.isEmpty(hworeasontype) && hworeasontype.equalsIgnoreCase("OD") && TextUtils.isEmpty(hwomovementRegisterEntry)) {
            showSnackBar(getResources().getString(R.string.sel_hwo_mov_reg));
            return false;
        }


        if (TextUtils.isEmpty(hmheadQuarters)) {
            showSnackBar(getResources().getString(R.string.sel_hm_head_qua));
            return false;
        }
        if (!TextUtils.isEmpty(hmheadQuarters) && hmheadQuarters.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(hmstayingFacilitiesType)) {
            showSnackBar(getResources().getString(R.string.sel_stay_facility));
            return false;
        }
        if (!TextUtils.isEmpty(hmheadQuarters) && hmheadQuarters.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(hmcaptureDistance)) {
            showSnackBar(getResources().getString(R.string.sel_distance));
            return false;
        }

        if (TextUtils.isEmpty(hwoheadQuarters)) {
            showSnackBar(getResources().getString(R.string.sel_hwo_head_qua));
            return false;
        }
        if (!TextUtils.isEmpty(hwoheadQuarters) && hwoheadQuarters.equalsIgnoreCase(AppConstants.Yes) && TextUtils.isEmpty(hwostayingFacilitiesType)) {
            showSnackBar(getResources().getString(R.string.sel_stay_facility));
            return false;
        }
        if (!TextUtils.isEmpty(hwoheadQuarters) && hwoheadQuarters.equalsIgnoreCase(AppConstants.No) && TextUtils.isEmpty(hwocaptureDistance)) {
            showSnackBar(getResources().getString(R.string.sel_distance));
            return false;
        }
        if (TextUtils.isEmpty(staffQuarters)) {
            ScrollToView(binding.rgStaffQuarters);
            showSnackBar(getResources().getString(R.string.sel_staff_qua));
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
                final LiveData<Integer> liveData = instMainViewModel.getSectionId("GI");
                liveData.observe(GeneralInfoActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer id) {
                        liveData.removeObservers(GeneralInfoActivity.this);
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
