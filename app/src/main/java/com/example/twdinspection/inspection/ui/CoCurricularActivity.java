package com.example.twdinspection.inspection.ui;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.custom.CustomFontEditText;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityCoCurricularBinding;
import com.example.twdinspection.inspection.source.cocurriularActivities.PlantsEntity;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.viewmodel.CocurricularCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.CocurricularViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class CoCurricularActivity extends BaseActivity {

    ActivityCoCurricularBinding binding;
    int slNoCnt = 0;
    CocurricularViewModel cocurricularViewModel;
    String participationStr, stuNameStr, studClassStr, locationStr, levelStr, eventStr,plantTypeStr, plantCntStr;
    CoordinatorLayout rootLayout;
    CoordinatorLayout bottomll;
    SharedPreferences sharedPreferences;
    String instId, officerId;
    BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_co_curricular," Co Circulator & Extra Curricular Activities");
        cocurricularViewModel = ViewModelProviders.of(CoCurricularActivity.this,
                new CocurricularCustomViewModel(binding, this, getApplication())).get(CocurricularViewModel.class);
        binding.setViewModel(cocurricularViewModel);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoCurricularActivity.this, EntitlementsActivity.class));
            }
        });
        binding.rgSportMatRec.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sportRecYes) {
                    binding.llEnteredStock.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sportRecNo) {
                    binding.llEnteredStock.setVisibility(View.GONE);
                }
            }
        });


        binding.btnViewStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoCurricularActivity.this, CocurricularStudAchActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        binding.btnViewplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoCurricularActivity.this, PlantsInfoActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        binding.btnAddplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showplantDetails();
            }
        });
        binding.rgPlayGrAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_playGrAvailYes) {
                    binding.llPlanToProcure.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_playGrAvailNo) {
                    binding.llPlanToProcure.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.rgHarithaharam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_harithaharamYes) {
                    binding.llPlants.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_harithaharamNo) {
                    binding.llPlants.setVisibility(View.GONE);
                }
            }
        });
        binding.rgPlanToProc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_planToProcYes) {
                    binding.measLandAvail.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_planToProcNo) {
                    binding.measLandAvail.setVisibility(View.GONE);
                }
            }
        });
        binding.rgScoutsImpl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_scoutsYes) {
                    binding.llScoutEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_scoutsNo) {
                    binding.llScoutEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.rgNccStudImpl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nccStudImpYes) {
                    binding.llNccEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nccStudImpNo) {
                    binding.llNccEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.rgSmcElections.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_smcElectionsYes) {
                    binding.llSmcElectionsEnties.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_smcElectionsNo) {
                    binding.llSmcElectionsEnties.setVisibility(View.GONE);
                }
            }
        });
        binding.rgKitchenGarAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_kitchenAvailYes) {
                    binding.llKitchenEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_kitchenAvailNo) {
                    binding.llKitchenEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.rgStudCounElect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_studCounElecYes) {
                    binding.llNameOfCommDisplayed.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_studCounElecNo) {
                    binding.llStudCommEntries.setVisibility(View.GONE);
                    binding.llNameOfCommDisplayed.setVisibility(View.GONE);
                }
            }
        });
        binding.rgNameOfCommDisp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nameOfCommYes) {
                    binding.llStudCommEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nameOfCommNo) {
                    binding.llStudCommEntries.setVisibility(View.GONE);
                }
            }
        });
        binding.etMedicalCheckupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastMeetingDateSelection();
            }
        });
        binding.btnAddStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStudAcheivementDetails();
            }
        });


        LiveData<Integer> liveData = cocurricularViewModel.getAchievementsCnt();
        liveData.observe(CoCurricularActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cnt) {
                if (cnt != null && cnt > 0) {
                    binding.btnViewStud.setVisibility(View.VISIBLE);
                    slNoCnt = cnt;
                } else {
                    slNoCnt = 0;
                    binding.btnViewStud.setVisibility(View.GONE);
                }
            }
        });

        LiveData<Integer> liveData1 = cocurricularViewModel.getPlantsCnt();
        liveData1.observe(CoCurricularActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cnt) {
                if (cnt != null && cnt > 0) {
                    binding.rbHarithaharamYes.setChecked(true);
                    binding.btnViewplant.setVisibility(View.VISIBLE);
                    slNoCnt = cnt;
                } else {
                    binding.rbHarithaharamYes.setChecked(false);
                    slNoCnt = 0;
                    binding.btnViewplant.setVisibility(View.GONE);
                    binding.llPlants.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showStudAcheivementDetails() {
        View view = getLayoutInflater().inflate(R.layout.stud_acheivement_bottom_sheet, null);
        bottomll = view.findViewById(R.id.bottomll);
        dialog = new BottomSheetDialog(CoCurricularActivity.this);
        LinearLayout ll_entries = view.findViewById(R.id.ll_entries);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(ll_entries);
        bottomSheetBehavior.setPeekHeight(2500);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        CustomFontTextView slNo = view.findViewById(R.id.tv_slCount);
        slNo.setText(String.valueOf(slNoCnt + 1));
        CustomFontEditText stuNameEt = view.findViewById(R.id.et_studName);
        CustomFontEditText classEt = view.findViewById(R.id.et_class);
        CustomFontEditText locationEt = view.findViewById(R.id.et_location);
        CustomFontEditText levelEt = view.findViewById(R.id.et_level);
        CustomFontEditText eventEt = view.findViewById(R.id.et_event);
        RadioGroup participationRg = view.findViewById(R.id.rg_participated);

        ImageView btn_save = view.findViewById(R.id.btn_save);
        ImageView btn_close = view.findViewById(R.id.ic_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        participationRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_participatedYes) {
                    participationStr = AppConstants.PARTICIPATED;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_participatedNo) {
                    participationStr = AppConstants.WIN;
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {
                    stuNameStr = stuNameEt.getText().toString().trim();
                    studClassStr = classEt.getText().toString().trim();
                    locationStr = locationEt.getText().toString().trim();
                    levelStr = levelEt.getText().toString().trim();
                    eventStr = eventEt.getText().toString().trim();

                    if (validate()) {

                        StudAchievementEntity studAchievementEntity = new StudAchievementEntity();
                        studAchievementEntity.setSl_no(slNoCnt + 1);
                        studAchievementEntity.setStudclass(studClassStr);
                        studAchievementEntity.setStudname(stuNameStr);
                        studAchievementEntity.setParticipated_win(participationStr);
                        studAchievementEntity.setEvent(eventStr);
                        studAchievementEntity.setLevel(levelStr);
                        studAchievementEntity.setWin_location(locationStr);

                        long x = cocurricularViewModel.insertAchievementInfo(studAchievementEntity);
                        dialog.dismiss();
                        if (x >= 0) {
                            showSnackBar(getResources().getString(R.string.data_saved));
                        }
                    }
                }
            }
        });

    }
    public void showplantDetails() {
        View view = getLayoutInflater().inflate(R.layout.plant_details_bottom_sheet, null);
        rootLayout = view.findViewById(R.id.root_cl);
        dialog = new BottomSheetDialog(CoCurricularActivity.this);
        LinearLayout ll_entries = view.findViewById(R.id.ll_entries);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(ll_entries);
        bottomSheetBehavior.setPeekHeight(1500);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        CustomFontTextView slNo = view.findViewById(R.id.tv_slCount);
        slNo.setText(String.valueOf(slNoCnt + 1));
        CustomFontEditText plantTypeEt = view.findViewById(R.id.et_plantType);
        CustomFontEditText plantCntEt = view.findViewById(R.id.et_plantsCnt);

        ImageView btn_save = view.findViewById(R.id.btn_save);
        ImageView btn_close = view.findViewById(R.id.ic_close);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {
                    plantTypeStr = plantTypeEt.getText().toString().trim();
                    plantCntStr = plantCntEt.getText().toString().trim();


                    if (validatePlantsData()) {

                        PlantsEntity plantsEntity = new PlantsEntity();
                        plantsEntity.setSl_no(slNoCnt + 1);
                        plantsEntity.setPlantType(plantTypeStr);
                        plantsEntity.setPlant_cnt(plantCntStr);
                        long x = cocurricularViewModel.insertPlantInfo(plantsEntity);
                        dialog.dismiss();
                        if (x >= 0) {
                            showSnackBar(getResources().getString(R.string.data_saved));
                        }
                    }
                }
            }
        });

    }

    private boolean validatePlantsData() {
        boolean returnFlag=true;
        if (TextUtils.isEmpty(plantTypeStr)) {
            showBottomSheetSnackBar("Please enter plant type");
            returnFlag = false;
        } else if (TextUtils.isEmpty(plantCntStr)) {
            showBottomSheetSnackBar("Please enter no of plants");
            returnFlag = false;
        }
        return returnFlag;
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(stuNameStr)) {
            showStudBottomSheetSnackBar("Please enter student name");
            returnFlag = false;
        } else if (TextUtils.isEmpty(studClassStr)) {
            showStudBottomSheetSnackBar("Please enter student class");
            returnFlag = false;
        } else if (TextUtils.isEmpty(locationStr)) {
            showStudBottomSheetSnackBar("Please enter winning place location");
            returnFlag = false;
        } else if (TextUtils.isEmpty(levelStr)) {
            showStudBottomSheetSnackBar("Please enter level");
            returnFlag = false;
        } else if (TextUtils.isEmpty(eventStr)) {
            showStudBottomSheetSnackBar("Please enter event");
            returnFlag = false;
        } else if (TextUtils.isEmpty(participationStr)) {
            showStudBottomSheetSnackBar("Please enter whether student participated/won");
            returnFlag = false;
        } else if (TextUtils.isEmpty(studClassStr)) {
            showStudBottomSheetSnackBar("Please enter student class");
            returnFlag = false;
        }
        return returnFlag;
    }

    private void showStudBottomSheetSnackBar(String str) {
        Snackbar.make(bottomll, str, Snackbar.LENGTH_SHORT).show();
    }
    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(rootLayout, str, Snackbar.LENGTH_SHORT).show();
    }
    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    private void lastMeetingDateSelection() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(CoCurricularActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        binding.etMedicalCheckupDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
    }

}
