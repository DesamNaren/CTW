package com.example.twdinspection.inspection.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterStudAttndBinding;
import com.example.twdinspection.inspection.interfaces.StudAttendInterface;
import com.example.twdinspection.inspection.source.inst_master.MasterClassInfo;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;
import java.util.Random;

public class StudentsAttAdapter extends RecyclerView.Adapter<StudentsAttAdapter.ItemHolder> {

    Context context;
    List<StudAttendInfoEntity> list;
    private int selectedPos=-1;
    StudAttendInterface studAttendInterface;
    public StudentsAttAdapter(Context context, List<StudAttendInfoEntity> list) {
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
//        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        AdapterStudAttndBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_stud_attnd, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final int position = i;
        final StudAttendInfoEntity dataModel = list.get(position);
        holder.listItemBinding.setStudentAttend(dataModel);

        if(dataModel.getFlag_completed()==1){
            holder.listItemBinding.tvExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.completed));
        }else{
            holder.listItemBinding.tvExpand.setImageDrawable(context.getResources().getDrawable(R.drawable.pending));
        }

        holder.listItemBinding.tvClass.setText(list.get(i).getClass_type());
        holder.listItemBinding.llClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = position;
                studAttendInterface.openBottomSheet(dataModel);
            }
        });


        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list!=null && list.size()>0? list.size():0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        public AdapterStudAttndBinding listItemBinding;

        public ItemHolder(AdapterStudAttndBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(com.example.twdinspection.BR.studentAttend, obj);
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

