package com.example.twdinspection.inspection.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twdinspection.R;
import com.example.twdinspection.common.application.TWDApplication;
import com.example.twdinspection.common.custom.CustomFontEditText;
import com.example.twdinspection.common.custom.CustomFontTextView;
import com.example.twdinspection.common.utils.AppConstants;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.ActivityStudentsAttendanceBinding;
import com.example.twdinspection.inspection.adapter.StudentsAttAdapter;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.interfaces.StudAttendInterface;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;
import com.example.twdinspection.inspection.viewmodel.InstMainViewModel;
import com.example.twdinspection.inspection.viewmodel.StudAttndCustomViewModel;
import com.example.twdinspection.inspection.viewmodel.StudentsAttndViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class StudentsAttendance_2 extends BaseActivity implements StudAttendInterface, SaveListener {
    ActivityStudentsAttendanceBinding binding;
    StudentsAttAdapter adapter;
    BottomSheetDialog dialog;
    StudentsAttndViewModel studentsAttndViewModel;
    List<StudAttendInfoEntity> studAttendInfoEntities;
    String IsattenMarked, count_reg, count_during_insp, variance;
    CustomFontEditText et_studMarkedPres, et_studPresInsp;
    LinearLayout ll_stud_pres;
    InstMainViewModel instMainViewModel;
    CoordinatorLayout rootLayout;
    SharedPreferences sharedPreferences;
    String instId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = putContentView(R.layout.activity_students_attendance, getResources().getString(R.string.stu_att));

        studentsAttndViewModel =
                ViewModelProviders.of(StudentsAttendance_2.this,
                        new StudAttndCustomViewModel(binding, this, getApplication())).get(StudentsAttndViewModel.class);
        instMainViewModel = new InstMainViewModel(getApplication());

        binding.setViewModel(studentsAttndViewModel);
        binding.btnLayout.btnPrevious.setVisibility(View.GONE);
        sharedPreferences = TWDApplication.get(this).getPreferences();
        instId = sharedPreferences.getString(AppConstants.INST_ID, "");
        studentsAttndViewModel.getClassInfo(TWDApplication.get(this).getPreferences().getString(AppConstants.INST_ID, "")).observe(this, new Observer<List<StudAttendInfoEntity>>() {

            @Override
            public void onChanged(List<StudAttendInfoEntity> studentsAttendanceBeans) {
                studAttendInfoEntities = studentsAttendanceBeans;
                adapter = new StudentsAttAdapter(StudentsAttendance_2.this, studAttendInfoEntities);
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(StudentsAttendance_2.this));
                binding.recyclerView.setAdapter(adapter);
            }
        });

        binding.btnLayout.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateSubmit()) {
                    Utils.customSaveAlert(StudentsAttendance_2.this, getString(R.string.app_name), getString(R.string.are_you_sure));
                }else{
                    showSnackBar("Please inspect all the classes");
                }
            }
        });
    }

    private boolean validateSubmit() {
        boolean returnFlag=true;
        for(int i=0;i<studAttendInfoEntities.size();i++){
            if(!(studAttendInfoEntities.get(i).getFlag_completed()==1))
                returnFlag=false;
        }
        return returnFlag;
    }

    private void showSnackBar(String str) {
        Snackbar.make(binding.root, str, Snackbar.LENGTH_SHORT).show();
    }

    private void showBottomSheetSnackBar(String str) {
        Snackbar.make(rootLayout, str, Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void openBottomSheet(StudAttendInfoEntity studAttendInfoEntity) {
        showContactDetails(studAttendInfoEntity);
    }

    public void showContactDetails(StudAttendInfoEntity studAttendInfoEntity) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        rootLayout=view.findViewById(R.id.root_layout);
        dialog = new BottomSheetDialog(StudentsAttendance_2.this);
        LinearLayout ll_entries = view.findViewById(R.id.ll_entries);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(ll_entries);
        bottomSheetBehavior.setPeekHeight(1500);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        ImageView ic_close = view.findViewById(R.id.ic_close);
        RadioGroup rg_IsAttndMarked_1_2 = view.findViewById(R.id.rg_IsAttndMarked_1_2);
        TextView tv_classType = view.findViewById(R.id.tv_classType);
        CustomFontTextView tv_classCount = view.findViewById(R.id.tv_classCount);
        RadioButton rb_attMark_yes = view.findViewById(R.id.rb_yes);
        RadioButton rb_attMark_yno = view.findViewById(R.id.rb_no);
        et_studPresInsp = view.findViewById(R.id.et_studPresInsp);
        et_studMarkedPres = view.findViewById(R.id.et_studMarkedPres);
        CustomFontTextView tv_variance = view.findViewById(R.id.tv_variance);
        ImageView btn_save = view.findViewById(R.id.btn_save);
        ll_stud_pres = view.findViewById(R.id.ll_stud_pres);

        if (studAttendInfoEntity.getAttendence_marked() != null) {
            if (studAttendInfoEntity.getAttendence_marked().equals(AppConstants.Yes)) {
                ll_stud_pres.setVisibility(View.VISIBLE);
                rb_attMark_yes.setChecked(true);
            }
            if (studAttendInfoEntity.getAttendence_marked().equals(AppConstants.No)) {
                ll_stud_pres.setVisibility(View.GONE);
                rb_attMark_yno.setChecked(true);
            }
        }

        tv_classCount.setText(studAttendInfoEntity.getTotal_students());
        tv_classType.setText(studAttendInfoEntity.getClass_type());
        et_studMarkedPres.setText(studAttendInfoEntity.getStudent_count_in_register());
        et_studPresInsp.setText(studAttendInfoEntity.getStudent_count_during_inspection());
        tv_variance.setText(studAttendInfoEntity.getVariance());
        IsattenMarked = studAttendInfoEntity.getAttendence_marked();


        ic_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        rg_IsAttndMarked_1_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_yes) {
                    IsattenMarked = AppConstants.Yes;
                    ll_stud_pres.setVisibility(View.VISIBLE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_no) {
                    IsattenMarked = AppConstants.No;
                    ll_stud_pres.setVisibility(View.GONE);
                    et_studMarkedPres.setText("");
                    tv_variance.setText("");
                }
            }
        });

        et_studPresInsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_studPresInsp.getVisibility() == View.VISIBLE) {
                    if (!TextUtils.isEmpty(et_studMarkedPres.getText().toString()) &&
                            !TextUtils.isEmpty(et_studPresInsp.getText().toString())) {
                        String var = String.valueOf(Integer.parseInt(et_studMarkedPres.getText().toString().trim()) -
                                Integer.parseInt(et_studPresInsp.getText().toString().trim()));
                        tv_variance.setText(var);
                    } else {
                        tv_variance.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_studMarkedPres.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (et_studMarkedPres.getVisibility() == View.VISIBLE) {
                    if (et_studMarkedPres.getVisibility() == View.VISIBLE) {
                        if (!TextUtils.isEmpty(et_studMarkedPres.getText().toString()) &&
                                !TextUtils.isEmpty(et_studPresInsp.getText().toString())) {
                            String var = String.valueOf(Integer.parseInt(et_studMarkedPres.getText().toString().trim()) -
                                    Integer.parseInt(et_studPresInsp.getText().toString().trim()));
                            tv_variance.setText(var);
                        } else {
                            tv_variance.setText("");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        dialog.show();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog.isShowing()) {

                    count_reg = et_studMarkedPres.getText().toString().trim();
                    count_during_insp = et_studPresInsp.getText().toString().trim();
                    variance = tv_variance.getText().toString().trim();

                    if (validate(studAttendInfoEntity)) {

                        studAttendInfoEntity.setAttendence_marked(IsattenMarked);
                        studAttendInfoEntity.setStudent_count_in_register(count_reg);
                        studAttendInfoEntity.setStudent_count_during_inspection(count_during_insp);
                        studAttendInfoEntity.setVariance(variance);
                        studAttendInfoEntity.setFlag_completed(1);

//                    long x=studentsAttndViewModel.updateClassInfo(IsattenMarked,count_reg,count_during_insp,variance,
//                            TWDApplication.get(StudentsAttendance_2.this).getPreferences().getString(AppConstants.InstId,"")
//                            ,studAttendInfoEntity.getClass_id());
//
                        long x = studentsAttndViewModel.updateClassInfo(studAttendInfoEntity);
                        //Toast.makeText(StudentsAttendance_2.this, "Updated " + x, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Utils.customAlert(StudentsAttendance_2.this, "Data submitted successfully", AppConstants.SUCCESS, false);
                    }


                }
            }
        });

    }

    private boolean validate(StudAttendInfoEntity studAttendInfoEntity) {
        boolean flag = true;
        if (TextUtils.isEmpty(IsattenMarked)) {
            showBottomSheetSnackBar(getResources().getString(R.string.attendance_marked));
            flag = false;
        } else if (TextUtils.isEmpty(count_reg) && ll_stud_pres.getVisibility() == View.VISIBLE) {
            showBottomSheetSnackBar(getResources().getString(R.string.count_in_register));
            flag = false;
        } else if (TextUtils.isEmpty(count_during_insp)) {
            showBottomSheetSnackBar(getResources().getString(R.string.count_dur_insp));
            flag = false;
        } else if (IsattenMarked.equals(AppConstants.Yes)) {
            if (Integer.parseInt(et_studMarkedPres.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
                showBottomSheetSnackBar(getResources().getString(R.string.stud_greater_than_roll));
                flag = false;
            }else if (Integer.parseInt(et_studPresInsp.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
                showBottomSheetSnackBar(getResources().getString(R.string.stud_greater_than_roll));
                flag = false;
            }
        }else if (Integer.parseInt(et_studPresInsp.getText().toString()) > Integer.parseInt(studAttendInfoEntity.getTotal_students())) {
            showBottomSheetSnackBar(getResources().getString(R.string.stud_greater_than_roll));
            flag = false;
        }
        return flag;
    }

    public void onHomeClick(View view){
        startActivity(new Intent(this, InstMenuMainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    public void submitData() {
//        startActivity(new Intent(StudentsAttendance_2.this, StaffAttendActivity.class));
        long z = instMainViewModel.updateSectionInfo(Utils.getCurrentDateTime(), 2,instId);
        if (z >= 0) {
            startActivity(new Intent(StudentsAttendance_2.this, StaffAttendActivity.class));
        } else {
            showSnackBar(getString(R.string.failed));
        }
    }
}
