package com.cgg.twdinspection.inspection.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.custom.CustomFontEditText;
import com.cgg.twdinspection.common.custom.CustomFontTextView;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityCoCurricularBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.CoCurricularEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.PlantsEntity;
import com.cgg.twdinspection.inspection.source.cocurriular_activities.StudAchievementEntity;
import com.cgg.twdinspection.inspection.viewmodel.CocurricularCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.CocurricularViewModel;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CoCurricularActivity extends BaseActivity implements SaveListener {

    ActivityCoCurricularBinding binding;
    int slNoCnt = 0, achSloCnt = 0;
    CocurricularViewModel cocurricularViewModel;
    String participationStr, stuNameStr, studClassStr, locationStr, levelStr, eventStr, plantTypeStr, plantCntStr;
    CoordinatorLayout rootLayout;
    CoordinatorLayout bottomLl;
    SharedPreferences sharedPreferences;
    String instId, officerId, insTime;
    BottomSheetDialog dialog;
    private CoCurricularEntity coCurricularEntity;
    private InstMainViewModel instMainViewModel;

    String spo_mat_rec, entry_stock, game_sport_room, pd_pft_name, pd_pft_num, district_level, state_level, national_level;
    String play_avail, plan_play, land_measure, ground_level_status, scouts_guides_status, scout_guide_name, scut_guide_num;
    String ncc_stu_impl, ncc_teacher_name, ncc_teacher_contact, ncc_teacher_battalion_num;
    String smc_election_status, smc_chai_name, smc_chai_contact, smc_resolution, smc_parents_meeting;
    String kitchen_garden_status, type_kitchen_garden, kitchen_in_charge_name, kitchen_in_charge_contact, plants_count;
    String student_council_ele_status, stu_com_name_dis_status;
    String stu_com_name_dis_status_reason, stu_Cou_date;
    private List<StudAchievementEntity> studAchievementEntities;
    private List<PlantsEntity> plantsEntities;
    private int localFlag = -1;

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view, view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_co_curricular, getString(R.string.title_co_cir));

        TextView[] ids = new TextView[]{binding.slno1, binding.slno2, binding.slno3, binding.slno4, binding.slno5,
                binding.slno6, binding.slno7, binding.slno8};
        BaseActivity.setIds(ids, 70);

        cocurricularViewModel = ViewModelProviders.of(CoCurricularActivity.this,
                new CocurricularCustomViewModel(binding, this, getApplication())).get(CocurricularViewModel.class);
        binding.setViewModel(cocurricularViewModel);
        instMainViewModel = new InstMainViewModel(getApplication());


        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        insTime = sharedPreferences.getString(AppConstants.INSP_TIME, "");


        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (submitValidate()) {

                    coCurricularEntity = new CoCurricularEntity();
                    coCurricularEntity.setOfficer_id(officerId);
                    coCurricularEntity.setInspection_time(Utils.getCurrentDateTime());
                    coCurricularEntity.setInstitute_id(instId);

                    coCurricularEntity.setSport_mat_received(spo_mat_rec);
                    coCurricularEntity.setStock_entry_register(entry_stock);
                    coCurricularEntity.setGame_sport_room_avail(game_sport_room);
                    coCurricularEntity.setPd_pft_name(pd_pft_name);
                    coCurricularEntity.setPd_pft_contact(pd_pft_num);
                    coCurricularEntity.setDistrict_level(district_level);
                    coCurricularEntity.setState_level(state_level);
                    coCurricularEntity.setNational_level(national_level);

                    if (studAchievementEntities != null && studAchievementEntities.size() > 0) {
                        coCurricularEntity.setStudAchievementEntities(studAchievementEntities);
                    }

                    coCurricularEntity.setPlay_ground_avail(play_avail);
                    coCurricularEntity.setLand_mea_value(land_measure);
                    coCurricularEntity.setPlan_to_prc_land(plan_play);

                    coCurricularEntity.setGround_level_status(ground_level_status);

                    coCurricularEntity.setScouts_guides_impl_status(scouts_guides_status);
                    coCurricularEntity.setScout_guide_captain_name(scout_guide_name);
                    coCurricularEntity.setScout_guide_captain_contact(scut_guide_num);

                    coCurricularEntity.setNcc_impl_status(ncc_stu_impl);
                    coCurricularEntity.setNcc_teacher_name(ncc_teacher_name);
                    coCurricularEntity.setNcc_teacher_contact(ncc_teacher_contact);
                    coCurricularEntity.setNcc_teacher_battalion_num(ncc_teacher_battalion_num);

                    coCurricularEntity.setSmc_ele_status(smc_election_status);
                    coCurricularEntity.setSmc_ele_chairman_name(smc_chai_name);
                    coCurricularEntity.setSmc_ele_chairman_contact(smc_chai_contact);
                    coCurricularEntity.setSmc_ele_resolution(smc_resolution);
                    coCurricularEntity.setSmc_ele_con_parent_meeting(smc_parents_meeting);


                    coCurricularEntity.setKitchen_garden_status(kitchen_garden_status);
                    coCurricularEntity.setKitchen_garden_types(type_kitchen_garden);
                    coCurricularEntity.setKitchen_garden_in_charge_name(kitchen_in_charge_name);
                    coCurricularEntity.setKitchen_garden_in_charge_contact(kitchen_in_charge_contact);


                    coCurricularEntity.setStu_cou_ele_status(student_council_ele_status);
                    coCurricularEntity.setStu_com_display_status(stu_com_name_dis_status);
                    coCurricularEntity.setStu_cou_date(stu_Cou_date);
                    coCurricularEntity.setReason(stu_com_name_dis_status_reason);
                    coCurricularEntity.setPlants_cnt(plants_count);

                    Utils.customSaveAlert(CoCurricularActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
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
                    binding.entryRegisterRg.clearCheck();
                } else {
                    spo_mat_rec = null;
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
                    entry_stock = null;
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
                    game_sport_room = null;
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
                    binding.rgPlanToProc.clearCheck();
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_playGrAvailNo) {
                    play_avail = AppConstants.No;
                    binding.llPlanToProcure.setVisibility(View.VISIBLE);
                    binding.measLandAvail.setVisibility(View.GONE);
                    binding.etLand.setText("");
                } else {
                    binding.measLandAvail.setVisibility(View.GONE);
                    binding.llPlanToProcure.setVisibility(View.GONE);
                    binding.rgPlanToProc.clearCheck();
                    binding.etLand.setText("");
                    play_avail = null;
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
                    plan_play = null;
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
                    ground_level_status = null;
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
                    binding.etScGuNmae.setText("");
                    binding.etScGuCtc.setText("");
                    scouts_guides_status = AppConstants.No;
                } else {
                    binding.llScoutEntries.setVisibility(View.GONE);
                    binding.etScGuNmae.setText("");
                    binding.etScGuCtc.setText("");
                    scouts_guides_status = null;
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
                    binding.nccTeaName.setText("");
                    binding.nccTeaCtc.setText("");
                    binding.nccBatNum.setText("");
                } else {
                    binding.llNccEntries.setVisibility(View.GONE);
                    ncc_stu_impl = null;
                    binding.nccTeaName.setText("");
                    binding.nccTeaCtc.setText("");
                    binding.nccBatNum.setText("");
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

                    binding.smcName.setText("");
                    binding.smcNum.setText("");
                    binding.smcResolution.setText("");
                    binding.rgParentMeeting.clearCheck();
                } else {
                    smc_election_status = null;
                    binding.llSmcElectionsEnties.setVisibility(View.GONE);
                    binding.smcName.setText("");
                    binding.smcNum.setText("");
                    binding.smcResolution.setText("");
                    binding.rgParentMeeting.clearCheck();
                }
            }
        });

        binding.rgParentMeeting.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.parents_meeting_yes) {
                    smc_parents_meeting = AppConstants.Yes;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.parents_meeting_no) {
                    smc_parents_meeting = AppConstants.No;
                } else {
                    smc_parents_meeting = null;
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

                    binding.kitType.setText("");
                    binding.kitInChargeName.setText("");
                    binding.kitInChaCtc.setText("");
                    binding.etPlantsCnt.setText("");

                } else {
                    binding.llKitchenEntries.setVisibility(View.GONE);
                    kitchen_garden_status = null;

                    binding.kitType.setText("");
                    binding.kitInChargeName.setText("");
                    binding.kitInChaCtc.setText("");
                    binding.etPlantsCnt.setText("");
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
                    binding.reasonEt.setText("");

                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_studCounElecNo) {
                    student_council_ele_status = AppConstants.No;
                    binding.llStudCommEntries.setVisibility(View.VISIBLE);
                    binding.llNameOfCommDisplayed.setVisibility(View.GONE);
                    binding.rgNameOfCommDisp.clearCheck();

                } else {
                    student_council_ele_status = null;
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
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_nameOfCommNo) {
                    stu_com_name_dis_status = AppConstants.No;
                } else {
                    stu_com_name_dis_status = null;
                }
            }
        });


        binding.btnViewStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoCurricularActivity.this, CocurricularStudAchActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                        .putExtra(AppConstants.FROM_CLASS, AppConstants.COCAR));
            }
        });

        binding.etStuCouncilDate.setOnClickListener(new View.OnClickListener() {
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


        cocurricularViewModel.getAchievementInfo().observe(CoCurricularActivity.this, new Observer<List<StudAchievementEntity>>() {
            @Override
            public void onChanged(List<StudAchievementEntity> studAchievementEntities) {
                CoCurricularActivity.this.studAchievementEntities = studAchievementEntities;
            }
        });
        cocurricularViewModel.getPlantsInfo().observe(CoCurricularActivity.this, new Observer<List<PlantsEntity>>() {
            @Override
            public void onChanged(List<PlantsEntity> plantsEntities) {
                CoCurricularActivity.this.plantsEntities = plantsEntities;
            }
        });

        LiveData<Integer> liveData = cocurricularViewModel.getAchievementsCnt();
        liveData.observe(CoCurricularActivity.this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer cnt) {
                if (cnt != null && cnt > 0) {
                    binding.btnViewStud.setVisibility(View.VISIBLE);
                    achSloCnt = cnt;
                } else {
                    achSloCnt = 0;
                    binding.btnViewStud.setVisibility(View.GONE);
                }
            }
        });


        try {
            localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<CoCurricularEntity> coCurricularEntityLiveData = instMainViewModel.getCocurricularInfoData(instId);
                coCurricularEntityLiveData.observe(CoCurricularActivity.this, new Observer<CoCurricularEntity>() {
                    @Override
                    public void onChanged(CoCurricularEntity coCurricularEntity) {
                        coCurricularEntityLiveData.removeObservers(CoCurricularActivity.this);
                        if (coCurricularEntity != null) {
                            binding.setInspData(coCurricularEntity);
                            binding.executePendingBindings();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showStudAcheivementDetails() {
        View view = getLayoutInflater().inflate(R.layout.stud_acheivement_bottom_sheet, null);
        bottomLl = view.findViewById(R.id.bottom_ll);
        dialog = new BottomSheetDialog(CoCurricularActivity.this);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        CustomFontTextView slNo = view.findViewById(R.id.tv_slCount);
        slNo.setText(String.valueOf(achSloCnt + 1));
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
                        studAchievementEntity.setSl_no(achSloCnt + 1);
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
            showBottomSheetSnackBar(getString(R.string.plant_type));
            returnFlag = false;
        } else if (TextUtils.isEmpty(plantCntStr)) {
            showBottomSheetSnackBar(getString(R.string.no_of_plants));
            returnFlag = false;
        } else if (!(Integer.parseInt(plantCntStr) > 0)) {
            showBottomSheetSnackBar(getString(R.string.count_greater_than_zero));
            returnFlag = false;
        }
        return returnFlag;
    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(stuNameStr)) {
            showStudBottomSheetSnackBar(getString(R.string.plz_enter_stu_name));
            returnFlag = false;
        } else if (TextUtils.isEmpty(studClassStr)) {
            showStudBottomSheetSnackBar(getString(R.string.plz_enter_stu_class));
            returnFlag = false;
        } else if (TextUtils.isEmpty(locationStr)) {
            showStudBottomSheetSnackBar(getString(R.string.winning_prize_loc));
            returnFlag = false;
        } else if (TextUtils.isEmpty(levelStr)) {
            showStudBottomSheetSnackBar(getString(R.string.enter_level));
            returnFlag = false;
        } else if (TextUtils.isEmpty(eventStr)) {
            showStudBottomSheetSnackBar(getString(R.string.enter_event));
            returnFlag = false;
        } else if (TextUtils.isEmpty(participationStr)) {
            showStudBottomSheetSnackBar(getString(R.string.enter_stud_part));
            returnFlag = false;
        } else if (TextUtils.isEmpty(studClassStr)) {
            showStudBottomSheetSnackBar(getString(R.string.plz_enter_stu_class));
            returnFlag = false;
        }
        return returnFlag;
    }

    private boolean submitValidate() {
        pd_pft_name = binding.pdPftEt.getText().toString();
        pd_pft_num = binding.pdMobEt.getText().toString();
        district_level = binding.etDistrict.getText().toString();
        state_level = binding.etState.getText().toString();
        national_level = binding.etNational.getText().toString();
        land_measure = binding.etLand.getText().toString();

        scout_guide_name = binding.etScGuNmae.getText().toString();
        scut_guide_num = binding.etScGuCtc.getText().toString();

        ncc_teacher_name = binding.nccTeaName.getText().toString();
        ncc_teacher_contact = binding.nccTeaCtc.getText().toString();
        ncc_teacher_battalion_num = binding.nccBatNum.getText().toString();

        smc_chai_name = binding.smcName.getText().toString();
        smc_chai_contact = binding.smcNum.getText().toString();
        smc_resolution = binding.smcResolution.getText().toString();

        type_kitchen_garden = binding.kitType.getText().toString();
        kitchen_in_charge_name = binding.kitInChargeName.getText().toString();
        kitchen_in_charge_contact = binding.kitInChaCtc.getText().toString();
        plants_count = binding.etPlantsCnt.getText().toString();

        stu_com_name_dis_status_reason = binding.reasonEt.getText().toString();
        stu_Cou_date = binding.etStuCouncilDate.getText().toString();

        boolean returnFlag = true;
        if (TextUtils.isEmpty(spo_mat_rec)) {
            ScrollToView(binding.rgSportMatRec);
            showSnackBar(getString(R.string.sel_spo_mat));
            returnFlag = false;
        } else if (spo_mat_rec.equals(AppConstants.Yes) && TextUtils.isEmpty(entry_stock)) {
            showSnackBar(getString(R.string.stock_entry_reg));
            returnFlag = false;
        } else if (TextUtils.isEmpty(game_sport_room)) {
            ScrollToView(binding.gameSportRoomRg);
            showSnackBar(getString(R.string.game_sport));
            returnFlag = false;
        } else if (TextUtils.isEmpty(pd_pft_name)) {
            binding.pdPftEt.requestFocus();
            showSnackBar(getString(R.string.pd_pft_name_enter));
            returnFlag = false;
        } else if (TextUtils.isEmpty(pd_pft_num)) {
            binding.pdMobEt.requestFocus();
            showSnackBar(getString(R.string.ent_mob_num));
            returnFlag = false;
        } else if (pd_pft_num.length() != 10) {
            binding.pdMobEt.requestFocus();
            showSnackBar(getString(R.string.ent_val_mob_num));
            returnFlag = false;
        } else if (!(pd_pft_num.startsWith("6")
                || pd_pft_num.startsWith("7") || pd_pft_num.startsWith("8") || pd_pft_num.startsWith("9"))) {
            binding.pdMobEt.requestFocus();
            showSnackBar(getString(R.string.ent_val_mob_num));
            returnFlag = false;
        } else if (TextUtils.isEmpty(district_level)) {
            binding.etDistrict.requestFocus();
            showSnackBar(getString(R.string.ent_dis_lvel));
            returnFlag = false;
        } else if (TextUtils.isEmpty(state_level)) {
            binding.etState.requestFocus();
            showSnackBar(getString(R.string.ent_state_level));
            returnFlag = false;
        } else if (TextUtils.isEmpty(national_level)) {
            binding.etNational.requestFocus();
            showSnackBar(getString(R.string.enter_nat_level));
            returnFlag = false;
        } else if (TextUtils.isEmpty(play_avail)) {
            ScrollToView(binding.rgPlayGrAvail);
            showSnackBar(getString(R.string.sel_play_avail));
            returnFlag = false;
        } else if (play_avail.equals(AppConstants.Yes) && TextUtils.isEmpty(land_measure)) {
            binding.measLandAvail.requestFocus();
            showSnackBar(getString(R.string.ent_mea_land));
            returnFlag = false;
        } else if (play_avail.equals(AppConstants.No) && TextUtils.isEmpty(plan_play)) {
            ScrollToView(binding.rgPlanToProc);
            showSnackBar(getString(R.string.sel_plan_pro));
            returnFlag = false;
        } else if (TextUtils.isEmpty(ground_level_status)) {
            ScrollToView(binding.groundLeivingRg);
            showSnackBar(getString(R.string.sel_gro_lel_status));
            returnFlag = false;
        } else if (TextUtils.isEmpty(scouts_guides_status)) {
            ScrollToView(binding.rgScoutsImpl);
            showSnackBar(getString(R.string.sco_gou_impl));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(scout_guide_name)) {
            binding.scoutCapName.requestFocus();
            showSnackBar(getString(R.string.sc_cap_name));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(scut_guide_num)) {
            binding.scoutsMobNo.requestFocus();
            showSnackBar(getString(R.string.sc_cap_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && scut_guide_num.length() != 10) {
            binding.scoutsMobNo.requestFocus();
            showSnackBar(getString(R.string.sc_valid_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(scouts_guides_status) && scouts_guides_status.equals(AppConstants.Yes)
                && !(scut_guide_num.startsWith("6")
                || scut_guide_num.startsWith("7") || scut_guide_num.startsWith("8") || scut_guide_num.startsWith("9"))) {
            binding.scoutsMobNo.requestFocus();
            showSnackBar(getString(R.string.sc_valid_ctc));
            returnFlag = false;
        } else if (TextUtils.isEmpty(ncc_stu_impl)) {
            ScrollToView(binding.rgNccStudImpl);
            showSnackBar(getString(R.string.ncc_impl));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && TextUtils.isEmpty(ncc_teacher_name)) {
            binding.nccTeachName.requestFocus();
            showSnackBar(getString(R.string.ncc_tea_name));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && TextUtils.isEmpty(ncc_teacher_contact)) {
            binding.nccTeachMobNo.requestFocus();
            showSnackBar(getString(R.string.ncc_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && ncc_teacher_contact.length() != 10) {
            binding.nccTeachMobNo.requestFocus();
            showSnackBar(getString(R.string.ncc_al_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && !(ncc_teacher_contact.startsWith("6")
                || ncc_teacher_contact.startsWith("7") || ncc_teacher_contact.startsWith("8") || ncc_teacher_contact.startsWith("9"))) {
            binding.nccTeachMobNo.requestFocus();
            showSnackBar(getString(R.string.ncc_al_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(ncc_stu_impl) && ncc_stu_impl.equals(AppConstants.Yes)
                && TextUtils.isEmpty(ncc_teacher_battalion_num)) {
            binding.nccBatallionNo.requestFocus();
            showSnackBar(getString(R.string.ncc_bat_num));
            returnFlag = false;
        } else if (TextUtils.isEmpty(smc_election_status)) {
            ScrollToView(binding.rgSmcElections);
            showSnackBar(getString(R.string.smc_ele_sta));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(smc_chai_name)) {
            binding.smcChiName.requestFocus();
            showSnackBar(getString(R.string.smc_cha_name));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(smc_chai_contact)) {
            binding.smcChiCtc.requestFocus();
            showSnackBar(getString(R.string.smc_cha_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && smc_chai_contact.length() != 10) {
            binding.smcChiCtc.requestFocus();
            showSnackBar(getString(R.string.smc_cha_valid_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && !(smc_chai_contact.startsWith("6")
                || smc_chai_contact.startsWith("7") || smc_chai_contact.startsWith("8") || smc_chai_contact.startsWith("9"))) {
            binding.smcChiCtc.requestFocus();
            showSnackBar(getString(R.string.smc_cha_valid_ctc));
            returnFlag = false;
        }/* else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(smc_resolution)) {
            binding.smcResolution.requestFocus();
            showSnackBar(getString(R.string.smc_res));
            returnFlag = false;
        }*/ else if (!TextUtils.isEmpty(smc_election_status) && smc_election_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(smc_parents_meeting)) {
            ScrollToView(binding.rgParentMeeting);
            showSnackBar(getString(R.string.pare_meeting));
            returnFlag = false;
        } else if (TextUtils.isEmpty(kitchen_garden_status)) {
            showSnackBar(getString(R.string.kitchen_garden_status));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(type_kitchen_garden)) {
            binding.typeKitchen.requestFocus();
            showSnackBar(getString(R.string.event_kit));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(kitchen_in_charge_name)) {
            binding.kitchenInchargeName.requestFocus();
            showSnackBar(getString(R.string.kit_inc_name));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(kitchen_in_charge_contact)) {
            binding.kitchenInchargeCtc.requestFocus();
            showSnackBar(getString(R.string.kit_inc_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && kitchen_in_charge_contact.length() != 10) {
            binding.kitchenInchargeCtc.requestFocus();
            showSnackBar(getString(R.string.kit_valid_ctc));
            returnFlag = false;
        } else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && !(kitchen_in_charge_contact.startsWith("6")
                || kitchen_in_charge_contact.startsWith("7") || kitchen_in_charge_contact.startsWith("8") || kitchen_in_charge_contact.startsWith("9"))) {
            binding.kitchenInchargeCtc.requestFocus();
            showSnackBar(getString(R.string.kit_valid_ctc));
            returnFlag = false;
        }/* else if (!TextUtils.isEmpty(kitchen_garden_status) && kitchen_garden_status.equals(AppConstants.Yes)
                && TextUtils.isEmpty(plants_count)) {
            binding.etPlantsCnt.requestFocus();
            showSnackBar(getString(R.string.ent_num_plant));
            returnFlag = false;
        }*/ else if (TextUtils.isEmpty(student_council_ele_status)) {
            ScrollToView(binding.rgStudCounElect);
            showSnackBar(getString(R.string.sel_stu_ele));
            returnFlag = false;
        } else if (student_council_ele_status.equals(AppConstants.Yes) && TextUtils.isEmpty(stu_com_name_dis_status)) {
            ScrollToView(binding.rgNameOfCommDisp);
            showSnackBar(getString(R.string.sel_stu_name));
            returnFlag = false;
        } else if (student_council_ele_status.equals(AppConstants.No) && TextUtils.isEmpty(stu_com_name_dis_status_reason)) {
            binding.etReason.requestFocus();
            showSnackBar(getString(R.string.ent_reason));
            returnFlag = false;
        } else if (TextUtils.isEmpty(stu_Cou_date)) {
            ScrollToView(binding.etStuCouncilDate);
            showSnackBar(getString(R.string.ent_stu_ele_date));
            returnFlag = false;
        }

        return returnFlag;
    }

    private void showStudBottomSheetSnackBar(String str) {
        Snackbar.make(bottomLl, str, Snackbar.LENGTH_SHORT).show();
    }

    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(rootLayout, str, Snackbar.LENGTH_SHORT).show();
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
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
                        binding.etStuCouncilDate.setText(stu_Cou_date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    @Override
    public void submitData() {
        long x = cocurricularViewModel.insertCoCurricularInfo(coCurricularEntity);
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("CoCurricular");
                liveData.observe(CoCurricularActivity.this, new Observer<Integer>() {
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
                Utils.customSectionSaveAlert(CoCurricularActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
            } else {
                showSnackBar(getString(R.string.failed));
            }
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }

    @Override
    public void onBackPressed() {
        if (((plantsEntities != null && plantsEntities.size() > 0) || (studAchievementEntities != null && studAchievementEntities.size() > 0)) && !(localFlag == 1)) {
            customExitAlert(CoCurricularActivity.this, getString(R.string.app_name), getString(R.string.data_lost));
        } else {
            super.callBack();
        }
    }

    private void customExitAlert(Activity activity, String title, String msg) {
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
                        finish();
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
}
