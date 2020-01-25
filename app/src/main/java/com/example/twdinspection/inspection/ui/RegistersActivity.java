package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityRegistersBinding;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.RegistersCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.RegistersViewModel;
import com.google.android.material.snackbar.Snackbar;

public class RegistersActivity extends BaseActivity implements SaveListener {

    ActivityRegistersBinding binding;
    RegistersViewModel registersViewModel;
    RegistersEntity registersEntity;
    String admReg, attBoaReg, boaSignReg, boaMovReg, dailyPurReg, cashBook, dailyMenuReg, attStaffReg, staffOrder, stockIssue, dailyMove;
    String perArticle, budWatch, payBill, parMeet, acqReg, acqDress, acqCos, taBill, treReg, teaMove, clAcc, conBill, insReg, visitBook;
    String instId,officerId;
    SharedPreferences sharedPreferences;
    InstMainViewModel instMainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_registers,getResources().getString(R.string.title_registers));
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        instMainViewModel = new InstMainViewModel(getApplication());

        registersViewModel = ViewModelProviders.of(RegistersActivity.this,
                new RegistersCustomViewModel(binding, this, getApplication())).get(RegistersViewModel.class);
        binding.setViewModel(registersViewModel);
        sharedPreferences= TWDApplication.get(this).getPreferences();
        instId=sharedPreferences.getString(AppConstants.INST_ID, "");
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

        binding.rgBoaSignReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBoaSignReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.boa_sign_reg_yes)
                    boaSignReg = AppConstants.Yes;
                else
                    boaSignReg = AppConstants.No;
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
        binding.rgTaBill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTaBill.getCheckedRadioButtonId();
                if (selctedItem == R.id.ta_bill_yes)
                    taBill = AppConstants.Yes;
                else
                    taBill = AppConstants.No;
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
                if(validate()){
                    Utils.customSaveAlert(RegistersActivity.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                }

            }
        });
    }

    private boolean validate() {
        boolean returnFlag=true;
        if(TextUtils.isEmpty(admReg)){
            returnFlag=false;
            showSnackBar("Check whether admission register is updated");
        } else if(TextUtils.isEmpty(attBoaReg)){
            returnFlag=false;
            showSnackBar("Check whether attendance boarders register is updated");
        } else if(TextUtils.isEmpty(boaSignReg)){
            returnFlag=false;
            showSnackBar("Check whether boarders signature register is updated");
        } else if(TextUtils.isEmpty(boaMovReg)){
            returnFlag=false;
            showSnackBar("Check whether boarders movement register is updated");
        } else if(TextUtils.isEmpty(dailyPurReg)){
            returnFlag=false;
            showSnackBar("Check whether daily purchase register is updated");
        } else if(TextUtils.isEmpty(cashBook)){
            returnFlag=false;
            showSnackBar("Check whether cashbook register is updated");
        } else if(TextUtils.isEmpty(dailyMenuReg)){
            returnFlag=false;
            showSnackBar("Check whether daily menu register is updated");
        } else if(TextUtils.isEmpty(attStaffReg)){
            returnFlag=false;
            showSnackBar("Check whether attendance staff register is updated");
        } else if(TextUtils.isEmpty(staffOrder)){
            returnFlag=false;
            showSnackBar("Check whether staff order register is updated");
        } else if(TextUtils.isEmpty(stockIssue)){
            returnFlag=false;
            showSnackBar("Check whether stock issue provision register is updated");
        } else if(TextUtils.isEmpty(perArticle)){
            returnFlag=false;
            showSnackBar("Check whether permanent article register is updated");
        } else if(TextUtils.isEmpty(budWatch)){
            returnFlag=false;
            showSnackBar("Check whether budget watch register is updated");
        } else if(TextUtils.isEmpty(acqReg)){
            returnFlag=false;
            showSnackBar("Check whether acquaintance register is updated");
        } else if(TextUtils.isEmpty(acqDress)){
            returnFlag=false;
            showSnackBar("Check whether acquaintance dresses notebooks register is updated");
        } else if(TextUtils.isEmpty(acqCos)){
            returnFlag=false;
            showSnackBar("Check whether acquaintance cosmetics register is updated");
        } else if(TextUtils.isEmpty(payBill)){
            returnFlag=false;
            showSnackBar("Check whether paybill register is updated");
        } else if(TextUtils.isEmpty(treReg)){
            returnFlag=false;
            showSnackBar("Check whether treasury bill register is updated");
        } else if(TextUtils.isEmpty(conBill)){
            returnFlag=false;
            showSnackBar("Check whether contingent bill register is updated");
        } else if(TextUtils.isEmpty(taBill)){
            returnFlag=false;
            showSnackBar("Check whether TA bill register is updated");
        } else if(TextUtils.isEmpty(dailyMove)){
            returnFlag=false;
            showSnackBar("Check whether daily movement register is updated");
        } else if(TextUtils.isEmpty(teaMove)){
            returnFlag=false;
            showSnackBar("Check whether teacher movement register is updated");
        } else if(TextUtils.isEmpty(clAcc)){
            returnFlag=false;
            showSnackBar("Check whether CL account register is updated");
        } else if(TextUtils.isEmpty(parMeet)){
            returnFlag=false;
            showSnackBar("Check whether parent meeting register is updated");
        } else if(TextUtils.isEmpty(insReg)){
            returnFlag=false;
            showSnackBar("Check whether inspection register for departmental officers updated");
        } else if(TextUtils.isEmpty(visitBook)){
            returnFlag=false;
            showSnackBar("Check whether visit book for officials & non officials updated");
        }
        return returnFlag;
    }
    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void submitData() {
        registersEntity = new RegistersEntity();
        registersEntity.setInspection_time(Utils.getCurrentDateTime());
        registersEntity.setInstitute_id(instId);
        registersEntity.setOfficer_id(officerId);
        registersEntity.setAdmission_reg(admReg);
        registersEntity.setAttendance_reg(attBoaReg);
        registersEntity.setBoarder_sign_reg(boaSignReg);
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
        registersEntity.setTA_bill(taBill);
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
                            z[0] = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), id,instId);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (z[0] >= 0) {
                Utils.customSectionSaveAlert(RegistersActivity.this,getString(R.string.data_saved),getString(R.string.app_name));
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
