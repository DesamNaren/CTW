package com.example.twdinspection.inspection.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterCocurricularStudBinding;
import com.example.twdinspection.databinding.AdapterStudAttndBinding;
import com.example.twdinspection.inspection.interfaces.StudAttendInterface;
import com.example.twdinspection.inspection.source.cocurriularActivities.CocurricularStudListEntity;
import com.example.twdinspection.inspection.source.studentAttendenceInfo.StudAttendInfoEntity;

import java.util.List;

public class CocurricularStudAdapter extends RecyclerView.Adapter<CocurricularStudAdapter.ItemHolder> {

    Context context;
    List<CocurricularStudListEntity> list;
    private int selectedPos=-1;
    public CocurricularStudAdapter(Context context, List<CocurricularStudListEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        AdapterCocurricularStudBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_cocurricular_stud, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final int position = i;
        final CocurricularStudListEntity dataModel = list.get(position);
        holder.listItemBinding.setCocurricularStud(dataModel);


        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list!=null && list.size()>0? list.size():0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        public AdapterCocurricularStudBinding listItemBinding;

        public ItemHolder(AdapterCocurricularStudBinding listItemBinding) {
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

