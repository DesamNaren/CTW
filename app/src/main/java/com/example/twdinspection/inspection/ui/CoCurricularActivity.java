package com.example.twdinspection.inspection.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
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
import android.widget.TextView;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.custom.CustomFontEditText;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityCoCurricularBinding;
import com.example.twdinspection.inspection.source.cocurriularActivities.StudAchievementEntity;
import com.example.twdinspection.inspection.viewmodel.CocurricularCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.CocurricularViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class CoCurricularActivity extends AppCompatActivity {

    ActivityCoCurricularBinding binding;
    int slNoCnt = 0;
    CocurricularViewModel cocurricularViewModel;
    String participationStr, stuNameStr, studClassStr, locationStr, levelStr, eventStr;
    CoordinatorLayout rootLayout;
    SharedPreferences sharedPreferences;
    String instId, officerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_co_curricular);
        cocurricularViewModel = ViewModelProviders.of(CoCurricularActivity.this,
                new CocurricularCustomViewModel(binding, this, getApplication())).get(CocurricularViewModel.class);
        binding.setViewModel(cocurricularViewModel);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText(" Co Circulator & Extra Curricular Activities");
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
                startActivity(new Intent(CoCurricularActivity.this, CocurricularStudAchActivity.class));
            }
        });
        binding.btnAddStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        binding.btnViewStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoCurricularActivity.this, CocurricularStudAchActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }

    public void showStudAcheivementDetails() {
        View view = getLayoutInflater().inflate(R.layout.stud_acheivement_bottom_sheet, null);
        rootLayout = view.findViewById(R.id.root_cl);
        BottomSheetDialog dialog = new BottomSheetDialog(CoCurricularActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        LinearLayout ll_entries = view.findViewById(R.id.ll_entries);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(ll_entries);
        bottomSheetBehavior.setPeekHeight(1500);
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
                        studAchievementEntity.setName(stuNameStr);
                        studAchievementEntity.setParticipated_win(participationStr);
                        studAchievementEntity.setEvent(eventStr);
                        studAchievementEntity.setLevel(levelStr);
                        studAchievementEntity.setWin_location(locationStr);
                        studAchievementEntity.setInspection_time(Utils.getCurrentDateTime());
                        studAchievementEntity.setInstitute_id(instId);
                        studAchievementEntity.setOfficer_id(officerId);

                        long x = cocurricularViewModel.insertAchievementInfo(studAchievementEntity);
                        dialog.dismiss();
                        if (x >= 0) {
                            showBottomSheetSnackBar(getResources().getString(R.string.data_saved));
                        }
                    }
                }
            }
        });

    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(stuNameStr)) {
            returnFlag = false;
            showBottomSheetSnackBar("Please enter student name");
        } else if (TextUtils.isEmpty(studClassStr)) {
            returnFlag = false;
            showBottomSheetSnackBar("Please enter student class");
        } else if (TextUtils.isEmpty(locationStr)) {
            returnFlag = false;
            showBottomSheetSnackBar("Please enter winning place location");
        } else if (TextUtils.isEmpty(levelStr)) {
            returnFlag = false;
            showBottomSheetSnackBar("Please enter level");
        } else if (TextUtils.isEmpty(eventStr)) {
            returnFlag = false;
            showBottomSheetSnackBar("Please enter event");
        } else if (TextUtils.isEmpty(participationStr)) {
            returnFlag = false;
            showBottomSheetSnackBar("Please enter whether student participated/won");
        } else if (TextUtils.isEmpty(studClassStr)) {
            returnFlag = false;
            showBottomSheetSnackBar("Please enter student class");
        }
        return returnFlag;
    }

    private void showBottomSheetSnackBar(String string) {
        Snackbar.make(rootLayout, string, Snackbar.LENGTH_SHORT).show();
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
