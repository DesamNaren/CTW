package com.cgg.twdinspection.offline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.AdapterGccOfflineDataBinding;
import com.cgg.twdinspection.gcc.interfaces.GCCSubmitInterface;
import com.cgg.twdinspection.gcc.reports.interfaces.ReportClickCallback;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.offline.interfaces.GCCOfflineSubmitInterface;

import java.util.List;

public class GCCOfflineDataAdapter extends RecyclerView.Adapter<GCCOfflineDataAdapter.ItemHolder> {

    private Context context;
    private List<GccOfflineEntity> list;
    private GCCOfflineSubmitInterface gccOfflineSubmitInterface;
    private GCCSubmitInterface gccSubmitInterface;

    public GCCOfflineDataAdapter(Context context, List<GccOfflineEntity> list) {
        this.context = context;
        this.list = list;
        try {
            gccOfflineSubmitInterface = (GCCOfflineSubmitInterface) context;
            gccSubmitInterface = (GCCSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterGccOfflineDataBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_gcc_offline_data, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final GccOfflineEntity dataModel = list.get(i);
        holder.listItemBinding.setGccReport(dataModel);

        holder.listItemBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gccOfflineSubmitInterface.submitRecord(dataModel);
            }
        });

        holder.listItemBinding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gccOfflineSubmitInterface.deleteRecord(dataModel);
            }
        });

        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        AdapterGccOfflineDataBinding listItemBinding;

        ItemHolder(AdapterGccOfflineDataBinding listItemBinding) {
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

