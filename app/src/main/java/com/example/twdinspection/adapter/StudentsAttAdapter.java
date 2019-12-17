package com.example.twdinspection.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterStudAttndBinding;
import com.example.twdinspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

public class StudentsAttAdapter extends RecyclerView.Adapter<StudentsAttAdapter.ItemHolder> {

    Context context;
    List<StudAttendInfoEntity> list;
    private int selectedPos=-1;
    public StudentsAttAdapter(Context context, List<StudAttendInfoEntity> list) {
        this.context = context;
        this.list = list;
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

        holder.listItemBinding.tvClass.setText("Class " + (position + 1));
        holder.listItemBinding.getRoot().findViewById(R.id.class_header).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPos = position;
                notifyDataSetChanged();
//                selectedPos=position;
//                holder.listItemBinding.llEntries.setVisibility(View.VISIBLE);
//                minimiseAll(selectedPos,holder);


            }
        });
        if (selectedPos == position) {

            if (holder.listItemBinding.llEntries.getVisibility() == View.VISIBLE) {

                holder.listItemBinding.llEntries.setVisibility(View.GONE);
                holder.listItemBinding.tvExpand.setBackground(context.getResources().getDrawable(R.drawable.downarrow_16));

            } else {
                Animation animSlide = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
                holder.listItemBinding.llEntries.setAnimation(animSlide);

                holder.listItemBinding.llEntries.setVisibility(View.VISIBLE);
                holder.listItemBinding.tvExpand.setBackground(context.getResources().getDrawable(R.drawable.uparrow_16));
            }
        } else {

            holder.listItemBinding.tvExpand.setBackground(context.getResources().getDrawable(R.drawable.downarrow_16));
            holder.listItemBinding.llEntries.setVisibility(View.GONE);
        }
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
}

