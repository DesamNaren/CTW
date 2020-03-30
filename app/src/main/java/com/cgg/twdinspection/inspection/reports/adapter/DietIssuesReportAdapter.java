package com.cgg.twdinspection.inspection.reports.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.AdapterDietIssuesBinding;
import com.cgg.twdinspection.databinding.AdapterReportDietIssuesBinding;
import com.cgg.twdinspection.inspection.reports.source.DietListEntity;

import java.util.ArrayList;
import java.util.List;

public class DietIssuesReportAdapter extends RecyclerView.Adapter<DietIssuesReportAdapter.ItemHolder> {

    Context context;
    List<DietListEntity> list;
    private int selectedPos = -1;

    public DietIssuesReportAdapter(Context context, List<DietListEntity> list) {
        this.context = context;
        this.list=list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterReportDietIssuesBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_report_diet_issues, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final int position = i;
        final DietListEntity dataModel = list.get(position);
        holder.listItemBinding.setDietIssues(dataModel);

        holder.listItemBinding.bookBal.setText(String.valueOf(list.get(i).getBookBal()));
        holder.listItemBinding.groundBal.setText(String.valueOf(list.get(i).getGroundBal()));
        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }


    class ItemHolder extends RecyclerView.ViewHolder {


        public AdapterReportDietIssuesBinding listItemBinding;

        public ItemHolder(AdapterReportDietIssuesBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        public void bind(Object obj) {
            listItemBinding.setVariable(com.cgg.twdinspection.BR.dietIssues, obj);
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

