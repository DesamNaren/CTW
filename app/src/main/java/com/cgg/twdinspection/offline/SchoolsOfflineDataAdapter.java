package com.cgg.twdinspection.offline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cgg.twdinspection.R;
import com.cgg.twdinspection.databinding.AdapterSchoolsOfflineDataBinding;
import com.cgg.twdinspection.gcc.source.offline.GccOfflineEntity;
import com.cgg.twdinspection.inspection.interfaces.SchoolOfflineInterface;
import com.cgg.twdinspection.inspection.interfaces.SchoolsOfflineSubmitInterface;
import com.cgg.twdinspection.inspection.offline.SchoolsOfflineEntity;
import com.cgg.twdinspection.offline.interfaces.GCCOfflineSubmitInterface;

import java.util.List;

public class SchoolsOfflineDataAdapter extends RecyclerView.Adapter<SchoolsOfflineDataAdapter.ItemHolder> {

    private final List<SchoolsOfflineEntity> list;
    private SchoolOfflineInterface schoolOfflineInterface;
    private SchoolsOfflineSubmitInterface schoolsOfflineSubmitInterface;

    public SchoolsOfflineDataAdapter(Context context, List<SchoolsOfflineEntity> list) {
        this.list = list;
        try {
            schoolOfflineInterface = (SchoolOfflineInterface) context;
            schoolsOfflineSubmitInterface = (SchoolsOfflineSubmitInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSchoolsOfflineDataBinding listItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adapter_schools_offline_data, parent, false);

        return new ItemHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemHolder holder, final int i) {
        final SchoolsOfflineEntity dataModel = list.get(i);
        holder.listItemBinding.setOfflineReport(dataModel);

        holder.listItemBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schoolsOfflineSubmitInterface.submitRecord(dataModel);
            }
        });

        holder.listItemBinding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schoolsOfflineSubmitInterface.deleteRecord(dataModel);
            }
        });

        holder.bind(dataModel);
    }


    @Override
    public int getItemCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    static class ItemHolder extends RecyclerView.ViewHolder {

        AdapterSchoolsOfflineDataBinding listItemBinding;

        ItemHolder(AdapterSchoolsOfflineDataBinding listItemBinding) {
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

