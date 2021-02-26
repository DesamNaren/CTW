package com.cgg.twdinspection.gcc.reports.adapter;

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

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.AppConstants;
import com.cgg.twdinspection.databinding.AdapterGccReportBinding;
import com.cgg.twdinspection.gcc.reports.interfaces.ReportClickCallback;
import com.cgg.twdinspection.gcc.reports.source.ReportData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GCCReportAdapter extends RecyclerView.Adapter<GCCReportAdapter.ItemHolder> implements Filterable {

    private List<ReportData> list;
    private List<ReportData> filterList;
    private ReportClickCallback reportClickCallback;
    private Context context;

    public GCCReportAdapter(Context context, List<ReportData> list) {
        this.context = context;
        this.list = list;
        filterList = new ArrayList<>();
        filterList.addAll(list);
        try {
            reportClickCallback = (ReportClickCallback) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterGccReportBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_gcc_report, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final ReportData dataModel = filterList.get(i);
        holder.listItemBinding.setGccReport(dataModel);
        setAnimation(holder.itemView, i);

        if (dataModel.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_GODOWN)) {
            holder.listItemBinding.tvSuppType.setText(context.getString(R.string.dr_godown));
        }
        if (dataModel.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_DEPOT_REP)) {
            holder.listItemBinding.tvSuppType.setText(context.getString(R.string.dr_depot));
        }
        if (dataModel.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_MFP_GODOWN_REP)) {
            holder.listItemBinding.tvSuppType.setText(context.getString(R.string.mfp_godown));
            holder.listItemBinding.llSoc.setVisibility(View.GONE);
            holder.listItemBinding.vSoc.setVisibility(View.GONE);
        }
        if (dataModel.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PUNIT_REP)) {
            holder.listItemBinding.tvSuppType.setText(context.getString(R.string.p_unit));
        }
        if (dataModel.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_PETROL_REP)) {
            holder.listItemBinding.tvSuppType.setText(context.getString(R.string.petrol_pump));
        }
        if (dataModel.getSupplierType().equalsIgnoreCase(AppConstants.REPORT_LPG_REP)) {
            holder.listItemBinding.tvSuppType.setText(context.getString(R.string.lpg_godown));
        }

        holder.listItemBinding.cvGccReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportClickCallback.onItemClick(dataModel);
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
                    for (ReportData otData : list) {
                        if (otData.getDivisionName().toLowerCase().contains(charString.toLowerCase()) ||
                                otData.getSocietyName().toLowerCase().contains(charString.toLowerCase()) ||
                                otData.getGodownName().toLowerCase().contains(charString.toLowerCase())) {
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
                filterList = (ArrayList<ReportData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ItemHolder extends RecyclerView.ViewHolder {


        AdapterGccReportBinding listItemBinding;

        ItemHolder(AdapterGccReportBinding listItemBinding) {
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

    public void setData(List<ReportData> beneficiaryDetailArrayList) {
        filterList.clear();
        filterList.addAll(beneficiaryDetailArrayList);
        notifyDataSetChanged();
    }

}

