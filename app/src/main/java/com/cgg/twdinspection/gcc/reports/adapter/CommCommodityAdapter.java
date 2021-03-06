package com.cgg.twdinspection.gcc.reports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.BR;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.StockChildRowBinding;
import com.cgg.twdinspection.gcc.reports.source.ReportSubmitReqCommodities;

import java.util.List;

public class CommCommodityAdapter extends RecyclerView.Adapter<CommCommodityAdapter.ItemHolder> {
    private final List<ReportSubmitReqCommodities> commonCommodities;

    public CommCommodityAdapter(Context context, List<ReportSubmitReqCommodities> commonCommodities) {
        this.commonCommodities = commonCommodities;
    }

    @NonNull
    @Override
    public CommCommodityAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        com.cgg.twdinspection.databinding.StockChildRowBinding stockChildRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.stock_child_row, parent, false);
        stockChildRowBinding.llPhysAvailQty.setVisibility(View.VISIBLE);
        stockChildRowBinding.llPhyEntry.setVisibility(View.GONE);
        return new ItemHolder(stockChildRowBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull final CommCommodityAdapter.ItemHolder holder, final int i) {

        final ReportSubmitReqCommodities dataModel = commonCommodities.get(i);
        holder.stockChildRowBinding.tvComCode.setText(dataModel.getComCode());
        holder.stockChildRowBinding.tvComName.setText(dataModel.getComType());
        if (dataModel.getUnits() != null && !dataModel.getUnits().contains("No")) {
            holder.stockChildRowBinding.sysQty.setText(dataModel.getSystemQty() + " " + dataModel.getUnits());
        } else {
            holder.stockChildRowBinding.sysQty.setText(String.valueOf(dataModel.getSystemQty()));
        }

        holder.stockChildRowBinding.tvSysRate.setText("Rs " + dataModel.getSystemRate());
        holder.stockChildRowBinding.tvSysVal.setText(String.valueOf(dataModel.getSystemValue()));
        holder.stockChildRowBinding.tvPhyRate.setText("Rs " + dataModel.getPhysicalRate());
        holder.stockChildRowBinding.tvPhyAvailQnty.setText(String.valueOf(dataModel.getPhysiacalQty()));
        holder.stockChildRowBinding.tvPhyVal.setText(String.valueOf(dataModel.getPhysicalValue()));
    }


    @Override
    public int getItemCount() {
        return commonCommodities != null && commonCommodities.size() > 0 ? commonCommodities.size() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

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
