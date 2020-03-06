package com.example.twdinspection.inspection.reports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.ItemStaffAttReportBinding;
import com.example.twdinspection.inspection.reports.interfaces.InspReportClickCallback;
import com.example.twdinspection.inspection.reports.source.StaffAttendenceInfo;

import java.util.ArrayList;
import java.util.List;

public class StaffAttReportAdapter extends RecyclerView.Adapter<StaffAttReportAdapter.ItemHolder> implements Filterable {

    private Context context;
    private List<StaffAttendenceInfo>  list;
    private List<StaffAttendenceInfo> filterList;
    private InspReportClickCallback inspreportClickCallback;

    public StaffAttReportAdapter(Context context, List<StaffAttendenceInfo>  list) {
        this.context = context;
        this.list = list;
        filterList=new ArrayList<>();
        filterList.addAll(list);
        try {
            inspreportClickCallback = (InspReportClickCallback) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int lastPosition = -1;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemStaffAttReportBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_staff_att_report, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final StaffAttendenceInfo dataModel = filterList.get(i);
        holder.listItemBinding.setStaff(dataModel);
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
                }

//                else {
//                    for (StaffAttendenceInfo staffAttendenceInfo : list) {
//                        if (staffAttendenceInfo.getBenID().toLowerCase().contains(charString.toLowerCase())) {
//                            filterList.add(otData);
//                        }
//                    }ivOndep
//                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<StaffAttendenceInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        ItemStaffAttReportBinding listItemBinding;

        ItemHolder(ItemStaffAttReportBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.listItemBinding = listItemBinding;
        }

        void bind(Object obj) {
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

    public  void setData(List<StaffAttendenceInfo> staffAttendenceInfos){
        filterList.clear();
        filterList.addAll(staffAttendenceInfos);
        notifyDataSetChanged();
    }

}

