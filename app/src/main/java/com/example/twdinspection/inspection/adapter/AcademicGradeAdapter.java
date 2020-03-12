package com.example.twdinspection.inspection.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AcademicGradeItemBinding;
import com.example.twdinspection.inspection.interfaces.StudAttendInterface;
import com.example.twdinspection.inspection.source.academic_overview.AcademicGradeEntity;

import java.util.List;

public class AcademicGradeAdapter extends RecyclerView.Adapter<AcademicGradeAdapter.ItemHolder> {

    Context context;
    List<AcademicGradeEntity> list;
    private StudAttendInterface studAttendInterface;
    public AcademicGradeAdapter(Context context, List<AcademicGradeEntity> list) {
        this.context = context;
        this.list = list;
        try {
            studAttendInterface=(StudAttendInterface)context;
        }catch (Exception e){
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
        final AcademicGradeEntity dataModel = list.get(i);
        holder.listItemBinding.setAcademicGrade(dataModel);
        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list!=null && list.size()>0? list.size():0;
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

