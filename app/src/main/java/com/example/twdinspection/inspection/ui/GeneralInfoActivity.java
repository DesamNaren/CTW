package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityGeneralInfoBinding;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.inspection.source.GeneralInformation.GeneralInfoEntity;
import com.example.twdinspection.inspection.viewmodel.GeneralInfoViewModel;

public class GeneralInfoActivity extends BaseActivity {
    private static final String TAG = GeneralInfoActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityGeneralInfoBinding binding;
    GeneralInfoViewModel generalInfoViewModel;
    GeneralInfoEntity generalInfoEntity;
    String headQuarters;
    String presentTime, leavetype, capturetype, movementRegisterEntry, staffQuarters, stayingFacilitiesType, captureDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_general_info, getResources().getString(R.string.general_info));

        generalInfoViewModel = new GeneralInfoViewModel(getApplication());
        binding.setViewModel(generalInfoViewModel);
        binding.executePendingBindings();


        try {
            sharedPreferences = TWDApplication.get(this).getPreferences();
            editor = sharedPreferences.edit();
            binding.includeBasicLayout.offNme.setText(sharedPreferences.getString(AppConstants.OFFICER_NAME, ""));
            binding.includeBasicLayout.offDes.setText(sharedPreferences.getString(AppConstants.OFFICER_DES, ""));
            binding.includeBasicLayout.inspectionTime.setText(sharedPreferences.getString(AppConstants.INSP_TIME, ""));
            binding.manNameTv.setText(sharedPreferences.getString(AppConstants.MAN_NAME, ""));
            binding.instNameTv.setText(sharedPreferences.getString(AppConstants.INST_NAME, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        int selectedId = binding.rgPresentTime.getCheckedRadioButtonId();

        binding.rgHeadQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgHeadQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_head_quarters)
                    headQuarters = "YES";
                else
                    headQuarters = "NO";
            }
        });
        binding.rgStaffQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_yes_staff_quarters)
                    staffQuarters = "YES";
                else
                    staffQuarters = "NO";
            }
        });

        binding.rgPresentTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPresentTime.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_present_time) {
                    presentTime = "YES";
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
                            } else {
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
                                                        movementRegisterEntry = "YES";
                                                    else
                                                        movementRegisterEntry = "NO";
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

                                        } else {
                                            capturetype = "State Level";
                                            binding.llMovementRegisterEntry.setVisibility(View.GONE);
                                            binding.rgMovementRegisterEntry.clearCheck();

                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    presentTime = "NO";

                    binding.llLeavetype.setVisibility(View.GONE);
                    binding.viewLeaveType.setVisibility(View.GONE);

                    binding.rgLeavetype.clearCheck();
                    binding.rgCapturetype.clearCheck();
                    binding.rgMovementRegisterEntry.clearCheck();
                }

            }
        });

        binding.rgStaffQuarters.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffQuarters.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_staff_quarters) {
                    staffQuarters = "YES";
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
                            else
                                captureDistance = "ABOVE 50";

                        }
                    });
                } else {
                    staffQuarters = "NO";
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
                            else
                                stayingFacilitiesType = "Pvt. Building";
                        }
                    });
                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                generalInfoEntity = new GeneralInfoEntity();
                generalInfoEntity.setHM_HWO_presence(presentTime);
                generalInfoEntity.setHaving_headquarters(headQuarters);
                generalInfoEntity.setLeaveType(leavetype);
                generalInfoEntity.setCapturetype(capturetype);
                generalInfoEntity.setMovementRegisterEntry(movementRegisterEntry);
                generalInfoEntity.setCaptureDistance(captureDistance);
                generalInfoEntity.setStaffQuarters(staffQuarters);
                generalInfoEntity.setStayingFacilitiesType(stayingFacilitiesType);

                long x = generalInfoViewModel.insertGeneralInfo(generalInfoEntity);
//                Toast.makeText(GeneralInfoActivity.this, "Inserted " + x, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(GeneralInfoActivity.this, StudentsAttendance_2.class));
            }
        });
    }

}
