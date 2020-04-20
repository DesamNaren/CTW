package com.cgg.twdinspection.gcc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.BR;
import com.cgg.twdinspection.R;
import com.cgg.twdinspection.common.utils.Utils;
import com.cgg.twdinspection.databinding.StockChildRowBinding;
import com.cgg.twdinspection.gcc.source.stock.CommonCommodity;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class DailyReqSubAdapter extends RecyclerView.Adapter<DailyReqSubAdapter.ItemHolder> {
    private Context context;
    private List<CommonCommodity> commonCommodities;
    private StockChildRowBinding stockChildRowBinding;

    public DailyReqSubAdapter(Context context, List<CommonCommodity> commonCommodities) {
        this.context = context;
        this.commonCommodities = commonCommodities;
    }

    @NonNull
    @Override
    public DailyReqSubAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        stockChildRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.stock_child_row, parent, false);

        return new DailyReqSubAdapter.ItemHolder(stockChildRowBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull final DailyReqSubAdapter.ItemHolder holder, final int i) {

        final CommonCommodity dataModel = commonCommodities.get(i);
        holder.stockChildRowBinding.tvComCode.setText(dataModel.getCommCode());
        holder.stockChildRowBinding.tvComName.setText(dataModel.getCommName());

        double qty = Utils.round(dataModel.getQty(), 2);
        if (dataModel.getUnits() != null && !dataModel.getUnits().contains("No")) {
            holder.stockChildRowBinding.sysQty.setText(qty + " " + dataModel.getUnits());
        } else {
            holder.stockChildRowBinding.sysQty.setText(String.valueOf(qty));
        }

        if (dataModel.getUnits() != null && !dataModel.getUnits().contains("No")) {
            holder.stockChildRowBinding.phyAvaQty.setHint("Physical Available Quantity (" + dataModel.getUnits() + ")");
        } else {
            holder.stockChildRowBinding.phyAvaQty.setHint("Physical Available Quantity");
        }
        holder.stockChildRowBinding.tvSysRate.setText("Rs " + dataModel.getRate());

        double val = Utils.round(dataModel.getQty() * dataModel.getRate(), 2);
        holder.stockChildRowBinding.tvSysVal.setText(String.valueOf(val));

        holder.stockChildRowBinding.tvPhyRate.setText("Rs " + dataModel.getRate());
        if (!TextUtils.isEmpty(dataModel.getPhyQuant()))
            holder.stockChildRowBinding.phyAvaQty.setText(String.valueOf(dataModel.getPhyQuant()));

        RxTextView
                .textChangeEvents(holder.stockChildRowBinding.phyAvaQty)
                .debounce(300, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TextViewTextChangeEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {

                        String str = textViewTextChangeEvent.text().toString();
                        if (!TextUtils.isEmpty(str) && !str.equals(".")) {
                            if (Double.valueOf(str) <= dataModel.getQty()) {
                                holder.stockChildRowBinding.tvPhyVal.setText(String.valueOf(Double.valueOf(str) * dataModel.getRate()));
                                dataModel.setPhyQuant(String.valueOf(Double.valueOf(str)));
                            } else {
                                holder.stockChildRowBinding.phyAvaQty.setText("");
                                holder.stockChildRowBinding.tvPhyVal.setText("");
                                dataModel.setPhyQuant(null);
                                Utils.customErrorAlert(context, context.getResources().getString(R.string.app_name), context.getResources().getString(R.string.stock_exceed));
                            }
                        } else {
                            holder.stockChildRowBinding.tvPhyVal.setText("");
                            dataModel.setPhyQuant(null);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
