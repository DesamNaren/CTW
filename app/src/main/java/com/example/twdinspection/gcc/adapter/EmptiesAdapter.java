package com.example.twdinspection.gcc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.BR;
import com.example.twdinspection.R;
import com.example.twdinspection.databinding.StockChildRowBinding;
import com.example.twdinspection.gcc.source.stock.CommonCommodity;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class EmptiesAdapter extends RecyclerView.Adapter<EmptiesAdapter.ItemHolder> {
    private Context context;
    private List<CommonCommodity> commonCommodities;
    private StockChildRowBinding stockChildRowBinding;

    public EmptiesAdapter(Context context, List<CommonCommodity> commonCommodities) {
        this.context = context;
        this.commonCommodities = commonCommodities;
    }

    @NonNull
    @Override
    public EmptiesAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        stockChildRowBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.stock_child_row, parent, false);

        return new EmptiesAdapter.ItemHolder(stockChildRowBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull final EmptiesAdapter.ItemHolder holder, final int i) {

        final CommonCommodity dataModel = commonCommodities.get(i);
        holder.stockChildRowBinding.tvComName.setText(dataModel.getCommName());
        if(dataModel.getUnits()!=null && !dataModel.getUnits().contains("No")) {
            holder.stockChildRowBinding.sysQty.setText(dataModel.getQty() + " " + dataModel.getUnits());
        }else {
            holder.stockChildRowBinding.sysQty.setText(String.valueOf(dataModel.getQty()));
        }
        if(dataModel.getUnits()!=null && !dataModel.getUnits().contains("No")) {
            holder.stockChildRowBinding.phyAvaQty.setHint("Physical Available Quantity (" + dataModel.getUnits() + ")");
        }else {
            holder.stockChildRowBinding.phyAvaQty.setHint("Physical Available Quantity");
        }
        holder.stockChildRowBinding.tvSysRate.setText(String.valueOf(dataModel.getRate()));
        holder.stockChildRowBinding.tvSysVal.setText(String.valueOf(dataModel.getQty() * dataModel.getRate()));
        holder.stockChildRowBinding.tvPhyRate.setText(String.valueOf(dataModel.getRate()));
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
                            holder.stockChildRowBinding.tvPhyVal.setText(String.valueOf(Double.valueOf(str) * dataModel.getRate()));
                            dataModel.setPhyQuant(String.valueOf(Double.valueOf(str)));
                        }else{
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