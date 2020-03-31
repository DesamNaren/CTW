package com.cgg.twdinspection.inspection.reports.adapter;


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

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.AcademicGradeItemBinding;
import com.cgg.twdinspection.databinding.ReportAcademicGradeItemBinding;
import com.cgg.twdinspection.inspection.interfaces.AcademicGradeInterface;
import com.cgg.twdinspection.inspection.reports.source.AcademicGradeEntity;

import java.util.List;

public class ReportAcademicGradeAdapter extends RecyclerView.Adapter<ReportAcademicGradeAdapter.ItemHolder> {

    Context context;
    List<AcademicGradeEntity> academicGradeEntities;


    public ReportAcademicGradeAdapter(Context context, List<AcademicGradeEntity> academicGradeEntities) {
        this.context = context;
        this.academicGradeEntities = academicGradeEntities;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReportAcademicGradeItemBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.report_academic_grade_item, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final AcademicGradeEntity dataModel = academicGradeEntities.get(i);
        holder.listItemBinding.setAcademicGrade(dataModel);

        holder.bind(dataModel);
    }



    @Override
    public int getItemCount() {
        return academicGradeEntities != null && academicGradeEntities.size() > 0 ? academicGradeEntities.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        public ReportAcademicGradeItemBinding listItemBinding;

        public ItemHolder(ReportAcademicGradeItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(com.cgg.twdinspection.BR.academicGrade, obj);
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

