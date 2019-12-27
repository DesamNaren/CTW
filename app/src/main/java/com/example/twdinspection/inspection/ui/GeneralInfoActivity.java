package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.databinding.ActivityGeneralInfoBinding;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.inspection.viewmodel.GeneralInfoViewModel;

public class GeneralInfoActivity extends BaseActivity {
    private static final String TAG = GeneralInfoActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ActivityGeneralInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_general_info, getResources().getString(R.string.general_info));

        GeneralInfoViewModel viewModel = new GeneralInfoViewModel(getApplication());
        binding.setViewModel(viewModel);
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


        binding.rgPresentTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPresentTime.getCheckedRadioButtonId();
                if (selctedItem == R.id.rb_no_present_time) {
                    binding.llLeavetype.setVisibility(View.VISIBLE);
                    binding.viewLeaveType.setVisibility(View.VISIBLE);

                    binding.rgLeavetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int selctedItem = binding.rgLeavetype.getCheckedRadioButtonId();
                            if (selctedItem == R.id.rb_od) {
                                binding.llOdCaptureType.setVisibility(View.VISIBLE);
                                binding.rgCapturetype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                        int selctedItem = binding.rgCapturetype.getCheckedRadioButtonId();
                                        if (selctedItem == R.id.rb_out_of_station) {
                                            binding.llMovementRegisterEntry.setVisibility(View.VISIBLE);
                                            binding.rgMovementRegisterEntry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                                    int selctedItem = binding.rgMovementRegisterEntry.getCheckedRadioButtonId();

                                                }
                                            });
                                        } else {
                                            binding.llMovementRegisterEntry.setVisibility(View.GONE);
                                            binding.rgMovementRegisterEntry.clearCheck();

                                        }
                                    }
                                });
                            } else {
                                binding.llOdCaptureType.setVisibility(View.GONE);
                                binding.rgCapturetype.clearCheck();
                                binding.rgMovementRegisterEntry.clearCheck();
                            }
                        }
                    });
                } else {
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

                    binding.llCapture.setVisibility(View.VISIBLE);
                    binding.llCaptureDistance.setVisibility(View.VISIBLE);

                    binding.llCaptureStayingFacilitiesType.setVisibility(View.GONE);
                    binding.rgCaptureStayingFacilitiesType.clearCheck();

                    binding.rgCaptureDistance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int selctedItem = binding.rgCaptureDistance.getCheckedRadioButtonId();

                        }
                    });
                } else {
                    binding.llCapture.setVisibility(View.VISIBLE);
                    binding.llCaptureDistance.setVisibility(View.GONE);
                    binding.rgCaptureDistance.clearCheck();

                    binding.llCaptureStayingFacilitiesType.setVisibility(View.VISIBLE);

                    binding.rgCaptureStayingFacilitiesType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            int selctedItem = binding.rgCaptureStayingFacilitiesType.getCheckedRadioButtonId();
                        }
                    });
                }
            }
        });

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeneralInfoActivity.this, StudentsAttendance_2.class));
            }
        });
    }

}
