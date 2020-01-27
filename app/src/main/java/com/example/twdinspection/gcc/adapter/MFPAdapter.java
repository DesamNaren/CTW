package com.example.twdinspection.gcc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.twdinspection.R;
import com.example.twdinspection.databinding.AdapterBenReportBinding;
import com.example.twdinspection.schemes.source.bendetails.BeneficiaryDetail;

import java.util.List;

public class MFPAdapter extends RecyclerView.Adapter<MFPAdapter.ItemHolder> {

    private Context context;
    private List<BeneficiaryDetail> list;

    public MFPAdapter(Context context, List<BeneficiaryDetail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBenReportBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_ben_report, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final BeneficiaryDetail dataModel = list.get(i);
        holder.listItemBinding.setBenReport(dataModel);


        holder.bind(dataModel);
    }

    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        AdapterBenReportBinding listItemBinding;

        ItemHolder(AdapterBenReportBinding listItemBinding) {
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
}

