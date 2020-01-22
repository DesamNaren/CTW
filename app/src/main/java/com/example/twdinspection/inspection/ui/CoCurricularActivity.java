package com.example.twdinspection.inspection.ui;

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

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.custom.CustomFontEditText;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.common.utils.AppConstants;
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
    String participationStr, stuNameStr, studClassStr, locationStr, levelStr, eventStr, plantTypeStr, plantCntStr;
    CoordinatorLayout rootLayout;
    CoordinatorLayout bottomll;
    SharedPreferences sharedPreferences;
    String instId, officerId, insTime;
    BottomSheetDialog dialog;

    String spo_mat_rec, entry_stock, game_sport_room, pd_pft_name, mainMobNum;
    String play_avail, plan_play, land_measure, ground_level_status, scouts_guides_status, scout_guide_name, scut_guide_num;
    String ncc_stu_impl, ncc_teacher_name, ncc_teacher_contact, ncc_teacher_battalion_num;
    String smc_election_status, smc_chai_name, smc_chai_contact, smc_parents_meeting;
    String kitchen_garden_status, kitchen_event_in_charge_name, type_kitchen_garden, kitchen_in_charge_name, kitchen_in_charge_contact;
    String harita_haram_status, student_council_ele_status, stu_com_name_dis_status;
    String stu_com_name_dis_status_reason, stu_Cou_cap_name, stu_Cou_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_co_curricular, getString(R.string.co_cir));
        cocurricularViewModel = ViewModelProviders.of(CoCurricularActivity.this,
                new CocurricularCustomViewModel(binding, this, getApplication())).get(CocurricularViewModel.class);
        binding.setViewModel(cocurricularViewModel);


        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitValidate()) {
                    startActivity(new Intent(CoCurricularActivity.this, EntitlementsActivity.class));
                }
            }
        });
        binding.rgSportMatRec.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sportRecYes) {
                    spo_mat_rec = AppConstants.Yes;
                    binding.llEnteredStock.setVisibility(View.VISIBLE);

                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_sportRecNo) {
                    spo_mat_rec = AppConstants.No;
                    binding.llEnteredStock.setVisibility(View.GONE);
                } else {
                    spo_mat_rec = AppConstants.No;
                    binding.llEnteredStock.setVisibility(View.GONE);
                }
            }
        });
        binding.entryRegisterRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.entry_register_yes) {
                    entry_stock = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.entry_register_no) {
                    entry_stock = AppConstants.No;
                } else {
                    entry_stock = AppConstants.No;
                }
            }
        });

        binding.gameSportRoomRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.game_room_yes) {
                    game_sport_room = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.game_room_no) {
                    game_sport_room = AppConstants.No;
                } else {
                    game_sport_room = AppConstants.No;
                }
            }
        });

        binding.rgPlayGrAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_playGrAvailYes) {
                    play_avail = AppConstants.Yes;
                    binding.measLandAvail.setVisibility(View.VISIBLE);
                    binding.llPlanToProcure.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_playGrAvailNo) {
                    binding.llPlanToProcure.setVisibility(View.VISIBLE);
                    binding.measLandAvail.setVisibility(View.GONE);
                    play_avail = AppConstants.No;
                } else {
                    binding.measLandAvail.setVisibility(View.GONE);
                    binding.llPlanToProcure.setVisibility(View.GONE);
                    play_avail = AppConstants.No;
                }
            }
        });

        binding.rgPlanToProc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_planToProcYes) {
                    plan_play = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_planToProcNo) {
                    plan_play = AppConstants.No;
                } else {
                    plan_play = AppConstants.No;
                }
            }
        });

        binding.groundLeivingRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.ground_lev_yes) {
                    ground_level_status = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.ground_lev_no) {
                    ground_level_status = AppConstants.No;
                } else {
                    ground_level_status = AppConstants.No;
                }
            }
        });
        binding.rgScoutsImpl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_scoutsYes) {
                    scouts_guides_status = AppConstants.Yes;
                    binding.llScoutEntries.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_scoutsNo) {
                    binding.llScoutEntries.setVisibility(View.GONE);
                    scouts_guides_status = AppConstants.No;
                } else {
                    binding.llScoutEntries.setVisibility(View.GONE);
                    scouts_guides_status = AppConstants.No;
                }
            }
        });

        binding.rgNccStudImpl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nccStudImpYes) {
                    binding.llNccEntries.setVisibility(View.VISIBLE);
                    ncc_stu_impl = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nccStudImpNo) {
                    binding.llNccEntries.setVisibility(View.GONE);
                    ncc_stu_impl = AppConstants.No;
                } else {
                    binding.llNccEntries.setVisibility(View.GONE);
                    ncc_stu_impl = AppConstants.No;
                }
            }
        });

        binding.rgSmcElections.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_smcElectionsYes) {
                    smc_election_status = AppConstants.Yes;
                    binding.llSmcElectionsEnties.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_smcElectionsNo) {
                    smc_election_status = AppConstants.No;
                    binding.llSmcElectionsEnties.setVisibility(View.GONE);
                } else {
                    smc_election_status = AppConstants.No;
                    binding.llSmcElectionsEnties.setVisibility(View.GONE);
                }
            }
        });

        binding.rgKitchenGarAvail.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_kitchenAvailYes) {
                    binding.llKitchenEntries.setVisibility(View.VISIBLE);
                    kitchen_garden_status = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_kitchenAvailNo) {
                    binding.llKitchenEntries.setVisibility(View.GONE);
                    kitchen_garden_status = AppConstants.No;
                } else {
                    binding.llKitchenEntries.setVisibility(View.GONE);
                    kitchen_garden_status = AppConstants.No;
                }
            }
        });

        binding.rgHarithaharam.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_harithaharamYes) {
                    harita_haram_status = AppConstants.Yes;
                    binding.llPlants.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_harithaharamNo) {
                    harita_haram_status = AppConstants.No;
                    binding.llPlants.setVisibility(View.GONE);
                } else {
                    harita_haram_status = AppConstants.No;
                    binding.llPlants.setVisibility(View.GONE);
                }
            }
        });
        binding.rgStudCounElect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_studCounElecYes) {
                    student_council_ele_status = AppConstants.Yes;
                    binding.llNameOfCommDisplayed.setVisibility(View.VISIBLE);
                    binding.llStudCommEntries.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_studCounElecNo) {
                    student_council_ele_status = AppConstants.No;
                    binding.llStudCommEntries.setVisibility(View.VISIBLE);
                    binding.llNameOfCommDisplayed.setVisibility(View.GONE);
                } else {
                    student_council_ele_status = AppConstants.No;
                    binding.llNameOfCommDisplayed.setVisibility(View.GONE);
                    binding.llStudCommEntries.setVisibility(View.GONE);
                }
            }
        });

        binding.rgNameOfCommDisp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nameOfCommYes) {
                    stu_com_name_dis_status = AppConstants.Yes;
                    binding.etReason.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nameOfCommNo) {
                    stu_com_name_dis_status = AppConstants.No;
                    binding.etReason.setVisibility(View.VISIBLE);
                } else {
                    stu_com_name_dis_status = AppConstants.No;
                    binding.etReason.setVisibility(View.GONE);
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
        boolean returnFlag = true;
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

    private boolean submitValidate() {
        pd_pft_name = binding.pdPftEt.getText().toString();
        mainMobNum = binding.pdMobEt.getText().toString();
        land_measure = binding.etLand.getText().toString();

        scout_guide_name = binding.etScGuNmae.getText().toString();
        scut_guide_num = binding.etScGuCtc.getText().toString();

        ncc_teacher_name = binding.nccTeaName.getText().toString();
        ncc_teacher_contact = binding.nccTeaCtc.getText().toString();
        ncc_teacher_battalion_num = binding.nccBatNum.getText().toString();

        smc_chai_name = binding.smcName.getText().toString();
        smc_chai_contact = binding.smcNum.getText().toString();
        smc_parents_meeting = binding.smcCon.getText().toString();

        kitchen_event_in_charge_name = binding.kitEveName.getText().toString();
        type_kitchen_garden = binding.kitType.getText().toString();
        kitchen_in_charge_name = binding.kitInChargeName.getText().toString();
        kitchen_in_charge_contact = binding.kitInChaCtc.getText().toString();

        stu_com_name_dis_status_reason = binding.reasonEt.getText().toString();
        stu_Cou_cap_name = binding.couName.getText().toString();

        boolean returnFlag = true;
        if (TextUtils.isEmpty(spo_mat_rec)) {
            showSnackBar("Select sports material received");
            returnFlag = false;
        } else if (spo_mat_rec.equals(AppConstants.Yes) && TextUtils.isEmpty(entry_stock)) {
            showSnackBar("Select stock entry register");
            returnFlag = false;
        } else if (TextUtils.isEmpty(game_sport_room)) {
            showSnackBar("Select game & sport room available");
            returnFlag = false;
        } else if (TextUtils.isEmpty(pd_pft_name)) {
            binding.pdPftEt.requestFocus();
            showSnackBar("Enter name of PD/PFT");
            returnFlag = false;
        } else if (TextUtils.isEmpty(mainMobNum)) {
            binding.pdMobEt.requestFocus();
            showSnackBar("Enter mobile number");
            returnFlag = false;
        } else if (mainMobNum.length() != 10) {
            binding.pdMobEt.requestFocus();
            showSnackBar("Enter valid mobile number");
            returnFlag = false;
        } else if (!(mainMobNum.startsWith("6")
                || mainMobNum.startsWith("7") || mainMobNum.startsWith("8") || mainMobNum.startsWith("9"))) {
            binding.pdMobEt.requestFocus();
            showSnackBar("Enter valid mobile number");
            returnFlag = false;
        } else if (TextUtils.isEmpty(play_avail)) {
            showSnackBar("Select play ground available");
            returnFlag = false;
        } else if (play_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(land_measure)) {
            binding.measLandAvail.requestFocus();
            showSnackBar("Enter measurement of land");
            returnFlag = false;
        } else if (play_avail.equals(AppConstants.No) && TextUtils.isEmpty(plan_play)) {
            showSnackBar("Select plan to procure play ground");
            returnFlag = false;
        } else if (TextUtils.isEmpty(ground_level_status)) {
            showSnackBar("Select ground levelling status");
            returnFlag = false;
        } else if (TextUtils.isEmpty(scouts_guides_status)) {
            showSnackBar("Select scouts / guides implementation status");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(scout_guide_name)) {
            binding.scoutCapName.requestFocus();
            showSnackBar("Enter scout / guide captain name");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(scut_guide_num)) {
            binding.scoutsMobNo.requestFocus();
            showSnackBar("Enter scout / guide contact number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && scut_guide_num.length() != 10) {
            binding.scoutsMobNo.requestFocus();
            showSnackBar("Enter scout / guide valid mobile number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && !(scut_guide_num.startsWith("6")
                || scut_guide_num.startsWith("7") || scut_guide_num.startsWith("8") || scut_guide_num.startsWith("9"))) {
            binding.scoutsMobNo.requestFocus();
            showSnackBar("Enter scout / guide valid mobile number");
            returnFlag = false;
        }



        else if (TextUtils.isEmpty(ncc_stu_impl)) {
            showSnackBar("Select NCC student implementation status");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && TextUtils.isEmpty(ncc_teacher_name)) {
            binding.nccTeachName.requestFocus();
            showSnackBar("Enter NCC teacher name");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && TextUtils.isEmpty(ncc_teacher_contact)) {
            binding.nccTeachMobNo.requestFocus();
            showSnackBar("Enter NCC contact number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && ncc_teacher_contact.length() != 10) {
            binding.nccTeachMobNo.requestFocus();
            showSnackBar("Enter valid NCC teacher mobile number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && !(ncc_teacher_contact.startsWith("6")
                || ncc_teacher_contact.startsWith("7") || ncc_teacher_contact.startsWith("8") || ncc_teacher_contact.startsWith("9"))) {
            binding.nccTeachMobNo.requestFocus();
            showSnackBar("Enter valid NCC teacher mobile number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && TextUtils.isEmpty(ncc_teacher_battalion_num)) {
            binding.nccBatallionNo.requestFocus();
            showSnackBar("Enter NCC battalion number");
            returnFlag = false;
        }


        else if (TextUtils.isEmpty(smc_election_status)) {
            showSnackBar("Select SMC elections status");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(smc_chai_name)) {
            binding.smcChiName.requestFocus();
            showSnackBar("Enter SMC chairman name");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(smc_chai_contact)) {
            binding.smcChiCtc.requestFocus();
            showSnackBar("Enter SMC chairman contact number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && smc_chai_contact.length() != 10) {
            binding.smcChiCtc.requestFocus();
            showSnackBar("Enter valid SMC chairman mobile number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && !(smc_chai_contact.startsWith("6")
                || smc_chai_contact.startsWith("7") || smc_chai_contact.startsWith("8") || smc_chai_contact.startsWith("9"))) {
            binding.smcChiCtc.requestFocus();
            showSnackBar("Enter valid SMC chairman mobile number");
            returnFlag = false;
        } else if (TextUtils.isEmpty(kitchen_garden_status)) {
            showSnackBar("Select kitchen garden status");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(kitchen_event_in_charge_name)) {
            binding.eventKitchen.requestFocus();
            showSnackBar("Enter event of kitchen in charge");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(type_kitchen_garden)) {
            binding.typeKitchen.requestFocus();
            showSnackBar("Enter type of kitchen gardens");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(kitchen_in_charge_name)) {
            binding.kitchenInchargeName.requestFocus();
            showSnackBar("Enter kitchen garden in charge / teacher name");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(kitchen_in_charge_contact)) {
            binding.kitchenInchargeCtc.requestFocus();
            showSnackBar("Enter kitchen in charge contact number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && kitchen_in_charge_contact.length() != 10) {
            binding.kitchenInchargeCtc.requestFocus();
            showSnackBar("Enter valid kitchen in charge contact number");
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && !(kitchen_in_charge_contact.startsWith("6")
                || kitchen_in_charge_contact.startsWith("7") || kitchen_in_charge_contact.startsWith("8") || kitchen_in_charge_contact.startsWith("9"))) {
            binding.kitchenInchargeCtc.requestFocus();
            showSnackBar("Enter valid kitchen in charge contact number");
            returnFlag = false;
        } else if (TextUtils.isEmpty(harita_haram_status)) {
            showSnackBar("Select harita haram conducted status");
            returnFlag = false;
        } else if (TextUtils.isEmpty(student_council_ele_status)) {
            showSnackBar("Select student council election conducted status");
            returnFlag = false;
        } else if (student_council_ele_status.equals(AppConstants.Yes) && TextUtils.isEmpty(stu_com_name_dis_status)) {
            showSnackBar("Select student council name displayed status");
            returnFlag = false;
        } else if (student_council_ele_status.equals(AppConstants.No) && TextUtils.isEmpty(stu_com_name_dis_status_reason)) {
            binding.etReason.requestFocus();
            showSnackBar("Enter reason");
            returnFlag = false;
        } else if (TextUtils.isEmpty(stu_Cou_cap_name)) {
            showSnackBar("Enter student council captain name");
            returnFlag = false;
        } else if (TextUtils.isEmpty(stu_Cou_date)) {
            showSnackBar("Enter student council date");
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
                        stu_Cou_date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        binding.etMedicalCheckupDate.setText(stu_Cou_date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
    }

}
