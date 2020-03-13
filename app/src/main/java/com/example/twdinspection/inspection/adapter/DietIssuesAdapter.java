package com.example.twdinspection.inspection.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterDietIssuesBinding;
import com.example.twdinspection.inspection.source.diet_issues.DietListEntity;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;

import java.util.ArrayList;
import java.util.List;

public class DietIssuesAdapter extends RecyclerView.Adapter<DietIssuesAdapter.ItemHolder> implements Filterable {

    Context context;
    List<DietListEntity> list;
    List<DietListEntity> filterList;
    private int selectedPos = -1;

    public DietIssuesAdapter(Context context, List<DietListEntity> list) {
        this.context = context;
        this.list = list;
        filterList=new ArrayList<>();
        filterList.addAll(list);}

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
        final DietListEntity dataModel = filterList.get(position);
        holder.listItemBinding.setDietIssues(dataModel);

        holder.listItemBinding.bookBal.setText(String.valueOf(filterList.get(i).getBook_bal()));
        holder.listItemBinding.groundBal.setText(String.valueOf(filterList.get(i).getGround_bal()));

        holder.listItemBinding.cbSel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                filterList.get(i).setFlag_selected(b);
            }
        });
        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return filterList != null && filterList.size() > 0 ? filterList.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                filterList.clear();
                if (charString.isEmpty()) {
                    filterList.addAll(list);
                } else {
                    for (DietListEntity otData : list) {
                        if (otData.getItem_name().toLowerCase().contains(charString.toLowerCase())) {
                            filterList.add(otData);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<DietListEntity>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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

