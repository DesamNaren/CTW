package com.example.twdinspection.inspection.adapter;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.common.utils.Utils;
import com.example.twdinspection.databinding.AcademicGradeItemBinding;
import com.example.twdinspection.inspection.interfaces.AcademicGradeInterface;
import com.example.twdinspection.inspection.interfaces.SaveListener;
import com.example.twdinspection.inspection.interfaces.StudAttendInterface;
import com.example.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;
import com.example.twdinspection.inspection.ui.AcademicActivity;

import java.util.List;

public class AcademicGradeAdapter extends RecyclerView.Adapter<AcademicGradeAdapter.ItemHolder> {

    Context context;
    List<AcademicGradeEntity> academicGradeEntities;
    private AcademicGradeInterface academicGradeInterface;


    public AcademicGradeAdapter(Context context, List<AcademicGradeEntity> academicGradeEntities) {
        this.context = context;
        this.academicGradeEntities = academicGradeEntities;
        try {
            academicGradeInterface = (AcademicGradeInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AcademicGradeItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.academic_grade_item, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final AcademicGradeEntity dataModel = academicGradeEntities.get(i);
        holder.listItemBinding.setAcademicGrade(dataModel);

        if(i==academicGradeEntities.size()-1){
            holder.listItemBinding.btnSave.setVisibility(View.VISIBLE);
        }else {
            holder.listItemBinding.btnSave.setVisibility(View.GONE);
        }

        holder.listItemBinding.gradeACount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditCode(holder, dataModel, i);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.listItemBinding.gradeBCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditCode(holder, dataModel, i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.listItemBinding.gradeCCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditCode(holder, dataModel, i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.listItemBinding.gradeDCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditCode(holder, dataModel, i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.listItemBinding.gradeECount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditCode(holder, dataModel, i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        holder.listItemBinding.gradeApCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditCode(holder, dataModel, i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        holder.listItemBinding.gradeBpCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setEditCode(holder, dataModel, i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        holder.listItemBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag =  true;
                if(academicGradeEntities!=null && academicGradeEntities.size()>0) {
                    for (int z = 0; z < academicGradeEntities.size(); z++) {
                        if (academicGradeEntities.get(z).getFlag_completed() == -1) {
                            Toast.makeText(context, context.getResources().getString(R.string.claer_errors), Toast.LENGTH_SHORT).show();
                            flag =  false;
                            break;
                        }
                    }

                    if(flag){
                        boolean submitFlag = false;
                        for (int z = 0; z < academicGradeEntities.size(); z++) {
                            if (academicGradeEntities.get(z).getFlag_completed() == 1) {
                                submitFlag =  true;
                                break;
                            }
                        }
                        if(submitFlag){
                            customSaveAlert(context, context.getString(R.string.app_name), context.getString(R.string.are_you_sure));
                        }else {
                            customSaveAlert(context, context.getString(R.string.app_name), context.getString(R.string.not_submitted_records));
                        }

                    }
                }else {
                    Toast.makeText(context, context.getResources().getString(R.string.no_records_found), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.bind(dataModel);
    }

    private void customSaveAlert(Context activity, String title, String msg) {
        try {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (dialog.getWindow() != null && dialog.getWindow().getAttributes() != null) {
                dialog.getWindow().getAttributes().windowAnimations = R.style.exitdialog_animation1;
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.custom_alert_information);
                dialog.setCancelable(false);
                TextView dialogTitle = dialog.findViewById(R.id.dialog_title);
                dialogTitle.setText(title);
                TextView dialogMessage = dialog.findViewById(R.id.dialog_message);
                dialogMessage.setText(msg);
                Button btDialogNo = dialog.findViewById(R.id.btDialogNo);
                btDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

                Button btDialogYes = dialog.findViewById(R.id.btDialogYes);
                btDialogYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        academicGradeInterface.submitData(academicGradeEntities);
                    }
                });

                if (!dialog.isShowing())
                    dialog.show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setEditCode(ItemHolder holder, AcademicGradeEntity dataModel, int i) {
        holder.listItemBinding.tooltipLl.setVisibility(View.GONE);

        String gradeACount = holder.listItemBinding.gradeACount.getText().toString();
        String gradeBCount = holder.listItemBinding.gradeBCount.getText().toString();
        String gradeCCount = holder.listItemBinding.gradeCCount.getText().toString();
        String gradeDCount = holder.listItemBinding.gradeDCount.getText().toString();
        String gradeECount = holder.listItemBinding.gradeECount.getText().toString();
        String gradeAPCount = holder.listItemBinding.gradeApCount.getText().toString();
        String gradeBPCount = holder.listItemBinding.gradeBpCount.getText().toString();

        validations(holder, dataModel, gradeACount, gradeBCount, gradeCCount, gradeDCount, gradeECount, gradeAPCount, gradeBPCount, i);
    }

    private void callSnackBar(ItemHolder holder, AcademicGradeEntity dataModel) {
        clearDatModel(dataModel);
        holder.listItemBinding.tooltipLl.setVisibility(View.VISIBLE);
        holder.listItemBinding.onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearUI(holder);
                holder.listItemBinding.tooltipLl.setVisibility(View.GONE);
            }
        });
    }

    private void clearDatModel(AcademicGradeEntity dataModel) {
        dataModel.setFlag_completed(-1);
        dataModel.setGrade_a_stu_count(null);
        dataModel.setGrade_b_stu_count(null);
        dataModel.setGrade_c_stu_count(null);
        dataModel.setGrade_d_stu_count(null);
        dataModel.setGrade_e_stu_count(null);
        dataModel.setGrade_aplus_stu_count(null);
        dataModel.setGrade_bplus_stu_count(null);
    }

    private void clearUI(ItemHolder holder) {
        holder.listItemBinding.gradeACount.setText("");
        holder.listItemBinding.gradeBCount.setText("");
        holder.listItemBinding.gradeCCount.setText("");
        holder.listItemBinding.gradeDCount.setText("");
        holder.listItemBinding.gradeECount.setText("");
        holder.listItemBinding.gradeApCount.setText("");
        holder.listItemBinding.gradeBpCount.setText("");

    }

    private void validations(ItemHolder holder, AcademicGradeEntity dataModel, String gradeACount,
                             String gradeBCount, String gradeCCount, String gradeDCount,
                             String gradeECount, String gradeAPCount, String gradeBPCount, int i) {

        int gradeACnt=0, gradeBCnt=0,gradeCCnt=0, gradeDCnt=0,gradeECnt=0, gradeAPCnt=0,gradeBPCnt=0;
        if(!TextUtils.isEmpty(gradeACount)){
            gradeACnt = Integer.valueOf(gradeACount);
        }
        if(!TextUtils.isEmpty(gradeBCount)){
            gradeBCnt = Integer.valueOf(gradeBCount);
        }
        if(!TextUtils.isEmpty(gradeCCount)){
            gradeCCnt = Integer.valueOf(gradeCCount);
        }
        if(!TextUtils.isEmpty(gradeDCount)){
            gradeDCnt = Integer.valueOf(gradeDCount);
        }
        if(!TextUtils.isEmpty(gradeECount)){
            gradeECnt = Integer.valueOf(gradeECount);
        }
        if(!TextUtils.isEmpty(gradeAPCount)){
            gradeAPCnt = Integer.valueOf(gradeAPCount);
        }
        if(!TextUtils.isEmpty(gradeBPCount)){
            gradeBPCnt = Integer.valueOf(gradeBPCount);
        }
        if ((gradeACnt+gradeBCnt+gradeCCnt+gradeDCnt+gradeECnt+gradeAPCnt+gradeBPCnt)> Integer.valueOf(dataModel.getTotal_students())) {
            callSnackBar(holder, dataModel);
        } else {
            dataModel.setGrade_a_stu_count(gradeACount);
            dataModel.setGrade_b_stu_count(gradeBCount);
            dataModel.setGrade_c_stu_count(gradeCCount);
            dataModel.setGrade_d_stu_count(gradeDCount);
            dataModel.setGrade_e_stu_count(gradeECount);
            dataModel.setGrade_aplus_stu_count(gradeAPCount);
            dataModel.setGrade_bplus_stu_count(gradeBPCount);
            dataModel.setFlag_completed(1);
            academicGradeEntities.set(i, dataModel);
        }
    }

    @Override
    public int getItemCount() {
        return academicGradeEntities != null && academicGradeEntities.size() > 0 ? academicGradeEntities.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public AcademicGradeItemBinding listItemBinding;

        public ItemHolder(AcademicGradeItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(com.example.twdinspection.BR.academicGrade, obj);
            listItemBinding.executePendingBindings();
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}

