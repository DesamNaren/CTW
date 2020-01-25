package com.example.twdinspection.inspection.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterDietIssuesBinding;
import com.example.twdinspection.inspection.interfaces.DietInterface;
import com.example.twdinspection.inspection.interfaces.StudAttendInterface;
import com.example.twdinspection.inspection.source.dietIssues.DietListEntity;

import java.util.List;

public class DietIssuesAdapter extends RecyclerView.Adapter<DietIssuesAdapter.ItemHolder> {

    Context context;
    List<DietListEntity> list;
    private int selectedPos = -1;

    public DietIssuesAdapter(Context context, List<DietListEntity> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterDietIssuesBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_diet_issues, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final int position = i;
        final DietListEntity dataModel = list.get(position);
        holder.listItemBinding.setDietIssues(dataModel);

        holder.listItemBinding.header.setText(list.get(i).getItem_name());
        holder.listItemBinding.bookBal.setText(String.valueOf(list.get(i).getBook_bal()));
        holder.listItemBinding.groundBal.setText(String.valueOf(list.get(i).getGround_bal()));

        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        public AdapterDietIssuesBinding listItemBinding;

        public ItemHolder(AdapterDietIssuesBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(com.example.twdinspection.BR.dietIssues, obj);
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

