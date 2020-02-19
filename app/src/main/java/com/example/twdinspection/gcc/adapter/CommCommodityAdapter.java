package com.example.twdinspection.gcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.StockChildRowBinding;
import com.example.twdinspection.gcc.reports.source.ReportSubmitReqCommodities;

import java.util.List;

public class CommCommodityAdapter extends RecyclerView.Adapter<CommCommodityAdapter.ItemHolder> {
    private Context context;
    private List<ReportSubmitReqCommodities> commonCommodities;
    private StockChildRowBinding stockChildRowBinding;

    public CommCommodityAdapter(Context context, List<ReportSubmitReqCommodities> commonCommodities) {
        this.context = context;
        this.commonCommodities = commonCommodities;
    }

    @NonNull
    @Override
    public CommCommodityAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        stockChildRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.stock_child_row, parent, false);
        stockChildRowBinding.llPhysAvailQty.setVisibility(View.VISIBLE);
        stockChildRowBinding.llPhyEntry.setVisibility(View.GONE);
        return new CommCommodityAdapter.ItemHolder(stockChildRowBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull final CommCommodityAdapter.ItemHolder holder, final int i) {

        final ReportSubmitReqCommodities dataModel = commonCommodities.get(i);
        holder.stockChildRowBinding.tvComName.setText(dataModel.getComCode());
        if(dataModel.getUnits()!=null && !dataModel.getUnits().contains("No")) {
            holder.stockChildRowBinding.sysQty.setText(dataModel.getSystemQty() + " " + dataModel.getUnits());
        }else {
            holder.stockChildRowBinding.sysQty.setText(String.valueOf(dataModel.getSystemQty()));
        }
        if(dataModel.getUnits()!=null && !dataModel.getUnits().contains("No")) {
            holder.stockChildRowBinding.phyAvaQty.setHint("Physical Available Quantity (" + dataModel.getUnits() + ")");
        }else {
            holder.stockChildRowBinding.phyAvaQty.setHint("Physical Available Quantity");
        }
        holder.stockChildRowBinding.tvSysRate.setText(String.valueOf(dataModel.getPhysicalRate()));
        holder.stockChildRowBinding.tvSysVal.setText(String.valueOf(dataModel.getSystemValue()));
        holder.stockChildRowBinding.tvPhyRate.setText(String.valueOf(dataModel.getPhysicalRate()));
        holder.stockChildRowBinding.tvPhyAvailQnty.setText(String.valueOf(dataModel.getPhysiacalQty()));
        holder.stockChildRowBinding.tvPhyVal.setText(String.valueOf(dataModel.getPhysicalValue()));
    }


    @Override
    public int getItemCount() {
        return commonCommodities != null && commonCommodities.size() > 0 ? commonCommodities.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        StockChildRowBinding stockChildRowBinding;

        ItemHolder(StockChildRowBinding stockChildRowBinding) {
            super(stockChildRowBinding.getRoot());
            this.stockChildRowBinding = stockChildRowBinding;
        }

        public void bind(Object obj) {
            stockChildRowBinding.setVariable(BR.comViewModel, obj);
            stockChildRowBinding.executePendingBindings();
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
