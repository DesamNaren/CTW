package com.cgg.twdinspection.inspection.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.application.TWDApplication;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.ActivityRegistersBinding;
import com.cgg.twdinspection.inspection.interfaces.SaveListener;
import com.cgg.twdinspection.inspection.source.registers_upto_date.RegistersEntity;
import com.cgg.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.cgg.twdinspection.inspection.viewmodel.RegistersCustomViewModel;
import com.cgg.twdinspection.inspection.viewmodel.RegistersViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegistersActivity extends BaseActivity implements SaveListener {

    ActivityRegistersBinding binding;
    RegistersViewModel registersViewModel;
    RegistersEntity registersEntity;
    String admReg, attBoaReg, boaMovReg, dailyPurReg, cashBook, dailyMenuReg, attStaffReg, staffOrder, stockIssue, dailyMove;
    String perArticle, budWatch, payBill, parMeet, acqReg, acqDress, acqCos, treReg, teaMove, clAcc, conBill, insReg, visitBook;
    String instId, officerId;
    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;
    private int localFlag = -1;

    private void ScrollToView(View view) {
        view.getParent().requestChildFocus(view,view);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_registers, getResources().getString(R.string.title_registers));

        TextView[] ids = new TextView[]{binding.slno1};
        BaseActivity.setIds(ids, 84);

        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        instMainViewModel = new InstMainViewModel(getApplication());

        registersViewModel = ViewModelProviders.of(RegistersActivity.this,
                new RegistersCustomViewModel(binding, this, getApplication())).get(RegistersViewModel.class);
        binding.setViewModel(registersViewModel);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        officerId = sharedPreferences.getString(AppConstants.OFFICER_ID, "");
        binding.rgAdmReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAdmReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.adm_reg_yes)
                    admReg = AppConstants.Yes;
                else
                    admReg = AppConstants.No;
            }
        });

        binding.rgAttBoaReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttBoaReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.att_boa_reg_yes)
                    attBoaReg = AppConstants.Yes;
                else
                    attBoaReg = AppConstants.No;
            }
        });

        binding.rgBoaMovReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBoaMovReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.boa_mov_reg_yes)
                    boaMovReg = AppConstants.Yes;
                else
                    boaMovReg = AppConstants.No;
            }
        });

        binding.rgDailyPurReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDailyPurReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.daily_pur_reg_yes)
                    dailyPurReg = AppConstants.Yes;
                else
                    dailyPurReg = AppConstants.No;
            }
        });

        binding.rgCashBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCashBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.cash_book_yes)
                    cashBook = AppConstants.Yes;
                else
                    cashBook = AppConstants.No;
            }
        });

        binding.rgDailyMenuReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDailyMenuReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.daily_menu_reg_yes)
                    dailyMenuReg = AppConstants.Yes;
                else
                    dailyMenuReg = AppConstants.No;
            }
        });
        binding.rgAttStaffReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttStaffReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.att_staff_reg_yes)
                    attStaffReg = AppConstants.Yes;
                else
                    attStaffReg = AppConstants.No;
            }
        });
        binding.rgStaffOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffOrder.getCheckedRadioButtonId();
                if (selctedItem == R.id.staff_order_yes)
                    staffOrder = AppConstants.Yes;
                else
                    staffOrder = AppConstants.No;
            }
        });
        binding.rgStockIssue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStockIssue.getCheckedRadioButtonId();
                if (selctedItem == R.id.stock_issue_yes)
                    stockIssue = AppConstants.Yes;
                else
                    stockIssue = AppConstants.No;
            }
        });

        binding.rgPerArticle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPerArticle.getCheckedRadioButtonId();
                if (selctedItem == R.id.per_article_yes)
                    perArticle = AppConstants.Yes;
                else
                    perArticle = AppConstants.No;
            }
        });

        binding.rgBudWatch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBudWatch.getCheckedRadioButtonId();
                if (selctedItem == R.id.bud_watch_yes)
                    budWatch = AppConstants.Yes;
                else
                    budWatch = AppConstants.No;
            }
        });
        binding.rgAcqReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAcqReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.acq_reg_yes)
                    acqReg = AppConstants.Yes;
                else
                    acqReg = AppConstants.No;
            }
        });
        binding.rgAcqDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAcqDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.acq_dress_yes)
                    acqDress = AppConstants.Yes;
                else
                    acqDress = AppConstants.No;
            }
        });
        binding.rgAcqCos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAcqCos.getCheckedRadioButtonId();
                if (selctedItem == R.id.acq_cos_yes)
                    acqCos = AppConstants.Yes;
                else
                    acqCos = AppConstants.No;
            }
        });
        binding.rgPayBill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPayBill.getCheckedRadioButtonId();
                if (selctedItem == R.id.pay_bill_yes)
                    payBill = AppConstants.Yes;
                else
                    payBill = AppConstants.No;
            }
        });
        binding.rgTreReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTreReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.tre_reg_yes)
                    treReg = AppConstants.Yes;
                else
                    treReg = AppConstants.No;
            }
        });
        binding.rgConBill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgConBill.getCheckedRadioButtonId();
                if (selctedItem == R.id.con_bill_yes)
                    conBill = AppConstants.Yes;
                else
                    conBill = AppConstants.No;
            }
        });

        binding.rgDailyMove.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDailyMove.getCheckedRadioButtonId();
                if (selctedItem == R.id.daily_move_yes)
                    dailyMove = AppConstants.Yes;
                else
                    dailyMove = AppConstants.No;
            }
        });
        binding.rgTeaMove.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTeaMove.getCheckedRadioButtonId();
                if (selctedItem == R.id.tea_move_yes)
                    teaMove = AppConstants.Yes;
                else
                    teaMove = AppConstants.No;
            }
        });
        binding.rgClAcc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgClAcc.getCheckedRadioButtonId();
                if (selctedItem == R.id.cl_acc_yes)
                    clAcc = AppConstants.Yes;
                else
                    clAcc = AppConstants.No;
            }
        });
        binding.rgParMeet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgParMeet.getCheckedRadioButtonId();
                if (selctedItem == R.id.par_meet_yes)
                    parMeet = AppConstants.Yes;
                else
                    parMeet = AppConstants.No;
            }
        });

        binding.rgInsReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgInsReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.ins_reg_yes)
                    insReg = AppConstants.Yes;
                else
                    insReg = AppConstants.No;
            }
        });
        binding.rgVisitBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgVisitBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.visit_book_yes)
                    visitBook = AppConstants.Yes;
                else
                    visitBook = AppConstants.No;
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    Utils.customSaveAlert(RegistersActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                }

            }
        });

        try {
            localFlag = getIntent().getIntExtra(AppConstants.LOCAL_FLAG, -1);
            if (localFlag == 1) {
                //get local record & set to data binding
                LiveData<RegistersEntity> registersEntityLiveData = instMainViewModel.getRegistersInfoData();
                registersEntityLiveData.observe(RegistersActivity.this, new Observer<RegistersEntity>() {
                    @Override
                    public void onChanged(RegistersEntity generalInfoEntity) {
                        registersEntityLiveData.removeObservers(RegistersActivity.this);
                        if (generalInfoEntity != null) {
                            binding.setInspData(generalInfoEntity);
                            binding.executePendingBindings();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean validate() {
        boolean returnFlag = true;
        if (TextUtils.isEmpty(admReg)) {
            ScrollToView(binding.rgAdmReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_adm));
        } else if (TextUtils.isEmpty(attBoaReg)) {
            ScrollToView(binding.rgAttBoaReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_att_boarders));
        } else if (TextUtils.isEmpty(boaMovReg)) {
            ScrollToView(binding.rgBoaMovReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_boarders));
        } else if (TextUtils.isEmpty(dailyPurReg)) {
            ScrollToView(binding.rgDailyPurReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_daily_pur));
        } else if (TextUtils.isEmpty(cashBook)) {
            ScrollToView(binding.rgCashBook);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_cash_book));
        } else if (TextUtils.isEmpty(dailyMenuReg)) {
            ScrollToView(binding.rgDailyMenuReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_daily_menu));
        } else if (TextUtils.isEmpty(attStaffReg)) {
            ScrollToView(binding.rgAttStaffReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_stt_att));
        } else if (TextUtils.isEmpty(staffOrder)) {
            ScrollToView(binding.rgStaffOrder);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_staff));
        } else if (TextUtils.isEmpty(stockIssue)) {
            ScrollToView(binding.rgStockIssue);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_stock));
        } else if (TextUtils.isEmpty(perArticle)) {
            ScrollToView(binding.rgPerArticle);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_per_art));
        } else if (TextUtils.isEmpty(budWatch)) {
            ScrollToView(binding.rgBudWatch);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_budget));
        } else if (TextUtils.isEmpty(acqReg)) {
            ScrollToView(binding.rgAcqReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_acq));
        } else if (TextUtils.isEmpty(acqDress)) {
            ScrollToView(binding.rgAcqDress);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_dress));
        } else if (TextUtils.isEmpty(acqCos)) {
            ScrollToView(binding.rgAcqCos);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_cos));
        } else if (TextUtils.isEmpty(payBill)) {
            ScrollToView(binding.rgPayBill);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_pay_bill));
        } else if (TextUtils.isEmpty(treReg)) {
            ScrollToView(binding.rgTreReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_tre_bill));
        } else if (TextUtils.isEmpty(conBill)) {
            ScrollToView(binding.rgConBill);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_contigent));
        } else if (TextUtils.isEmpty(dailyMove)) {
            ScrollToView(binding.rgDailyMove);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_daily_movement));
        } else if (TextUtils.isEmpty(teaMove)) {
            ScrollToView(binding.rgTeaMove);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_tea_movement));
        } else if (TextUtils.isEmpty(clAcc)) {
            ScrollToView(binding.rgClAcc);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_cl_acc));
        } else if (TextUtils.isEmpty(parMeet)) {
            ScrollToView(binding.rgParMeet);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_par_meet));
        } else if (TextUtils.isEmpty(insReg)) {
            ScrollToView(binding.rgInsReg);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_ins));
        } else if (TextUtils.isEmpty(visitBook)) {
            ScrollToView(binding.rgVisitBook);
            returnFlag = false;
            showSnackBar(getString(R.string.reg_visit_book));
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.cl, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void submitData() {
        registersEntity = new RegistersEntity();
        registersEntity.setInspection_time(Utils.getCurrentDateTime());
        registersEntity.setInstitute_id(instId);
        registersEntity.setOfficer_id(officerId);
        registersEntity.setAdmission_reg(admReg);
        registersEntity.setAttendance_reg(attBoaReg);
        registersEntity.setBoarder_movement_reg(boaMovReg);
        registersEntity.setDaily_purchase_reg(dailyPurReg);
        registersEntity.setCash_book_reg(cashBook);
        registersEntity.setDaily_menu_reg(dailyMenuReg);
        registersEntity.setAttend_staff_reg(attStaffReg);
        registersEntity.setStaff_order_book(staffOrder);
        registersEntity.setStock_issue_prov(stockIssue);
        registersEntity.setPermanent_article(perArticle);
        registersEntity.setBudget_watch(budWatch);
        registersEntity.setAcquaintance_reg(acqReg);
        registersEntity.setAcquaintance_dress(acqDress);
        registersEntity.setAcquiantance_cosmetic(acqCos);
        registersEntity.setPaybill_reg(payBill);
        registersEntity.setTreasury_bill(treReg);
        registersEntity.setContingent_bill(conBill);
        registersEntity.setDaily_movement(dailyMove);
        registersEntity.setTeacher_movement(teaMove);
        registersEntity.setCL_account(clAcc);
        registersEntity.setParents_meeting(parMeet);
        registersEntity.setInspection_reg(insReg);
        registersEntity.setVisit_book(visitBook);

        long x = registersViewModel.insertRegistersInfo(registersEntity);
//                Toast.makeText(RegistersActivity.this, "Inserted " + x, Toast.LENGTH_SHORT).show();
        if (x >= 0) {
            final long[] z = {0};
            try {
                LiveData<Integer> liveData = instMainViewModel.getSectionId("Registers");
                liveData.observe(RegistersActivity.this, new Observer<Integer>() {
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
                Utils.customSectionSaveAlert(RegistersActivity.this, getString(R.string.data_saved), getString(R.string.app_name));
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
