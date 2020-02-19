package com.example.twdinspection.inspection.reports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterInspReportBinding;
import com.example.twdinspection.inspection.reports.interfaces.InspReportClickCallback;
import com.example.twdinspection.inspection.reports.source.InspReportData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InspectionReportAdapter extends RecyclerView.Adapter<InspectionReportAdapter.ItemHolder> implements Filterable {

    private Context context;
    private List<InspReportData> list;
    private List<InspReportData> filterList;
    private InspReportClickCallback inspreportClickCallback;

    public InspectionReportAdapter(Context context, List<InspReportData> list) {
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
        AdapterInspReportBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_insp_report, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final InspReportData dataModel = filterList.get(i);
        holder.listItemBinding.setInspReport(dataModel);
        setAnimation(holder.itemView, i);

        holder.listItemBinding.cvGccReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inspreportClickCallback.onItemClick(dataModel);
            }
        });

        holder.bind(dataModel);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;
        }
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
                    for (InspReportData otData : list) {
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
                filterList = (ArrayList<InspReportData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        AdapterInspReportBinding listItemBinding;

        ItemHolder(AdapterInspReportBinding listItemBinding) {
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

    public  void setData(List<InspReportData> beneficiaryDetailArrayList){
        filterList.clear();
        filterList.addAll(beneficiaryDetailArrayList);
        notifyDataSetChanged();
    }

}

