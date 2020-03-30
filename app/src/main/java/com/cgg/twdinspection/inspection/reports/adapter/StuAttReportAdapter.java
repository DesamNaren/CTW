package com.cgg.twdinspection.inspection.reports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.AdapterInspReportBinding;
import com.cgg.twdinspection.databinding.AdapterStuAttReoprtBinding;
import com.cgg.twdinspection.inspection.reports.interfaces.InspReportClickCallback;
import com.cgg.twdinspection.inspection.reports.source.StudentAttendenceInfo;

import java.util.ArrayList;
import java.util.List;

public class StuAttReportAdapter extends RecyclerView.Adapter<StuAttReportAdapter.ItemHolder> implements Filterable {

    private Context context;
    private List<StudentAttendenceInfo> list;
    private List<StudentAttendenceInfo> filterList;
    private InspReportClickCallback inspreportClickCallback;

    public StuAttReportAdapter(Context context, List<StudentAttendenceInfo> list) {
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
        AdapterStuAttReoprtBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_stu_att_reoprt, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final StudentAttendenceInfo dataModel = filterList.get(i);
        holder.listItemBinding.setStuData(dataModel);
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
                    for (StudentAttendenceInfo otData : list) {
//                        if (otData.getBenID().toLowerCase().contains(charString.toLowerCase())) {
//                            filterList.add(otData);
//                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<StudentAttendenceInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        AdapterStuAttReoprtBinding listItemBinding;

        ItemHolder(AdapterStuAttReoprtBinding listItemBinding) {
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

    public  void setData(List<StudentAttendenceInfo> studentAttendenceInfos){
        filterList.clear();
        filterList.addAll(studentAttendenceInfos);
        notifyDataSetChanged();
    }

}

