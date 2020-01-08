package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ActivityRegistersBinding;
import com.example.twdinspection.inspection.source.RegistersUptoDate.RegistersEntity;
import com.example.twdinspection.inspection.viewmodel.RegistersCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.RegistersViewModel;

public class RegistersActivity extends AppCompatActivity {

    ActivityRegistersBinding binding;
    RegistersViewModel registersViewModel;
    RegistersEntity registersEntity;
    String admReg, attBoaReg, boaSignReg, boaMovReg, dailyPurReg, cashBook, dailyMenuReg, attStaffReg, staffOrder, stockIssue, dailyMove;
    String perArticle, budWatch, payBill, parMeet, acqReg, acqDress, acqCos, taBill, treReg, teaMove, clAcc, conBill, insReg, visitBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registers);
        TextView tv_title = findViewById(R.id.header_title);
        tv_title.setText("Registers Up to date");

        registersViewModel = ViewModelProviders.of(RegistersActivity.this,
                new RegistersCustomViewModel(binding, this, getApplication())).get(RegistersViewModel.class);
        binding.setViewModel(registersViewModel);

        binding.rgAdmReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAdmReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.adm_reg_yes)
                    admReg = "YES";
                else
                    admReg = "NO";
            }
        });

        binding.rgAttBoaReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttBoaReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.att_boa_reg_yes)
                    attBoaReg = "YES";
                else
                    attBoaReg = "NO";
            }
        });

        binding.rgBoaSignReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBoaSignReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.boa_sign_reg_yes)
                    boaSignReg = "YES";
                else
                    boaSignReg = "NO";
            }
        });
        binding.rgBoaMovReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBoaMovReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.boa_mov_reg_yes)
                    boaMovReg = "YES";
                else
                    boaMovReg = "NO";
            }
        });

        binding.rgDailyPurReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDailyPurReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.daily_pur_reg_yes)
                    dailyPurReg = "YES";
                else
                    dailyPurReg = "NO";
            }
        });

        binding.rgCashBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgCashBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.cash_book_yes)
                    cashBook = "YES";
                else
                    cashBook = "NO";
            }
        });

        binding.rgDailyMenuReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDailyMenuReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.daily_menu_reg_yes)
                    dailyMenuReg = "YES";
                else
                    dailyMenuReg = "NO";
            }
        });
        binding.rgAttStaffReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAttStaffReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.att_staff_reg_yes)
                    attStaffReg = "YES";
                else
                    attStaffReg = "NO";
            }
        });
        binding.rgStaffOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStaffOrder.getCheckedRadioButtonId();
                if (selctedItem == R.id.staff_order_yes)
                    staffOrder = "YES";
                else
                    staffOrder = "NO";
            }
        });
        binding.rgStockIssue.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgStockIssue.getCheckedRadioButtonId();
                if (selctedItem == R.id.stock_issue_yes)
                    stockIssue = "YES";
                else
                    stockIssue = "NO";
            }
        });

        binding.rgPerArticle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPerArticle.getCheckedRadioButtonId();
                if (selctedItem == R.id.per_article_yes)
                    perArticle = "YES";
                else
                    perArticle = "NO";
            }
        });

        binding.rgBudWatch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgBudWatch.getCheckedRadioButtonId();
                if (selctedItem == R.id.bud_watch_yes)
                    budWatch = "YES";
                else
                    budWatch = "NO";
            }
        });
        binding.rgAcqReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAcqReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.acq_reg_yes)
                    acqReg = "YES";
                else
                    acqReg = "NO";
            }
        });
        binding.rgAcqDress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAcqDress.getCheckedRadioButtonId();
                if (selctedItem == R.id.acq_dress_yes)
                    acqDress = "YES";
                else
                    acqDress = "NO";
            }
        });
        binding.rgAcqCos.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgAcqCos.getCheckedRadioButtonId();
                if (selctedItem == R.id.acq_cos_yes)
                    acqCos = "YES";
                else
                    acqCos = "NO";
            }
        });
        binding.rgPayBill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgPayBill.getCheckedRadioButtonId();
                if (selctedItem == R.id.pay_bill_yes)
                    payBill = "YES";
                else
                    payBill = "NO";
            }
        });
        binding.rgTreReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTreReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.tre_reg_yes)
                    treReg = "YES";
                else
                    treReg = "NO";
            }
        });
        binding.rgConBill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgConBill.getCheckedRadioButtonId();
                if (selctedItem == R.id.con_bill_yes)
                    conBill = "YES";
                else
                    conBill = "NO";
            }
        });
        binding.rgTaBill.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTaBill.getCheckedRadioButtonId();
                if (selctedItem == R.id.ta_bill_yes)
                    taBill = "YES";
                else
                    taBill = "NO";
            }
        });
        binding.rgDailyMove.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgDailyMove.getCheckedRadioButtonId();
                if (selctedItem == R.id.daily_move_yes)
                    dailyMove = "YES";
                else
                    dailyMove = "NO";
            }
        });
        binding.rgTeaMove.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgTeaMove.getCheckedRadioButtonId();
                if (selctedItem == R.id.tea_move_yes)
                    teaMove = "YES";
                else
                    teaMove = "NO";
            }
        });
        binding.rgClAcc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgClAcc.getCheckedRadioButtonId();
                if (selctedItem == R.id.cl_acc_yes)
                    clAcc = "YES";
                else
                    clAcc = "NO";
            }
        });
        binding.rgParMeet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgParMeet.getCheckedRadioButtonId();
                if (selctedItem == R.id.par_meet_yes)
                    parMeet = "YES";
                else
                    parMeet = "NO";
            }
        });

        binding.rgInsReg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgInsReg.getCheckedRadioButtonId();
                if (selctedItem == R.id.ins_reg_yes)
                    insReg = "YES";
                else
                    insReg = "NO";
            }
        });
        binding.rgVisitBook.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selctedItem = binding.rgVisitBook.getCheckedRadioButtonId();
                if (selctedItem == R.id.visit_book_yes)
                    visitBook = "YES";
                else
                    visitBook = "NO";
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registersEntity = new RegistersEntity();
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

                startActivity(new Intent(RegistersActivity.this, GeneralCommentsActivity.class));
            }
        });
    }
}
